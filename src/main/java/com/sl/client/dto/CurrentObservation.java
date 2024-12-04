package com.sl.client.dto;

public record CurrentObservation (
        double wind_spd,
        String wind_cdir,
        double temp,
        Weather weather,

        int rh
){}
