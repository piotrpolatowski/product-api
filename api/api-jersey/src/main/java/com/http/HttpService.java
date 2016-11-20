package com.http;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;

import javax.inject.Singleton;

@Singleton
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HttpService extends AbstractExecutionThreadService {

    @Inject
    private final Server server;

    @Override
    protected void startUp() throws Exception {
        server.start();
    }

    @Override
    protected void run() throws Exception {
        try {
            server.join();
        } catch (InterruptedException e) {
            // Ignore
        }
    }

    @Override
    @SneakyThrows
    protected void triggerShutdown() {
        if (server.isRunning()) {
            server.stop();
        }
    }

}
