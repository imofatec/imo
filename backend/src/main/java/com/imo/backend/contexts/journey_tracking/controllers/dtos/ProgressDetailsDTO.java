package com.imo.backend.contexts.journey_tracking.controllers.dtos;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.identity.http.dtos.UserDTO;
import com.imo.backend.contexts.journey_tracking.ProgressDetails;

import java.util.List;

public record ProgressDetailsDTO(
    ProgressDTO progress,
    UserDTO user,
    Course course,
    List<Lesson> lessons
) {
  public static ProgressDetailsDTO fromProgressDetails(ProgressDetails progressDetails) {
    return new ProgressDetailsDTO(
        ProgressDTO.fromProgress(progressDetails.progress()),
        UserDTO.fromUser(progressDetails.user()),
        progressDetails.course(),
        progressDetails.lessons()
    );
  }
}
