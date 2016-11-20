package com.http;

import com.resources.HealthResource;
import com.resources.MetricsResource;
import com.google.inject.AbstractModule;
import org.eclipse.jetty.server.Server;

public class _JerseyModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new _JerseyServletModule());
        bind(HealthResource.class);
        bind(MetricsResource.class);
        bind(Server.class).toProvider(HttpServerProvider.class);
    }

}
