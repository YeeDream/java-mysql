AJAX
-----

AJAX=Asynchronous and XML(异步的JavaScript和xml)

AJAX是一种用于创建快速动态网页的技术。

AJAX不是新的编程语言，而是一种使用现有的标准的新方法。

AJAX 是一种在无需重新加载整个网页的情况下，能够更新部分网页的技术。

**1.XMLHttpRequest是AJAX的基础**

(1)XMLHttpRequest对象

所有现代浏览器均支持XMLHttpRequest对象

XMLHttpRequest用于在后台与服务器交换数据。这意味着可以在不重新加载整个网页的情况下，对网页的某部分进行更新。

(2)创建XMLHttpRequest对象的语法


```
 variable=new XMLHttpRequest();
```


(3)向服务器发送请求

```
xmlhttp.open("GET","test1.txt",true);
xmlhttp.send();

```

(4)GET还是POST?

与POST相比，GET更简单也更快，并且在大部分情况下都能用

然而，在以下情况下，请使用POST请求：

    ①无法使用缓存文件（更新服务器上的文件或数据库）

    ②向服务器发送大量数据（POST没有数据量限制）

    ③发送包含未知字符的用户输入时，POST比GET更稳定也更可靠


```
例：
一个简单的GET请求：
xmlhttp.open("GET","文件名",true);
xmlhttp.send();

```
完整代码：

```
<!--ajax_div.html-->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<script type="text/javascript">
			function loadXMLDoc()
            {
             var xmlhttp;
             if (window.XMLHttpRequest)
             {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp=new XMLHttpRequest();
             }else
             {// code for IE6, IE5
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
             }
                xmlhttp.onreadystatechange=function(){
                  if (xmlhttp.readyState==4 && xmlhttp.status==200){
                     document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
                  }
                }
                xmlhttp.open("GET","ajax_test.asp",true);
                xmlhttp.send();
            }
		</script>
	</head>
	<body>
		<h2>AJAX</h2>
		<button type="button" onclick="loadXMLDoc()">请求数据</button>
		<div id="myDiv"></div>
	</body>
</html>



<!--ajax_test.asp-->
<!DOCTYPE html>
<html>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<head>
		<title>ASP Page</title>
	</head>
	<body>
	   <p>哈哈哈哈哈</p> 
	</body>
</html>
```
向URL添加一个唯一的ID:


```
xmlhttp.open("GET","文件名?t=" + Math.random(),true);
xmlhttp.send();
```

```
例：
一个简单的POST请求：
xmlhttp.open("POST","文件名",true);
xmlhttp.send();
```
如果需要像HTML表单那样POST数据，请使用setRequestHeader()来添加HTTP头


```
xmlhttp.open("POST","文件名",true);
xmlhttp.setRequestHeader("Content-type","application/X-www-form-urlencoded");
xmlhttp.send("fname=Bill&lname=Gates");
```
(5)URL-服务器上的文件

open()方法的url参数是服务器上文件的地址

```
xmlhttp.open("GET","文件名",true);
```
该文件可以是任何类型的文件，比如：.txt和.xml，或者服务器脚本文件，比如：.asp和.php（在传回响应之前，能够在服务器上执行任务）。

(6)异步-True或False?

AJAX=Asynchronous and XML(异步的JavaScript和xml)

XMLHttpRequest 对象如果要用于 AJAX 的话，其 open() 方法的 async 参数必须设置为 true：


```
xmlhttp.open("GET","文件名",true);
```
对于web开发人员来说，发送异步请求是一个巨大的进步。很多在服务器执行的任务都相当费时。AJAX 出现之前，这可能会引起应用程序挂起或停止。

通过AJAX，JavaScript无需等待服务器的响应，而是：

·在等待服务器响应时执行其它脚本

·当响应就绪后对响应进行处理

**2.AJAX-服务器响应**

如需获得来自服务器的响应，请使用XMLRequest对象的responseText或responseXML属性

responseText：获得字符串形式的响应数据

responseXML：获得xml形式的相应数据

(1)responseText属性

如果来自服务器的响应并非xml，请使用responseText属性

responseText属性返回字符串形式的响应，因此可以这样使用：

```
document.getElementByld("myDiv").innerHTML=xmlhttp.responseText;
```

(2)responseXML属性

如果来自服务器的响应是xml,而且需要作为xml对象进行解析，请使用responseXML属性


**3.AJAX - onreadystatechange 事件**

当请求被发送到服务器时，我们需要执行一些基于响应的任务。

每当readyState改变时，就会触发onreadystatechange事件。

readyState属性有XMLHttpRequest的状态信息。


**4.使用Callback函数**

callback 函数是一种以参数形式传递给另一个函数的函数。

如果您的网站上存在多个 AJAX 任务，那么您应该为创建 XMLHttpRequest 对象编写一个标准的函数，并为每个 AJAX 任务调用该函数。



