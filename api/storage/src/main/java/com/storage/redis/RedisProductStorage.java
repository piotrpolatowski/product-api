package com.storage.redis;

import com.api.model.Currency;
import com.api.model.Product;
import com.google.inject.Inject;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

public class RedisProductStorage implements Storage<Product> {

    private static final String TITLE_FIELD = "title";
    private static final String PRICE_FIELD = "price";
    private static final String ID_FIELD = "id";
    private static final String CURRENCY_FIELD = "currency";

    private static final String SORTED_CATALOG_INDEX = "product_keys";
    private static final String PRODUCT_CATALOG = "catalog";

    private final RedisCommands<String, String> commands;

    @Inject
    public RedisProductStorage(RedisClient client) {
        this.commands = client.connect().sync();
    }


    @Override
    public void add(Product product) {
        String key = getProductKey(product.getId());
        commands.hsetnx(key, TITLE_FIELD, product.getTitle());
        commands.hsetnx(key, PRICE_FIELD, String.valueOf(product.getPrice()));
        commands.hsetnx(key, ID_FIELD, String.valueOf(product.getId()));
        commands.hsetnx(key, CURRENCY_FIELD, String.valueOf(product.getCurrency()));

        commands.zadd("product_catalog", 1.0, String.valueOf(product.getId()));
    }

    @Override
    public List<Product> get(int offset, int limit) {
        List<String> productIds = commands.zrange(SORTED_CATALOG_INDEX, offset, offset + limit - 1);

        return productIds.stream()
                .map(this::getProductKey)
                .map(commands::hgetall)
                .map(prod -> {
                    String title = prod.get(TITLE_FIELD);
                    Long price = Long.parseLong(prod.get(PRICE_FIELD));
                    Long id = Long.parseLong(prod.get(ID_FIELD));
                    Currency currency = Currency.valueOf(prod.get(CURRENCY_FIELD));
                    return Product.create(id, title, price);
                })
                .collect(Collectors.toList());
    }

    private String getProductKey(long id) {
        return String.format("%s:%s", PRODUCT_CATALOG, id);
    }

    private String getProductKey(String id) {
        return String.format("%s:%s", PRODUCT_CATALOG, id);
    }
}
