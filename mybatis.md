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


3.手动映射和自动映射
  
  (1)在MyBatis中，自动映射有三种模式，分别是NONE,PARTIAL,FULL。其中NONE表示不启用自动映射，PARTIAL表示只对非嵌套的resultMap进行自动映射，FULL表示只对所有的resultMap都进行自动映射。默认的自动映射模式为PARTIAL。
  
   在MyBatis的核心文件中<settings>节点下添加如下节点来控制自动映射的方式：
  
   
```
<setting name="autoMappingBehavior" value="PARTIAL"/>
```

(2)手动映射，当表中的字段和实体类中的属性名称不相同的时候，自动映射机制不能映射成功，需要手动将二者进行关联


```
<resultMap id="唯一的标识" type="映射的pojo对象">
  <id column="表的主键字段，或者可以为查询语句中的别名字段" jdbcType="字段类型" property="映射pojo对象的主键属性" /> 
  <result column="表的一个字段（可以为任意表的一个字段）" jdbcType="字段类型" property="映射到pojo对象的一个属性（须为type定义的pojo对象中的一个属性）"/>
</resultMap>
```

 当进行手动映射之后，select之类的标签，resultTypep将更改为resultMap。

```
  <select id="getById"  resultMap="resultMap的唯一的标识"></select>
```

4.会话工厂和会话
 
 (1)会话工厂类
        
①目的：如果每一次的CRUD操作都需要读取MyBatis的核心配置文件，这样就会造成资源的浪费。可以将打开数据库连接的方法编写成一个工具类，在Dao中就可以直接调用。
        
②设计思路：如果每一次都需要创建一个工厂类对象，也会造成资源的浪费。解决的方法就是将把这个工厂类设计成单例模式。

③单例模式：保证一个类只有一个实例，并且可以全局访问。
           
        A.私有化静态类对象，类加载的那一刻，就进行实例化
          
        B.私有化构造方法，确保该类不会被外部进行实例化操作。在里面初始化MyBatis
           的配置
           
        C.创建一个公共静态方法，用于获得类对象。GetLnstance()用于获得SqlSession对象，MyBatis支持手动提交事务，这里设置成false。
    
(2)会话
        
①SQL会话工厂构造器类SqlSessionFactoryBuilder的build方法用于构建SqlSessionFactory类的实例；

②SQL会话工厂类的实例用于创建sql会话SQLSession的实例；
    
③SQL会话SqlSession用于执行具体的CRUD操作，其类似于JDBC中连接类Connection;
        
④SQL会话模板SqlSessionTemplate是MyBatis为Spring提供的模板化的会话工具，是现场安全的，可以通过构造器或setter方法注入SqlSessionFactory类的实例。  
    
5.MyBatis的事务
     
A.MyBatis管理事务分为两种方式：
      
(1)使用JDBC的事务管理机制，就是利用java.sql.Connection对象完成对事务的提交；
    
(2)使用MANAGED的事务管理机制，这种机制MyBatis自身不会去实现事务管理，而是让程序的容器(JBOSS,WebLogic)来实现对事务的管理。
      
B.MyBatis事务遇到的问题
      
(1)如果开启MyBatis事务管理，则需要手动进行事务提交，否则事务会回滚到原状态；

(2)如果在；只有执行了具体操作执行完后不通过sqlSession.commit()方法进行提交，事务在sqlSession关闭时会自动回滚到原状态commit()事务提交方法才会真正完成操作；
      
(3)如果不执行sqlSession.commit()操作，直接执行sqlSession.close()，则会在close()进行事务回滚；
      
(4)如果不执行sqlSession.commit()操作也不手动关闭sqlSession,在程序结束时关闭数据库连接时会进行事务回滚。

C.事务管理入口
      
在XML配置文件中定义事务工厂类型，JDBC或者MANAGED分别对应JdbcTransactionFactory.class和ManagedTransactionFactory.class;
     
(1)如果type=”JDBC”则使用JdbcTransactionFactory事务工厂则MyBatis独立管理事务，直接使用JDK提供的JDBC来管理事务的各个环节：提交、回滚、关闭等操作；
      
(2)如果type=”MANAGED”则使用ManagedTransactionFactory事务工厂则MyBatis不在ORM层管理事务而是将事务管理托付给其他框架，如Spring；
      
D.事务对update操作的影响

事务的提交：
        
- [x]        在sqlSession中执行了update操作，需要执行sqlSession.commit()方法提交事务，不然在连接关闭时候自动回滚；
        
- [x] exector.commit()事务提交方法归根结底到底是调用了transaction.commit()事务的提交方法；
     
- [x] 如果是jdbcTransaction的commit()方法，通过调用connection.commit()方法通过数据库连接实现事务提交；
        
- [x] 如果是ManagedTransaction的commit()方法，则为空方法不进行任何操作；
        事物的回滚：
    
- [x] sqlSession执行close()关闭操作时，如果close()操作之前进行了update操作未进行commit()事务提交则会进行事务回滚然后再关闭会话；如果update后执行了commit则直接关闭会话；
      
      
E.事务对select的影响
        
事务对select操作的影响主要体现在对缓存的影响上，主要包括一级缓存和二级缓存。
     
总结：
 
(1)MyBatis可以通过xml配置是否独立处理事务，可以选择不单独处理事务，将事务托管给其它上层框架如spring等；

(2)如果MyBatis选择处理事务则事务会对数据库操作产生影响
            
        ①对update操作的影响主要体现在update操作没有进行事务提交则会话关闭时进行数据库回滚；
           
        ②对select操作的影响主要表现在二级缓存上，执行select操作后如果未进行事务提交则缓存会被放在临时缓存中，后续的select操作无法使用该缓存，直到进行commit()事务提交，临时缓存中的数据才会被正式转移到正式缓存中。


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
