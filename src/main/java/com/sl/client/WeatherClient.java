package com.sl.client;

import com.sl.client.dto.CurrentObservationGroup;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey =  "weatherbit")
public interface WeatherClient {

    @GET
    @Path("/current")
    CurrentObservationGroup getCurrentObservation(@QueryParam("key") String key, @QueryParam("city") String city);

}
