package com.api.model;

import lombok.Data;

@Data
public class Product {
    private final long id;
    private final String title;
    private final Price price;

    public static final Product create(long id, String title, long cent) {
        return new Product(id, title, Price.of(cent));
    }
}
