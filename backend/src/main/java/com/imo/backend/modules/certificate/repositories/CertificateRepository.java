package com.imo.backend.modules.certificate.repositories;

import com.imo.backend.modules.certificate.Certificate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository
    extends MongoRepository<Certificate, String>, CustomCertificateRepository {
}
