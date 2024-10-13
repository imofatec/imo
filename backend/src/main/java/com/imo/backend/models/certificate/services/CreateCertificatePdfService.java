package com.imo.backend.models.certificate.services;

import com.imo.backend.models.certificate.Certificate;
import com.imo.backend.models.strategy.create.CreateService;
import com.imo.backend.lib.FormatDateTime;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class CreateCertificatePdfService implements CreateService<Certificate, byte[]> {

    private final TemplateEngine templateEngine;

    public CreateCertificatePdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public byte[] execute(Certificate certificate) {
        String htmlContent = createTemplate(certificate, templateEngine);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                htmlContent.getBytes(StandardCharsets.UTF_8)
        );

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ConverterProperties converterProperties = new ConverterProperties();

        try {
            HtmlConverter.convertToPdf(byteArrayInputStream, out, converterProperties);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return out.toByteArray();
    }

    private static String createTemplate(Certificate certificate, TemplateEngine templateEngine) {
        Context context = new Context();

        context.setVariable("certificateId", certificate.getId());
        context.setVariable("userId", certificate.getUserId());
        context.setVariable("username", certificate.getUsername());
        context.setVariable("userEmail", certificate.getUserEmail());
        context.setVariable("courseId", certificate.getCourseId());
        context.setVariable("courseName", certificate.getCourseName());

        var startedAt = FormatDateTime.toDateTime(certificate.getCourseStartedAt())
                .replaceAll("-", "/");

        var finishedAt = FormatDateTime.toDateTime(certificate.getCourseFinishedAt())
                .replaceAll("-", "/");

        var issuedAt = FormatDateTime.toDateTime(certificate.getIssuedAt())
                .replaceAll("-", "/");

        context.setVariable("startDate", startedAt);
        context.setVariable("endDate", finishedAt);
        context.setVariable("issueDate", issuedAt);


        return templateEngine.process("certificado", context);
    }
}
