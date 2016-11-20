package com;

import com.api.ProductService;
import com.http.HttpService;
import com.service.ServiceUtil;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CommandLineApplication {

    public static void main(String... args) {
        Injector injector = Guice.createInjector(new ApiModule());

        ServiceUtil.createServices(injector,
                Key.get(ProductService.class),
                Key.get(HttpService.class))
                .startAsync().awaitStopped();
    }
}