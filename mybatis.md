一.MyBatis历史和介绍
---------------------
  
  1.iBatis与MyBatis

 针对持久层框架而言（Hibernate,JPA,iBatis）则需要配置两类文件：

·一类是用于指定数据源、事务属性以及其他一些参数配置信息；

·另一类则是用于指定数据库表和程序之间的映射信息。

①根据iBatis的习惯，通常把全局配置文件命名为sqlMapConfig.xml,文件名本身并没有要求，在MyBatis中，会将该文件命名为Configuration.xml；

②在映射文件中配置sql语句：在iBatis中，namespace不是必需的，且它的存在没有实际的意义。在MyBatis中，namespace使得映射文件与接口绑定变得非常自然；

③配置事务管理器和数据源的方式不同；

④指定映射文件的方式不同；

⑤调用存储过程的方式不同；

二．MyBatis的实现步骤
-------------

1.#{}和${}

①#{}是通配符的写法，而${}是拼接写法；

②#{}是在赋值时自动添加’’,而${}不会执行这样的操作；

③#{}可以单独使用，也可以在封装类中使用，而${}只能在封装类使用


2.加载步骤

 ①首先创建 SqlSessionFactory 实例，SqlSessionFactory 就是创建 SqlSession 的工厂类。

②加载配置文件创建 Configuration 对象，配置文件包括数据库相关配置文件以及我们在 XML 文件中写的 SQL。

③通过 SqlSessionFactory 创建 SqlSession。

④通过 SqlSession 获取 mapper 接口动态代理。

⑤动态代理回调 SqlSession 中某查询方法。

⑥SqlSession 将查询方法转发给 Executor。

⑦Executor 基于 JDBC 访问数据库获取数据，最后还是通过 JDBC 操作数据库。

⑧Executor 通过反射将数据转换成 POJO 并返回给 SqlSession。
将数据返回给调用者。

三．多表之间的关系
----------

  1.一对多


```
<collection property="属性名" ofType="引用类类型" 
column="外键">
<id property="引用类属性" column="引用表主键" />
<result property="引用类属性" column="引用表普通列"/>
……
</collection>
```



  2.多对一


```
<association property="属性名" javaType="引用类类型" column="外键">
<id property="引用类属性" column="引用表主键" />
<result property="引用类属性" column="引用表普通列"/> 
……
</association> 
```


四．动态SQL
--------
   
   MyBatis的强大特性之一便是它的动态SQL。如果你有使用JDBC或其它类似框架的经验，你能体会到根据不同条件拼接SQL语句的痛苦。例如拼接时要确保不能忘记添加不必要的空格，还要注意去掉列表最后一个列名的逗号。
  
  1.if
  
  2.choose(when,otherwise)
  
  3.trim
  
  4.foreach
  
  job=#{job} 等号左边是列名，右边大括号内是属性名;

如果where语句中的条件都不成立，不用单独写where;若有一个成立，则会自动添加where，忽略第一个and(只忽略一次)

五．二级缓存
-----------

  1.一级缓存:基于PerpetualCache的HashMap 
  本地缓存，其存储作用域为Session，当Session flush或close之后，该Session中所有Cache就将清空。一级缓存是常开启的。

没有执行Session flush或close时，同一会话中的操作只会请求一次数据库。

**？？？一级缓存的生命周期有多长**？ 

==①MyBatis在开启一个数据库会话时，创建一个新的SqlSession对象，SqlSession对象中会有一个Executor对象。Executor对象中持有一个新的PerpetualCache对象也一并释放掉。==

==②如果SqlSession调用了close()方法，会释放掉一级缓存PerpetualCache对象，一级缓存将不可用==

==③如果SqlSession调用了clearCache(),会清空PerpetualCache对象中的数据，但是该对象仍可使用==

==④SqlSession中执行了任何一个update操作(update(),delete(),insert())，都会清空PerpetualCache对象中的数据，但是该对象可以继续使用。==

2.二级缓存：二级缓存与一级缓存其机制相同，默认也是采用PerpetualCache，HashMap存储，不同于其存储作用域为Mapper(NameSpace),并且可自定义存储源，如Ehcache。二级缓存默认不开启。


```
开启二级缓存的步骤：

1.在config.xml中加上：<setting name="cacheEnabled" values="true" />

2.在XxxMapper.cml中加上：<cache eviction="LRU" flushInterval="100000" readOnly="true" size="1024" />

3.在bean包中对当前封装类实现接口：Serializable
```

若想要重新开启一个会话，则需将之前的会话关闭。不能占用。