package com.storage.redis;

import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.lambdaworks.redis.RedisClient;

public class _RedisModule extends PrivateModule {

    @Override
    protected void configure() {

        expose(RedisClient.class);
    }

    @Provides
    public RedisClient storage(@Named("api.redis.uri") String redisUri) {
        return RedisClient.create(redisUri);
    }
}
