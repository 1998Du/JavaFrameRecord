#  SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

上一篇：[Mybatis源码阅读，创建Reader对象都干了什么](https://editor.csdn.net/md/?articleId=117392200)

Reader对象成功创建之后就是使用已经创建好的Reader对象来创建SqlSessionFactory对象了。

1. 源码中解释这样SqlSessionFactory：Creates an SqlSession out of a connection
   or a DataSource 有道翻译=>从连接或数据源创建SqlSession，
2. SqlSessionFactory

==源码：==
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210529235811952.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ0ODcyNzg3,size_16,color_FFFFFF,t_70)

发现，SqlSessionFactory只是一个接口，仔细看下里面的方法，一堆的openSession和一个getConfiguration方法。既然是个接口，那看一下它的实现类，SqlSessionFactory只有两个实现类：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210529235514623.png)
一个一个看下去

2.1  DefaultSqlSessionFactory

==openSession咱就先不看了，无非就是不同的方式创建session，这个等后面创建SqlSession的时候再看。==
进入到DefaultSqlSessionFactory内部先看到的是

```java
private final Configuration configuration;

  public DefaultSqlSessionFactory(Configuration configuration) {
    this.configuration = configuration;
  }
```
哦，final关键字创建了一个Configuration变量然后构造方法中给这个变量初始化了一下，那么看看这个Configuration是个什么吧

2.1.1 Configuration
刚刚进来，你就会看到


```java
public class Configuration {

  protected Environment environment;

  protected boolean safeRowBoundsEnabled;
  protected boolean safeResultHandlerEnabled = true;
  protected boolean mapUnderscoreToCamelCase;
  protected boolean aggressiveLazyLoading;
  protected boolean multipleResultSetsEnabled = true;
  protected boolean useGeneratedKeys;
  protected boolean useColumnLabel = true;
  protected boolean cacheEnabled = true;
  protected boolean callSettersOnNulls;
  protected boolean useActualParamName = true;
  protected boolean returnInstanceForEmptyRow;
  protected boolean shrinkWhitespacesInSql;
```
这么大一坨boolean，虽然有点吓人，但是仔细一看，有没有发现其中有些东西是很眼熟的，比如说cacheEnabled、useGeneratedKeys。。。没错！这就是我们经常在mybatis的mapper映射文件中所用到的配置，，记住哈，这些配置的开关都是在 ==Configuration==类里面。
来个注释版的洗洗眼：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210530003449283.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ0ODcyNzg3,size_16,color_FFFFFF,t_70)
好，再仔细一看，还有个排头兵Environment，这又是什么玩意？

2.1.2 Environment
字面上是环境，那么是什么环境呢，上源码：

```java
public final class Environment {
  private final String id;
  private final TransactionFactory transactionFactory;
  private final DataSource dataSource;

  public Environment(String id, TransactionFactory transactionFactory, DataSource dataSource) {
    if (id == null) {
      throw new IllegalArgumentException("Parameter 'id' must not be null");
    }
    if (transactionFactory == null) {
      throw new IllegalArgumentException("Parameter 'transactionFactory' must not be null");
    }
    this.id = id;
    if (dataSource == null) {
      throw new IllegalArgumentException("Parameter 'dataSource' must not be null");
    }
    this.transactionFactory = transactionFactory;
    this.dataSource = dataSource;
  }
```
看到构造方法中抛出的这么些个异常，有没有觉得熟悉？
什么？没有？  那么搭配上这个来看一看
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210530004421762.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ0ODcyNzg3,size_16,color_FFFFFF,t_70)
没错了，，这个Environment类就是用来配置数据库环境的。我很肯定哈！
是不是想起了当初使用spring手写配置的恐惧感了？既然id是个String咱就放过它了，来扒一扒TransactionFactory和DataSource的壳，看看这俩到底是个什么玩意。
2.1.2.1 TransactionFactory
这个很好翻译嘛：事务工厂
上源码：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210530010041974.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ0ODcyNzg3,size_16,color_FFFFFF,t_70)
是个接口来的，那么看看它的实现类
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210530010133565.png)
很熟悉有没有，这不正对应了mybatis中的两种事务管理器嘛，进去看看
JdbcTransactionFactory源码
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210530010435310.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ0ODcyNzg3,size_16,color_FFFFFF,t_70)
只是实现了TransactionFactory中的两个newTransaction方法
那么看看ManagedTransactionFactory的源码：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210530010630246.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ0ODcyNzg3,size_16,color_FFFFFF,t_70)
哟，ManagedTransactionFactory三个都实现了。
那再来看看这些方法都干了些什么吧
**==>** JdbcTransactionFactory中的newTransaction(Connection conn)：
只是简单的创建了一个JdbcTransaction对象呢，再进去看看是不是真的这么简单
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210530011036714.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ0ODcyNzg3,size_16,color_FFFFFF,t_70)
只是初始化了JdbcTransaction中的Connection对象，那么看看它所实现的Transaction接口中都有什么方法吧
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210530011354536.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ0ODcyNzg3,size_16,color_FFFFFF,t_70)
获取connectinon、提交、回滚、关闭、获取超时，，妥妥的事务管理了
**==>** JdbcTransactionFactory中的newTransaction(DataSource ds, TransactionIsolationLevel level, boolean autoCommit)；也只创建了一个JdbcTransaction对象，再进去瞅瞅
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210530011843480.png)
也只是对JdbcTransaction中的DataSource、TransactionIsolationLevel、autoCommit进行了初始化。
**==>** ManagedTransactionFactory中的setProperties

```java
private boolean closeConnection = true;
public void setProperties(Properties props) {
    if (props != null) {
      String closeConnectionProperty = props.getProperty("closeConnection");
      if (closeConnectionProperty != null) {
        closeConnection = Boolean.parseBoolean(closeConnectionProperty);
      }
    }
  }
```
这个void方法就不解释了，无非就是根据传入的Properties对象是否为空来更改closeConnection的布尔值
**==>** ManagedTransactionFactory中的newTransaction(Connection conn)
创建了一个ManagedTransaction对象，点进去：

```java
public ManagedTransaction(Connection connection, boolean closeConnection) {
    this.connection = connection;
    this.closeConnection = closeConnection;
  }
```
也是和JdbcTransactionFactory中的一样对几个属性进行初始化
**==>** ManagedTransactionFactory中的newTransaction(DataSource ds, TransactionIsolationLevel level, boolean autoCommit)方法，点进去：

```java
public ManagedTransaction(DataSource ds, TransactionIsolationLevel level, boolean closeConnection) {
    this.dataSource = ds;
    this.level = level;
    this.closeConnection = closeConnection;
  }
```
也是一样，初始化了几个属性。

==那么到此为止Configuration中的Environment就看完了，总结一下：就是对数据库环境进行了配置，对事务管理类中的一些属性进行了初始化==

那我们接着看Configuration吧，1000多行呢：
除去get、set方法 再提取一下，，核心的几个方法应该就是这几个了：

```java
public MetaObject newMetaObject(Object object) {
    return MetaObject.forObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
  }

  public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
    ParameterHandler parameterHandler = mappedStatement.getLang().createParameterHandler(mappedStatement, parameterObject, boundSql);
    parameterHandler = (ParameterHandler) interceptorChain.pluginAll(parameterHandler);
    return parameterHandler;
  }

  public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler,
      ResultHandler resultHandler, BoundSql boundSql) {
    ResultSetHandler resultSetHandler = new DefaultResultSetHandler(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
    resultSetHandler = (ResultSetHandler) interceptorChain.pluginAll(resultSetHandler);
    return resultSetHandler;
  }

  public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
    StatementHandler statementHandler = new RoutingStatementHandler(executor, mappedStatement, parameterObject, rowBounds, resultHandler, boundSql);
    statementHandler = (StatementHandler) interceptorChain.pluginAll(statementHandler);
    return statementHandler;
  }

  public Executor newExecutor(Transaction transaction) {
    return newExecutor(transaction, defaultExecutorType);
  }

  public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
    executorType = executorType == null ? defaultExecutorType : executorType;
    executorType = executorType == null ? ExecutorType.SIMPLE : executorType;
    Executor executor;
    if (ExecutorType.BATCH == executorType) {
      executor = new BatchExecutor(this, transaction);
    } else if (ExecutorType.REUSE == executorType) {
      executor = new ReuseExecutor(this, transaction);
    } else {
      executor = new SimpleExecutor(this, transaction);
    }
    if (cacheEnabled) {
      executor = new CachingExecutor(executor);
    }
    executor = (Executor) interceptorChain.pluginAll(executor);
    return executor;
  }
```
那么，基于以上代码块中的几个方法来分析一下，这些个方法又都干了些什么不为人知的事！
首先提出这几个方法名来拆解一波：
newMetaObject----new一个元数据对象？
newParameterHandler----new一个参数处理器?
newResultSetHandler-----new一个结果集处理器？
newStatementHandler-----new一个Statement处理器？
newExecutor-----new一个执行器？
newExecutor----new另外一个执行器？

==算了，先透露一下吧，这个地方非常重要，快一万字了，下篇再肝。==

最后记录一下，此篇文章写到Configuration类。


**最后最后，此文章只是记录一下个人阅读源码的笔记，若有错误还请指正。**
