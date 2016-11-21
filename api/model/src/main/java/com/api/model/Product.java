package com.api.model;

import lombok.Data;

@Data
public class Product {
    private long id;
    private String title;
    private Price price;

    public static final Product create(long id, String title, long cent) {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(Price.of(cent));
        return product;
    }
}
