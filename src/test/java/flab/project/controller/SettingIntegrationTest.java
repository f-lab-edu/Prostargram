package flab.project.controller;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static flab.project.data.enums.ScreenMode.LIGHT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SettingIntegrationTest {

    private static final String UPDATE_SCREEN_MODE_URL = "/users/{userId}/settings";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
        }
    }

    @DisplayName("라이트/다크모드를 설정하면, 쿠키에 모드 이름이 담겨온다.")
    @Test
    void updateScreenMode() {
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("userId", 1)
                .queryParam("screen-mode", LIGHT)
            .when()
                .patch(UPDATE_SCREEN_MODE_URL)
            .then().log().all()
                .statusCode(200)
                .assertThat().body("isSuccess", equalTo(SUCCESS.isSuccess()))
                .assertThat().body("code", equalTo(SUCCESS.getCode()))
                .assertThat().body("message", equalTo(SUCCESS.getMessage()))
                .assertThat().cookie("screen-mode",equalTo(LIGHT.name()))
            .extract();

        Cookie cookie = response.detailedCookie("screen-mode");

        assertThat(cookie.getMaxAge()).isEqualTo(60 * 60 * 24 * 400);
    }
}
