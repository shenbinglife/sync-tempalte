package com.shenbinglife.sync;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public class RepositorySyncTemplate<T> implements SyncTemplate<Repository<T>> {

    private SyncStrategy syncStrategy = SyncStrategy.PULL_AND_PUSH;

    private EqualsHandler<T> equalsHandler = new IgnoreEqual<>();

    public RepositorySyncTemplate() {
    }

    public RepositorySyncTemplate(SyncStrategy syncStrategy, EqualsHandler<T> equalsHandler) {
        this.syncStrategy = syncStrategy;
        this.equalsHandler = equalsHandler;
    }

    @Override
    public void sync(Repository<T> source, Repository<T> target) {
        switch (syncStrategy) {
            case PUSH_LOCAL: {
                pushLocal(source, target, this.equalsHandler);
            }
                break;

            case PULL_REMOTE:
                this.pushLocal(target, source, reverse(this.equalsHandler));
                break;

            case PULL_AND_PUSH: {
                List<T> sourceItems = source.getAll();
                List<T> sourceItemsCache = new ArrayList<>(sourceItems);
                List<T> targetItems = target.getAll();
                List<T> targetItemsCache = new ArrayList<>(targetItems);

                sourceItems.removeAll(targetItems);
                RepositoryUtils.addAll(target, sourceItems);

                targetItems.removeAll(sourceItemsCache);
                RepositoryUtils.addAll(source, targetItems);

                sourceItemsCache.removeAll(sourceItems);
                targetItemsCache.removeAll(targetItems);
                equalsHandler.handler(source, sourceItemsCache, target, targetItemsCache);
            }

                break;

            default:
                throw new IllegalArgumentException("illegal sync strategy:" + syncStrategy);
        }
    }

    public void sync(Repository<T> source, Repository<T> target, EqualsHandler<T> handler) {
        RepositorySyncTemplate<T> re = new RepositorySyncTemplate<>(this.syncStrategy, handler);
        re.sync(source, target);
    }

    public static <T> void sync(Repository<T> source, Repository<T> target, SyncStrategy strategy, EqualsHandler<T> handler) {
        RepositorySyncTemplate<T> re = new RepositorySyncTemplate<>(strategy, handler);
        re.sync(source, target);
    }

    private void pushLocal(Repository<T> source, Repository<T> target, EqualsHandler<T> equalsHandler) {
        List<T> sourceR = new ArrayList<>();
        List<T> targetR = new ArrayList<>();
        for(T sourceI : source.getAll()) {
            if(target.contains(sourceI)) {
                sourceR.add(sourceI);
            } else {
                target.add(sourceI);
            }
        }
        for(T targetI : target.getAll()) {
            if(source.contains(targetI)) {
                targetR.add(targetI);
            } else {
                target.remove(targetI);
            }
        }
        equalsHandler.handler(source, sourceR, target, targetR);
    }

    private EqualsHandler<T> reverse(final EqualsHandler<T> handler) {
        return new EqualsHandler<T>() {
            @Override
            public void handler(Repository<T> source, List<T> equaledSource, Repository<T> target, List<T> equaledTarget) {
                handler.handler(target, equaledTarget, source, equaledSource);
            }
        };
    }

    public SyncStrategy getSyncStrategy() {
        return syncStrategy;
    }

    public void setSyncStrategy(SyncStrategy syncStrategy) {
        this.syncStrategy = syncStrategy;
    }

    public EqualsHandler<T> getEqualsHandler() {
        return equalsHandler;
    }

    public void setEqualsHandler(EqualsHandler<T> equalsHandler) {
        this.equalsHandler = equalsHandler;
    }
}
