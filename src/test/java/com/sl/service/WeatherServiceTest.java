package com.sl.service;

import com.sl.client.WeatherClient;
import com.sl.client.dto.CurrentObservation;
import com.sl.client.dto.CurrentObservationGroup;
import com.sl.client.dto.Weather;
import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

@QuarkusTest
class WeatherServiceTest {

    @InjectMock
    @RestClient
    WeatherClient client;

    @Inject
    WeatherService service;

    @Test
    void shouldUseTheCachedData() {
        // given
        var location = "Paris";
        given(client.getCurrentObservation(anyString(), eq(location)))
                .willReturn(currentObservationGroup());
        // when
        service.getCurrentWeather(location);
        // second call
        service.getCurrentWeather(location);
        // then
        verify(client, atMostOnce()).getCurrentObservation(anyString(), eq(location));
    }

    private static CurrentObservationGroup currentObservationGroup() {
        return new CurrentObservationGroup(List.of(
                new CurrentObservation(50, "NE", 47, new Weather("Hot!"), 12)
        ));
    }
}