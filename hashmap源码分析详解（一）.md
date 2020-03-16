HashMap源码
-------------------------
###### 1.hash算法介绍
   
散列表，又称哈希表，基于快速存取。时间换空间的算法。理解为线性表。
   
   根据关键码值（key,value）而直接进行访问的数据结构，通过把关键码值映射到表中一个位置来访问记录，加快查找的速度。
   
   有冲突：两个元素通过散列函数得到的地址相同，两个元素成为“同义词”

###### 2.hashmap源码分析

  ○构造方法
  

```java
public HashMap(Map<? extends K, ? extends V> m) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        putMapEntries(m, false);
} 

//空参构造方法 
public HashMap() {
        //0.75f 负载因子赋值
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
}  
 
//int类型构造方法    初始化容量
public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
}   

//跟传容量的构造方法一致，只不过自定义了负载因子
public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
}
```
○int类型构造方法

```java
public HashMap(int initialCapacity, float loadFactor) {
      //判断容量（initialCapacity）
      if (initialCapacity < 0)
          throw new IllegalArgumentException("Illegal initial capacity: " +initialCapacity);
     //判断容量的最大值
if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
     //负载因子判断
      if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +loadFactor);
        this.loadFactor = loadFactor;
        //tableSizeFor
        this.threshold = tableSizeFor(initialCapacity);
}

//initialCapacity=12
//求2的n次方 >= initialCapacity
static final int tableSizeFor(int cap) {
//n=12-1=11
    int n = cap - 1;
//      0000 1011-11
    n |= n >>> 1;
//右移  0000 0101=5
// 异或 0000 1111=15=n
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```
○参数是Map(Map<? extends K, ? extends V> m)

```java
final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
  //传入集合大小  
int s = m.size();
    if (s > 0) {
        //判断transient Node<K,V>[] table数组是否为null
        if (table == null) { // pre-size
            float ft = ((float)s / loadFactor) + 1.0F;
            int t = ((ft < (float)MAXIMUM_CAPACITY) ?
                     (int)ft : MAXIMUM_CAPACITY);
            if (t > threshold)
                threshold = tableSizeFor(t);
        }
        else if (s > threshold)
            //扩容
            resize();
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            K key = e.getKey();
            V value = e.getValue();
            putVal(hash(key), key, value, false, evict);
        }
    }
}

//获取key的hash值
static final int hash(Object key) {
    int h;
    //int 32位  1111 1111 1111 1111 0000 0000 0000 0000
    //对应的hashcode值^(异或)    对象的hashcode值的高位（前16位）
    //目的：提高hashcode的随机性
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}

解决冲突问题：
例如：1101 1001=hashcode,容量是15=0000 1111
9 % 15 = 9 
1101 1001
0000 1101
--------- ^
1101 0100
0000 1111
--------- &
0000 0100
```


详细视频可观看csdn上面称伟鑫老师的免费视频课程：深入讲解HashMap底层原理
视频链接：https://edu.csdn.net/course/detail/26100