package com.storage.redis;

import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.lambdaworks.redis.RedisClient;

public class _RedisModule extends PrivateModule {

    @Override
    protected void configure() {

        expose(RedisClient.class);
    }

    @Provides
    public RedisClient storage() {
        return RedisClient.create();
    }
}
