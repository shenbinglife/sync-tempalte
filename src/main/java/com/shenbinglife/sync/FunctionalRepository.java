package com.shenbinglife.sync;

import com.shenbinglife.sync.function.BiFunction;
import com.shenbinglife.sync.function.Consumer;
import com.shenbinglife.sync.function.Supplier;

import java.util.List;
import java.util.Objects;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public class FunctionalRepository<T> extends AbstractRepository<T> implements Repository<T> {

    private Supplier<List<T>> supplier;

    private Consumer<T> adder;

    private Consumer<T> remover;

    public FunctionalRepository(Supplier<List<T>> supplier, Consumer<T> adder, Consumer<T> remover) {
        this.supplier = supplier;
        this.adder = adder;
        this.remover = remover;
        equaler = new BiFunction<T, T, Boolean>() {
            @Override
            public Boolean map(T t, T t2) {
                return Objects.equals(t, t2);
            }
        };
    }

    public FunctionalRepository(Supplier<List<T>> supplier, Consumer<T> adder, Consumer<T> remover, BiFunction<T, T, Boolean> equaler) {
        this.supplier = supplier;
        this.adder = adder;
        this.remover = remover;
        this.equaler = equaler;
    }

    @Override
    public void add(T t) {
        adder.consum(t);
    }

    @Override
    public void remove(T t) {
        remover.consum(t);
    }

    @Override
    public List<T> getAll() {
        return supplier.supply();
    }

    @Override
    public boolean contains(T t) {
        List<T> all = getAll();
        for(T item : all) {
            Boolean equals = equaler.map(item, t);
            if(equals) {
                return true;
            }
        }
        return false;
    }
}
