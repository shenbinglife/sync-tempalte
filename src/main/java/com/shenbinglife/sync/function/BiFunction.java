package com.shenbinglife.sync.function;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public interface BiFunction<P1, P2, R> {

    R map(P1 p1, P2 p2);
}
