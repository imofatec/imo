package com.imo.backend.modules.course.events;

public record UpdateCourseFirstYoutubeLinkEvent(
    String courseId,
    String newFirstYoutubeLink
) {
}
