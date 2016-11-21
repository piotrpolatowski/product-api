package com.http;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Singleton;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class _JerseyServletModule extends JerseyServletModule {

    @Override
    protected void configureServlets() {
        bind(JacksonJsonProvider.class).toInstance(new JacksonJsonProvider());
        serve("/*").with(GuiceContainer.class);
    }

}
