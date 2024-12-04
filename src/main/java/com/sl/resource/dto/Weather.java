package com.sl.resource.dto;

public record Weather(
        String description,
        double temperature,
        double windSpeed,
        int humidity

){}
