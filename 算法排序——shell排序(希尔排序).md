  前面我们学习了冒泡排序、选择排序、插入排序，不知道大家清楚没这三种算法原理没？如果还不太明白，可要抓紧时间理清楚咯！从这次我们将要学习新的算法啦！所以宝宝们要加油哦！！！(我们都是爱学习的小宝宝)
  
  =============================================================

 Shell 排序算法严格来说基于插入排序的思想，其又称为希尔排序或者缩小增量排序。Shell排序算法的排序流程如下：

（1）将有n个元素的数纠分成n/2个数字序列，第1个数据和第n/2+1个数据为一对，.…..

（2）一次循坏使每一个序列对排好顺序。 

（3）然后，再变为n/4个序列，再次排序。

（4）不断重复上述过程，随着序列减少最后变为一个，也就完成了整个排序。

为了更好地理解Shell排序算法的执行过程，下面举一个实际数据的例子来一步-一步地执行Shell过程，如图4-8所示。 排序算法。6 个整型数据 127、118、105、101、112、100 是一组无序的数据。对其执行 Shell 排序过程：


    初始数据：127  118  105  101  112  100

    一次排序：~~101~~   **112**  ==100==  ~~127~~   **118**  ==105==
 
    二次排序：100  101  105  112  118  127

Shell 排序算法的执行步骤如下：

（1）第1次排序，首先将数组分为6/2=3个数字序列，第1个数据127和第4个数据101为一对，第2个数据118和第5个数据112为一对，第3个数据105和第6个数据100为一对。每一对数据进行排序后的数据为101、112、100、127、118、105. 

（2）第2次排序，将数组分为6/4=1个序列（这里执行取整操作），此时逐个对数据比较，按照插入排序算法对该序列进行排序。排序后的数据为 100、101、105、112、118、127.

从上面的例子可以非常直观地了解到 Shell 排序算法的执行过程。插入挂序时，*如果原数据己经是基本有序的，则排序的效率就可大大提高。*另外，对于数量较小的序列使用直接插入挂序，因需要移动的数据量较少，其效率也较高。因此，
Shell排序算法具有比较高的执行效率。

我们来根据代码看看：
这里有两种方法，一种是交换法，一种是移位法

```java
     //交换法
    //希尔排序，逐步推导
    public static void shellSort(int[] arr){

        //根据前面的逐步分析，使用循环处理
        for(int gap=arr.length/2;gap>0;gap/=2){
            int temp=0;
            int count=0;
            for(int i=gap;i<arr.length;i++){
                //遍历各组中所有的元素（共有gap组，每组有2个元素）,步长gap
                for(int j=i-gap;j>=0;j-=gap){
                    //如果当前元素大于加上步长后的元素，说明交换
                    if(arr[j]>arr[j+gap]){
                        temp=arr[j];
                        arr[j]=arr[j+gap];
                        arr[j+gap]=temp;
                    }
                }
            }
            System.out.println("希尔排序第"+(++count)+"轮后:"+Arrays.toString(arr));
        }
```

交换法结果截图：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20191024200357375.png)

```java
//对交换式的希尔排序改成移位法
    public static void shellSort2(int[] arr){
        int count=0;
       //增量gap,并逐步的缩小增量
        for(int gap=arr.length/2;gap>0;gap/=2){
            //从第gap个元素，逐个对其所在的组进行直接插入排序
            for(int i=gap;i<arr.length;i++){
                int j=i;
                int temp=arr[j];
                if(arr[j]<arr[j-gap]){
                    while (j-gap>=0 && temp<arr[j-gap]){
                        //移动
                        arr[j]=arr[j-gap];
                        j -= gap;
                    }
                    //当退出while循环时，就给gap找到了插入的位置
                    arr[j]=temp;
                }
            }
        }
        //System.out.println("希尔排序第"+(++count)+"轮后:"+Arrays.toString(arr));

    }
```

移位法结果截图：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20191024200454225.png)

可见移位法比交换法更好！

我们来看一看随机80000个数据，它的效率如何？

```java
//创建要给80000个数据的数组
        int[] arr=new int[80000];
        for(int i=0;i<80000;i++){
            arr[i]=(int)(Math.random()*8000000);//生成一个[0,8000000)数
        }

        Date date1=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str=simpleDateFormat.format(date1);
        System.out.println("排序前的时间："+date1Str);

        shellSort2(arr);

        Date date2=new Date();
        String date2Str=simpleDateFormat.format(date2);
        System.out.println("排序后的时间："+date2Str);

```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20191024200920847.png)

看！排序连一秒都不到哦。快不快？

完整代码为大家附上，其中里面有每一步的排序过程，最后根据规律，写出统一性算法：

```java
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author DreamYee
 * @Create 2019/10/24  8:55
 */
public class ShellSort {
    public static void main(String[] args) {
        //int[] arr={8,9,1,7,2,3,5,4,6,0};
        //shellSort2(arr);


        //创建要给80000个数据的数组
        int[] arr=new int[80000];
        for(int i=0;i<80000;i++){
            arr[i]=(int)(Math.random()*8000000);//生成一个[0,8000000)数
        }

        Date date1=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str=simpleDateFormat.format(date1);
        System.out.println("排序前的时间："+date1Str);

        shellSort2(arr);

        Date date2=new Date();
        String date2Str=simpleDateFormat.format(date2);
        System.out.println("排序后的时间："+date2Str);


    }

    //交换法
    //希尔排序，逐步推导
    public static void shellSort(int[] arr){

        //根据前面的逐步分析，使用循环处理
        for(int gap=arr.length/2;gap>0;gap/=2){
            int temp=0;
            int count=0;
            for(int i=gap;i<arr.length;i++){
                //遍历各组中所有的元素（共有gap组，每组有2个元素）,步长gap
                for(int j=i-gap;j>=0;j-=gap){
                    //如果当前元素大于加上步长后的元素，说明交换
                    if(arr[j]>arr[j+gap]){
                        temp=arr[j];
                        arr[j]=arr[j+gap];
                        arr[j+gap]=temp;
                    }
                }
            }
            System.out.println("希尔排序第"+(++count)+"轮后:"+Arrays.toString(arr));
        }

        /*
        int temp=0;
        //希尔排序的第1轮
        //是将个数据分为5组
        for(int i=5;i<arr.length;i++){
            //遍历各组中所有的元素（共有5组，每组有2个元素）,步长5
            for(int j=i-5;j>=0;j-=5){
                //如果当前元素大于加上步长后的元素，说明交换
                if(arr[j]>arr[j+5]){
                    temp=arr[j];
                    arr[j]=arr[j+5];
                    arr[j+5]=temp;
                }
            }
        }
        System.out.println("希尔排序1轮后："+Arrays.toString(arr));

        //第2轮
        for(int i=2;i<arr.length;i++){
            //遍历各组中所有的元素（共有5组，每组有2个元素）,步长5
            for(int j=i-2;j>=0;j-=2){
                //如果当前元素大于加上步长后的元素，说明交换
                if(arr[j]>arr[j+2]){
                    temp=arr[j];
                    arr[j]=arr[j+2];
                    arr[j+2]=temp;
                }
            }
        }
        System.out.println("希尔排序2轮后："+Arrays.toString(arr));

        //第3轮
        for(int i=1;i<arr.length;i++){
            //遍历各组中所有的元素（共有5组，每组有2个元素）,步长5
            for(int j=i-1;j>=0;j-=1){
                //如果当前元素大于加上步长后的元素，说明交换
                if(arr[j]>arr[j+1]){
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
        System.out.println("希尔排序3轮后："+Arrays.toString(arr));
        */
    }

    //对交换式的希尔排序改成移位法
    public static void shellSort2(int[] arr){
        int count=0;
        //增量gap,并逐步的缩小增量
        for(int gap=arr.length/2;gap>0;gap/=2){
            //从第gap个元素，逐个对其所在的组进行直接插入排序
            for(int i=gap;i<arr.length;i++){
                int j=i;
                int temp=arr[j];
                if(arr[j]<arr[j-gap]){
                    while (j-gap>=0 && temp<arr[j-gap]){
                        //移动
                        arr[j]=arr[j-gap];
                        j -= gap;
                    }
                    //当退出while循环时，就给gap找到了插入的位置
                    arr[j]=temp;
                }
            }
        }
        //System.out.println("希尔排序第"+(++count)+"轮后:"+Arrays.toString(arr));

    }

}

```

好啦，今天就说到这吧，如果还有问题的话，就留言吧！很期待互相交流想法呦。



