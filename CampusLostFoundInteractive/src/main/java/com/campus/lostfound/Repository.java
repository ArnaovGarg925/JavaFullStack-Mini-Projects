package com.campus.lostfound;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();
    void save(T value);
    void delete(String id);
}
