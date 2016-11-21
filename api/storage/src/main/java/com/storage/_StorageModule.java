package com.storage;

import com.api.model.Product;
import com.google.inject.PrivateModule;
import com.google.inject.TypeLiteral;
import com.lambdaworks.redis.RedisClient;
import com.storage.redis.RedisClientProvider;
import com.storage.redis.RedisProductStorage;

public class _StorageModule extends PrivateModule {

    private static final TypeLiteral<Storage<Product>> STORAGE_TYPE = new StorageTypeLiteral();

    @Override
    protected void configure() {
        bind(RedisClient.class).toProvider(RedisClientProvider.class);
        bind(STORAGE_TYPE).to(RedisProductStorage.class).asEagerSingleton();
        expose(STORAGE_TYPE);
    }


    private static class StorageTypeLiteral extends TypeLiteral<Storage<Product>> {
    }
}
