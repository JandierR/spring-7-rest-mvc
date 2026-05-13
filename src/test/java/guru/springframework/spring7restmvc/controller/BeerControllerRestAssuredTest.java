package guru.springframework.spring7restmvc.controller;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerControllerRestAssuredTest {

    @LocalServerPort
    Integer localPort;

    @Test
    void testListBeers() {

        RestAssured
                .given()
                .config(RestAssuredConfig.config()
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam("http.route.default-proxy", null)))
                .baseUri("http://localhost")
                .port(localPort)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/beer")
                .then()
                .statusCode(200);
    }
}