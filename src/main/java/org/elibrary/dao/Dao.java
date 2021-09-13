package org.elibrary.dao;

import java.util.List;

public interface Dao<E> {
    E get(int id) throws Exception;
    List<E> getAll() throws Exception;
    void insert(E e) throws Exception;
    void update(E e) throws Exception;
    default void delete(int id) throws Exception {
        throw new UnsupportedOperationException();
    };
}
