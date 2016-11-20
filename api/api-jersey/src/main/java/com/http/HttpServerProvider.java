package com.http;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class HttpServerProvider implements Provider<Server> {

    @Inject
    Injector injector;

    @Inject @Named("http.port")
    Integer port;

    @Override
    public Server get() {
        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addFilter(GuiceFilter.class, "/*", null);
        servletHandler.addServlet(DefaultServlet.class, "/");
        servletHandler.addEventListener(new GuiceServletConfig(injector));


        Server server = new Server(port);
        server.setHandler(servletHandler);
        server.setStopAtShutdown(true);
        return server;
    }

    @Getter
    @RequiredArgsConstructor
    private static class GuiceServletConfig extends GuiceServletContextListener {
        private final Injector injector;
    }

}
