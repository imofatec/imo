package com.imo.backend.lib.pdf;

import com.imo.backend.contexts.certification.CertificateDetails;
import com.imo.backend.contexts.certification.lib.PdfManager;
import com.imo.backend.contexts.certification.values_objects.CertificatePeriod;
import com.imo.backend.contexts.common.HttpDateTimeFormatter;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class ItextPdfManager implements PdfManager {
  @Override
  public byte[] execute(CertificateDetails certificateDetails) {
    String htmlContent = createTemplate(certificateDetails, templateEngine);
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); ) {
      convertHtmlToPdf(htmlContent, outputStream);
      return outputStream.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("Erro gerando o certificado: " + e.getMessage(), e);
    }
  }

  private final TemplateEngine templateEngine;

  private static final PageSize PAGE_SIZE = PageSize.A4.rotate();

  public ItextPdfManager(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  private static String createTemplate(
      CertificateDetails certificateDetails, TemplateEngine templateEngine) {
    Context context = new Context();

    var user = certificateDetails.user();
    var course = certificateDetails.course();

    var documentTile =
        String.format(
            "IMO-%s-%s-%s",
            user.getName().toUpperCase(),
            course.getName().slug().toUpperCase(),
            HttpDateTimeFormatter.toDate(certificateDetails.certificate().getIssuedAt())
                .replace('/', '-'));

    context.setVariable("documentTitle", documentTile);
    context.setVariable("name", user.getName().toUpperCase());
    context.setVariable("courseName", course.getName().name());
    context.setVariable("certificateId", certificateDetails.certificate().getId());

    CertificatePeriod certificatePeriod = certificateDetails.certificate().getCertificatePeriod();
    var startedAt = HttpDateTimeFormatter.toDate(certificatePeriod.courseStartedAt());

    var finishedAt = HttpDateTimeFormatter.toDate(certificatePeriod.courseFinishedAt());

    var issuedAt = HttpDateTimeFormatter.toDateTime(certificateDetails.certificate().getIssuedAt());

    context.setVariable("startedAt", startedAt);
    context.setVariable("finishedAt", finishedAt);
    context.setVariable("issuedAt", issuedAt);

    return templateEngine.process("certificado", context);
  }

  private void convertHtmlToPdf(String htmlContent, ByteArrayOutputStream outputStream)
      throws IOException {
    try (ByteArrayInputStream inputStream =
        new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8))) {

      PdfWriter writer = new PdfWriter(outputStream);
      PdfDocument pdfDocument = new PdfDocument(writer);
      pdfDocument.setDefaultPageSize(PAGE_SIZE);

      ConverterProperties converterProperties = new ConverterProperties();
      converterProperties.setBaseUri(
          Paths.get("src", "main", "resources", "static").toAbsolutePath().toString());

      HtmlConverter.convertToPdf(inputStream, pdfDocument, converterProperties);

      pdfDocument.close();
    }
  }
}
