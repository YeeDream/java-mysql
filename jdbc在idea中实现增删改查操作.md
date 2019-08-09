首先在idea中建立一个webapp项目，将数据库的jar包导进去，然后建立四个包：bean,biz,db,dao,还有一个测试类，biz暂时用不到。具体操作方法可参考我的“***Maven仓库与JDBC在idea中的使用***”。
https://blog.csdn.net/weixin_44694317/article/details/98789164

---

**一.在bean包中建立 jdbc_dome 类：以jdbc_dome表为例**

```
public class jdbc_dome {
    private String UserName;
    private String Passward;

    public jdbc_dome(){
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String username) {
        UserName = username;
    }

    public String getPassward() {
        return Passward;
    }

    public void setPassward(String passward) {
        Passward = passward;
    }
}
```
**二.在db包建立DBManager类：连接数据库**

```
import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {
    /**
     *负责获取到数据库的连接
     * 获取到数据库连接为conn
     */
    public static Connection getConnection(){
        //驱动类的完整路径
        String driverClassName="com.mysql.cj.jdbc.Driver";
        //MySQL数据库的URL地址，变的主要有：IP,端口号，数据库名
        String url="jdbc:mysql://localhost:3306/db4(这块是数据库名,我的库名是db4)?characterEncoding=UTF8"+
                "&userSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true";
        String user="root";
        String password="数据库密码";
        Connection conn=null;
        //加载驱动文件
        try{
            Class.forName(driverClassName);
            conn= DriverManager.getConnection(url,user,password);

            String sql="select * from jdbc_dome";
        }catch (Exception e){
            System.out.println("数据库连接时出现问题："+e.getMessage());
        }
        return conn;
    }
 }
```
**三.在dao包中建立jdbcDAO类：进行增删改查操作**

1.插入操作

```
import db.DBManager;
import bean.jdbc_dome;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class jdbcDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    //插入
    public void saveJdbc_dome(jdbc_dome jd){
        conn= DBManager.getConnection();
        String sql="insert into jdbc_dome values(?,?)";
        try{
            System.out.println(sql);
            ps=conn.prepareStatement(sql);

            ps.setString(1,jd.getUserName());
            ps.setString(2,jd.getPassward());
            int result=ps.executeUpdate();
            System.out.println(result);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
```

2.删除操作

```
//删除
public void deleteJdbc_dome(String username){
        String sql = " delete from jdbc_dome where username=?";
        conn = DBManager.getConnection();
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
```

3.更新操作

```
//更新
public void updateJdbc_dome(String username){
        String sql = "update jdbc_dome set passward=? where username=?";
        conn = DBManager.getConnection();
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
```

4.查询操作

```
//查询
public List<jdbc_dome> findAll(){
        String sql = "SELECT * FROM jdbc_dome";
        List<jdbc_dome> list = new ArrayList<>();
        conn = DBManager.getConnection();
        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                jdbc_dome jd = new jdbc_dome();
                jd.setUserName(rs.getString(1));
                jd.setPassward(rs.getString(2));
                list.add(jd);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }
```

**四.在test类中进行测试**

1.插入操作：

```
public class jdbc_domeTest{
	public static void a(){
        jdbc_dome jd=new jdbc_dome();
        jdbcDAO ja=new jdbcDAO();
        jd.setUserName("qqqqqq");
        jd.setPassward("123456");
        ja.saveJdbc_dome(jd);
    }
    public static void main(String[] args){
        a();
    }
}
```
结果如图：

==插入之前：==

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190809162623641.png)

==插入之后：==

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190809162759359.png)

2.删除操作：

```
public static void b(){
        jdbc_dome jd=new jdbc_dome();
        jdbcDAO ja=new jdbcDAO();
        jd.setUserName("12345");
        ja.deletJdbc_dome("12345");
    }
```
结果如下：

==原图可参考上图，删除了用户名为12345的：==

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190809162918755.png)

3.更新操作：

 - [ ] 下面的就不截图啦，跟上述方法一致
```
 public static void c(){
        jdbc_dome jd=new jdbc_dome();
        jdbcDAO ja=new jdbcDAO();
        jd.setUserName("qqqqqq");
        jd.setPassward("jackson");
        ja.updateJdbc_dome("qqqqqq");
    }
```
4.查询操作

```
 public static void d(){
        jdbcDAO ja=new jdbcDAO();
        ja.findAll();
    }
```
我们可以在数据库上查看我们的更新，删除，插入操作是否成功！
在后面我们还会有更简单的对于数据库操作的方法！
以上就是一个简单的maven中对于jdbc的增删改查，还有很多不完善的地方*=**=*