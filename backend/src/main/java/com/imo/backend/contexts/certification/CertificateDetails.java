package com.imo.backend.contexts.certification;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.identity.User;

public record CertificateDetails(Certificate certificate, User user, Course course) {}
