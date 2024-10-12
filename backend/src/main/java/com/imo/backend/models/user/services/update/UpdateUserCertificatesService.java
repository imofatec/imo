package com.imo.backend.models.user.services.update;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.certificate.Certificate;
import com.imo.backend.models.course.CourseFactory;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.user.User;
import com.imo.backend.models.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserCertificatesService {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    public UpdateUserCertificatesService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public Certificate updateUserCertificates(String id, String courseId) {

        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

        var certificate = CourseFactory.createCertificate(user, course);

        userRepository.pushCertificateById(id, certificate);


        return certificate;
    }
}
