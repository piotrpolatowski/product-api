package com.api.model;

import lombok.Data;

@Data
public class Price {
    /**
     * Encapsulated price to abstract the way we store it's value.
     * The data type could be easily changed to BigDecimal if needed.
     */
    private long cent;

    public static final Price of(long cent) {
        Price price = new Price();
        price.setCent(cent);
        return price;
    }
}
