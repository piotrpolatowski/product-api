package com.resources;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.jvm.BufferPoolMetricSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import lombok.SneakyThrows;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.management.ManagementFactory;
import java.util.Map;

@Singleton
@Path("/metrics")
public class MetricsResource {

    private final MetricRegistry registry;

    @com.google.inject.Inject
    public MetricsResource(MetricRegistry registry) {
        this.registry = registry;

        registerAll("gc", new GarbageCollectorMetricSet(), registry);
        registerAll("buffers", new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()), registry);
        registerAll("memory", new MemoryUsageGaugeSet(), registry);
        registerAll("threads", new ThreadStatesGaugeSet(), registry);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response getHealth() {
        return Response.ok(registry.getMetrics()).build();
    }


    private void registerAll(String prefix, MetricSet metricSet, MetricRegistry registry) {
        for (Map.Entry<String, Metric> entry : metricSet.getMetrics().entrySet()) {
            if (entry.getValue() instanceof MetricSet) {
                registerAll(prefix + "." + entry.getKey(), (MetricSet) entry.getValue(), registry);
            } else {
                registry.register(prefix + "." + entry.getKey(), entry.getValue());
            }
        }
    }
}
