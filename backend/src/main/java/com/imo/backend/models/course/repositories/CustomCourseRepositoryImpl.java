package com.imo.backend.models.course.repositories;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.dtos.FieldsToUpdateCourse;
import com.imo.backend.models.course.dtos.FieldsToUpdateLesson;
import com.imo.backend.models.lessons.Lesson;
import com.imo.backend.models.lessons.dtos.CreateLessonDto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class CustomCourseRepositoryImpl implements CustomCourseRepository {

  private final MongoTemplate mongoTemplate;

  public CustomCourseRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Course updateCourseById(String courseId, FieldsToUpdateCourse fieldsToUpdateCourse) {
    Query query = new Query(Criteria.where("_id").is(courseId));
    Update update = new Update();

    this.setFieldsToUpdateCourse(update, fieldsToUpdateCourse);

    System.out.println(this.hasAnyFieldsToUpdate(update));

    return this.hasAnyFieldsToUpdate(update)
        ? this.mongoTemplate.findAndModify(query, update,
        FindAndModifyOptions.options().returnNew(true), Course.class)
        : null;
  }

  @Override
  public Course updateLessonById(String lessonId, FieldsToUpdateLesson fieldsToUpdateLesson) {
    Query query = new Query(Criteria.where("lessons._id").is(lessonId));
    Update update = new Update();

    this.setFieldsToUpdateLesson(update, fieldsToUpdateLesson);

    return this.hasAnyFieldsToUpdate(update)
        ? this.mongoTemplate.findAndModify(query, update,
        FindAndModifyOptions.options().returnNew(true), Course.class)
        : null;
  }

  @Override
  public Course pushLesson(String courseId, List<CreateLessonDto> lessonsDto) {
    Query query = new Query(Criteria.where("_id").is(courseId));
    Update update = new Update();

    Course course = this.mongoTemplate.findOne(query, Course.class);

    assert course != null;

    List<Lesson> lessons = IntStream
        .range(0, lessonsDto.size())
        .mapToObj(i -> {
          var item = lessonsDto.get(i);
          var lessonEntity = new Lesson();

          lessonEntity.setId(new ObjectId().toString());
          lessonEntity.setIndex(course.getTotalLessons() + i + 1);
          lessonEntity.setTitle(item.getTitle());
          lessonEntity.setDescription(item.getDescription());
          lessonEntity.setYoutubeLink(item.getYoutubeLink());

          return lessonEntity;
        })
        .toList();

    lessons.forEach(lesson -> {
      update.push("lessons", lesson);
    });
    update.set("totalLessons", course.getTotalLessons() + lessons.size());
    return this.hasAnyFieldsToUpdate(update)
        ? this.mongoTemplate.findAndModify(query, update,
        FindAndModifyOptions.options().returnNew(true), Course.class)
        : null;
  }

  private void setFieldsToUpdateCourse(Update update, FieldsToUpdateCourse fieldsToUpdateCourse) {
    var declaredFields = fieldsToUpdateCourse.getClass().getDeclaredFields();
    Arrays.stream(declaredFields).peek(field -> field.setAccessible(true))
        .forEach(field -> {
          try {
            if (field.get(fieldsToUpdateCourse) == null) return;
            update.set(field.getName(), field.get(fieldsToUpdateCourse));
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        });
  }

  private void setFieldsToUpdateLesson(Update update, FieldsToUpdateLesson fieldsToUpdateLesson) {
    var declaredFields = fieldsToUpdateLesson.getClass().getDeclaredFields();
    Arrays.stream(declaredFields).peek(field -> field.setAccessible(true))
        .forEach(field -> {
          try {
            if (field.get(fieldsToUpdateLesson) == null) return;
            update.set(String.format("lessons.$.%s", field.getName()),
                field.get(fieldsToUpdateLesson));
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        });
  }

  private boolean hasAnyFieldsToUpdate(Update update) {
    return !update.getUpdateObject().isEmpty();
  }

  @Override
  public Course updateCourseStatus(String courseId, boolean isActive) {
    Query query = new Query(Criteria.where("_id").is(courseId));

    Update update = new Update()
        .set("active", isActive)
        .currentDate("updatedAt");

    return mongoTemplate.findAndModify(query, update,
        FindAndModifyOptions.options().returnNew(true), Course.class);
  }

  @Override
  public Course deleteLessonFromCourse(String lessonId) {
    Query query = new Query(Criteria.where("lessons._id").is(lessonId));

    Update update = new Update()
        .pull("lessons", Query.query(Criteria.where("_id").is(lessonId)))
        .inc("totalLessons", -1)
        .currentDate("updatedAt");

    var updatedCourse = mongoTemplate.findAndModify(query, update,
        FindAndModifyOptions.options().returnNew(false), Course.class);

    if (updatedCourse == null) return null;

    var updatedLessons = new ArrayList<>(updatedCourse.getLessons());
    updatedLessons.removeIf(lesson -> lesson.getId().equals(lessonId));

    if (updatedLessons.isEmpty()) {
      return updatedCourse;
    }

    this.reindexUpdatedLessons(updatedLessons);

    Query reindexQuery = new Query(Criteria.where("_id").is(updatedCourse.getId()));
    Update reindexUpdate = new Update();
    reindexUpdate.set("lessons", updatedLessons).currentDate("updatedAt");

    mongoTemplate.updateFirst(reindexQuery, reindexUpdate, Course.class);

    System.out.println(updatedLessons);

    return updatedCourse;
  }

  private void reindexUpdatedLessons(List<Lesson> updatedLessons) {
    updatedLessons.sort(Comparator.comparing(Lesson::getIndex));
    IntStream.range(0, updatedLessons.size()).forEach(i -> {
      updatedLessons.get(i).setIndex(i + 1);
    });
  }
}
