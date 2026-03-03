package com.imo.backend.contexts.catalog.course.actions.inputs;


import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.lesson.actions.inputs.CreateLessonInput;

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
