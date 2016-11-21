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
        int from = offset;
        int to = Math.min(offset + limit, list.size());
        if(from > list.size()) {
            return list.subList(0,0);
        }

        return list.subList(from, to);
    }

    public List<T> getAll() {
        return list;
    }
}
