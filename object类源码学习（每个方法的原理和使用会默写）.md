写的不是很详细，欢迎大家来补充

--------------------

(1)clone()      

**要点**

- clone方法用于对象的克隆，一般想要克隆出的对象是独立的(与原有的对象是分开的)

- 深拷贝指的是该对象的成员变量(如果是可变引用)都应该克隆一份，浅拷贝指的是成员变量没有被克隆一份

**clone用法**

a.实现Cloneable接口

b.重写克隆方法

```
@Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
```

(2)finalize()
     
 finalize()方法将在垃圾回收器清除对象之前调用，但该方法不知道何时调用，具有不定性，所以不建议重写。这个方法在gc启动，该对象被回收的时候被调用。gc可以回收大部分的对象（凡是new出来的对象，gc都能搞定，一般情况下我们又不会用new以外的方式去创建对象），所以一般是不需要程序员去实现finalize的。 特殊情况下，需要程序员实现finalize，当对象被回收的时候释放一些资源，比如：一个socket链接，在对象初始化时创建，整个生命周期内有效，那么就需要实现finalize，关闭这个链接。

使用finalize还需要注意一个事，调用super.finalize();

一个对象的finalize()方法只会被调用一次，而且finalize()被调用不意味着gc会立即回收该对象，所以有可能调用finalize()后，该对象又不需要被回收了，然后到了真正要被回收的时候，因为前面调用过一次，所以不会调用finalize()，产生问题。

所以，推荐不要使用finalize()方法，它跟析构函数不一样。

(3)equals(Object obj)

equals()方法默认是比较对象的地址，使用的是==等值运算符。但是按我们正常开发来说，比较的是对象地址是没有意义的。


```
@Override
    public boolean equals(Object obj)
    {
       if(obj == null) {
           return false;

       }

         //如果是同一个对象返回true，反之返回false
       if(this == obj){
           return true;
       }

         //判断是否类型相同
       if(this.getClass() != obj.getClass()){
           return false;
       }

         Student student = (Student)obj;
         return id==student.id;
     }
```

(4)hashcode()


```
@Override
    public int hashCode() {
        return this.getId();
    }
```


```
 1. 重写equals()方法，就必须重写hashCode()的方法
 2. equals()方法默认是比较对象的地址，使用的是==等值运算符
 3. hashCode()方法对底层是散列表的对象有提升性能的功能，例如HashMap
 4. 同一个对象(如果该对象没有被修改)：那么重复调用hashCode()那么返回的int是相同的！
 5. hashCode()方法默认是由对象的地址转换而来的 equals()方法还有5个默认的原则：
   自反性--->调用equals()返回的是true，无论这两个对象谁调用equals()都好，返回的都是true   
   一致性--->只要对象没有被修改，那么多次调用还是返回对应的结果！   
   传递性--->x.equals(y)和y.equals(z)都返回true，那么可以得出：x.equals(z)返回true   
   对称性--->x.equals(y)和y.equals(x)结果应该是相等的。 传入的参数为null，返回的是false
```

(5)registerNatives()

(6)toString()
     
返回对象的hashCode值()，用来标识对象


```
public String toString() {//返回当前类的绝对路径+“@”+哈希码的无符号十六进制
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
```

==(7)notify()==

==(8)notifyAll()==

==(9)wait()==

- 无论是wait、notify还是notifyAll()都需要由监听器对象(锁对象)来进行调用。简单来 说：他们都是在同步代码块中调用的，否则会抛出异常！
- notify()唤醒的是在等待队列的某个线程(不确定会唤醒哪个)，notifyAll()唤醒的是等待队列所有线程
- 导致wait()的线程被唤醒可以有4种情况 该线程被中断


```
a.wait()时间到了
b.被notify()唤醒
c.被notifyAll()唤醒
d.调用wait()的线程会释放掉锁
```

(10)wait(long timeout)

(11)wait(long timeout,int nanos)//毫微秒级


