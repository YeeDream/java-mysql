HashMap源码分析详解（二）
-----------------


○     HashMap的删除remove()方法

```java
public V remove(Object key) {
    Node<K,V> e;
    return (e = removeNode(hash(key), key, null, false, true)) == null ?
        null : e.value;
}

final Node<K,V> removeNode(int hash, Object key, Object value,boolean matchValue, boolean movable) {
    Node<K,V>[] tab; Node<K,V> p; int n, index;  
    if ((tab = table) != null && (n = tab.length) > 0 &&
        //元素要存储的位置p
        (p = tab[index = (n - 1) & hash]) != null) {
           Node<K,V> node = null, e; K k; V v;
    //hash上没有冲突
    if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            //定位删除的节点node
            node = p;
    //有冲突，不止是一个元素在同一个位置
    else if ((e = p.next) != null) {
            if (p instanceof TreeNode)
               //红黑树定位删除元素
                node = ((TreeNode<K,V>)p).getTreeNode(hash, key);
            else {
                //链表，定位删除元素
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key ||
                         (key != null && key.equals(k)))) {
                        node = e;
                        break;
                    }
                    p = e;
                } while ((e = e.next) != null);
            }
        }
        //node要删除的元素
        if (node != null && (!matchValue || (v = node.value) == value ||(value != null && value.equals(v)))) {
            if (node instanceof TreeNode)
                //红黑树删除节点
                ((TreeNode<K,V>)node).removeTreeNode(this, tab, movable);
            else if (node == p)
                //链表删除节点
                tab[index] = node.next;
            else
                //数组中p位置的对象下一个元素 =  删除元素的下一个元素
                p.next = node.next;
            ++modCount;
            --size;
            afterNodeRemoval(node);
            return node;
        }
    }
    return null;
}
```

○     hashmap的put方法详解

```java
public V put(K key, V value) {
    //首先根据传入参数k，获取hashcode值
    return putVal(hash(key), key, value, false, true);
}

//onlyIfAbsent-false
//evict-true
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    //判断table数组是否为空或长度为0
    if ((tab = table) == null || (n = tab.length) == 0)
        //初始化table
        n = (tab = resize()).length;
       //16 - 1 = 15 & hash值 = i
       //i=元素在tab数组中存储的位置
       //p = tab[i]   p链表 == null
        //（n-1）& hash 取余运算，目的：优化计算速度
    if ((p = tab[i = (n - 1) & hash]) == null)
        //创建节点，直接存放到tab[i]位置
        tab[i] = newNode(hash, key, value, null);
    else {
         //tab[i]上有元素的情况
        Node<K,V> e; K k;值相同的情况
        //hash值相同的情况
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        //判断是否是树结构    jdk——红黑树优化方法
        else if (p instanceof TreeNode)
            //基于红黑树的插入逻辑
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            //链表插入元素
            for (int binCount = 0; ; ++binCount) {
                 //判断p的下一个元素是否null
                if ((e = p.next) == null) {
                    //p的下一个元素 = newNode(hash, key, value, null);
                    p.next = newNode(hash, key, value, null);
                   //判断当前链表的数量·是否大于树结构的阈值
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                       //转换结构
                       //链表——>红黑树（来优化查询性能）
                        treeifyBin(tab, hash);
                    break;
                }
                //当前链表包含要插入的值，结束遍历
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        //判断插入的值是否存在hashmap中
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
     //修改次数+1
    ++modCount;
    //判断当前数组大小是否大于阈值
    if (++size > threshold)
        //扩容操作
        resize();
    afterNodeInsertion(evict);
    return null;
}
```

○     resize扩容

```java
final Node<K,V>[] resize() {
    //数组初始值
    Node<K,V>[] oldTab = table;
    //扩容前的变量初始化
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
     //扩容后的变量初始化
    int newCap, newThr = 0;
    if (oldCap > 0) {
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        //oldCap << 1    乘以2  =  新的容量
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            //oldThr  乘以2  =  newThr
            newThr = oldThr << 1; // double threshold
    }
    else if (oldThr > 0) // initial capacity was placed in threshold
        newCap = oldThr;
    else {               // zero initial threshold signifies using defaults
        //调用的无参构造方法，进入这个分支
        newCap = DEFAULT_INITIAL_CAPACITY;
        //负载因子 * 初始容量 = 阈值
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr;
    //创建一个*2的容量的数组
    @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
     //准备重新对元素进行定位
    if (oldTab != null) {
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            //获取第j个位置的元素
            if ((e = oldTab[j]) != null) {
                //清空原数组
                oldTab[j] = null;
               //判断原有j位置上是否有元素
                if (e.next == null)
                   //重新计算位置，进行元素保存
                   //例如：16
                   //[e.hash * 31 = 新元素的位置]
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                    //红黑树拆分
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else { // preserve order
                    Node<K,V> loHead = null, loTail = null;
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    do {
                       //遍历链表，将链表节点按照顺序进行分组
                        next = e.next;
                        //计算原有元素在扩容后，还在原位置
                        if ((e.hash & oldCap) == 0) {
                            //old链表添加到一组
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        //计算原有元素在扩容后，不在原位置
                        else {
                            //new链表添加到一组
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        //原位置j + 原容量 = 新的位置
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
     //扩容之后的数组返回
    return newTab;
}
```


○   hashmap的遍历

```java
final Node<K,V> nextNode() {
    Node<K,V>[] t;
    Node<K,V> e = next;
    if (modCount != expectedModCount)
        //报错的原因
        throw new ConcurrentModificationException();
    if (e == null)
        throw new NoSuchElementException();
    if ((next = (current = e).next) == null && (t = table) != null) {
        //寻找数组中下一个hash槽中不为空的节点
        do {} while (index < t.length && (next = t[index++]) == null);
    }
    return e;
}
```
