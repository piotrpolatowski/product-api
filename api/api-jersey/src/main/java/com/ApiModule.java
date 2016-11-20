package com;

import com.api._ProductApiModule;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.common.PropertiesModule;
import com.http._JerseyModule;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.google.inject.name.Names.bindProperties;
@Slf4j
@RequiredArgsConstructor
public class ApiModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new PropertiesModule());
        install(new _JerseyModule());
        install(new _ProductApiModule());
        bind(MetricRegistry.class).in(Singleton.class);
        bind(HealthCheckRegistry.class).toInstance(new HealthCheckRegistry());
    }

}