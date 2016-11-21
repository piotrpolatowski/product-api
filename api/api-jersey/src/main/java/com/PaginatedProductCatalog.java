package com;

import com.api.model.Product;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.storage.Storage;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PaginatedProductCatalog {

    @Inject @Named("api.pageSize")
    private final Integer pageSize;

    private final Storage<Product> storage;

    public List<Product> list(int page) {
            return storage.get(pageSize * page, pageSize);
    }

    public void add(Product product) {
        storage.add(product);
    }
}
