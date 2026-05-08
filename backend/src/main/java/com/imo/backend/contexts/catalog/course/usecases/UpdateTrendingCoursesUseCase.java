package com.imo.backend.contexts.catalog.course.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateTrendingCoursesUseCase {

  private final MongoTemplate mongoTemplate;

  @Scheduled(cron = "0 0 0 * * MON")
  public void execute() {
    Aggregation aggregation =
        Aggregation.newAggregation(
            Aggregation.group("courseId").count().as("studentCount"),
            Aggregation.sort(Sort.Direction.DESC, "studentCount"),
            Aggregation.limit(30));
    List<ObjectId> topCoursesId =
        mongoTemplate
            .aggregate(aggregation, "progress", AggregationResult.class)
            .getMappedResults()
            .stream()
            .map(result -> result.get_id())
            .toList();

    if (topCoursesId.isEmpty()) {
      return;
    }

    Query query = new Query(Criteria.where("_id").in(topCoursesId));
    List<Course> trendingCourses = mongoTemplate.find(query, Course.class);

    mongoTemplate.dropCollection("trending_courses");
    mongoTemplate.insert(trendingCourses, "trending_courses");
  }

  @Data
  @NoArgsConstructor
  private static class AggregationResult {
    private ObjectId _id;
  }
}
