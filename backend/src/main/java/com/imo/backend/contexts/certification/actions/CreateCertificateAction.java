package com.imo.backend.contexts.certification.actions;

import com.imo.backend.contexts.certification.Certificate;

public interface CreateCertificateAction {
  Certificate execute(String userId, String courseId);
}
