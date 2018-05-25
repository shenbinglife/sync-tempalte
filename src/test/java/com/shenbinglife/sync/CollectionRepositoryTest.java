package com.shenbinglife.sync;

import static org.junit.Assert.*;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public class CollectionRepositoryTest {

    @Test
    public void newInstance() {
        CollectionRepository<String> of = CollectionRepository.of("123", "12");
        assertEquals("respository has 2 item", 2, of.getAll().size());
        assertTrue(of.contains("123"));
        assertTrue(of.contains("12"));
        System.out.println(of);
    }

    @Test
    public void newInstance2() {
        ArrayList<Object> serializables = Lists.<Object>newArrayList("123", 123);
        CollectionRepository<Object> repository = new CollectionRepository<>(serializables);
        assertEquals("respository has 2 item", 2, repository.getAll().size());
        assertTrue(repository.contains("123"));
        assertTrue(repository.contains(123));
        System.out.println(repository);
    }

    @Test
    public void add() {
        CollectionRepository<Object> repository = new CollectionRepository<>();
        repository.add("test");
        repository.add(123);

        assertEquals("respository has 2 item", 2, repository.getAll().size());
        assertTrue(repository.contains("add"));
        assertTrue(repository.contains(123));
        System.out.println(repository);
    }

    @Test
    public void retain() {
        CollectionRepository<User> source = CollectionRepository.of(new User("x"), new User("x"), new User("y"), new User("source"));
        CollectionRepository<User> target = CollectionRepository.of(new User("y"), new User("y"), new User("x"), new User("target"));

        List<User> retain = RepositoryUtils.retain(source, target);
        List<User> retain2 = RepositoryUtils.retain(target, source);

        System.out.println(retain);
        System.out.println(retain2);
    }
}
