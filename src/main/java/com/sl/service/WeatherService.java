package com.sl.service;

import com.sl.client.WeatherClient;
import com.sl.resource.dto.Weather;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class WeatherService {

    @ConfigProperty(name = "quarkus.rest-client.weatherbit.key")
    String apiKey;

    @RestClient
    WeatherClient weatherClient;

    @CacheResult(cacheName = "weather-cache")
    public Weather getCurrentWeather(String location) {
        var observation = weatherClient.getCurrentObservation(apiKey, location).data().get(0);
        return new Weather(observation.weather().description(),
                observation.temp(),
                observation.wind_spd() * 3.6F,
                observation.rh()
        );
    }
}
