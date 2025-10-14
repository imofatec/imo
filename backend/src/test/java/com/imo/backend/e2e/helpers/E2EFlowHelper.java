package com.imo.backend.e2e.helpers;

import com.imo.backend.e2e.factories.CourseTestFactory;
import com.imo.backend.modules.course.http.dtos.CreateCourseRequest;
import com.imo.backend.modules.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.e2e.factories.UserTestFactory;
import com.imo.backend.e2e.utils.JsonString;
import com.imo.backend.modules.user.actions.inputs.CreateUserInput;
import com.imo.backend.modules.user.http.dtos.auth.LoginRequestDTO;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import static com.imo.backend.e2e.config.BaseE2ETest.givenBaseRequest;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;


public class E2EFlowHelper {
    private static final JsonString jsonString = new JsonString();

    public static String createAndAuthenticateUser() {
        CreateUserInput user = UserTestFactory.mountValidUser();
        String jsonBody = jsonString.fromObj(user);

        givenBaseRequest()
                .body(jsonBody)
                .post("/user")
                .then()
                .statusCode(anyOf(is(HttpStatus.CREATED.value()), is(HttpStatus.OK.value())));

        LoginRequestDTO loginRequest = new LoginRequestDTO(user.email(), user.password());
        String loginJson = jsonString.fromObj(loginRequest);

        Response response = givenBaseRequest().body(loginJson).post("/user/login");
        response.then().statusCode(HttpStatus.OK.value());

        return response.jsonPath().getString("accessToken");
    }


    public static CourseDetailsDTO createCourseAndReturnDetails(String token, int lessonsCount) {
        CreateCourseRequest courseRequest = CourseTestFactory.createValidCourseWithLessons(lessonsCount);
        String jsonBody = jsonString.fromObj(courseRequest);

        Response response = givenBaseRequest()
                .header("Authorization", "Bearer " + token)
                .body(jsonBody)
                .post("/course")
                .then()
                .statusCode(anyOf(is(HttpStatus.CREATED.value()), is(HttpStatus.OK.value())))
                .extract().response();

        try {
            return response.as(CourseDetailsDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao desserializar CourseDetailsDTO: " + response.asString(), e);
        }
    }
}
