package com.imo.backend.contexts.learning_path.skill_profile;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.skill.Skill;
import java.util.List;

public record CourseSkillsDetails(Course course, List<Skill> skills) {}
