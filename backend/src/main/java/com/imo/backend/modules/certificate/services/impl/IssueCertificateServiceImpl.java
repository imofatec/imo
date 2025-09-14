package com.imo.backend.modules.certificate.services.impl;

import com.imo.backend.modules.certificate.CertificateDetails;
import com.imo.backend.modules.certificate.services.IssueCertificateService;
import com.imo.backend.modules.certificate.values_objects.CertificatePeriod;
import com.imo.backend.utils.FormatDateTime;
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
public class IssueCertificateServiceImpl implements IssueCertificateService {
  @Override
  public byte[] execute(CertificateDetails certificateDetails) {
    String htmlContent = createTemplate(certificateDetails, templateEngine);
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
      convertHtmlToPdf(htmlContent, outputStream);
      return outputStream.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("Erro gerando o certificado: " + e.getMessage(), e);
    }
  }

  private final TemplateEngine templateEngine;

  private static final PageSize PAGE_SIZE = PageSize.A4.rotate();

  public IssueCertificateServiceImpl(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  private static String createTemplate(
      CertificateDetails certificateDetails,
      TemplateEngine templateEngine
  ) {
    Context context = new Context();

    var user = certificateDetails.user();
    var course = certificateDetails.course();

    var documentTile = String.format(
        "IMO-%s-%s-%s",
        user.getName().toUpperCase(),
        course.getName().slug().toUpperCase(),
        FormatDateTime.toDate(certificateDetails.certificate().getIssuedAt())
    );

    context.setVariable("documentTitle", documentTile);
    context.setVariable("name", user.getName().toUpperCase());
    context.setVariable("courseName", course.getName().name());
    context.setVariable("certificateId", certificateDetails.certificate().getId());

    CertificatePeriod certificatePeriod = certificateDetails.certificate().getCertificatePeriod();
    var startedAt = FormatDateTime.toDate(certificatePeriod.courseStartedAt()).replaceAll("-", "/");

    var finishedAt = FormatDateTime
        .toDate(certificatePeriod.courseFinishedAt())
        .replaceAll("-", "/");

    var issuedAt = FormatDateTime
        .toDateTime(certificateDetails.certificate().getIssuedAt())
        .replaceAll("-", "/");

    context.setVariable("startedAt", startedAt);
    context.setVariable("finishedAt", finishedAt);
    context.setVariable("issuedAt", issuedAt);

    return templateEngine.process("certificado", context);
  }

  private void convertHtmlToPdf(String htmlContent, ByteArrayOutputStream outputStream) throws
      IOException {
    try (ByteArrayInputStream inputStream = new ByteArrayInputStream(htmlContent.getBytes(
        StandardCharsets.UTF_8))) {

      PdfWriter writer = new PdfWriter(outputStream);
      PdfDocument pdfDocument = new PdfDocument(writer);
      pdfDocument.setDefaultPageSize(PAGE_SIZE);

      ConverterProperties converterProperties = new ConverterProperties();
      converterProperties.setBaseUri(Paths
          .get("src", "main", "resources", "static")
          .toAbsolutePath()
          .toString());

      HtmlConverter.convertToPdf(inputStream, pdfDocument, converterProperties);

      pdfDocument.close();
    }
  }
}
