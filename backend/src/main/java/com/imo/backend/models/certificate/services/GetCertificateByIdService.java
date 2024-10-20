package com.imo.backend.models.certificate.services;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.certificate.dtos.FormattedCertificate;
import com.imo.backend.models.strategy.get.one.GetOneByService;
import com.imo.backend.models.user.repositories.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class GetCertificateByIdService implements GetOneByService<FormattedCertificate> {
    private final UserRepository userRepository;

    public GetCertificateByIdService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public FormattedCertificate execute(String certificateId) {
        var users = userRepository.findAll();

        var certificates = users.stream()
                .flatMap(user -> user.getCertificates().stream())
                .toList();

        var foundCertificate = certificates.stream()
                .filter(certificate -> certificate.getId().equals(certificateId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Certificado não encontrado"));

        return FormattedCertificate.fromCertificate(foundCertificate);
    }
}
