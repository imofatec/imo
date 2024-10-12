package com.imo.backend.models.certificate.services;

import com.imo.backend.models.certificate.Certificate;
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
public class CreatePdfService {

    private final TemplateEngine templateEngine;

    public CreatePdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public ByteArrayInputStream execute(Certificate certificate) throws IOException {

        Context context = new Context();
        context.setVariable("certificateId", certificate.getId());
        context.setVariable("userId", certificate.getUserId());
        context.setVariable("username", certificate.getUsername());
        context.setVariable("userEmail", certificate.getUserEmail());
        context.setVariable("courseId", certificate.getCourseId());
        context.setVariable("courseName", certificate.getCourseName());
        context.setVariable("startDate", certificate.getCourseStartDate());
        context.setVariable("endDate", certificate.getCourseEndDate());
        context.setVariable("issueDate", certificate.getIssuedAt());



        String htmlContent = templateEngine.process("certificado", context);


        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8)), out, converterProperties);

        return new ByteArrayInputStream(out.toByteArray());
    }
}
