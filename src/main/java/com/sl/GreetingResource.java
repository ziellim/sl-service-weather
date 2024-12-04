package com.sl;

import com.sl.service.WeatherService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/weather")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {

    @Inject
    WeatherService weatherService;

    @GET
    @Path("/current")
    public RestResponse<Object> getCurrentWeather(@QueryParam("location") String location) {
        return RestResponse.ResponseBuilder.ok()
                .entity(weatherService.getCurrentWeather(location))
                .build();
    }
}
