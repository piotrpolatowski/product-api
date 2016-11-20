package com.storage;

import java.util.List;

public interface Storage<T> {

    void add(T product);
    List<T> get(int offset, int limit);
}
