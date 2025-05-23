package com.imo.backend.models.course.services.patch.interfaces;

import com.imo.backend.models.course.dtos.CourseOverview;

public interface ToggleCourseActivationService {
    
    void execute(String courseId, boolean isActive);
    void activate(String token, String courseId);
    void deactivate(String token, String courseId);
    CourseOverview toggle(String courseId);
}