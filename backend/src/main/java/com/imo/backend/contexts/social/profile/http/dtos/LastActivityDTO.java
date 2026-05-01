package com.imo.backend.contexts.social.profile.http.dtos;

import java.util.List;

public record LastActivityDTO(
    List<RecentCourseActivityDTO> recentCourses, LastCommentActivityDTO lastComment) {}
