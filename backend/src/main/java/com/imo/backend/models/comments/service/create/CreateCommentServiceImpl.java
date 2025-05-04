package com.imo.backend.models.comments.service.create;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.comments.Comment;
import com.imo.backend.models.comments.dto.CommentDTO;
import com.imo.backend.models.comments.service.create.interfaces.CreateCommentService;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.user.User;
import com.imo.backend.models.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCommentServiceImpl implements CreateCommentService {

  private final CourseRepository courseRepository;

  private final UserRepository userRepository;

  private final TokenService tokenService;


  public CreateCommentServiceImpl(CourseRepository courseRepository,
                                  UserRepository userRepository,
                                  TokenService tokenService) {
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
    this.tokenService = tokenService;
  }

  @Override
  public Comment execute(String token, String lessonId,
                         CommentDTO commentDTO) {
    var user = tokenService.getSub(token);
    String userId = user.get("id");

    User foundUser =
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuario " +
            "nao encontrado"));

    Course course = courseRepository.findByLessonId(lessonId);
    var courseId = course.getId();

    Comment newComment = Comment.fromDTO(userId, foundUser.getName(),
        commentDTO.getComment());

    long updateCount = courseRepository.addCommentToLesson(courseId, lessonId, newComment);

    if (updateCount == 0) {
      throw new NotFoundException("Aula nao encontrada");
    }
    return newComment;

  }
}
