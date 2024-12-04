package com.sl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.sl.client.dto.CurrentObservation;
import com.sl.client.dto.CurrentObservationGroup;
import com.sl.client.dto.Weather;
import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@ConnectWireMock
class GreetingResourceTest {

    WireMock wireMock;

    @Test
    void testHelloEndpoint() {
        wireMock.register(get(urlEqualTo("/current?key=key&city=Paris"))
                .willReturn(ok().withHeader("content-type", "application/json")
                        .withBody(weatherbitResponse())));

        //@formatter:off
        given()
                .contentType("application/json")
                .accept("application/json")
                .queryParam("location", "Paris")
        .when()
                .get("/weather/current")
        .then()
                .log().ifError()
                .statusCode(200)
                .body("description", is("Broken clouds"))
                .body("temperature", is(24.19F))
                .body("windSpeed", is(22.212F))
                .body("humidity", is(59));
        //@formatter:on
    }

    private String weatherbitResponse() {
        return """
            {
               "data":[\s
                  {\s
                     "wind_cdir":"NE",
                     "rh":59,
                     "wind_spd":6.17,
                     "wind_cdir_full":"northeast",
                     "weather":{\s
                        "description":"Broken clouds"
                     },
                     "wind_dir":50,
                     "temp":24.19
                  }
               ],
               "count":1
            }
            """;
    }

}