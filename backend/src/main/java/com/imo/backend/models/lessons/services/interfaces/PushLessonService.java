package com.imo.backend.models.lessons.services.interfaces;

import com.imo.backend.models.lessons.dtos.CreateLessonDto;
import com.imo.backend.models.lessons.dtos.NoCommentsLesson;

import java.util.List;

public interface PushLessonService {
  List<NoCommentsLesson> execute(String courseId, List<CreateLessonDto> dto);
}
