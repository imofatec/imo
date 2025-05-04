package com.imo.backend.models.comments.service.get;

import com.imo.backend.models.comments.Comment;
import com.imo.backend.models.comments.service.get.interfaces.GetAllCommentsService;
import com.imo.backend.models.course.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCommentsServiceImpl implements GetAllCommentsService {

  private final CourseRepository courseRepository;

  public GetAllCommentsServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public List<Comment> execute(String lessonId) {
    String courseId = courseRepository.findByLessonId(lessonId).getId();
    return courseRepository.findCommentsByLessonId(courseId,lessonId);
  }
}
