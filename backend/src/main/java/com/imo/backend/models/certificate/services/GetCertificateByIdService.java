package com.imo.backend.models.certificate.services;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.certificate.Certificate;
import com.imo.backend.models.strategy.get.one.GetOneByService;
import com.imo.backend.models.user.repositories.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class GetCertificateByIdService implements GetOneByService<Certificate> {
    private final UserRepository userRepository;

    public GetCertificateByIdService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Certificate execute(String certificateId) {
        var users = userRepository.findAll();

        var certificates = users.stream()
                .flatMap(user -> user.getCertificates().stream())
                .toList();

        return certificates.stream()
                .filter(certificate -> certificate.getId().equals(certificateId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Certificado não encontrado"));
    }
}
