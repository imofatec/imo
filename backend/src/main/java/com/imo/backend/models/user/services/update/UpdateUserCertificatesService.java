package com.imo.backend.models.user.services.update;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.certificate.Certificate;
import com.imo.backend.models.course.CourseFactory;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.strategy.get.one.GetOneByWithTokenService;
import com.imo.backend.models.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserCertificatesService implements GetOneByWithTokenService<Certificate> {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    public UpdateUserCertificatesService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Certificate execute(String courseId, String userId) {

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

        var certificate = CourseFactory.createCertificate(user, course);

        userRepository.pushCertificateById(userId, certificate);

        return certificate;
    }
}
