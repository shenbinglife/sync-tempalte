package com.shenbinglife.sync;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/24
 * @since since
 */
public class CollectionMergeTest {


    @Test
    public void syncList() {
        List<String> c1 = Lists.newArrayList("c1");
        List<String> c2 = Lists.newArrayList("c2");

        // c1.addAll(c2);
        SyncUtils.mergeCollection(c1, c2);
        assertEquals("c1 size must be 2 ", 2, c1.size());
        assertEquals("c2 size must be 2 ", 2, c2.size());
        assertTrue("c1 must contains c2", c1.contains("c2"));
        assertTrue("c2 must contains c1", c2.contains("c1"));
    }

    @Test
    public void syncSet() {
        Set<String> c1 = Sets.newHashSet("c1");
        Set<String> c2 = Sets.newHashSet("c2");

        SyncUtils.mergeCollection(c1, c2);
        assertEquals("c1 size must be 2 ", 2, c1.size());
        assertEquals("c2 size must be 2 ", 2, c2.size());
        assertTrue("c1 must contains c2", c1.contains("c2"));
        assertTrue("c2 must contains c1", c2.contains("c1"));
    }

    @Test
    public void syncListAndSet() {
        Set<String> c1 = Sets.newHashSet("c1");
        List<String> c2 = Lists.newArrayList("c2");

        SyncUtils.mergeCollection(c1, c2);
        assertEquals("c1 size must be 2 ", 2, c1.size());
        assertEquals("c2 size must be 2 ", 2, c2.size());
        assertTrue("c1 must contains c2", c1.contains("c2"));
        assertTrue("c2 must contains c1", c2.contains("c1"));
    }

    @Test
    public void syncNullAndNull() {
        Collection<String> c1 = null;
        Collection<String> c2 = null;
        SyncUtils.mergeCollection(c1, c2);
        assertNull("c1 must be null", c1);
        assertNull("c2 must be null", c2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void syncNull() {
        Collection<String> c1 = Lists.newArrayList("c1");
        Collection<String> c2 = null;
        SyncUtils.mergeCollection(c1, c2);
    }
}
