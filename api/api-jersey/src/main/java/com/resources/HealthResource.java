package com.resources;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.SortedMap;

import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

@Singleton
@Path("/health")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HealthResource {

    private final HealthCheckRegistry registry;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response getHealth() {
        SortedMap<String, Result> results = registry.runHealthChecks();
        boolean healthy = results.values().stream().allMatch(Result::isHealthy);
        return Response.status(healthy ? OK : SERVICE_UNAVAILABLE)
                .entity(results)
                .build();
    }

}
