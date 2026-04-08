package com.imo.backend.contexts.journey_tracking.controllers.dtos;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.catalog.lesson.http.dtos.LessonDTO;
import com.imo.backend.contexts.identity.user.http.dtos.UserDTO;
import com.imo.backend.contexts.journey_tracking.ProgressDetails;
import java.util.List;

public record ProgressDetailsDTO(
    ProgressDTO progress, UserDTO user, CourseDTO course, List<LessonDTO> lessons) {
  public static ProgressDetailsDTO fromProgressDetails(ProgressDetails progressDetails) {
    return new ProgressDetailsDTO(
        ProgressDTO.fromProgress(progressDetails.progress()),
        UserDTO.fromUser(progressDetails.user()),
        CourseDTO.fromEntity(progressDetails.course()),
        progressDetails.lessons().stream().map(LessonDTO::fromEntity).toList());
  }
}
