package com.imo.backend.e2e.domains.certificate;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.e2e.config.BaseE2ETest;
import com.imo.backend.e2e.helpers.E2EFlowHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.containsString;

@Slf4j
public class GetCertificateE2ETest extends BaseE2ETest {

  @Test
  @DisplayName("Deve concluir um curso e gerar um certificado")
  public void shouldFinishCourseAndGenerateCertificate() {

    String token = E2EFlowHelper.createAndAuthenticateUser();
    CourseDetailsDTO courseDetailsDTO = E2EFlowHelper.createCourseAndReturnDetails(token, 5);
    String courseId = courseDetailsDTO.course().id();

    log.info("Curso criado com ID: {} e {} aulas", courseId, courseDetailsDTO.lessons().size());

    E2EFlowHelper.markAllLessonsAsWatched(token, courseDetailsDTO);

    log.info("Todas as {} aulas foram marcadas como concluídas", courseDetailsDTO.lessons().size());

    byte[] pdfBytes = givenBaseRequest()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/certificate/issue/" + courseId)
        .then()
        .statusCode(HttpStatus.OK.value())
        .header("Content-Type", "application/pdf")
        .header("Content-Disposition", containsString(".pdf"))
        .extract()
        .asByteArray();

    assert pdfBytes.length > 0 : "O PDF do certificado não deve estar vazio";
  }
}
