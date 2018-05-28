# sync-template  

wrote by Java.  

一个同步模型工具，封装了同步数据的操作流程, 通过SyncTemplate可以一行代码立即同步。  

使用方法：  
1.  声明同步对象
    ```$java
    
            
    class LocalCatalogRepository extends AbstractRepository<Catalog> {
    
        @Autowired
        private CatalogMapper catalogMapper;

        @Override
        public void add(Catalog catalog) {
            catalogMapper.insert(catalog);
        }
    
        @Override
        public void remove(Catalog catalog) {
            catalogMapper.delete(catalog.getId());
        }
    
        @Override
        public List<Catalog> getAll() {
            return catalogMapper.selectAll();
        }
    
        @Override
        public BiFunction<Catalog, Catalog, Boolean> getEqualer() {
            return (o1, o2) -> Objects.equals(o1.getName(), o2.getName());
        }
    }
    
    
    
    
    class RemoteCatalogRepository extends AbstractRepository<Catalog> {
        ...
    }
    
    // 或 Java8
    Repository<Catalog> remoteRepository = new FunctionalRepository<> (catalogMapper::getAll, catalogMapper::insert, catalogMapper::delete, Objects::equals);    
    
    // 通过缓存 Repository.getAll() 的结果提高效率
    Repository<Catalog> cachedRepository = new CachedRepository<>(remoteRepository);
       
    
    ```
2. 执行同步  
    ```java
    RepositorySyncTemplate template =
                            new RepositorySyncTemplate(SyncStrategy.PULL_REMOTE, new IgnoreEqual());

    template.sync(new LocalCatalogRepository(resource), new RemoteCatalogRepository(resource));
    ```
3. 同步策略说明  
    `SyncStrategy.PULL_REMOTE` : 拉取远程资源同步到本地，添加新资源，删除本地在远程不存在资源，默认忽略相同的资源  
    `SyncStrategy.PUSH_LOCAL` : 推送本地资源到远程，等同于对`SyncStrategy.PULL_REMOTE` 反向调用  
    `SyncStrategy.PULL_AND_PUSH `：互相同步不存在的资源，默认忽略相同的资源
    
4. 如何处理相同资源  
    `RepositorySyncTemplate`接受一个`EqualsHandler`的参数类型做构造参数， 重写`EqualsHandler`的`void handler(Repository<T> source, List<T> equaledSource, Repository<T> target, List<T> equaledTarget)`方法，处理本地和远程相同的资源。