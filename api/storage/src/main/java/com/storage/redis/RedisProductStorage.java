package com.storage.redis;

import com.api.model.Product;
import com.google.inject.Inject;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

public class RedisProductStorage implements Storage<Product> {

    private static final String TITLE_FIELD = "title";

    @Inject
    public RedisProductStorage(RedisClient client) {
        this.commands = client.connect().sync();
    }

    private static final String PRICE_FIELD = "price";
    private static final String SORTED_CATALOG_INDEX = "product_keys";
    private static final String PRODUCT_CATALOG = "catalog";

    private final RedisCommands<String, String> commands;

    @Override
    public void add(Product product) {
        String key = String.format("%s:%s", PRODUCT_CATALOG, product.getId());
        commands.hsetnx(key, TITLE_FIELD, product.getTitle());
        commands.hsetnx(key, PRICE_FIELD, String.valueOf(product.getPrice().getCent()));

        commands.zadd("product_catalog", 1.0, String.valueOf(product.getId()));
    }

    @Override
    public List<Product> get(int offset, int limit) {
        List<String> productIds = commands.zrange(SORTED_CATALOG_INDEX, offset, offset + limit - 1);

        return productIds.stream()
                .map(Long::new)
                .map(id -> Product.create(id, "title", 1))
                .collect(Collectors.toList());
    }
}
