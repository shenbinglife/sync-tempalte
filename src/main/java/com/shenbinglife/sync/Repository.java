package com.shenbinglife.sync;

import java.util.List;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public interface Repository<T> {

    void add(T t);

    /**
     * remove first one from repository which equals t
     */
    void remove(T t);

    List<T> getAll();

    boolean contains(T t);
}
