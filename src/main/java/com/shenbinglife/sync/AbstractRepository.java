package com.shenbinglife.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.shenbinglife.sync.function.BiFunction;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/25
 * @since since
 */
public abstract class AbstractRepository<T> implements Repository<T> {

    public AbstractRepository() {
    }

    public AbstractRepository(BiFunction<T, T, Boolean> equaler) {
        this.equaler = equaler;
    }

    protected BiFunction<T, T, Boolean> equaler = new BiFunction<T, T, Boolean>() {
        @Override
        public Boolean map(T t, T t2) {
            return Objects.equals(t, t2);
        }
    };

    @Override
    public void add(T t) {

    }

    public void add(T first, T... t) {
        add(first);
        for (T item : t) {
            add(item);
        }
    }

    /**
     * remove list by {@link AbstractRepository#remove(T item)}, this method not judge contains, differ
     * from @code{remove(List<T> list)}
     *
     * @see AbstractRepository#removeAll(List)
     */
    public void remove(List<T> list) {
        for (T item : list) {
            this.remove(item);
        }
    }


    public T getFirstOne() {
        List<T> all = getAll();
        if (all.isEmpty()) {
            return null;
        }
        return all.get(0);
    }

    @Override
    public boolean contains(T t) {
        List<T> all = getAll();
        for (T item : all) {
            if (equaler.map(item, t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * remove all which contains the item differ from @code{remove(List<T> list)}
     *
     * @see AbstractRepository#remove(List)
     */
    public void removeAll(List<T> list) {
        List<T> all = getAll();
        for (T t : all) {
            this.removeAll(t);
        }
    }

    /**
     * remove all which equals target
     */
    private void removeAll(T t) {
        for(T item : getAll()) {
            if(equaler.map(item, t)) {
                this.remove(item);
            }
        }
    }

    /**
     * get intersection in this repository
     */
    public List<T> retain(List<T> list) {
        List<T> all = getAll();
        List<T> result = new ArrayList<>(all.size());
        for (T item : all) {
            for (T compare : list) {
                if (equaler.map(item, compare)) {
                    result.add(item);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * get intersection in self repository
     */
    public List<T> retain(Repository<T> target) {
        List<T> all = getAll();
        List<T> result = new ArrayList<>(all.size());
        for (T item : all) {
            for (T compare : target.getAll()) {
                if (equaler.map(item, compare)) {
                    result.add(item);
                    break;
                }
            }
        }
        return result;
    }

    public BiFunction<T, T, Boolean> getEqualer() {
        return equaler;
    }

    public void setEqualer(BiFunction<T, T, Boolean> equaler) {
        this.equaler = equaler;
    }

    @Override
    public String toString() {
        return this.getAll().toString();
    }
}
