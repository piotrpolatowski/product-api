package com.storage.redis;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.lambdaworks.redis.RedisClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RedisClientProvider implements Provider<RedisClient> {

    @Inject
    @Named("api.redis.uri")
    private final String uri;

    @Override
    public RedisClient get() {
        log.info("Connecting to redis: {}", uri);
        return RedisClient.create(uri);
    }
}
