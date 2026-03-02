package com.imo.backend.modules.course.actions.inputs;


import com.imo.backend.modules.course.value_objects.Categories;
import com.imo.backend.modules.lesson.actions.inputs.CreateLessonInput;

import java.util.List;


public record CreateCourseInput(
    String name,
    Categories category,
    String level,
    String description,
    List<CreateLessonInput> lessons,
    String contributorId
) {
}
