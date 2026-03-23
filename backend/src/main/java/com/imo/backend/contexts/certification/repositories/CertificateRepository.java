package com.imo.backend.contexts.certification.repositories;

import com.imo.backend.contexts.certification.Certificate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.imo.backend.contexts.certification.CertificateDetails;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;

@Repository
public interface CertificateRepository
    extends MongoRepository<Certificate, String>, CustomCertificateRepository {
    
    default CertificateDetails findByIdOrThrow(String id) {
        return this.findDetailsById(id).orElseThrow(() -> new NotFoundException("Certificado não encontrado"));
    }
}

