package com.imo.backend.models.course.services.patch.interfaces;

public interface ToggleCourseActivationService {
    
    void execute(String token, String courseId, boolean isActive);
    void activate(String token, String courseId);
    void deactivate(String token, String courseId);
    void toggle(String token, String courseId);
}