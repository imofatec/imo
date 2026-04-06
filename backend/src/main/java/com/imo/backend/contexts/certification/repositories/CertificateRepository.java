package com.imo.backend.contexts.certification.repositories;

import com.imo.backend.contexts.certification.Certificate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository
    extends MongoRepository<Certificate, String>, CustomCertificateRepository {}
