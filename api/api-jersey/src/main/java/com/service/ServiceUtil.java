package com.service;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.google.inject.Injector;
import com.google.inject.Key;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ServiceUtil {

    @SafeVarargs
    public static ServiceManager createServices(Injector injector, Key<? extends Service>... services) {
        HealthCheckRegistry registry = injector.getInstance(HealthCheckRegistry.class);

        List<Service> instances = Arrays.stream(services)
                .map(key -> {
                    Service service = injector.getInstance(key);
                    registerServiceChecks(key.toString(), service, registry);
                    return service;
                })
                .collect(Collectors.toList());

        ServiceManager manager = new ServiceManager(instances);
        return addShutdownHook(manager);
    }

    private static ServiceManager addShutdownHook(ServiceManager manager) {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    manager.stopAsync().awaitStopped(5, SECONDS);
                } catch (TimeoutException timeout) {
                    // stopping timed out
                }
            }
        });
        return manager;
    }

    private static void registerServiceChecks(String key, Service service, HealthCheckRegistry registry) {
        registry.register(key, new HealthCheck() {
            @Override
            protected Result check() throws Exception {
                if (service.isRunning()) {
                    return Result.healthy();
                } else {
                    return Result.unhealthy(service.state().name(), service.failureCause());
                }
            }
        });
    }
}
