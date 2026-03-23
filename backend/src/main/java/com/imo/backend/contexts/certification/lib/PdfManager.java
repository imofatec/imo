package com.imo.backend.contexts.certification.lib;

import com.imo.backend.contexts.certification.CertificateDetails;

public interface PdfManager {
  byte[] execute(CertificateDetails certificateDetails);
}
