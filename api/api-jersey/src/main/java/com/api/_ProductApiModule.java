package com.api;

import com.google.inject.PrivateModule;
import com.storage._StorageModule;

public class _ProductApiModule extends PrivateModule {

    @Override
    protected void configure() {

        install(new _StorageModule());
        bind(ProductService.class);
        expose(ProductService.class);
    }
}
