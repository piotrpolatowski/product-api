package com.storage;

import com.api.model.Product;
import com.google.inject.PrivateModule;
import com.google.inject.TypeLiteral;
import com.storage.redis.RedisProductStorage;
import com.storage.redis._RedisModule;

public class _StorageModule extends PrivateModule {

    private static final TypeLiteral<Storage<Product>> STORAGE_TYPE = new StorageTypeLiteral();

    @Override
    protected void configure() {
        install(new _RedisModule());

        bind(STORAGE_TYPE).to(new TypeLiteral<InMemoryStorage<Product>>() {}).asEagerSingleton();
        expose(STORAGE_TYPE);
    }


    private static class StorageTypeLiteral extends TypeLiteral<Storage<Product>> {
    }
}
