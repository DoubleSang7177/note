### 用户注册

#### 1 创建数据库表

```sql
create database store;

use  store;

create table t_user(
    uid int auto_increment comment '用户id',
    username varchar(20) not null unique comment '用户名',
    password char(32) not null comment '密码',
    salt char(36) comment '盐值',
    phone varchar(20) comment '电话号码',
    email varchar(30) comment '电子邮箱',
    gender int comment '性别：0-女，1-男',
    avatar varchar(50) comment '头像',
    is_delete int comment '是否删除：0-未删除，1-已删除',
    created_user varchar(20) comment '日志-创建人',
    created_time datetime comment '日志-创建时间',
    modified_user varchar(20) comment '日志-最后修改执行人',
    modified_time datetime comment '日志-最后修改时间',
    primary key (uid)
) engine = InnoDB default charset =utf8;
```

#### 2 创建用户的实体类

BaseUser类

```java
package com.cy.store.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
/*
* @Data包含了@ToString、@EqualsAndHashCode、@Getter / @Setter和@RequiredArgsConstructor的功能。
* 不包含@AllArgsConstructor和@NoArgsConstructor
* */
public class BaseEntity implements Serializable {
    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date m
```

User类

```java
package com.cy.store.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class User extends BaseEntity implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;

}

```

#### 3 注册-持久层

###### 3.1 规划需要执行的sql语句

###### 3.2 设计接口和抽象方法

UserMapper

```java
package com.cy.store.mapper;
import com.cy.store.entity.User;
/*
* 用户模块吧的持久层接口
* */
/*
@Mapper
这样写所有mapper接口都要加这个注解，所以直接加在启动类上
*/
public interface UserMapper {
    /*
    * 插入用户数据（注册）
    * return受影响的行数（增删改都以受影响的行数作为返回值
    * */
    Integer insert(User user);
    /*
    * 根据用户名来查询用户数据
    * */
    User findByUsername(String username);
}

```

###### 3.3 编写映射

**UserMapper.xml**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace属性：用于指定当前的映射文件和哪个接口进行映射，需要指定接口的文件路径，需要标注包的完整路径-->
<mapper namespace="com.cy.store.mapper.UserMapper">
    <!-- 
    自定义映射规则：resultMap 
    id属性：标签给这个映射负责分配唯一的id值
    type属性：取值是一个类，表示数据库中的查询结果与java中的哪个实体类进行结果集的映射
    -->
    <resultMap id="UserEntityMap" type="com.cy.store.entity.User">
        <!-- 将表的字段与类的属性名不一样的进行匹配指定，名称一致的省略不写,但是主键不可以省略！！！ -->
        <result column="uid" property="uid"/>
        <result column="is_delete" property="isDelete"/>
        <result column="created_user" property="createdUser"/>
        <result column="created_time" property="createdTime"/>
        <result column="modified_user" property="modifiedUser"/>
        <result column="modified_time" property="modifiedTime"/>
    </resultMap>
    <!--id属性：指映射接口中的方法名，在标签内部直接编写sql语句-->
    <!--
    resultType:表示查询的结果集类型，只需要指定对应映射类的类型，并且包含完整包接口：resultType="com.cy.store.entity.User
    resultMap:当表的字段名和类的对象属性的字段名称不一样时，来自定义查询结果的映射规则
    -->
    <select id="findByUsername" resultMap="UserEntityMap">
        select *
        from t_user
        where username = #{username}
    </select>
    <!--useGeneratedKeys="true"开启主键自增 并通过keyProperty指明是哪个属性作为主键递增-->
    <insert id="insert" useGeneratedKeys="true" keyProperty="uid">
        INSERT INTO t_user(
            uid,username,password,salt,phone,email,gender,avatar,is_delete,
            created_user,created_time,modified_user,modified_time
        ) values (#{username},#{password},#{salt},,#{phone},#{email},#{gender},#{avatar},#{isDelete},
                  #{createdUser},#{createdTime},#{modifiedUser},#{modifiedTime})
    </insert>
</mapper>
```

##### 3.4 将mapper文件的位置注册到properties对应的配置文件中

```properties
mybatis.mapper-locations=classpath:mapper/*.xml
```

3. 5 编写mapper接口的测试代码
   
   ```java
   package com.cy.store.mapper;
   
   import com.cy.store.entity.User;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.mybatis.spring.annotation.MapperScan;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   import org.springframework.test.context.junit4.SpringRunner;
   
   @MapperScan("com.cy.store.mapper")
   @SpringBootTest
   /*
   @SpringBootTest 标志当前的类是一个测试类，不会随同项目一块打包（过滤作用）
   * */
   @RunWith(SpringRunner.class)
   /*
   * @RunWith：表示启动这个单元测试类，如果不写测试类不能运行，传递参数固定，必须是SpringRunner的实体类型
   * */
   public class UserMapperTests {
       /*
       * 单元测试方法：可以单独独立运行，不用启动整个项目，提升了代码的测试效率
       * 1.必须用@Test修饰
       * 2.返回值类型必须是void
       * 3.方法的参数列表不指定任何类型
       * 4.方法的访问修饰符必须是public
       * */
   
       /*
       *报错原因：idea有检测的功能，接口不能直接创建Bean（动态代理技术来解决）
       * */
       @Autowired
       private  UserMapper userMapper;
       @Test
       public void insert(){
           User user=new User();
           user.setUsername("张三");
           user.setPassword("123");
           Integer rows = userMapper.insert(user);
           System.out.println(rows);
   
       }
       @Test
       public void findByUsername(){
           String username="张三";
           User user = userMapper.findByUsername(username);
           System.out.println(user);
   
       }
   }
   
   ```

#### 4 注册-业务层

##### 4.1 异常规划

1.定义ServiceException继承RuntimeException，然后再去定义具体的异常类型来继承整个异常

```java
package com.cy.store.service.ex;
/*
* 业务层异常的基类：throws new ServiceException("业务层产生未知的异常")
* */
public class ServiceException extends RuntimeException{
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

```

> 跟据业务层不同的业务功能详细定义具体的异常类型，统一去继承ServiceException异常类

2. 用户名被占用的异常：UsernameDuplicatedException
   
   ```java
   package com.cy.store.service.ex;
   /*
   * 用户名被占用的异常
   * */
   public class UsernameDuplicatedException extends ServiceException{
       public UsernameDuplicatedException() {
           super();
       }
   
       public UsernameDuplicatedException(String message) {
           super(message);
       }
   
       public UsernameDuplicatedException(String message, Throwable cause) {
           super(message, cause);
       }
   
       public UsernameDuplicatedException(Throwable cause) {
           super(cause);
       }
   
       protected UsernameDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
           super(message, cause, enableSuppression, writable
   StackTrace);
       }
   }
   ```

3. 正在执行数据库插入操作是发生数据库或服务器宕机。处于正在执行插入的过程中所产生的异常，InsertException
   
   ```java
   package com.cy.store.service.ex;
   /*
   * 执行插入的过程中所产生的异常
   * */
   public class InsertException extends ServiceException{
       public InsertException() {
           super();
       }
   
       public InsertException(String message) {
           super(message);
       }
   
       public InsertException(String message, Throwable cause) {
           super(message, cause);
       }
   
       public InsertException(Throwable cause) {
           super(cause);
       }
   
       protected InsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
           super(message, cause, enableSuppression, writableStackTrace);
       }
   }
   
   ```

##### 4.2 设计接口和抽象方法

1. 创建UserService接口

```java
package com.cy.store.service;

import com.cy.store.entity.User;

/*
* 用户模块业务层接口
* */
public interface UserService {
    /*
    * 用户注册方法
    * */
    void reg(User user);
}

```

2. 创建实现类UserServiceImpl（注意要加上@Service注解）
   
   > @Service注解的作用：将当前类的对象交给Spring来管理，起到自动创建对象以及对象的维护作用
   > 密码加密处理的实现
   > 
   >      （串+password+串） --- md5算法进行加密，连续加载3次
   >         盐值+password+盐值---盐值就是一个随机的字符串
   >         好处：忽略原有密码的强度，提升了数据的安全性
   
   
   
   

```java
package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.UserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/*
* UserService接口的实现类
* */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void reg(User user) {
        /*
        * 检查用户是否已经存在
        * */
        User result = userMapper.findByUsername(user.getUsername());
        if(result!=null){
            throw new UsernameDuplicatedException("用户名被占用");
        }
        /*
        * 密码加密处理的实现
        * （串+password+串） --- md5算法进行加密，连续加载3次
        * 盐值+password+盐值---盐值就是一个随机的字符串
        * 好处：忽略原有密码的强度，提升了数据的安全性
        * */
        String oldPassword=user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        // 补全数据，一定要记录盐值
        user.setSalt(salt);
        String md5Password = getMD5Password(oldPassword, salt);
        user.setPassword(md5Password);
        /*
        * 补全用户信息
        * */
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date=new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        /*
        * 执行注册业务功能的实现（插入）
        * */
        Integer rows = userMapper.insert(user);
        if(rows!=1){
            // 这种情况就是发生插入过程异常
            throw new InsertException("在用户注册过程中发生了未知异常");
        }
    }
    private String getMD5Password(String password,String salt){
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toString();
        }
        return password;
    }
}

```

3. 编写业务层的单元测试类
   
   ```java
   package com.cy.store.service;
   
   import com.cy.store.entity.User;
   import com.cy.store.service.ex.ServiceException;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.mybatis.spring.annotation.MapperScan;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   import org.springframework.test.context.junit4.SpringRunner;
   @MapperScan("com.cy.store.mapper")
   @RunWith(SpringRunner.class)
   @SpringBootTest
   public class UserServiceTests {
       @Autowired
       private UserService userService;
       @Test
       public void reg(){
           try {
               User user=new User();
               user.setUsername("屌丝");
               user.setPassword("123");
               userService.reg(user);
               System.out.println("操作成功");
           } catch (ServiceException e) {
               // 获取类的对象，在获取类的名称
               System.out.println(e.getClass().getSimpleName());
               // 获取异常的具体描述信息
               System.out.println(e.getMessage());
           }
       }
   }
   
   ```

#### 5  注册-控制层

##### 5.1 创建响应

 状态码，状态描述信息，数据。这部分功能进行封装，作为方法返回值，返回给前端浏览器。

```java
package com.cy.store.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
* Json格式的数据进行响应
* */

//一个类中有泛型的数据类型，就需要把这个类声明的时候声明成泛型
public class JsonResult<E> implements Serializable {
    /* 状态码 */
    private Integer state;
    /* 描述信息 */
    private String message;
    /* 数据 */
    private E data;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Throwable e){
        this.message=e.getMessage();
    }
    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}

```

##### 5.2  设计请求

依据当前的业务功能模块进行请求的设计

> 请求路径：users/reg
> 
> 请求参数：username password 
> 
> 请求类型：POST
> 
> 响应结果：JsonResult<Void>

##### 5.3 处理请求

1. 创建一个控制层的UserController类，依赖于业务层的接口
   
   > <mark>控制层接收前端数据的方式：</mark>
   > 
   > 1. <mark>请求处理方法的参数列表设置为pojo类型来接收前端数据，SpringBoot会将前端url地址中的参数名和pojo类的属性名进行比较，如果这两个名称相同，则将会注入到pojo类中对应的属性上。</mark>
   > 
   > 2. <mark>请求处理方法的参数列表设置为非pojo类型，SpringBoot会直接将请求的参数名和方法的参数名直接进行比较，如果名称相同则会自动完成值的依赖注入。</mark>
   
   ```java
   package com.cy.store.controller;
   
   import com.cy.store.entity.User;
   import com.cy.store.service.UserService;
   import com.cy.store.service.ex.InsertException;
   import com.cy.store.service.ex.UsernameDuplicatedException;
   import com.cy.store.util.JsonResult;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.web.bind.annotation.*;
   
   @RestController //等同于@Cobtroller+@ResponseBody注解
   //@ResponseBody表示此方法的响应结果以json格式进行数据的响应给到前端
   @RequestMapping("users")
   public class UserController {
       @Autowired
       private UserService userService;
       @RequestMapping("/reg")
       public JsonResult<Void> register(User user){
           JsonResult<Void> jsonResult=new JsonResult<>();
           try {
               userService.reg(user);
               jsonResult.setState(200);
               jsonResult.setMessage("用户注册成功");
           } catch (UsernameDuplicatedException e) {
               jsonResult.setState(4000);
               jsonResult.setMessage("用户名被占用");
           }catch (InsertException e){
               jsonResult.setState(5000);
               jsonResult.setMessage("注册是产生未知的异常");
           }
           return jsonResult;
       }
   
   }
   
   ```

##### 5.4 控制层优化设计

在控制层抽离出一个父类，在这个父类中统一的去处理关于异常的相关操作。编写一个叫BaseController类，统一处理异常。

```java
package com.cy.store.controller;

import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
控制层类的基类
*/
public class BaseController {
    // 处理成功的状态码
    public static final int OK=200;
    /*
    * 方法返回值就是需要返回给前端的数据
    * 自动将异常对象传递给此方法的参数列表上
    * 项目中产生了异常会被统一拦截到此方法中
    * */
    @ExceptionHandler
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> jsonResult=new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            jsonResult.setState(4000);
            jsonResult.setMessage("用户名被占用");
        }else if(e instanceof InsertException){
            jsonResult.setState(5000);
            jsonResult.setMessage("注册是产生未知的异常");
        }
        return jsonResult;
    }

}

```

重新构建了reg()方法。只需要关注请求方法的处理，不需要关注异常捕获和处理。

```java
@RequestMapping("/reg")
    public JsonResult<Void> register(User user){
        userService.reg(user);
        return new JsonResult<>(OK);
    }
```

![](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Desktop/%E7%AC%94%E8%AE%B0%E6%9C%AC.png)

#### 6 注册-前端页面

ajax().....前端部分后续单独写吧。。。

### 用户登录

#### 1 登录-持久层

##### 1.1 规划需要执行的sql语句

依据用户提交的用户名进行select查询，然后在业务层判断密码正确与否。

```sql
select * from t_user where username = #{username}
```

##### 1.2 接口设计和方法

> 不用重复开发。单元测试也不用单独执行。

#### 2 登录-业务层

##### 2.1 规划异常

1. 密码错误异常 WrongPasswordException

2. 用户名未找到(未注册)异常NoRedisterException

3. 异常的编写
* ```java
  package com.cy.store.service.ex;
  /*
  * 密码错误异常
  * */
  public class WrongPasswordException extends ServiceException{
      public WrongPasswordException() {
          super();
      }
  
      public WrongPasswordException(String message) {
          super(message);
      }
  
      public WrongPasswordException(String message, Throwable cause) {
          super(message, cause);
      }
  
      public WrongPasswordException(Throwable cause) {
          super(cause);
      }
  
      protected WrongPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
          super(message, cause, enableSuppression, writableStackTrace);
      }
  }
  
  ```

```java
package com.cy.store.service.ex;

/*
* 用户登录时未注册异常
* */
public class NoRegisterException extends ServiceException {

    public NoRegisterException() {
        super();
    }

    public NoRegisterException(String message) {
        super(message);
    }

    public NoRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRegisterException(Throwable cause) {
        super(cause);
    }

    protected NoRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

```

##### 2.2 设计接口和抽象方法

```java
package com.cy.store.service;

import com.cy.store.entity.User;
import org.springframework.stereotype.Service;

/*
* 用户模块业务层接口
* */
public interface UserService {
    /*
    * 用户注册方法
    * */
    void reg(User user);
    /*
    * 用户登录方法
    * */
    User login(String username,String password);
}

```

##### 2.3 抽象方法实现

UserServiceImpl中

```java
/*
    *用户登录
    * */
    @Override
    public User login(String username,String password) {
        User result = userMapper.findByUsername(username);
        /*
        * 未注册异常
        * */
        if(result==null){
            throw new UserNotFoundException("用户未注册");
        }
        String salt = result.getSalt();
        String md5Password = getMD5Password(password, salt);
        if(!md5Password.equals(result.getPassword())){
            /*
             * 密码错误的异常
             * */
            throw new WrongPasswordException("用户密码错误");
        }
        if(result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //数据中转，只返回需要的数据，进行一个数据压缩，有助于提升系统性能
        User user =new User();
        user.setUsername(username);
        user.setUid(result.getUid());
        user.setAvatar(result.getAvatar());
        return user;
    }
```

#### 3 登录-控制层

##### 3.1 处理异常

在统一异常处理类中进行统一的捕获和处理。

```java
else if (e instanceof UserNotFoundException) {
            jsonResult.setState(5001);
            jsonResult.setMessage("用户数据未找到异常");
  }
else if (e instanceof WrongPasswordException) {
            jsonResult.setState(5002);
            jsonResult.setMessage("用户密码错误");

  }

```

##### 3.2 设计请求

> 请求路径：/users/login
> 
> 请求方式：POST
> 
> 请求数据：String username , String password ,HttpSession  session
> 
> 响应结果：JsonResult<User>

##### 3.3 用户会话session

> session对象主要存储在服务器端，可以用于保存服务器的临时数据的对象，所保存的数据可以在整个项目中通过访问来获取，把session的数据看作一个共享的数据。首次登录时所获取的用户数据，转移到sesion对象即可。封装在BaseController中。

```java
/*
    * 获取session对象中的uid
    * */
    protected final Integer getUidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());

    }
    /*
     * 获取session对象中的username
     * */
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
```

##### 3.4 拦截器

> 拦截器：首先将所有的请求拦截到拦截器中，可以拦截器中来定义过滤规则，如果不满足系统定义的过滤规则，统一的处理是重新打开login.html页面（重定向和转发），推荐使用重定向。
> 
> SpringBoot是依靠SpringMVC来完成。SpringMVC提供了一个叫HandlerInterceptor的接口，用于表示定义一个拦截器。自定义实现类。

 源码分析

```java
public interface HandlerInterceptor {
// 在调用所有处理请求的方法之前被自动调用执行
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
//  在ModelAndView对象返回之后被调用
    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }
//  在整个请求所有关联的资源被执行完毕最后所执行
    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
```

> 注册过滤器：添加黑名单（哪些资源可以在不登陆的情况下访问）添加黑名单（在用户登录的状态下才可以访问的页面资源。
> 
> 注册过滤器的技术：借助WebMvcConfigure接口，可以将用户定义的拦截器进行注册，才能保证拦截器能够生效和使用。自定义实现类。

源码解析：

```java
// 将自定义拦截器进行注册
default void addInterceptors(InterceptorRegistry registry) {
    }
```

##### 3.4 处理请求

```java
 @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User user = userService.login(username, password);
        /*
        * 向session对象中完成数据的绑定（这是全局session）
        * */
        session.setAttribute("uid",user.getUid());
        session.setAttribute("username",user.getUsername());
        /*
        * 获取session中绑定的数据
        * */
        System.out.println(getUsernameFromSession(session));
        System.out.println(getUidFromSession(session));
        return new JsonResult<>(OK,user);
    }
```

#### 4 登录-前端页面

### 资料修改

#### 1 修改密码

> 需要用户提交新密码和旧密码，然后根据当前登录的用户进行信息的修改操作。

##### 1.1  持久层-修改密码

1. 规划需要执行的sql语句
   
   > 根据uid查询用户信息
   
   ```sql
   select *
           from t_user where uid=#{uid}
   ```
   
   > 根据uid修改密码
   
   ```sql
   update t_user set password=#{password},modified_user=#{modifiedUser},modified_time=#{modifiedTime} where uid=#{uid}
   ```

2. 设计接口和抽象方法
   
   ```java
    /*
       * 根据uid来查询用户数据
       * */
       User findByUid(Integer uid);
       /*
       * 修改密码
       * */
       Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);
   ```

3. sql映射
   
   ```xml
   <select id="findByUid" resultMap="UserEntityMap">
           select *
           from t_user where uid=#{uid};
       </select>
   <update id="updatePasswordByUid">
           update t_user set password=#{password},modified_user=#{modifiedUser},modified_time=#{modifiedTime} where uid=#{uid}
       </update>
   ```

4. 编写单元测试

```java

@Test
    public void findByUid(){
        User user = userMapper.findByUid(1);
        System.out.println(user);

    }

 @Test
    public void updatePasswordByUid(){
        Integer rows = userMapper.updatePasswordByUid(1, "12345","管理员" , new Date());
        System.out.println(rows);
    }
```

##### 1.2 业务层-修改密码

1. 规划异常
   
   > 1. 用户密码错误，is_delete==1, uid找不到，在用户没有发现的异常。
   > 
   > 2. 在update的过程中产生的未知异常，UpdateException
   
   ```java
   package com.cy.store.service.ex;
   /*
   * 更新时产生的未知异常
   * */
   public class UpdateException extends ServiceException {
       public UpdateException() {
           super();
       }
   
       public UpdateException(String message) {
           super(message);
       }
   
       public UpdateException(String message, Throwable cause) {
           super(message, cause);
       }
   
       public UpdateException(Throwable cause) {
           super(cause);
       }
   
       protected UpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
           super(message, cause, enableSuppression, writableStackTrace);
       }
   }
   
   
   ```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

```

2. 设计接口和抽象方法

```

```java
/*
    * 修改密码
    * */
    void changePassword(Integer uid,String username,String password,String newPassword);
```

2. 在实现类中实现当前的抽象方法
   
   ```java
   /*
       * 修改密码
       * */
       @Override
       public void changePassword(Integer uid, String username, String password, String newPassword) {
           User result = userMapper.findByUid(uid);
           if(result==null || result.getIsDelete()==1){
               throw new UserNotFoundException("用户数据不存在");
           }
           String salt = result.getSalt();
           String newMd5Password = getMD5Password(newPassword,salt);
           String oldMd5Password = getMD5Password(password, salt);
           if(!oldMd5Password.equals(result.getPassword())){
               /*
               * 密码错误异常
               * */
               throw new WrongPasswordException("用户密码错误");
           }
   
           Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
           if(rows!=1){
               throw new UpdateException("更新数据时发生未知异常");
           }
       }
   ```

3. 编写测试类
   
   ```java
    @Test
       public void changePassword(){
           userService.changePassword(10,"管理员" , "1234", "0000");
       }
   ```

##### 1.3 控制层-修改密码

1. 处理异常
   
   > UpdateException异常在BaseController中统一捕获和处理
   
   ```java
   else if(e instanceof UpdateException){
               jsonResult.setState(5003);
               jsonResult.setMessage("修改密码时产生未知的异常");
           }
   ```

2. 设计请求
   
   > 请求路径：users/changePassword
   > 请求方式：POST
   > 请求数据：password，newPassword , HttpSession session
   > 返回数据：JsonResult<Void>

3. 处理请求
   
   ```java
   /*
       * 修改密码
       * */
       @RequestMapping("/changePassword")
       public JsonResult<Void> changePassword(String password,String newPassword,HttpSession session){
           userService.changePassword(getUidFromSession(session),getUsernameFromSession(session),password,newPassword);
           return new JsonResult<>(OK);
       }
       }
   ```

#### 2 个人资料

##### 2.1  持久层-个人资料

1. 规划需要执行的sql语句

```sql
 update t_user set phone=?,email=?,gender=?,modified_user=?,modified_time=? where uid=?
```

2. 设计接口和抽象方法

```java
/*
    * 修改电话号码 电子邮箱 性别
    * */
    Integer updateUserInfo(User user);
```

3. sql映射

```xml
<update id="updateUserInfo">
        update t_user
        <!--if表示条件判断标签，test结果为true时执行if标签内部的语句-->
        set
            <if test="phone!=null">phone=#{phone},</if>
            <if test="email!=null">email=#{email},</if>
            <if test="gender!=null">gender=#{gender},</if>
            modified_user=#{modifiedUser},
            modified_time=#{modifiedTime}
        where uid = #{uid}
    </update>
```

4. mapper层单元测试
   
   ```java
   @Testpublic void updateUserInfo(){    User user=new User();    user.setUid(1);    user.setPhone("12345678901");    userMapper.updateUserInfo(user);}
   ```

##### 2.2 业务层-个人资料

1. 规划异常
   
   > 1. 用户数据不存在
   > 
   > 2. 数据更新过程中发生的未知异常UpdateException异常

2. 设计接口和抽象方法
   
   ```java
   /** 个人资料修改* */void changeUserInfo(Integer uid, String username,User user);
    /*
       * 根据uid获取用户信息
       * */
       User getByUid(Integer uid);
   ```

3. 在实现类中实现方法
   
   ```java
    /*
        * 根据uid获取用户信息
        * */
       @Override
       public User getByUid(Integer uid) {
           User result = userMapper.findByUid(uid);
           if(result==null || result.getIsDelete()==1){
               throw new UserNotFoundException("用户数据不存在");
           }
           User user=new User();
           user.setUsername(result.getUsername());
           user.setPhone(result.getPhone());
           user.setEmail(result.getEmail());
           user.setGender(result.getGender());
           return user;
       }
   
     /*
        * 个人资料修改
        * */
       @Override
       public void changeUserInfo(Integer uid, String username, User user) {
           User result = userMapper.findByUid(uid);
           if(result==null || result.getIsDelete()==1){
               throw new UserNotFoundException("用户数据不存在");
           }
           user.setUid(uid);
           user.setModifiedUser(username);
           user.setModifiedTime(new Date());
           Integer rows = userMapper.updateUserInfo(user);
           if(rows!=1){
               throw new UpdateException("更新过程中发生未知异常");
           }
       }
   ```

##### 2.3 控制层

1. 处理异常

2. 设计请求
   
   > 请求1：users/getByUid
   > 请求2：users/changeInfo

3. 处理请求
   
   ```java
   /*
       * 根据Uid获取用户信息
       * */
       @RequestMapping("/getByUid")
       public JsonResult<User> getByUid(HttpSession session){
           User data=userService.getByUid(getUidFromSession(session));
           return new JsonResult<>(OK,data);
       }
       /*
       *修改个人资料
       * */
       @RequestMapping("/changeInfo")
       public JsonResult<Void> changeUserInfo(User user,HttpSession session){
           userService.changeUserInfo(getUidFromSession(session),getUsernameFromSession(session),user);
           return new JsonResult<>(OK);
       }
   ```

#### 3 上传头像

##### 3.1 持久层

1. 规划需要执行的sql语句
   
   > 根据uid更新avatar

```sql
update t_user set avatar=?,modified_user=?,modified_time=? where uid=?
```

2. 设计接口和抽象方法
   
   ```java
   /*
       * 更新头像
       * */
       Integer updateAvatarByUid(@Param("uid") Integer uid,// @Param 将参数uid和占位符uid匹配（占位符指#{}中的字符）
                                 @Param("avatar")String avatar,
                                 @Param("modifiedUser")String modifiedUser,
                                 @Param("modifiedTime")Date modifiedTime);
   ```

3. sql映射
   
   ```xml
   <update id="updateAvatarByUid">
           update t_user set avatar=#{avatar},modified_user=#{modifiedUser},modified_time=#{modifiedTime} where uid=#{uid}
       </update>
   ```

4. 单元测试
   
   ```java
   @Test
       public void updateAvatarByUid(){
           userMapper.updateAvatarByUid(10,"jjjj","管理员",new Date());
       }
   ```

##### 3.2 业务层

1. 规划异常

> 1. 用户数据不存在的异常。
> 
> 2. 更新过程中发生的未知异常。
> 
> 都已经实现，无需重复开发。

2. 设计接口和抽象方法

```java
/**
     * 上传头像
     * @param uid 用户id
     * @param username 用户名
     * @param avatar 用户头像路径
     */
    void changeAvatarByUid(Integer uid,String username,String avatar);
```

3. 在实现类中写方法的实现
   
   ```java
   @Override
       public void changeAvatarByUid(Integer uid, String username, String avatar) {
           User result = userMapper.findByUid(uid);
           if(result==null || result.getIsDelete()==1){
               throw new UserNotFoundException("用户数据不存在");
           }
           Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
           if(rows!=1){
               throw new UpdateException("更新用户头像的过程中发生未知异常");
           }
       }
   ```

4. 单元测试
   
   ```j
   @Test
       public void changeAvatarByUid(){
           userService.changeAvatarByUid(8,"管理员","img/p1.png");
       }
   ```

##### 3.3 控制层

1. 规划异常

> 异常的父类：FileUploadException继承RuntimeException
> 
> FileUploadException的子类：
> 
> 1. FileEmptyException
> 
> 2. FileStateException
> 
> 3. FileSizeException
> 
> 4. FileTypeException
> 
> 5. UploadIOException

```java
package com.cy.store.controller.ex;
/**
 * 文件上传相关异常的基类
 */
public class FileUploadException extends RuntimeException{
    public FileUploadException() {
        super();
    }

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadException(Throwable cause) {
        super(cause);
    }

    protected FileUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

```

```java
package com.cy.store.controller.ex;

/**
 * 上传文件为空的异常
 */
public class FileEmptyException extends FileUploadException{
    public FileEmptyException() {
        super();
    }

    public FileEmptyException(String message) {
        super(message);
    }

    public FileEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileEmptyException(Throwable cause) {
        super(cause);
    }

    protected FileEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

```

```java
package com.cy.store.controller.ex;
/*
* 文件大小超出限制的异常
* */
public class FileSizeException extends FileUploadException{
    public FileSizeException() {
        super();
    }

    public FileSizeException(String message) {
        super(message);
    }

    public FileSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSizeException(Throwable cause) {
        super(cause);
    }

    protected FileSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

```

```java
package com.cy.store.controller.ex;
/*
* 文件类型错误的异常
* */
public class FileTypeException extends FileUploadException{
    public FileTypeException() {
        super();
    }

    public FileTypeException(String message) {
        super(message);
    }

    public FileTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileTypeException(Throwable cause) {
        super(cause);
    }

    protected FileTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

```

```java
package com.cy.store.controller.ex;

/*
* 文件上传过程中的IO流异常
* */
public class FileUploadIOException extends FileUploadException {
    public FileUploadIOException() {
        super();
    }

    public FileUploadIOException(String message) {
        super(message);
    }

    public FileUploadIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadIOException(Throwable cause) {
        super(cause);
    }

    protected FileUploadIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

```

```java
package com.cy.store.controller.ex;

/*
* 上传的文件状态异常
* */
public class FileStateException extends FileUploadException {
    public FileStateException() {
        super();
    }

    public FileStateException(String message) {
        super(message);
    }

    public FileStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStateException(Throwable cause) {
        super(cause);
    }

    protected FileStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

```

2. 处理异常

> 在基类BaseController层中进行统一的捕获和处理。
> 
> 在BaseController中统一处理异常的方法上参数中添加新的异常处理类

```java

```

```java
else if (e instanceof FileEmptyException) {
            jsonResult.setState(6000);
            jsonResult.setMessage("文件为空的异常");
        }
        else if (e instanceof FileSizeException) {
            jsonResult.setState(6001);
            jsonResult.setMessage("文件大小超出限制的异常");
        }
        else if (e instanceof FileTypeException) {
            jsonResult.setState(6002);
            jsonResult.setMessage("文件类型异常");
        }
        else if (e instanceof FileStateException) {
            jsonResult.setState(6003);
            jsonResult.setMessage("文件状态异常");
        }
        else if (e instanceof FileUploadIOException) {
            jsonResult.setState(6004);
            jsonResult.setMessage("文件上传IO流异常");
        }
```

2. 设计请求

> 请求路径：users/changeAvatar
> 
> 请求方式：POST（GET请求提交数据2KB）
> 
> 请求数据：MultipartFile , HttpSession session
> 
> 返回数据：JsonResult<String>

3. 处理请求

> 1. MultipartFile是SpringMVC提供的一个接口，该接口包装了获取文件类型的数据（任何类型的file都可以接收),SpringBoot又整合了SpringMVC，只需在处理请求的参数列表上声明一个类型为MultipartFile的参数，SpringBoot自动将传递给服务器端的数据赋值给这个参数。
> 
> 2. @RequestParam 表示请求中的参数，将请求中的参数注入请求处理方法的某个参数上，如果名称不一致则可以使用@RequesParam注解进行标记和映射。

```java
/*
    * 上传头像
    * */
    // 设计上传文件的最大值
    public static final int AVATAR_MAX_SIZE=10+1024*1024; //字节
    // 限制上传文件的类型
    public static final List<String> AVATAR_TYPE=new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }
    @RequestMapping("/changeAvatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                           @RequestParam("file") MultipartFile file){
        /* 判断文件是否为空 */
        if(file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        /* 判断文件大小 */
        if(file.getSize()>AVATAR_MAX_SIZE){
            throw new FileSizeException("文件超出限制");
        }
        /* 判断文件类型 */
        if(!AVATAR_TYPE.contains(file.getContentType())){
            throw new FileTypeException("文件类型不支持");
        }
        /* 上传的文件存在 .../upload/文件.png*/
        String parent =
                session.getServletContext().
                        getRealPath("upload");
        // File对象指向这个路径，File是否存在
        File dir=new File(parent);
        if(!dir.exists()){//检测目录是否存在
            dir.mkdir();// 创建当前的目录
        }
        // 获取到文件名后用UUID工具来生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();//例如：test01.png
        int index = originalFilename.lastIndexOf(".");//获取.的位置
        String suffix = originalFilename.substring(index);//或许后缀
        /* 生成新的文件名:例如 HAHSH-23HSH-HSHF-3726.png*/
        String filename = UUID.randomUUID().toString().toUpperCase()+suffix;
        File dest=new File(dir,filename);//一个空文件
        // 将参数file中的数据写入到这个空文件中
        try {
            file.transferTo(dest);
        } catch (FileStateException e){
            throw new FileStateException("文件状态异常");
        }
        catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        String avatar="/upload/"+filename;
        userService.changeAvatarByUid(getUidFromSession(session),getUsernameFromSession(session),avatar);
        return new JsonResult<>(OK,avatar);
    }
```

##### 3.4 前端页面

接口只能用前端测试，所以目前还没测试。。。

##### 3.5 解决BUG

1. 方法一 ：SpringMVC默认为1MB文件可以进行上传，手动修改默认上传文件大小。

```properties
spring.servlet.multipart.max-request-size=15MB
spring.servlet.multipart.max-file-size=10MB
```

2. 方法二：需要采用java代码的形式来设置文件上传大小的限制。在主类(StoreApplication)中进行配置，可以定义一个方法，该方法必须用@Bean修饰符来修饰(@Bean表示该方法的返回值是一个Bean对象，交给Spring来管理),  返回值类型MultipartConfigElement，主类用@Configuration来修饰

```java
 @Bean
    public static MultipartConfigElement getMultipartConfigElement(){
        // 创建一个配置的工厂类对象
        MultipartConfigFactory factory =new MultipartConfigFactory();
        // 设置需要创建的对象的相关信息
        factory.setMaxFileSize(DataSize.of(10, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(15,DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }
```

##### 3.6 显示头像

属于前端优化以后写。。。

##### 3.7 登录后显示头像

更新头像成功后，将服务器返回的头像路径保存在客户端cookie对象，然后每次检测到用户打开上传头像页面，在中国页面中通过ready()方法来自动检测去读取cookie中头像并设到src属性上。

1. 设置cookie中的值
   
   1. 导入cookie.js文件
   
   ```js
   <scrip src="../bootstrap3/js/jquary.cookie.js" type="text/javascript" charset="utf-8"></scrip>
   ```

2. 调用cookie方法

```js
$.cookie(key,value,time) //time的单位为天
```

3. 在登录页面中引入cookie.js

```js
<scrip src="../bootstrap3/js/jquary.cookie.js" type="text/javascript" charset="utf-8"></scrip>
```

4. 在登录页面中通过ready()自动读取cookie中的数据

后面补。。。。

#### 4 收货管理

##### 4.1 新增收货地址

1. 新增收货地址-数据表创建

```sql
CREATE TABLE t_address (
    aid INT AUTO_INCREMENT COMMENT '收货地址id',
    uid INT COMMENT '归属的用户id',
    name VARCHAR(20) COMMENT '收货人姓名',
    province_name VARCHAR(15) COMMENT '省-名称',
    province_code CHAR(6) COMMENT '省-行政代号',
    city_name VARCHAR(15) COMMENT '市-名称',
    city_code CHAR(6) COMMENT '市-行政代号',
    area_name VARCHAR(15) COMMENT '区-名称',
    area_code CHAR(6) COMMENT '区-行政代号',
    zip CHAR(6) COMMENT '邮政编码',
    address VARCHAR(50) COMMENT '详细地址',
    phone VARCHAR(20) COMMENT '手机',
    tel VARCHAR(20) COMMENT '固话',
    tag VARCHAR(6) COMMENT '标签',
    is_default INT COMMENT '是否默认：0-不默认，1-默认',
    created_user VARCHAR (20) COMMENT '创建人',
    created_time DATETIME COMMENT '创建时间',
    modified_user VARCHAR (20) COMMENT '修改人',
    modified_time DATETIME COMMENT '修改时间',
    PRIMARY KEY (aid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

2. 创建实体类
   
   ```java
   package com.cy.store.entity;
   
   import lombok.Data;
   /*
   * 收获地址实体类
   * */
   @Data
   public class Address extends BaseEntity implements Serializabl{
       private Integer aid;
       private Integer uid;
       private String name;
       private String provinceName;
       private String provinceCode;
       private String cityName;
       private String cityCode;
       private String areaName;
       private String areaCode;
       private String zip;
       private String address;
       private String phone;
       private String tel;
       private String tag;
       private Integer isDefault;
   }
   ```

3. 持久层
   
   1. 规划需要执行的sql
      
      1. 插入语句
         
         ```sql
         insert into t_address(aid,uid,name,province_name, province_code,city_name,city_code,
             area_name,area_code,zip,address,phone,tel,tag,
         is_default,created_user,created_time,modified_user,modified_time)
         values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
         ```
      
      2. 一个用户最多能有20个收货地址。超过20就是说货地址逻辑控制方面的异常。
         
         ```sql
         select count(*) from t_address where uid=?
         ```
   
   2. 编写接口和抽象方法
   
   ```java
   package com.cy.store.mapper;
   
   import com.cy.store.entity.Address;
   /*
   * 地址模块的持久层
   * */
   public interface AddressMapper {
       /**
        * 插入用用户的收获地址信息
        * @param address 地址
        * @return 受影响的行数
        */
       Integer insertAddress(Address address);
   
       /**
        * 根据用户id统计收货地址数量
        * @param uid 用户id
        * @return 数量
        */
       Integer countByUid(Integer uid);
   }
   
   ```
   
   3. sql映射
      不写返回类型ResultType报错！
   
   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.cy.store.mapper.AddressMapper">
       <resultMap id="address" type="com.cy.store.entity.Address">
           <id column="aid" property="aid"/>
           <result property="provinceName" column="province_name"></result>
           <result property="provinceCode" column="province_code"></result>
           <result property="cityName" column="city_name"></result>
           <result property="cityCode" column="city_code"></result>
           <result property="areaName" column="area_name"></result>
           <result property="areaCode" column="area_code"></result>
           <result property="createdTime" column="created_time"></result>
           <result property="createdUser" column="created_user"></result>
           <result property="modifiedUser" column="modified_user"></result>
           <result property="modifiedTime" column="modified_time"></result>
           <result property="isDefault" column="is_default"></result>
       </resultMap>
       <!--主键自增,aid不用插入！-->
       <insert id="insertAddress" useGeneratedKeys="true" keyProperty="aid">
           insert into t_address(uid,name,province_name, province_code,city_name,city_code,
                                 area_name,area_code,zip,address,phone,tel,tag,
                                 is_default,created_user,created_time,modified_user,modified_time)
           values (#{uid},#{name},#{provinceName},#{provinceCode},
                   #{cityName},#{cityCode},#{areaName},#{areaCode},#{zip},
                   #{address},#{phone},#{tel},#{tag},#{isDefault},
                   #{createdUser},#{createdTime},#{modifiedUser},#{modifiedTime})
       </insert>
       <select id="countByUid" resultType="java.lang.Integer">
           select count(*) from t_address where uid=#{uid}
       </select>
   </mapper>
   ```
   
   4. 编写单元测试
      
      > @RunWith(SpringRunner.class) 这个注释很重要卧槽！表示启动这个测试类
      > @MapperScan("com.cy.store.mapper") 中写路径就可以了，不用具体到AddressMapper
      
      ```java
      package com.cy.store.mapper;
      
      import com.cy.store.entity.Address;
      import org.junit.Test;
      import org.junit.runner.RunWith;
      import org.mybatis.spring.annotation.MapperScan;
      import org.springframework.beans.factory.annotation.Autowired;
      import org.springframework.boot.test.context.SpringBootTest;
      import org.springframework.test.context.junit4.SpringRunner;
      
      @MapperScan("com.cy.store.mapper")
      @SpringBootTest
      /*
      * @RunWith(SpringRunner.class) 这个注释很重要卧槽！表示启动这个测试类
      * */
      @RunWith(SpringRunner.class)
      public class AddressMapperTests {
          @Autowired
          private AddressMapper addressMapper;
          @Test
          public void insertAddress(){
              Address address=new Address();
              address.setUid(1);
              address.setName("张三");
              address.setProvinceName("北京市");
              address.setCityName("北京市");
              address.setAreaName("昌平区");
              address.setAddress("中央财经大学");
              addressMapper.insertAddress(address);
          }
          @Test
          public void countByUid(){
              System.out.println(addressMapper.countByUid(1));
          }
      }
      
      ```

4. 业务层
   
   1. 规划异常
   
   > 1. IntertException 插入过程中产生的未知错误。
   > 
   > 2. 用户数据不存在的异常
   > 
   > 3. 地址查询总数岛屿20时抛AddressCountLimitException异常
   
   ```java
   package com.cy.store.service.ex;
   /*
   * 地址总数超出限制的异常
   * */
   public class AddressCountLimitException extends ServiceException{
       public AddressCountLimitException() {
           super();
       }
   
       public AddressCountLimitException(String message) {
           super(message);
       }
   
       public AddressCountLimitException(String message, Throwable cause) {
           super(message, cause);
       }
   
       public AddressCountLimitException(Throwable cause) {
           super(cause);
       }
   
       protected AddressCountLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
           super(message, cause, enableSuppression, writableStackTrace);
       }
   }
   
   ```
   
   2. 编写接口和抽象方法
   
   ```java
   package com.cy.store.service;
   
   import com.cy.store.entity.Address;
   
   /*
   * 地址模块的业务层
   * */
   public interface AddressService {
       void addAddress(Address address,Integer uid,String username);
   }
   ```
   
   3. 抽象方法的实现
      在配置文件中定义数据
      
      ```properties
      # spring读取配置文件中的数据 @Value(${user.address.max-count})
      user.address.max-count=2
      ```
      
      在业务层显现业务控制
      
      ```java
      @Value("${user.address.max-count}")
          private Integer maxCount;
      ```
   
   ```java
   package com.cy.store.service.impl;
   
   import com.cy.store.entity.Address;
   import com.cy.store.entity.User;
   import com.cy.store.mapper.AddressMapper;
   import com.cy.store.mapper.UserMapper;
   import com.cy.store.service.AddressService;
   import com.cy.store.service.ex.AddressCountLimitException;
   import com.cy.store.service.ex.InsertException;
   import com.cy.store.service.ex.UserNotFoundException;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.beans.factory.annotation.Value;
   import org.springframework.stereotype.Service;
   
   import java.io.Serializable;
   import java.util.Date;
   
   @Service
   public class AddresServiceImpl implements AddressService, Serializable {
       @Autowired
       private UserMapper userMapper;
       @Autowired
       private AddressMapper addressMapper;
       @Value("${user.address.max-count}")
       private Integer maxCount;
   
       @Override
       public void addAddress(Address address, Integer uid, String username) {
           User result = userMapper.findByUid(uid);
           if(result==null || result.getIsDelete()==1){
               throw new UserNotFoundException("用户数据不存在");
           }
           /*
           * 地址已经存在
           * */
           /*
           * 地址数量异常
           * */
           Integer count = addressMapper.countByUid(uid);
           if(count>=maxCount){
               throw new AddressCountLimitException("地址数量超出限制");
           }
           address.setUid(uid);
           Integer isDefault = count==0?1:0;
          address.setIsDefault(isDefault);
           /*
           *  省市区的编码。。
           * */
           address.setCreatedUser(username);
           address.setCreatedTime(new Date());
           address.setModifiedTime(new Date());
           address.setModifiedUser(username);
           Integer rows = addressMapper.insertAddress(address);
           if(rows!=1){
               throw new InsertException("数据插入过程中发生未知异常");
           }
       }
   }
   ```
   
   4. 编写测试类
   
   ```java
   package com.cy.store.service;
   
   import com.cy.store.entity.Address;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.mybatis.spring.annotation.MapperScan;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   import org.springframework.test.context.junit4.SpringRunner;
   
   @SpringBootTest
   @RunWith(SpringRunner.class)
   @MapperScan("com.cy.store.mapper")
   public class AddressServiceTests {
       @Autowired
       private AddressService addressService;
       @Test
       public void addAddress(){
           Address address=new Address();
           address.setName("刘亦菲");
           address.setProvinceName("北京市");
           address.setCityName("北京市");
           address.setAreaName("昌平区");
           address.setAddress("沙河镇");
           address.setPhone("18927381216");
           address.setTel("1892738836");
           address.setTag("家");
           addressService.addAddress(address,5,"Tom001");
       }
   }
   ```

5. 控制层
   5.1 处理异常
   
   ```java
   else if (e instanceof AddressCountLimitException) {
               jsonResult.setState(4003);
               jsonResult.setMessage("地址数量超出限制的异常");
           }
   ```
   
   5.2 设计请求
   
   > 请求路径：address/addAddress
   > 请求方式：POST（GET请求提交数据2KB）
   > 请求数据：HttpSession session,Address address
   > 返回数据：JsonResult<Void>
   
   5.3 处理请求
   
   ```java
   package com.cy.store.controller;
   
   import com.cy.store.entity.Address;
   import com.cy.store.service.AddressService;
   import com.cy.store.util.JsonResult;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.RestController;
   
   import javax.servlet.http.HttpSession;
   
   @RestController
   @RequestMapping("/address")
   public class AddressController extends BaseController{
       @Autowired
       private AddressService addressService;
   
       /**
        * 新增收获地址
        * @param session 
        * @param address 收货地址
        * @return
        */
       @RequestMapping("/addAddress")
       public JsonResult<Void> addAddress(HttpSession session, Address address){
           addressService.addAddress(address,getUidFromSession(session),getUsernameFromSession(session));
           return new JsonResult<>(OK);
       }
   
   }
   ```
   
   

6. 前端页面
   后面补哦亲爱的。。。

##### 4.2  获取省市区列表

1. 数据库获取省市区列表

```sql
create table t_dict_district(
    id int(11) not null AUTO_INCREMENT,
    parent varchar(6) DEFAULT NULL,
    code varchar(6) DEFAULT NULL,
    name varchar(16) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

2. 创建省市区列表实体类
   
   ```java
   
   package com.cy.store.entity;
   
   import lombok.Data;
   /*
   * 省市区的数据实体类
   * */
   @Data
   public class District extends BaseEntity{
       private Integer id;
       private String parent;
       private String code;
       private String name;
   }
   ```

3. 持久层
   sql语句的规划。
   
   ```sql
   select * from t_dict_district where parent=?
   ```
   
   编写接口和抽象方法。
   
   ```java
   package com.cy.store.mapper;
   
   import com.cy.store.entity.District;
   
   import java.util.List;
   
   /*
   * 获取省市区的持久层
   * */
   public interface DistrictMapper {
       /**
        * 根据父代号查询
        * @param parent 父代号
        * @return District集合
        */
       List<District> findByParent(String parent);
   }
   ```
   
   sql映射
   
   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.cy.store.mapper.DistrictMapper">
       <resultMap id="DistrictEntity" type="com.cy.store.entity.District">
           <id property="id" column="id"/>
           <result property="modifiedTime" column="modified_time"></result>
           <result property="modifiedUser" column="modified_user"></result>
           <result property="createdUser" column="created_user"></result>
           <result property="createdTime" column="created_time"></result>
       </resultMap>
       <select id="findByParent" resultType="com.cy.store.entity.District">
           select * from t_dict_district where parent=#{parent} order by code ASC
       </select>
   </mapper>
   ```
   
   单元测试
   
   ```java
   package com.cy.store.mapper;
   
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.mybatis.spring.annotation.MapperScan;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   import org.springframework.test.context.junit4.SpringRunner;
   
   @SpringBootTest
   @RunWith(SpringRunner.class)
   @MapperScan("com.cy.store.mapper")
   public class DistrictMapperTests {
       @Autowired
       private DistrictMapper districtMapper;
       @Test
       public void selectDistrict(){
           System.out.println(districtMapper.findByParent("110100"));
       }
   }
   ```

4. 业务层
   编写接口和抽象方法
   
   ```java
   package com.cy.store.service;
   
   import com.cy.store.entity.District;
   
   import java.util.List;
   
   public interface DistrictService {
       /**
        * 根据父代号查询区域信息
        * @param parent 父代号
        * @return 区域信息集合
        */
       List<District> getByParent(String parent);
   }
   ```
   
   实现抽象方法
   
   ```java
   package com.cy.store.service.impl;
   
   import com.cy.store.entity.District;
   import com.cy.store.mapper.DistrictMapper;
   import com.cy.store.service.DistrictService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   
   import java.util.List;
   
   @Service
   public class DistrictServiceImpl implements DistrictService {
       @Autowired
       private DistrictMapper districtMapper;
       @Override
       public List<District> getByParent(String parent) {
           List<District> list = districtMapper.findByParent(parent);
           /*
           * 在进行网络数据传输时，为了尽量避免无效数据的传输，可以将无效数据设置为null
           * 可以节省流量，另一方面提升了效率
           * */
           for (District district : list) {
               district.setParent(null);
               district.setId(null);
           }
   
      return list;
       }
   }
   ```
   
   单元测试
   
   ```java
   package com.cy.store.service;
   
   import com.cy.store.entity.District;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.mybatis.spring.annotation.MapperScan;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   import org.springframework.test.context.junit4.SpringRunner;
   
   import javax.xml.transform.Source;
   
   @SpringBootTest
   @RunWith(SpringRunner.class)
   @MapperScan("com.cy.store.mapper")
   public class DistrictServiceTests {
       @Autowired
       private DistrictService districtService;
       @Test
       public void getByParent(){
           for (District district : districtService.getByParent("110100")) {
               System.out.println(district);
           }
       }
   }
   ```

5. 控制层
   设计请求
   
   > 请求路径：district/getByParent
   > 请求方式：GET（GET请求提交数据2KB）
   > 请求数据：String parent
   > 返回数据：JsonResult<List<District>>
   
   处理请求
   
   ```java
   package com.cy.store.controller;
   
   import com.cy.store.entity.District;
   import com.cy.store.service.DistrictService;
   import com.cy.store.util.JsonResult;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.RestController;
   
   import java.util.List;
   
   @RestController
   @RequestMapping("/district")
   public class DistrictController extends BaseController{
      @Autowired
      private DistrictService districtService;
   
      /**
       * 根据父代号获取区域信息
       * @param parent 父代号
       * @return 区域信息集合
       */
      // district开头的请求都被拦截到getByParent()这个方法上
      @RequestMapping({"/",""})
      public JsonResult<List<District>> getByParent(String parent){
          List<District> data = districtService.getByParent(parent);
          return new JsonResult<>(OK,data);
      }
   }
   ```
   
   把该请求添加到白名单中
   
   ```java
   patterns.add("/districts/**");
   ```
   
   接口测试
   
   ```
   http://localhost:8080/districts?parent=110100
   ```

6. 前端页面
   后面补哦。。

##### 4.3 获取省市区的名称

1. sql规划
   
   ```sql
   select name from t_dict_district where code=?
   ```

2. 持久层
   
   ```java
   /**
        * 根据code查询区域名称
        * @param code 代号
        * @return 区域
        */
       String findNameByCode(String code);
   ```
   
   ```xml
   <select id="findNameByCode" resultType="string">
           select name
           from t_dict_district where code=#{code};
       </select>
   ```
   
   ```java
   @Test
       public void findNameByCode(){
           System.out.println(districtMapper.findNameByCode("110102"));
       }
   ```

3. 业务层
   
   ```java
   /*
       * 根据代号查询区域名称
       * */
       String getNameByCode(String code);
   ```
   
   ```java
    @Override
       public String getNameByCode(String code) {
           return districtMapper.findNameByCode(code);
       }
   ```
   
   业务层优化:前端只传code在后端对name进行补全。
   
   ```java
   /*
           * 对address对象中的数据进行补全。
           * */
           address.setProvinceName(districtMapper.findNameByCode(address.getProvinceCode()));
           address.setCityName(districtMapper.findNameByCode(address.getCityCode()));
           address.setAreaName(districtMapper.findNameByCode(address.getAreaCode()));
   ```

##### 4.3 收获地址列表展示

持久层

1. sql规划
   
   ```sql
   select * from t_address where uid=? order by is_default DESC,created_time DESC
   ```

2. 抽象方法
   
   ```java
   /**
        * 根据用户id获取收获地址
        * @param uid 用户id
        * @return 收获地址
        */
       List<Address> findAddressByUid(Integer uid);
   ```

3. sql映射
   
   ```xml
   <select id="findAddressByUid" resultMap="address">
           select * from t_address where uid=#{uid} order by is_default DESC,created_time DESC
       </select>
   ```

4. 单元测试
   
   ```java
   @Test
       public void getAddressByUid(){
           for (Address address : addressMapper.findAddressByUid(6)) {
               System.out.println(address);
           }
       }
   ```

业务层

1. 不用抛出相关的异常

2. 抽象方法和实现
   
   ```java
    List<Address> getAddressByUid(Integer uid);
   ```
   
   ```java
   @Override
       public List<Address> getAddressByUid(Integer uid) {
           List<Address> addressList = addressMapper.findAddressByUid(uid);
           for (Address address : addressList) {
               address.setProvinceCode(null);
               address.setAreaCode(null);
               address.setCityCode(null);
               address.setTel(null);
               address.setZip(null);
               address.setCreatedTime(null);
               address.setCreatedUser(null);
               address.setModifiedUser(null);
               address.setModifiedTime(null);
           }
           return addressList;
       }
   ```

控制层

1. 设计请求
   
   > 请求路径：/address 
   > 请求方式：GET（GET请求提交数据2KB）
   > 请求数据：HttpSession session
   > 返回数据：JsonResult<List<Address>>

2. 处理请求
   
   ```java
    @RequestMapping({"","/"})
       public JsonResult<List<Address>> getAddress(HttpSession session){
           List<Address> data = addressService.getAddressByUid(getUidFromSession(session));
           return new JsonResult<>(OK,data);
       }
   ```

3. 测试步骤
   先登录，后测试

4. 前端页面
   后面补。。。

##### 4.4 设置默认收货地址

持久层

1. sql规划
   
   ```sql
   update t_address set is_default=?,modified_time=?,modified_user=? where aid=?
   ```
   
   ```sql
   select * from t_address where aid=?
   ```
   
   ```sql
   update t_address set is_default=0,modified_time=?,modified_user=? where uid=?
   ```

2. 抽象方法
   
   ```java
    List<Address> findAddressByUid(Integer uid);
       Address findByAid(Integer aid);
       Integer updateNonDefaultByUid(Integer uid,String modifiedUser, Date modifiedTime);
       Integer updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime);
   ```

3. xml映射
   
   ```xml
   <update id="updateNonDefaultByUid">
           update t_address set is_default=0,modified_time=#{modifiedTime},modified_user=#{modifiedUser} where uid=#{uid}
       </update>
       <update id="updateDefaultByAid">
           update t_address set is_default=1,modified_time=#{modifiedTime},modified_user=#{modifiedUser} where aid=#{aid}
       </update>  
   <select id="findByAid" resultType="com.cy.store.entity.Address">
           select * from t_address where aid=#{aid}
       </select>
   ```

业务层

1. 异常规划
   
   > 1. UpdateException
   > 
   > 2. AccessDeniedException非法访问，访问的数据不是当前用户的收货地址
   > 
   > 3. AddressNotFoundException
   
   ```java
   package com.cy.store.service.ex;
   /*
   *地址信息不存在的异常
   * */
   public class AddressNotFoundException extends ServiceException{
       public AddressNotFoundException() {
           super();
       }
   
       public AddressNotFoundException(String message) {
           super(message);
       }
   
       public AddressNotFoundException(String message, Throwable cause) {
           super(message, cause);
       }
   
       public AddressNotFoundException(Throwable cause) {
           super(cause);
       }
   
       protected AddressNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
           super(message, cause, enableSuppression, writableStackTrace);
       }
   }
   ```
   
   ```java
   package com.cy.store.service.ex;
   /*
   * 非法访问异常
   * */
   public class AccessDeniedException extends ServiceException{
       public AccessDeniedException() {
           super();
       }
   
       public AccessDeniedException(String message) {
           super(message);
       }
   
       public AccessDeniedException(String message, Throwable cause) {
           super(message, cause);
       }
   
       public AccessDeniedException(Throwable cause) {
           super(cause);
       }
   
       protected AccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
           super(message, cause, enableSuppression, writableStackTrace);
       }
   }
   
   ```

2. 编写抽象方法
   
   ```java
   void setDefault(Integer uid,Integer aid,String username);
   ```

3. 方法实现
   
   ```java
    @Override
       public void setDefault(Integer uid, Integer aid, String username) {
           Address result = addressMapper.findByAid(aid);
           if(result==null){
               throw new AddressNotFoundException("地址信息不存在");
           }
           if(!result.getUid().equals(uid)){
               throw new AccessDeniedException("数据非法访问");
           }
           Integer rows = addressMapper.updateNonDefaultByUid(uid, username, new Date());
           if(rows<1){
               throw new UpdateException("更新数据产生未知异常");
           }
           Integer row = addressMapper.updateDefaultByAid(aid, username, new Date());
           if(row!=1){
               throw new UpdateException("更新数据产生未知异常");
           }
       }
   ```

4. 单元测试
   
   ```java
   @Test
       public void setDefault(){
           addressService.setDefault(6,3,"管理员");
       }
   ```

控制层

1. 设计请求
   
   > 请求路径：/address/{aid}/setDefault
   > 请求方式：GET（GET请求提交数据2KB）
   > 请求数据：HttpSession session,@PathVariable("aid") Integer aid
   > 返回数据：JsonResult<Void>

2. 处理请求
   
   ```java
   @RequestMapping({"","/"})
       public JsonResult<List<Address>> getAddress(HttpSession session){
           List<Address> data = addressService.getAddressByUid(getUidFromSession(session));
           return new JsonResult<>(OK,data);
       }
       // RestFul风格的请求编写
       @RequestMapping("{aid}/setDefault")
       public JsonResult<Void> setDefault(HttpSession session,@PathVariable("aid") Integer aid){
           addressService.setDefault(getUidFromSession(session),aid,getUsernameFromSession(session));
           return new JsonResult<>(OK);
       }
   ```

3. 测试
   
   > http://localhost:8080/address/3/setDefault

前端页面

。。。

##### 4.5 删除收货地址

1. 持久层
   
   ```java
   Integer deleteByAid(Integer aid);
       Address findLastModified(Integer uid);
   ```
   
   ```xml
   <delete id="deleteByAid">
           delete from t_address where aid=#{aid}
       </delete>
   ```
   
   ```java
   @Test
       public void deleteByAidAfter (){
           addressMapper.deleteByAid(8);
       }
       @Test
       public void findLastModified(){
           System.out.println(addressMapper.findLastModified(6));
       }
   ```

业务层

1. 规划异常
   删除过程异常
   
   ```java
   package com.cy.store.service.ex;
   
   public class DeleteException extends ServiceException{
       public DeleteException() {
           super();
       }
   
       public DeleteException(String message) {
           super(message);
       }
   
       public DeleteException(String message, Throwable cause) {
           super(message, cause);
       }
   
       public DeleteException(Throwable cause) {
           super(cause);
       }
   
       protected DeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
           super(message, cause, enableSuppression, writableStackTrace);
       }
   }
   ```
   
   

2. 抽象方法
   
   ```java
   void deleteAddress(Integer uid,String username,Integer aid);
   ```

3. 方法的实现
   
   ```java
   @Override
       public void deleteAddress(Integer uid, String username, Integer aid) {
           Address result = addressMapper.findByAid(aid);
           if(result==null){
               throw new AddressNotFoundException("地址信息不存在");
           }
           if(!result.getUid().equals(uid)){
               throw new AccessDeniedException("数据非法访问");
           }
           Integer flag=result.getIsDefault();
           Integer rows = addressMapper.deleteByAid(aid);
           if(rows!=1){
               throw new DeleteException("删除数据产生未知异常");
           }
           Integer count = addressMapper.countByUid(uid);
           if(count==0){
               return;
           }
           if(flag==1){
               Address lastModified = addressMapper.findLastModified(uid);
               Integer row = addressMapper.updateDefaultByAid(lastModified.getAid(), username, new Date());
               if(row!=1){
                   throw new UpdateException("更新异常");
               }
           }
       }
   ```

4. 单元测试
   
   ```java
   @Test
       public void deleteAddress(){
           addressService.deleteAddress(5,"管理员",7);
       }
   ```
   
   
   
   
   
   

### 商品热销排行

#### 1 商品-创建数据库表

```sql
CREATE table t_product (
id int(20) NOT NULL COMMENT '商品id',
category_id int(20) DEFAULT NULL COMMENT '分类id',
item_type varchar(100) DEFAULT NULL COMMENT '商品系列',
title varchar(100) DEFAULT NULL COMMENT '商品标题',
sell_point varchar(150) DEFAULT NULL COMMENT '商品卖点',
price bigint(20) DEFAULT NULL COMMENT '商品单价',
num int(10) DEFAULT NULL COMMENT '库存数量',
image varchar(500) DEFAULT NULL COMMENT '图片路径',
status int(1) DEFAULT '1' COMMENT '商品状态 1：上架 2：下架 3：删除',
priority int(10) DEFAULT NULL COMMENT '显示优先级',
created_time datetime DEFAULT NULL COMMENT '创建时间',
modified_time datetime DEFAULT NULL COMMENT '最后修改时间',
created_user varchar(50) DEFAULT NULL COMMENT '创建人',
modified_user varchar(50) DEFAULT NULL COMMENT '最后修改人',
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

#### 2 商品-创建商品实体类

```java
package com.cy.store.entity;

import lombok.Data;

@Data
public class Product extends BaseEntity{
    private Integer id;
    private Integer categoryId;
    private String itemType;
    private String title;
    private String sellPoint;
    private Long price;
    private Integer num;
    private String image;
    private Integer status;
    private Integer priority;
}
```

#### 3 持久层

##### 3.1 sql语句规划

```sql
 select *
        from t_product where status=1 order by priority DESC LIMIT 0,4
```

##### 3.2 Mapper接口

```java
package com.cy.store.mapper;

import com.cy.store.entity.Product;

import java.util.List;

public interface ProductMapper {
    List<Product> findHotList();
}
```

##### 3.3 xml映射

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.cy.store.mapper.ProductMapper">
    <resultMap id="product" type="com.cy.store.entity.Product">
        <id column="id" property="id"/>
        <result column="category_id" property="categoryId"/>
        <result column="item_type" property="itemType"/>
        <result column="sell_point" property="sellPoint"/>
        <result column="created_user" property="createdUser"/>
        <result column="created_time" property="createdTime"/>
        <result column="modified_user" property="modifiedUser"/>
        <result column="modified time" property="modifiedTime"/>
    </resultMap>

    <select id="findHotList" resultMap="product">
        select *
        from t_product where status=1 order by priority DESC LIMIT 0,4
    </select>
</mapper>
```

##### 3.4 单元测试

```java
package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@MapperScan("com.cy.store.mapper")
@SpringBootTest
/*
* @RunWith(SpringRunner.class) 这个注释很重要卧槽！表示启动这个测试类
* */
@RunWith(SpringRunner.class)
public class ProductMapperTests {
    @Autowired
    private ProductMapper productMapper;
    @Test
    public void findHotList() {
        System.out.println(productMapper.findHotList());

    }
}

```

#### 4 业务层

##### 4.1 编写接口和抽象方法

```java
package com.cy.store.service;

import com.cy.store.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findHotList();

}
```

##### 4.2 抽象方法的实现

```java
package com.cy.store.service.impl;

import com.cy.store.entity.Product;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<Product> findHotList() {
        List<Product> result = productMapper.findHotList();
        for (Product product : result) {
            product.setPriority(null);
            product.setCreatedTime(null);
            product.setCreatedUser(null);
            product.setModifiedTime(null);
            product.setModifiedUser(null);
        }
        return result;
    }
}

```

##### 4.3 单元测试

```java
package com.cy.store.service;

import com.cy.store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@MapperScan("com.cy.store.mapper")
public class ProductServiceTests {
    @Autowired
    private ProductService productService;
    @Test
    public void findHotList(){
        List<Product> list = productService.findHotList();
        for (Product product : list) {
            System.out.println(product);
        }
    }
}
```

#### 5 控制层

##### 5.1 设计请求

> 请求路径：products/getHotList
> 
> 请求方式：GET（GET请求提交数据2KB）
> 
> 请求数据：无
> 
> 返回数据：JsonResult<List<Product>>

##### 5.2 在配置类中将index.html和products/**请求添加到白名单

```java
patterns.add("/web/index.html");
patterns.add("/products/**");
```

##### 5.2 处理请求

```java
package com.cy.store.controller;

import com.cy.store.entity.Product;
import com.cy.store.service.ProductService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController extends BaseController{
    @Autowired
    private ProductService productService;
    @RequestMapping("getHotList")
    public JsonResult<List<Product>> getHotList(){
        return new JsonResult<>(OK,productService.findHotList());
    }
}
```

##### 5.3 测试

> http://localhost:8080/product/getHotList

### 商品-显示商品详情

#### 1 持久层

##### 1.1 sql规划

```sql
select * from t_product where id=?
```

##### 1.2 mapper接口

```java
Product findById(Integer id);
```

##### 1.3 xml映射

```xml
<select id="findById" resultMap="product">
        select * from t_product where id=#{id}
    </select>
```

##### 1.4 单元测试

```java
@Test
    public void findById() {
        System.out.println(productMapper.findById(1));
    }
```

#### 2 业务层

##### 2.1 异常规划

```java
package com.cy.store.service.ex;

public class ProductNotFoundException extends ServiceException{
    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ProductNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
```

##### 2.2 接口和抽象方法

```java
Product findById(Integer id);
```

##### 2.3 方法的实现

```java
@Override
    public Product findById(Integer id) {
        Product result = productMapper.findById(id);
        if(result==null){
            throw new ProductNotFoundException("商品数据不存在");
        }
        result.setPriority(null);
        result.setCreatedUser(null);
        result.setCreatedTime(null);
        result.setModifiedUser(null);
        result.setModifiedTime(null);
        return result;
    }
```

##### 2.4 单元测试

```java
 @Test
    public void findById() {
        System.out.println(productService.findById(1));
  }
```

#### 3 控制层

##### 3.1 异常处理

```java
else if (e instanceof ProductNotFoundException) {
            jsonResult.setState(4006);
            jsonResult.setMessage("商品数据不存在");
        }
```

##### 3.2 设计请求

> 请求路径：product/getById/{id}
> 
> 请求方式：GET（GET请求提交数据2KB）
> 
> 请求数据：Interger id
> 
> 返回数据：JsonResult<Product>

##### 3.3 处理请求

```java
@RequestMapping("getById/{id}")
    public JsonResult<Product> getById(@PathVariable("id") Integer id){
        return new JsonResult<>(OK,productService.findById(id));
    } 
```

##### 3.4 测试

> http://localhost:8080/product/getById/1

### 加入购物车

#### 1 数据创建

```sql
CREATE TABLE t_cart
(
    cid INT AUTO_INCREMENT COMMENT '购物车数据id',
    uid INT NOT NULL COMMENT '用户id',
    pid INT NOT NULL COMMENT '商品id',
    price BIGINT COMMENT '加入时商品单价',
    num INT COMMENT '商品数量',
    created_user VARCHAR(20) COMMENT '创建人',
    created_time DATETIME COMMENT '创建时间',
    modified_user VARCHAR(20) COMMENT '修改人',
    modified_time DATETIME COMMENT '修改时间',
    PRIMARY KEY (cid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

#### 2 创建实体类

```java
package com.cy.store.entity;

import lombok.Data;

@Data
public class Cart extends BaseEntity{
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
}

```

#### 3 持久层

##### 3.1 sql语句规划

1. 向购物车表中插入数据

```sql
insert into t_cart(aid除外) values (值列表)
```

2. 已经存在则更改num的值

```sql
update t_cart set num=? where cid=?
```

```sql

select * from t_cart where cid=? and uid=?
```

##### 3.2 接口和抽象方法

```java
package com.cy.store.mapper;

import com.cy.store.entity.Cart;

import java.util.Date;

public interface CartMapper {
    Integer insert(Cart cart) ;
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);
    Cart findByUidAndPid(Integer uid,Integer pid);
}

```

##### 3.3 sql映射

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.cy.store.mapper.CartMapper">
    <resultMap id="CartEntity" type="com.cy.store.entity.Cart">
        <id property="cid" column="cid"/>
        <result property="modifiedTime" column="modified_time"></result>
        <result property="modifiedUser" column="modified_user"></result>
        <result property="createdUser" column="created_user"></result>
        <result property="createdTime" column="created_time"></result>
    </resultMap>
    <insert id="insert"  useGeneratedKeys="true" keyProperty="cid">
        insert into t_cart(uid,pid,price,num,created_user,created_time,modified_user,modified_time) values
         (#{uid},#{pid},#{price},#{num},#{createdUser},#{createdTime},#{modifiedUser},#{modifiedTime})
    </insert>
    <update id="updateNumByCid">
         update t_cart set 
                           num=#{num},
                           modified_user=#{modifiedUser},
                           modified_time=#{modifiedTime} 
          where cid=#{cid}
    </update>
    <select id="findByUidAndPid" resultMap="CartEntity">
        select * from t_cart where uid=#{uid} and pid=#{pid}
    </select>

</mapper>
```

##### 3.4 单元测试

```java
package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.Cart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@MapperScan("com.cy.store.mapper")
@SpringBootTest
/*
* @RunWith(SpringRunner.class) 这个注释很重要卧槽！表示启动这个测试类
* */
@RunWith(SpringRunner.class)
public class CartMapperTests {
    @Autowired
    private CartMapper cartMapper;
    @Test
    public void insert() {
        Cart cart=new Cart();
        cart.setUid(1);
        cart.setPid(1);
        cart.setPrice(15000L);
        cart.setNum(2);
        cart.setCreatedUser("管理员");
        cart.setCreatedTime(new Date());
        cart.setModifiedTime(new Date());
        cart.setModifiedUser("管理员");
        cartMapper.insert(cart);
    }
    @Test
    public void updateNumByCid(){
        cartMapper.updateNumByCid(1,3,"管理员",new Date());
    }
    @Test
    public void findByUidAndPid(){
        System.err.println(cartMapper.findByUidAndPid(1, 1));
    }
}

```

#### 4 业务层

##### 4.1 异常规划

> 1. 插入时异常
> 
> 2. 更新时异常

##### 4.2 接口和方法

```java
package com.cy.store.service;

public interface CartService{
    void addToCart(Integer uid,String username,Integer pid,Integer cid,Integer amount);
}

```

##### 4.3 实现类

```java
package com.cy.store.service.impl;

import com.cy.store.entity.Cart;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.CartService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public void addToCart(Integer uid, String username, Integer pid, Integer amount) {
        /*
        * 判断购物车数据是否已经存在
        * */
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        if(result==null){
            /*
            * 表示第一次添加购物车
            * */
            Cart cart=new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setPrice(productMapper.findById(pid).getPrice());
            cart.setNum(amount);
            cart.setCreatedTime(new Date());
            cart.setModifiedTime(new Date());
            cart.setCreatedUser(username);
            cart.setModifiedUser(username);
            Integer rows = cartMapper.insert(cart);
            if(rows!=1){
                throw new InsertException("数据插入过程发生未知异常");
            }
        }else{
            Integer rows = cartMapper.updateNumByCid(result.getCid(), amount+result.getNum(), username, new Date());
            if(rows!=1){
                throw new UpdateException("数据更新过程发生未知异常");
            }
        }

    }
}

```

##### 4.4 单元测试

```java
package com.cy.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@MapperScan("com.cy.store.mapper")
public class CartServiceTests {
    @Autowired
    private CartService cartService;
    @Test
    public void addToCart(){
        cartService.addToCart(4,"屌丝",4,13);
    }

```

#### 5 控制层

##### 5.1 设计请求

> 请求路径：carts/addToCart
> 
> 请求方式：GET（GET请求提交数据2KB）
> 
> 请求数据：Integer pid, Integer amount, HttpSession session
> 
> 返回数据：JsonResult<Void>

##### 5.2 处理请求

```java
package com.cy.store.controller;

import com.cy.store.service.CartService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController{
    @Autowired
    private CartService cartService;
    @RequestMapping("addToCart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session){
        cartService.addToCart(
                getUidFromSession(session),
                getUsernameFromSession(session),
                pid,
                amount);
        return new JsonResult<>(OK);
    }

}

```

##### 5.3 测试

> http://localhost:8080/carts/addToCart?pid=3&amount=1

##### 5.4 前端

后面补。。。

### 展示购物车列表

#### 1 持久层

##### 1.1 sql语句规划

```sql
select 
  cid,
  uid,
  t_product.id,
  t_cart.price,
  t_cart.num,
  t_product.title,
  t_product.image,
  t_product.price as realPrice
from 
  t_cart LEFT JOIN t_product ON t_cart.pid=t_product.id
where 
  uid=?
order by 
  t_cart.created_time DESC
```

VO： Value Object,值对象。查询结构属于多张表时构建新对象来存储查询出来的接口。

```java
package com.cy.store.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class CartVO implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String title;
    private String image;
    private Long realPrice;
}

```

##### 1.2 抽象方法

```java
List<CartVO> findByUid(Integer uid);
```

##### 1.3 sql映射

```xml
<select id="findByUid" resultType="com.cy.store.vo.CartVO">
        select
            cid,
            uid,
            pid,
            t_cart.price,
            t_cart.num,
            t_product.title,
            t_product.image,
            t_product.price as realPrice
        from
            t_cart LEFT JOIN t_product ON t_cart.pid=t_product.id
        where
            uid=#{uid}
        order by
            t_cart.created_time DESC
    </select>
```

##### 1.4 单元测试

```java
@Test
    public void findByUid(){
        System.err.println(cartMapper.findByUid(4));
    }
```

#### 2 业务层

##### 2.1 抽象方法

```java
List<CartVO> getByUid(Integer uid);
```

##### 2.2 实现

```java
@Override
    public List<CartVO> getByUid(Integer uid) {
        return cartMapper.findByUid(uid);
    }
```

#### 2.3 控制层

```java
@RequestMapping({"","/"})
    public JsonResult<List<CartVO>> getCarts(HttpSession session){
        return new JsonResult<>(OK,cartService.getByUid(getUidFromSession(session)));
    }
```

##### 2.4 测试

> http://localhost:8080/carts/

### 增加购物车商品数量

#### 1 持久层

```java
Cart findByCid(Integer cid);
```

```xml
<select id="findByCid" resultMap="CartEntity">
select * from t_cart where cid=#{cid}
    </select>
```

```java
@Test
    public void findByCid(){
        System.err.println(cartMapper.findByCid(4));
    }
```

#### 2 业务层

##### 2.1 异常规划

> 1. 更新异常
> 
> 2. 访问权限
> 
> 3. 数据不存在s CartNotFoundExceptio

```java
package com.cy.store.service.ex;

public class CartNotFoundException extends ServiceException{
    public CartNotFoundException() {
        super();
    }

    public CartNotFoundException(String message) {
        super(message);
    }

    public CartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CartNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CartNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

```

##### 2.2 抽象方法

```java
Integer addNum(Integer cid, Integer uid,String username);
```

##### 2.3 实现

```java
@Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if(result==null){
            throw new CartNotFoundException("数据不存在");
        }
        if(!result.getUid().equals(uid)){
            throw new AccessDeniedException("数据非法访问");
        }
        Integer rows = cartMapper.updateNumByCid(cid, result.getNum() + 1, username, new Date());
        if(rows!=1){
            throw new UpdateException("更新过程产生未知异常");
        }
        return result.getNum();
    }
```

##### 2.4 单元测试

```java
@Test
    public void addNum(){
        cartService.addNum(4,6,"Tom002");
    }
```

#### 3 控制层

##### 3.1 异常捕获

```java
 else if (e instanceof CartNotFoundException) {
            jsonResult.setState(4007);
            jsonResult.setMessage("购物车数据不存在");
        }
```

##### 3.2 处理请求

```java
@RequestMapping("addNum/{cid}")
    public JsonResult<Integer> addNum(HttpSession session,@PathVariable("cid") Integer cid){
        return new JsonResult<>(OK,cartService.addNum(cid,getUidFromSession(session),getUsernameFromSession(session)));
    }
```

##### 3.3 测试

> http://localhost:8080/carts/addNum/4

##### 3.4 前端

。。。

### 显示勾选的购物车信息

#### 1 持久层

##### 1.1 sql规划

```sql
select 
  cid,
  uid,
  t_product.id,
  t_cart.price,
  t_cart.num,
  t_product.title,
  t_product.image,
  t_product.price as realPrice
from 
  t_cart LEFT JOIN t_product ON t_cart.pid=t_product.id
where 
  cid in (?,?,?)
order by 
  t_cart.created_time DESC
```

##### 1.2 抽象方法

```java
List<CartVO> findVOByCid(Integer[] cids);
```

##### 1.3 sql映射

```xml
<select id="findVOByCid" resultType="com.cy.store.vo.CartVO">
        select
            cid,
            uid,
            t_product.id,
            t_cart.price,
            t_cart.num,
            t_product.title,
            t_product.image,
            t_product.price as realPrice
        from
            t_cart LEFT JOIN t_product ON t_cart.pid=t_product.id
        where
            cid in (
                <foreach collection="array" item="cid" separator=",">
                    #{cid}
                </foreach>
                )
        order by
            t_cart.created_time DESC
    </select>
```

##### 1.4 单元测试

```java
@Test
    public void findVOByCid(){
        Integer[] array={1,2,3};
        System.err.println(cartMapper.findVOByCid(array));
    }
```

#### 2 业务层

##### 2.1 抽象方法

```java
 List<CartVO> getVOByCid(Integer uid, Integer[] cids);
```

##### 2.2 实现

```java
 @Override
    public List<CartVO> getVOByCid(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCid(cids);
        for (CartVO cartVO : list) {
            if(cartVO==null){
                list.remove(null);
            }else if(!cartVO.getUid().equals(uid)){
                list.remove(cartVO);
            }
        }
        return list;
    }
```

##### 2.3 单元测试

```java
 @Test
    public void getVOByCid(){
        System.out.println(cartService.getVOByCid(1, new Integer[1]));
    }
```

##### 2.4 测试

> http://localhost:8080/carts/list?cids=4&cids=2

##### 2.5 前端

。。。。。

### 订单

#### 1 数据库创建

```sqf
CREATE TABLE t_order (
        oid INT AUTO_INCREMENT COMMENT '订单id',
        uid INT NOT NULL COMMENT '用户id',
        recv_name VARCHAR(20) NOT NULL COMMENT '收货人姓名',
        recv_phone VARCHAR(20) COMMENT '收货人电话',
        recv_province VARCHAR(15) COMMENT '收货人所在省',
        recv_city VARCHAR(15) COMMENT '收货人所在市',
        recv_area VARCHAR(15) COMMENT '收货人所在区',
        recv_address VARCHAR(50) COMMENT '收货详细地址',
        total_price BIGINT COMMENT '总价',
        status INT COMMENT '状态：0-未支付，1-己支付，2-己取消，3-已关闭，4-己完成',
        order_time DATETIME COMMENT '下单时间',
        pay_time DATETIME COMMENT '支付时间',
        created_user VARCHAR(20) COMMENT '创建人',
        created_time DATETIME COMMENT '创建时间',
        modified_user VARCHAR(20) COMMENT '修改人',
        modified_time DATETIME COMMENT '修改时间',
        PRIMARY KEY (oid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

```sql
CREATE TABLE t_order_item
(
    id            INT AUTO_INCREMENT COMMENT '订单中的商品记录的id',
    oid           INT NOT NULL COMMENT '所归属的订单的id',
    pid           INT NOT NULL COMMENT '商品的id',
    title         VARCHAR(100) NOT NULL COMMENT '商品标题',
    image         VARCHAR(500) COMMENT '商品图片',
    price         BIGINT COMMENT '商品价格',
    num           INT COMMENT '购买数量',
    created_user  VARCHAR(20) COMMENT '创建人',
    created_time  DATETIME COMMENT '创建时间',
    modified_user VARCHAR(20) COMMENT '修改人',
    modified_time DATETIME COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

#### 2 实体类的创建

##### 2.1 订单

```java
package com.cy.store.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Order extends BaseEntity implements Serializable {
    private Integer oid;
    private Integer uid;
    private String recvName;
    private String recvPhone;
    private String recvProvince;
    private String recvCity;
    private String recvArea;
    private String recvAddress;
    private Long totalPrice;
    private Integer status;
    private Date orderTime;
    private Date payTime;

}

```

##### 2.2 订单项

```java
package com.cy.store.vo;

import com.cy.store.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
@Data
public class OrderItem extends BaseEntity implements Serializable {
    private Integer id;
    private Integer oid;
    private Integer pid;
    private String title;
    private String image;
    private Long price;
    private Integer num;
}

```

#### 3 持久层

##### 3.1 sql语句

```sql
insert into t_order(oid 除外) values (各字段的值)
```

```sql
insert into t_order_item(id 除外) values (各字段的值)
```

##### 3.2 接口和抽象方法

```java
package com.cy.store.mapper;

import com.cy.store.entity.Order;
import com.cy.store.vo.OrderItem;

public interface OrderMapper {
    Integer insertOrder(Order order);
    Integer insertOrderItem(OrderItem orderItem);
}

```

##### 3.2 sql语句的映射

```xml

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.cy.store.mapper.OrderMapper">
    <resultMap id="order" type="com.cy.store.entity.Order">
    </resultMap>
    <insert id="insertOrder" useGeneratedKeys="true" keyProperty="oid">
        insert into t_order(uid,recv_name,recv_phone,recv_province,recv_city,recv_area,recv_address,total_price,
                            status,order_time,pay_time,created_user,created_time,modified_user,modified_time)
        values (#{uid},#{recvName},#{recvPhone},#{recvProvince},#{recvCity},#{recvArea},#{recvAddress},#{totalPrice},
                #{status},#{orderTime},#{payTime},#{createdUser},#{createdTime},#{modifiedUser},#{modifiedTime})
    </insert>
    <insert id="insertOrderItem" useGeneratedKeys="true" keyProperty="id">
        insert into t_order_item(
                                 oid,pid,title,image,price,num,created_user,created_time,modified_user,modified_time
        ) values (
                  #{oid},#{pid},#{title},#{image},#{price},#{num},#{createdUser},#{createdTime},#{modifiedUser},#{modifiedTime}
                         )
    </insert>

</mapper>
```

##### 3.3 单元测试

```java
package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.Order;
import com.cy.store.vo.OrderItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@MapperScan("com.cy.store.mapper")
@SpringBootTest
/*
* @RunWith(SpringRunner.class) 这个注释很重要卧槽！表示启动这个测试类
* */
@RunWith(SpringRunner.class)
public class OrderMapperTests {
    @Autowired
    private OrderMapper orderMapper;
    @Test
    public void insertOrder(){
        Order order=new Order();
        order.setUid(1);
        order.setStatus(1);
        order.setOrderTime(new Date());
        order.setPayTime(new Date());
        order.setRecvProvince("北京市");
        order.setRecvCity("北京市");
        order.setRecvArea("朝阳区");
        order.setRecvArea("北京联合大学");
        order.setRecvName("张三");
        order.setTotalPrice(15000L);
        order.setCreatedTime(new Date());
        order.setCreatedUser("张三");
        orderMapper.insertOrder(order);
    }

    @Test
    public void insertOrderItem(){
        OrderItem orderItem=new OrderItem();
        orderItem.setPid(1);
        orderItem.setOid(1);
        orderItem.setPrice(12000L);
        orderItem.setTitle("拯救者y9000p 冰魄白");
        orderMapper.insertOrderItem(orderItem);
    }

}

```



#### 4 业务层

```java
@Override
    public Address getByAid(Integer aid, Integer uid) {
        Address result = addressMapper.findByAid(aid);
        if(result==null){
            throw new AddressNotFoundException("地址信息不存在");
        }
        if(!result.getUid().equals(uid)){
            throw new AccessDeniedException("数据非法访问");
        }
        result.setProvinceCode(null);
        result.setCityCode(null);
        result.setAreaCode(null);
        result.setCreatedUser(null);
        result.setCreatedTime(null);
        result.setModifiedUser(null);
        result.setModifiedTime(null);
        return result;
    }
```

```java
@Test
    public void getByAid(){
        System.err.println(addressService.getByAid(2, 3));
    }  
```

##### 4.1 抽象方法

```java
Order createOrder(Integer aid, Integer uid, String username, Integer[] cids);
```

##### 4.2 实现

```java
package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.entity.Order;
import com.cy.store.mapper.OrderMapper;
import com.cy.store.service.AddressService;
import com.cy.store.service.CartService;
import com.cy.store.service.OrderService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.vo.CartVO;
import com.cy.store.vo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AddressService addressService;
    @Autowired
    private CartService cartService;
    @Override
    public Order createOrder(Integer aid, Integer uid, String username, Integer[] cids) {
        List<CartVO> list = cartService.getVOByCid(uid, cids);
        Long totalPrice=0L;
        for (CartVO cartVO : list) {
            totalPrice +=cartVO.getPrice()*cartVO.getNum();
        }
        Address address = addressService.getByAid(aid, uid);
        Order order = new Order();
        order.setUid(uid);
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        order.setTotalPrice(totalPrice);
        order.setStatus(0);
        order.setOrderTime(new Date());
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());
        Integer rows = orderMapper.insertOrder(order);
        if(rows!=1){
            throw new InsertException("插入时异常");
        }
        for (CartVO cartVO : list) {
            /*
             * 详细订单项的数据
             * */
            OrderItem orderItem=new OrderItem();
            orderItem.setOid(order.getOid());
            orderItem.setPid(cartVO.getPid());
            orderItem.setTitle(cartVO.getTitle());
            orderItem.setPrice(cartVO.getPrice());
            orderItem.setImage(cartVO.getImage());
            orderItem.setNum(cartVO.getNum());
            orderItem.setCreatedTime(new Date());
            orderItem.setCreatedUser(username);
            orderItem.setModifiedTime(new Date());
            orderItem.setModifiedUser(username);
            Integer row = orderMapper.insertOrderItem(orderItem);
            if(row!=1){
                throw new InsertException("插入时异常");
            }
        }
        return order;
    }
}

```

##### 4.3 单元测试

```java
 @Test
    public void createOrder(){
        Integer[] cids={1};
        System.out.println(orderService.createOrder(5, 1, "管理员", cids));
    }
```

#### 5 控制层

##### 5.1 设计请求

> 请求路径：orders/create
> 
> 请求方式：GET（GET请求提交数据2KB）
> 
> 请求数据：Integer aid, Integer[] cids, HttpSession session
> 
> 返回数据：JsonResult<Order>

##### 5.2 处理请求

```java
package com.cy.store.controller;

import com.cy.store.entity.Order;
import com.cy.store.service.OrderService;
import com.cy.store.util.JsonResult;
import com.sun.org.apache.xerces.internal.util.HTTPInputSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController{
    @Autowired
    private OrderService orderService;
    @RequestMapping("create")
    public JsonResult<Order> createOrder(Integer aid, Integer[] cids, HttpSession session){
        Order order = orderService.createOrder(aid, getUidFromSession(session), getUsernameFromSession(session), cids);
        return new JsonResult<>(OK,order);
    }
}

```

##### 5.3 测试

> http://localhost:8080/users/login?username=Tom002&password=1234

### AOP

> 面向切面编程。它并不是spring框架的特性，Spring很好的支持AOP编程。  
> 如果我们想对业务某一些方法同时添加相同的功能需求，并且在不改变原有的业务逻辑功能的基础上  
> 完成，可以使用AOP切面编程进行开发。  
> 首先定义一个类，将这个类作为切面类。  
> 在这个类中定义切面方法（5）类。  
> 通过连接点来连接目标方法，就是用粗粒度表达式和细粒度表示进行连接。

#### 1. 切面方法

> 1. 切面方法的修饰符必须是public  
> 2. 切面方法的返回值可以是void和Object，如果方法用@Around修饰那么返回值必须是Object类型，反之随意  
> 3. 切面方法的方法名称可以自定义。  
> 4. 切面方法的参数是ProceedingJoinPoint接口类型的参数，如果方法用@Around修饰那么返回值必须是Object类型，  
>    必须传递这个参数，其他随意。

#### 2. 导包

> AOP不是Spring内部封装的技术，所以使用需要导包操作。

```properties
<dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjtools</artifactId>
        </dependency>
```

#### 3. 定义一个切面类

```java
package com.cy.store.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component //将当前类的对象使用维护交由Spring容器维护
@Aspect //将当前类标记为切面类
public class TimerAspect {
}

```

#### 4. 定义切面方法,使用环绕通知的方式来进行编写。ProceedingJoinPoint接口表示连接点，目标方法的对象。

```java
public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 先记录当前时间
        Long start=System.currentTimeMillis();
        Object result = pjp.proceed();
        // 后记录当前时间
        Long end =System.currentTimeMillis();
        System.out.println("耗时："+(end-start));
        return result;
    }
```

#### 5. 将当前的环绕通知映射到某个页面上（指定连接点）

```java
@Around("execution(* com.cy.store.service.impl.*.*(..))")//加到切面方法上
```
