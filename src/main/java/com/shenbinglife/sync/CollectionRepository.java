package com.shenbinglife.sync;

import java.util.*;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public class CollectionRepository<E> implements Repository<E> {

    private Collection<E> collection;

    public CollectionRepository() {
        this.collection = new ArrayList<>();
    }

    public CollectionRepository(Collection<E> collection) {
        this.collection = collection;
    }

    public static <T> CollectionRepository<T> of(Collection<T> collection) {
        return new CollectionRepository<>(collection);
    }

    public static <T> CollectionRepository<T> of(T ... t){
        ArrayList<T> ts = new ArrayList<>(Arrays.asList(t));
        return new CollectionRepository<>(ts);
    }

    @Override
    public void add(E e) {
        collection.add(e);
    }

    @Override
    public void remove(E e) {
        collection.remove(e);
    }

    @Override
    public List<E> getAll() {
        return new ArrayList<>(collection);
    }

    @Override
    public boolean contains(E e) {
        return collection.contains(e);
    }

    @Override
    public String toString() {
        return collection.toString();
    }
}
