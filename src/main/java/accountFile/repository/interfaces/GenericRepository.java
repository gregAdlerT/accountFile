package main.java.accountFile.repository.interfaces;

import java.util.List;

public interface GenericRepository<T,ID> {
    T getById(ID id);
    List<T> getAll();
    T add(T t);
    T update(T t);
    boolean deleteById(ID id);
    boolean deleteAll();
}
