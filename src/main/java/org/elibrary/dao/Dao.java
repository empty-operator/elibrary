package org.elibrary.dao;

import java.util.List;

public interface Dao<E> {
    E get(int id);
    List<E> getAll();
    void insert(E e);
    void update(E e);
    void delete(E e);
}
