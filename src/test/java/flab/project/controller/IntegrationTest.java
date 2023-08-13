package flab.project.controller;

import flab.project.config.baseresponse.ResponseEnum;
import flab.project.data.dto.model.Follows;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@flab.common.IntegrationTest
public class IntegrationTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
        }
    }

    @DisplayName("")
    @Test
    void getFollowers() {
        // given
//        given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(new Follows(1L, 2L))
//                .pathParam("userId", "1")
//                .when().log().all()
//                .post("/users/{userId}/follows")
//                .then().log().all();

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("userId", "1")
                .when().log().all()
                .get("/users/{userId}/followings")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("isSuccess", equalTo(SUCCESS.isSuccess()))
                .assertThat().body("code", equalTo(SUCCESS.getCode()))
                .assertThat().body("message", equalTo(SUCCESS.getMessage()));

        // when

        // then
    }

    @DisplayName("팔로워/팔로잉을 생성 할 수 있다.")
    @Test
    void test() {
        // given
        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new Follows(1L, 2L))
                .pathParam("userId", "1")
                .when()
                .post("/users/{userId}/follows")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("isSuccess", equalTo(SUCCESS.isSuccess()))
                .assertThat().body("code", equalTo(SUCCESS.getCode()))
                .assertThat().body("message", equalTo(SUCCESS.getMessage()));
    }
}
