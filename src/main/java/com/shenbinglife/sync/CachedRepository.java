package com.shenbinglife.sync;

import java.util.ArrayList;
import java.util.List;

import com.shenbinglife.sync.function.BiFunction;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public class CachedRepository<T> extends AbstractRepository<T> implements Repository<T> {

    private Repository<T> repository;

    private List<T> cache;

    private boolean reloadOnAction = false;

    public CachedRepository(Repository<T> repository) {
        this.repository = repository;
        this.cache = repository.getAll();
    }

    @Override
    public void add(T t) {
        repository.add(t);
        if (reloadOnAction) {
            reloadCache();
        } else {
            cache.add(t);
        }
    }

    /**
     * if reloadAction == false, then this remove depend on item's equals method because of using remove form
     * java.util.List
     */
    @Override
    public void remove(T t) {
        repository.remove(t);
        if (reloadOnAction) {
            reloadCache();
        } else {
            for (T item : cache) {
                if (getEqualer().map(item, t)) {
                    cache.remove(item);
                    break;
                }
            }
        }
    }

    @Override
    public List<T> getAll() {
        if (cache == null) {
            cache = repository.getAll();
        }
        return cache();
    }

    @Override
    public boolean contains(T t) {
        for (T item : cache) {
            if (getEqualer().map(item, t)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BiFunction<T, T, Boolean> getEqualer() {
        return repository instanceof AbstractRepository ? ((AbstractRepository<T>) repository).getEqualer()
                        : super.getEqualer();
    }

    public List<T> reloadCache() {
        cache = repository.getAll();
        return cache();
    }

    public List<T> cache() {
        return new ArrayList<>(cache);
    }


}
