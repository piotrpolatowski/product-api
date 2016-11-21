package com;

import com.api.model.Product;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.storage.Storage;

import java.util.List;

public class PaginatedProductCatalog {

    private final int pageSize;
    private final Storage<Product> storage;

    @Inject
    public PaginatedProductCatalog(Storage<Product> storage, @Named("api.pageSize") int pageSize) {
        this.storage = storage;
        this.pageSize = pageSize;
    }


    public List<Product> list(int page) {
            return storage.get(pageSize * page, pageSize);
    }

    public void add(Product product) {
        storage.add(product);
    }
}
