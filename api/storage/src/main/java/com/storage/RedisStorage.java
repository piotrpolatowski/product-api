package com.storage;

import java.util.List;

public class RedisStorage<T> implements Storage<T>{


    @Override
    public void add(T product) {
    }

    @Override
    public List<T> get(int offset, int limit) {
        return null;
    }
}
