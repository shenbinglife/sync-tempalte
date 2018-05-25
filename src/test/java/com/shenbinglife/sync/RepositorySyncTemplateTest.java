package com.shenbinglife.sync;

import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public class RepositorySyncTemplateTest {
    RepositorySyncTemplate repositorySyncTemplate = new RepositorySyncTemplate();

    @Before
    public void before() {
        repositorySyncTemplate.setEqualsHandler(new SysoutEqualsHandler());
    }

    @Test
    public void sync() {


        CollectionRepository<Serializable> source = CollectionRepository.of("123", 456, new User("source"));
        CollectionRepository<Serializable> target = CollectionRepository.of("456", 456, new User("target"));

        repositorySyncTemplate.sync(source, target);

        assertEquals("source has 6 item", 5, source.getAll().size());
        assertEquals("target has 6 item", 5, target.getAll().size());
        assertTrue(source.contains("456"));
        assertTrue(source.contains(456));
        assertTrue(source.contains(new User("target")));

        assertTrue(target.contains("123"));
        assertTrue(target.contains(456));
        assertTrue(target.contains(new User("source")));

        System.out.println(source);
        System.out.println(target);
    }

    @Test
    public void sysoutEquals() {
        repositorySyncTemplate.setEqualsHandler(new SysoutEqualsHandler());

        CollectionRepository<User> source = CollectionRepository.of(new User("x"), new User("x"), new User("y"), new User("source"));
        CollectionRepository<User> target = CollectionRepository.of(new User("y"), new User("y"), new User("x"), new User("target"));

        repositorySyncTemplate.sync(source, target);

        System.out.println(source);
        System.out.println(target);
    }

    @Test
    public void pushLocal() {
        repositorySyncTemplate.setSyncStrategy(SyncStrategy.PUSH_LOCAL);

        CollectionRepository<User> source = CollectionRepository.of(new User("x"), new User("x"), new User("y"), new User("source"));
        CollectionRepository<User> target = CollectionRepository.of(new User("y"), new User("y"), new User("x"), new User("target"));

        repositorySyncTemplate.sync(source, target);

        System.out.println(source);
        System.out.println(target);
    }


}
class User implements Serializable{
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}

class SysoutEqualsHandler implements EqualsHandler<Object> {
    @Override
    public void handler(Repository<Object> source, List<Object> equaledSource, Repository<Object> target, List<Object> equaledTarget) {
        System.out.println("source equals : " + equaledSource);
        System.out.println("target equals : " + equaledTarget);
    }
}