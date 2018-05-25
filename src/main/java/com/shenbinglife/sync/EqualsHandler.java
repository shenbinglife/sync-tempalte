package com.shenbinglife.sync;

import java.util.List;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public interface EqualsHandler<T> {

    void handler(Repository<T> source, List<T> equaledSource, Repository<T> target, List<T> equaledTarget);
}


