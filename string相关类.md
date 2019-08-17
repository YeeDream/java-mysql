### String相关类
**一.String类**

1.字符串常量

String的值不能修改，它是常量，它隶属于java.lang包(这个包不需要导入)

```
String a="abc";//产生一个字符串
String b=new String("abc");//通过产生对象方式，产生一个字符串
String c=new String();//产生一个空字符串
char[] arr={'a','b','c','d'};
byte[] arr2={97,98,99};
```
2.获取字符串长度：==字符串名.length()==

3.获取指定位置的字符：==字符串名.charAt()==

4.获取到字符串去掉前后空格之后的形式：==字符串名.trim()==

5.获取到指定字符或者字符串在目标字符串中的第一个位置，如果不存在返回-1：==字符串名.indexOf()==

6.获取到指定字符或者字符串在目标字符串中的最后一个位置，如果不存在返回-1:==字符串名.lastIndexOf()==


```
String str=" acbchs   hdxcja       s     ";

//获取字符串长度
int len=str.length();//public int length();

//获取指定位置的字符
char chr=str.charAt(3);//public char charAt(int index);

//获取到字符串去掉前后空格之后的形式
String s1=str.trim();//public String trim();

//获取到指定字符或者字符串在目标字符串中的第一个位置，如果不存在返回-1
int index1=str.indexOf('c');//public int indexOf(int ch);
int index2=str.indexOf("ch");//public int indexOf(String ch);

//获取到指定字符或者字符串在目标字符串中的最后一个位置，如果不存在返回-1
int index3=str.lastIndexOf('c');//public int lastIndexOf(int ch);
int index4=str.lastIndexOf("cj");//public int lastIndexOf(String ch);
```
7.判断两个字符串之间的值是否相等:==字符串名1.equals(字符串名2)==

8.判断两个字符串之间的值是否相等,忽略大小写：==字符串名1.equalIsIgnoreCase(字符串名2)==

9.获取到目标字符串的大写和小写形式：

==字符串名.toUpperCasse()==   (大写形式)
                        
==字符串名.toLowerCase()==     (小写形式)

10.将字符串1和字符串2字符串拼接成一个新的字符串：

①==字符串1.concat(字符串2)==
                                                 
                                    
②==字符串1+字符串2==


```
//判断两个字符串之间的值是否相等
boolean b1=str1.equals(str2);//public boolean equals(String str);
//判断两个字符串之间的值是否相等,忽略大小写
boolean b2=str1.equalsIgnoreCase(str2);

//获取到目标字符串的大写和小写形式
String s1=str1.toUpperCase();
String s2=str1.toLowerCase();

//将str1和str2字符串拼接成一个新的字符串
String s3=str1.concat(str2);
String s4=str1+str2;
```

11.获取到目标字符串从第几个开始（从0开始数），到自然结束的新字符串：==字符串名.substring(数字)==

12.获取到目标字符串从第几个开始（从0开始数）,到第几个（从1开始数）结束的字符串：
==字符串名.substring(数字，数字)；==

13.将目标字符串按照相应格式进行分割，分割成一个字符串数组：==字符串名.split(",")==;(按照“，”的格式分割)

14.替换目标字符串中相应内容为指定内容：==字符串名.replace("字符"，"要替换的内容")==

15.获取到字符串的字符数组形式:==字符串名.toCharArray()==

16.判断字符串是否为空:==字符串名.isEmpty()==

17.判断字符串是否以什么开头:==字符串名.startWith("aa")==

18.判断字符串是否以什么开结尾:==字符串名.endsWith("aa")==

19.获取目标字符串的字符串形式：==字符串名.toString()==


```
//获取到目标字符串从第几个开始（从0开始数），到自然结束的新字符串
String s1=str.substring(3);

//获取到目标字符串从第几个开始（从0开始数）,到第几个（从1开始数）结束的字符串
String s2=str.substring(2,4);

String s="ash,dj,hs,kfa,sdk,lf";
//将目标字符串按照相应格式进行分割，分割成一个字符串数组
String[] arr=s.split(",");

//替换目标字符串中相应内容为指定内容
String s3=str.replace("s","好");

//获取到字符串的字符数组形式
char[] crr=str.toCharArray();

//判断字符串是否为空
boolean b=str.isEmpty();

//判断字符串是否以什么开头
boolean b1=str.startsWith("aa");
//判断字符串是否以什么开结尾
boolean b2=str.endsWith("aa");

//获取目标字符串的字符串形式
String s4=str.toString();
```


**二.StringBuffer与StringBuilder**

1.StringBuffer:线程安全的可变字符序列

2.StringBuilder:线程不安全的可变字符序列,其余同StringBuffer

3.String与StringBuffer的区别？
  
    a.String是一个字符串常量，而StringBuffer是一个字符串缓冲区，值可以修改
  
    b.String的值因为不能修改，所以共享，而StringBuffer不共享
    
    c.如果字符串被频繁大量修改，建议使用StringBuffer效率高些，反之使用String

4.
```
//构造一个其中不带字符的字符串缓冲区，其初始容量为16个字符
StringBuffer s1 = new StringBuffer();

//构造一个字符串缓冲区，并将其内容初始化为指定的字符串内容
StringBuffer s2 = new StringBuffer("abc");

//构造一个不带字符，但具有指定初始容量的字符串缓冲区
StringBuffer s3 = new StringBuffer(3);
```
5.

```
//将StringBuffer转化成String
String s=str.toString();

//反转
StringBuffer s1=str.reverse();

//追加
str.append("好");

//在第3个之前添加内容“啊”（从0开始数）
str.insert(3,"啊");

//从1开始数，删除，从3个到第5个（也就是说，删除从1开始数的第4个元素）
str.delete(3,5);
```
6.

```
//将String----->StringBuffer
StringBuffer str1=new StringBuffer(s1);

//将StringBuffer---->String
String str2=s2.toString();
```


