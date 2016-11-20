package com.storage;

import com.google.common.collect.Lists;

import java.util.List;

public class InMemoryStorage<T> implements Storage<T>{
    List<T> list = Lists.newArrayList();


    @Override
    public void add(T product) {
        list.add(product);
    }

    @Override
    public List<T> get(int offset, int limit) {
        return list.subList(offset, offset + limit + 1);
    }
}
