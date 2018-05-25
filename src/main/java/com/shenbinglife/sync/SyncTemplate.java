package com.shenbinglife.sync;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public interface SyncTemplate<T> {

    void sync(T source, T target);
}

