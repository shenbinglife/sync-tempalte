package com.shenbinglife.sync;

import java.util.List;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public class RepositoryUtils {

    public static <T> void removeAll(Repository<T> repository) {
        List<T> all = repository.getAll();
        if (all.isEmpty()) {
            return;
        }
        for (T t : all) {
            repository.remove(t);
        }
    }

    public static <T> boolean isEmpty(Repository<T> repository) {
        return repository.getAll().isEmpty();
    }

    /**
     * copy source to target
     */
    public static <T> void transfer(Repository<T> source, Repository<T> target) {
        if (isEmpty(source)) {
            return;
        }
        List<T> items = source.getAll();
        for (T item : items) {
            target.add(item);
        }
    }

    public static <T> List<T> retain(Repository<T> source, Repository<T> target) {
        if (source instanceof AbstractRepository) {
            return ((AbstractRepository<T>) source).retain(target);
        } else {
            List<T> sourceItems = source.getAll();
            List<T> targetItems = target.getAll();
            sourceItems.retainAll(targetItems);
            return sourceItems;
        }
    }

    public static <T> void addAll(Repository<T> source, List<T> targetItems) {
        for (T item : targetItems) {
            source.add(item);
        }
    }

    public static <T> void removeAll(Repository<T> source, List<T> sourceItems) {
        for (T t : sourceItems) {
            source.remove(t);
        }
    }
}
