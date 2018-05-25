package com.shenbinglife.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.shenbinglife.sync.function.BiFunction;
import com.shenbinglife.sync.function.Consumer;
import com.shenbinglife.sync.function.Supplier;
import org.junit.Before;
import org.junit.Test;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/25
 * @since since
 */
public class CollectionFunctionalRepositoryTest {

    RepositorySyncTemplate template = new RepositorySyncTemplate();

    @Before
    public void before() {
        template.setSyncStrategy(SyncStrategy.PUSH_LOCAL);
//        template.setEqualsHandler(new SysoutEqualsHandler());
    }

    public FunctionalRepository<Object> newRepository() {
        final List<Object> list = new ArrayList<>();

        Consumer<Object> adder = new Consumer<Object>() {

            @Override
            public void consum(Object o) {
                list.add(o);
            }
        };

        Consumer<Object> remover = new Consumer<Object>() {
            @Override
            public void consum(Object o) {
                list.remove(o);
            }
        };

        BiFunction<Object, Object, Boolean> equaler = new BiFunction<Object, Object, Boolean>() {
            @Override
            public Boolean map(Object o, Object o2) {
                return Objects.equals(o, o2);
            }
        };

        Supplier<List<Object>> getAll = new Supplier<List<Object>>() {
            @Override
            public List<Object> supply() {
                return new ArrayList<>(list);
            }
        };

        FunctionalRepository<Object> objectFunctionalRepository =
                        new FunctionalRepository<>(getAll, adder, remover, equaler);
        return objectFunctionalRepository;
    }


    @Test
    public void newInstance () {
        FunctionalRepository<Object> repository = newRepository();
        repository.add("test");

        FunctionalRepository<Object> repository2 = newRepository();
        repository2.add("asdf", new User("sd"));

        FunctionalRepository<Object> repository3 = newRepository();
        repository3.add("xxx", new User("sd"), new User("shen"));

        template.sync(repository, repository2);

        System.out.println(repository);
        System.out.println(repository2);

        template.sync(repository2, repository3);

        System.out.println(repository);
        System.out.println(repository2);
        System.out.println(repository3);
    }
}
