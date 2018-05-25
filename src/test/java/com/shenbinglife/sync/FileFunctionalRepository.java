package com.shenbinglife.sync;

import com.shenbinglife.sync.function.BiFunction;
import com.shenbinglife.sync.function.Consumer;
import com.shenbinglife.sync.function.Supplier;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import sun.misc.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 类名
 *
 * @author shenbing
 * @version 2018/5/25
 * @since since
 */
public class FileFunctionalRepository {

    File dir1 = new File("D:dir1");
    File dir2 = new File("D:dir2");

    RepositorySyncTemplate template = new RepositorySyncTemplate();

    @Before
    public void before() {
        template.setSyncStrategy(SyncStrategy.PULL_REMOTE);
//        template.setEqualsHandler(new SysoutEqualsHandler());
    }

    FunctionalRepository<File> fileRepo(final String path) {
        final File root = new File(path);
        if(!root.exists() || root.isFile()) {
            throw new IllegalArgumentException("path not dir:" + path);
        }

        Consumer<File> adder = new Consumer<File>() {

            @Override
            public void consum(File o) {
                try {
                    String name = o.getName();
                    File file = Paths.get(path, name).toFile();
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Consumer<File> remover = new Consumer<File>() {
            @Override
            public void consum(File o) {
                String name = o.getName();
                Path fileName = Paths.get(path, name).getFileName();
                FileUtils.deleteQuietly(fileName.toFile());
            }
        };

        BiFunction<File, File, Boolean> equaler = new BiFunction<File, File, Boolean>() {
            @Override
            public Boolean map(File o, File o2) {
                return Objects.equals(o.getAbsolutePath(), o2.getAbsolutePath());
            }
        };

        Supplier<List<File>> getAll = new Supplier<List<File>>() {
            @Override
            public List<File> supply() {
                return Arrays.asList(root.listFiles());
            }
        };

      return   new FunctionalRepository<File>(getAll, adder, remover, equaler);
    }

    @Test
    public void testFileRepo() {
        FunctionalRepository<File> dir1 = fileRepo("d:/dir1");
        FunctionalRepository<File> dir2 = fileRepo("d:/dir2");

        System.out.println(dir1);
        System.out.println(dir2);

        template.sync(dir1, dir2);

        System.out.println(dir1);
        System.out.println(dir2);
    }

}
