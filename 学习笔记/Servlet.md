## HTTP协议

历史版本：

  1.0：每一次请求响应都会建立新的连接

  1.1：复用连接

请求方式：

1. GET:
   请求参数在请求行中，在url后；
   对请求的url长度有限制；
   不太安全

2. POST：
   请求参数在请求体中；
   对请求的url长度没有限制；
   相对安全

请求消息数据格式：

1. 请求行
   请求方式  请求url  请求协议/版本
   GET  /login.html  HTTP/1.1

2. 请求头
   请求头名称：请求头值
   
   > 常见的请求头：
   > User-Agent：浏览器信息
   > 例如：User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0
   > Accept：image/avif,image/webp,*/*
   > Referer:告诉服务器，我（当前的请求）从哪里来，作用：1. 防盗连 2.统计工作
   > Referer:http://localhost:8080/servlet01/login.html

3. 请求空行
   空行

4. 请求体（正文）

## Request

1. request对象继承体系结构：
   ServletRequest--- 接口
      | 继承
   HttpServletRequest---接口
     | 实现
   org.apache.catalina.connector.RequestFacade类（tomcat)

2. request功能
   
   1. 获取请求行数据
      *GET /demo1/user?name=zhangsan HTTP/1.1*
      String getMethod() :GET
      String getContextPath(): /demo1
      String getServletPath()：/user
      String getQueryString()：name=zhangsan
      String getRequestURI()：/demo1/user
      StringBuffer getRequestURL()：http://localhost:8080/demo1/user
      String getProtocol：HTTP/1.1
      String getRemoteAddr()：获取客户机的ip地址
   
   2. 获取请求头数据
      Enumeration<String> getHeaderNames()
      String getHeader(String name)
   
   3. 获取请求体数据
      只有post请求才有请求体，请求体中封装了post请求的参数
      步骤：
      
      1. 获取流对象
      
      2. 在从流对象中获取数据

3. 中文乱码问题：
   在doPost()方法中添加request.setCharacterEncoding("utf-8");

4. 请求转发: 服务端行为
     浏览器地址栏路径不发生变化；只能转发到当前服务器的内部资源中；转发是一次请求
   
   > request.getRequestDispatcher("/RequestDemo5").forward(request,response);

5. 共享数据
   
   ```java
   request.setAttribute("msg","hello");
   request.getAttribute("msg")
   ```

6. 获取ServletContext

## Response

1. 重定向：客户端行为
   服务端指导客户端行为；两次请求；浏览器地址栏路径会发生变化

## Cookie

1. Cookie的创建
   
   ```java
   package com.itheima.cookie;
   
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.Cookie;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   import java.io.IOException;
   
   @WebServlet("/cook1")
   public class CookieDemo1 extends HttpServlet {
   
       @Override
       protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           Cookie cookie = new Cookie("name", "admin");
           resp.addCookie(cookie);
       }
   }
   
   ```

2. Cookie的获取
   
   ```java
   package com.itheima.cookie;
   
   import javax.servlet.ServletException;
   import javax.servlet.annotation.WebServlet;
   import javax.servlet.http.Cookie;
   import javax.servlet.http.HttpServlet;
   import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;
   import java.io.IOException;
   
   @WebServlet("/cook2")
   public class CookieDemo2 extends HttpServlet {
   
       @Override
       protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           Cookie[] cookies = req.getCookies();
           if(cookies!=null && cookies.length>0){
               for(Cookie cookie:cookies){
                   System.out.println(cookie.getName()+":"+cookie.getValue());
               }
           }
       }
   }
   
   ```

Cookie不能存中文，用URLEncoder.encode()进行编码，用URLDecoder.decode()进行解码；Cookie的存储量有限。

## Session
