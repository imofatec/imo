package com.imo.backend.contexts.certification.services;

import com.imo.backend.contexts.certification.Certificate;

public interface CreateCertificateService {
  Certificate execute(String userId, String courseId);
}
