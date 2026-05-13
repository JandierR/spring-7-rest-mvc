package guru.springframework.spring7restmvc.controller;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(BeerControllerRestAssuredTest.TestConfig.class)
@ComponentScan(basePackages = "guru.springframework.spring7restmvc")
public class BeerControllerRestAssuredTest {

    @Configuration
    public static class TestConfig{
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth ->
                    auth.anyRequest().permitAll());

            return http.build();
        }
    }

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