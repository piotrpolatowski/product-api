package com.storage;

import com.api.model.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class InMemoryStorageTest {

    InMemoryStorage<Product> storage;

    Long[] ids = {1L, 2L ,3L ,4L ,5L};
    List<Product> products = Arrays.stream(ids)
            .map(id -> Product.create(id, String.valueOf(id), id))
            .collect(Collectors.toList());

    @Before
    public void setUp() throws Exception {
        storage = new InMemoryStorage<>();
    }

    @Test
    public void getFirst() throws Exception {
        products.forEach(storage::add);

        List<Product> products = storage.get(0,1);

        assertEquals("same amount of products", 1, products.size());

        for (int i = 0; i < products.size(); i++) {
            int id = 1+i;
            assertEquals("product is the same", id, products.get(i).getId());
        }
    }

    @Test
    public void getZero() throws Exception {
        products.forEach(storage::add);

        List<Product> products = storage.get(0,0);

        assertEquals("same amount of products", 0, products.size());
    }

    @Test
    public void getSome() throws Exception {
        products.forEach(storage::add);

        List<Product> products = storage.get(0,3);

        assertEquals("same amount of products", 3, products.size());
    }

    @Test
    public void getTooMuch() throws Exception {
        products.forEach(storage::add);

        List<Product> products = storage.get(0,300);

        assertEquals("same amount of products", 5, products.size());
    }

    @Test
    public void add() throws Exception {

        products.forEach(storage::add);

        List<Product> actualProducts = storage.getAll();

        assertEquals("same amount of products", ids.length, actualProducts.size());
        for (int i = 0; i < actualProducts.size(); i++) {
            assertEquals("product is the same", products.get(i), actualProducts.get(i));
        }
    }

}