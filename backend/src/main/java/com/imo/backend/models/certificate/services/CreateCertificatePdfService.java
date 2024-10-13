package com.imo.backend.models.certificate.services;

import com.imo.backend.models.certificate.Certificate;
import com.imo.backend.models.strategy.create.CreateService;
import com.imo.backend.lib.FormatDateTime;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

@Service
public class CreateCertificatePdfService implements CreateService<Certificate, byte[]> {

    private final TemplateEngine templateEngine;

    private static final PageSize PAGE_SIZE = PageSize.A4.rotate();

    public CreateCertificatePdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public byte[] execute(Certificate certificate) {
        String htmlContent = createTemplate(certificate, templateEngine);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            convertHtmlToPdf(htmlContent, outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Erro gerando o certificado: " + e.getMessage(), e);
        }
    }

    private static String createTemplate(Certificate certificate, TemplateEngine templateEngine) {
        Context context = new Context();

        var documentTile = String.format("IMO-%s-%s-%s",
                certificate.getUsername().toUpperCase(),
                certificate.getCourseSlug().toUpperCase(),
                FormatDateTime.toDate(certificate.getIssuedAt()));

        context.setVariable("documentTitle", documentTile);
        context.setVariable("username", certificate.getUsername().toUpperCase());
        context.setVariable("courseName", certificate.getCourseName());
        context.setVariable("certificateId", certificate.getId());

        var startedAt = FormatDateTime.toDate(certificate.getCourseStartedAt())
                .replaceAll("-", "/");

        var finishedAt = FormatDateTime.toDate(certificate.getCourseFinishedAt())
                .replaceAll("-", "/");

        var issuedAt = FormatDateTime.toDateTime(certificate.getIssuedAt())
                .replaceAll("-", "/");

        context.setVariable("startedAt", startedAt);
        context.setVariable("finishedAt", finishedAt);
        context.setVariable("issuedAt", issuedAt);

        return templateEngine.process("certificado", context);
    }

    private static void convertHtmlToPdf(
            String htmlContent,
            ByteArrayOutputStream outputStream
    ) throws IOException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(
                htmlContent.getBytes(StandardCharsets.UTF_8))
        ) {

            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.setDefaultPageSize(PAGE_SIZE);

            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setBaseUri(
                    Paths.get("src", "main", "resources", "static").toAbsolutePath().toString()
            );

            HtmlConverter.convertToPdf(inputStream, pdfDocument, converterProperties);

            pdfDocument.close();
        }
    }
}
