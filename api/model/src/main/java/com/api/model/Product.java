package com.api.model;

import lombok.Data;

@Data
public class Product {
    private long id;
    private String title;
    private Long price;
    private Currency currency;

    public static final Product create(long id, String title, long cent) {
        return create(id, title, cent, Currency.USD);
    }

    public static final Product create(long id, String title, long cent, Currency currency) {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(cent);
        product.setCurrency(currency);
        return product;
    }
}
