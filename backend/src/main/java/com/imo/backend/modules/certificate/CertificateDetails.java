package com.imo.backend.modules.certificate;

import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.user.User;

public record CertificateDetails(
    Certificate certificate,
    User user,
    Course course
) {
}
