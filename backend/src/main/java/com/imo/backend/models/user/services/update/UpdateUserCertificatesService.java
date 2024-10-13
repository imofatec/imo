package com.imo.backend.models.user.services.update;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.certificate.Certificate;
import com.imo.backend.models.course.CourseFactory;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.strategy.get.one.GetOneByWithTokenService;
import com.imo.backend.models.user.User;
import com.imo.backend.models.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        var alreadyIssuedCertificate = checkIssuedCertificate(user, courseId);

        if (alreadyIssuedCertificate != null) {
            userRepository.updateIssuedAtCertificateById(
                    userId, alreadyIssuedCertificate.getId(), LocalDateTime.now());
            return alreadyIssuedCertificate;
        }

        var newCertificate = CourseFactory.createCertificate(user, course);
        userRepository.pushCertificateById(userId, newCertificate);
        return newCertificate;
    }

    private static Certificate checkIssuedCertificate(User user, String courseId) {
        return user.getCertificates().stream()
                .filter(certificate -> certificate.getCourseId().equals(courseId))
                .findFirst().orElse(null);
    }
}
