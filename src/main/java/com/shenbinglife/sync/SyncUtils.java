package com.shenbinglife.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public class SyncUtils {

    /**
     * merge c1 and c2, c1 add all which not repeated from c2, and c2 add all which not repeated from c1
     */
    public static <E>  void mergeCollection(Collection<E> c1, Collection<E> c2) {
        if (c1 == c2) {
            return;
        }
        if (c1 == null || c2 == null) {
            throw new IllegalArgumentException("source or target collection can not be null.");
        }

        List<E> intersection = new ArrayList<>(c1);
        intersection.retainAll(c2);
        boolean hasIntersection = !intersection.isEmpty();

        if (hasIntersection) {
            c1.addAll(intersection);
            c2.addAll(intersection);
        } else {
            Collection<E> cacheC1 = new ArrayList<>(c1);
            c1.addAll(c2);
            c2.addAll(cacheC1);
        }
    }


    /**
     * merge m1 and m2, key/value of m2 will replace m1's key/value.
     */
    public void mergeMap(Map m1, Map m2) {
        m1.putAll(m2);
        m2.putAll(m1);
    }
}
