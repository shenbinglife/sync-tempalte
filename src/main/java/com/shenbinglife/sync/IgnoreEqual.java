package com.shenbinglife.sync;

import java.util.List;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public class IgnoreEqual<T> implements EqualsHandler<T> {

    @Override
    public void handler(Repository<T> source, List<T> equaledSource, Repository<T> target, List<T> equaledTarget) {

    }
}
