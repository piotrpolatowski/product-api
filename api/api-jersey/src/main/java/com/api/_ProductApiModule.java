package com.api;

import com.api.model.Product;
import com.storage.InMemoryStorage;
import com.storage.Storage;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;

public class _ProductApiModule extends PrivateModule {

    @Override
    protected void configure() {

        bind(ProductService.class);
        expose(ProductService.class);
    }

    @Provides
    public Storage<Product> storage() {
        Storage<Product> storage = new InMemoryStorage<>();
        storage.add(Product.create(1, "Fallout", 199L));
        storage.add(Product.create(2, "Don't Starve", 299L));
        storage.add(Product.create(3, "Baldour's Gate", 399L));
        storage.add(Product.create(4, "Icewind Dale", 499L));
        storage.add(Product.create(5, "Bloodborne", 599L));
        return storage;
    }
}
