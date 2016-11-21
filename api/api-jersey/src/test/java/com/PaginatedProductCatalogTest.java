package com;

import com.api.model.Product;
import com.storage.InMemoryStorage;
import com.storage.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class PaginatedProductCatalogTest {

    private final int PAGE_SIZE = 3;

    PaginatedProductCatalog catalog;
    InMemoryStorage<Product> storage;

    Long[] ids = {1L, 2L ,3L ,4L ,5L};
    List<Product> products = Arrays.stream(ids)
            .map(id -> Product.create(id, String.valueOf(id), id))
            .collect(Collectors.toList());

    @Before
    public void setUp() throws Exception {
        storage = new InMemoryStorage<>();
        catalog = new PaginatedProductCatalog(storage, PAGE_SIZE);
    }

    @Test
    public void list() throws Exception {
        products.forEach(catalog::add);

        List<Product> page0 = catalog.list(0);
        List<Product> page1 = catalog.list(1);
        List<Product> page2 = catalog.list(2);

        assertEquals("same amount of products", 3, page0.size());
        assertEquals("same amount of products", 2, page1.size());
        assertEquals("same amount of products", 0, page2.size());

        for (int i = 0; i < page0.size(); i++) {
            int id = 1+i;
            assertEquals("product is the same", id, page0.get(i).getId());
        }

        for (int i = 0; i < page1.size(); i++) {
            int id = 4+i;
            assertEquals("product is the same", id, page1.get(i).getId());
        }
    }

    @Test
    public void add() throws Exception {

        products.forEach(catalog::add);

        List<Product> actualProducts = storage.getAll();

        assertEquals("same amount of products", ids.length, actualProducts.size());
        for (int i = 0; i < actualProducts.size(); i++) {
            assertEquals("product is the same", products.get(i), actualProducts.get(i));
        }
    }

}