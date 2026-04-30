package com.imo.backend.contexts.learning_path.recommendation.lib;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.skill.Skill;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record RecommendationInput(
    List<Course> activeCourses,
    Map<String, Skill> skillsById,
    Set<String> finishedCourseIds,
    Map<String, Integer> coverageBySkillId) {}
