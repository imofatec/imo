package com.imo.backend.modules.certificate.actions;

import com.imo.backend.modules.certificate.Certificate;

public interface CreateCertificateAction {
  Certificate execute(String userId, String courseId);
}
