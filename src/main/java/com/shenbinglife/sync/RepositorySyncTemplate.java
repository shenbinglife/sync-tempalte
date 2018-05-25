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
                pushLocal(source, target);
            }
                break;

            case PULL_REMOTE:
                this.pushLocal(target, source);
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

    private void pushLocal(Repository<T> source, Repository<T> target) {
        List<T> retain = RepositoryUtils.retain(source, target);
        if (retain.isEmpty()) {
            RepositoryUtils.addAll(target, source.getAll());
        } else {
            List<T> sourceItems = source.getAll();
            List<T> sourceItemsCache = new ArrayList<>(sourceItems);
            List<T> targetItems = target.getAll();
            sourceItems.removeAll(targetItems);

            RepositoryUtils.addAll(target, sourceItems);

            targetItems.retainAll(sourceItemsCache);
            equalsHandler.handler(source, retain, target, targetItems);
        }
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
