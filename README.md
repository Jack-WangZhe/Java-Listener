## JAVA监听器

> 作为JAVA开发人员，我们应对javaweb的三个技术要点之一的Listener进行学习，楼主查阅相关资料并学习相关视频，总结如下JAVA监听器内容，供大家参考学习。

### 一.监听器Listener

#### 1.什么是监听器

* 监听器就是监听某个域对象的的状态变化的组件
* 监听器的相关概念：
  * 事件源：被监听的对象(三个域对象 request、session、servletContext) 
  * 监听器：监听事件源对象事件源对象的状态的变化都会触发监听器（6+2）
  * 注册监听器：将监听器与事件源进行绑定
  * 响应行为：监听器监听到事件源的状态变化时所涉及的功能代码（程序员编写代码）

#### 2.监听器有哪些

* 第一维度按照被监听的对象划分：ServletRequest域、HttpSession域、ServletContext域
* 第二维度按照监听的内容分：监听域对象的创建与销毁的、监听域对象的属性变化的

![](/Users/wangzhe/Practice/java监听器mk图片/6个监听器.png)

#### 3.监听三大域对象的创建与销毁的监听器

* 监听器的编写步骤（重点）：
  * 编写一个监听器类去实现监听器接口
  * 覆盖监听器的方法
  * 需要在web.xml中进行配置---注册

* 监听ServletContext域的创建与销毁的监听器ServletContextListener

  * Servlet域的生命周期

    * 何时创建：服务器启动创建
    * 何时销毁：服务器关闭销毁

  * ServletContextListener监听器的主要作用

    * 初始化的工作：初始化对象、初始化数据（加载数据库驱动、连接池的初始化）
    * 加载一些初始化的配置文件(spring的配置文件）
    * **任务调度(定时器—Timer/TimerTask）**

  * 实例应用：

    * 整体流程：create包下创建MyServletContextListener类实现ServletContextListener接口并覆盖方法`public void contextInitialized(ServletContextEvent arg0) `监听context域对象的创建；覆盖方法`public void contextDestroyed(ServletContextEvent arg0) `监听context域对象的销毁。并在web.xml中通过`<listener>`中的`<listener-class>`书写监听器全类名配置。

    * 实现任务调度（即定时器）的思路：使用Timer类对象的`scheduleAtFixedRate(task,firstTime,period)`方法第一个参数书写TimerTask()的匿名内部类重写run方法从而实现调度任务内容；firstTime是Date类型对象，我们可以设置成当前晚上12点；period是间隔执行时间（单位毫秒），我们可以设置成24小时。从而实现从当前晚上12点开始，每24小时都执行对应的调度任务。

    * 实例代码：

      ```java
      //web.xml
      <?xml version="1.0" encoding="UTF-8"?>
      <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
        <!-- 注册监听器 -->
        <listener>
        	<listener-class>create.MyServletContextListener</listener-class>
        </listener>
        <display-name>WEB23_LISTENER</display-name>
        <welcome-file-list>
          <welcome-file>index.jsp</welcome-file>
        </welcome-file-list>
      </web-app>
      
      //MyServletContextListener.java
      package create;
      import java.text.ParseException;
      import java.text.SimpleDateFormat;
      import java.util.Date;
      import java.util.Timer;
      import java.util.TimerTask;
      import javax.servlet.ServletContext;
      import javax.servlet.ServletContextEvent;
      import javax.servlet.ServletContextListener;
      public class MyServletContextListener implements ServletContextListener{
      	//监听context域对象的创建
      	@Override
      	public void contextInitialized(ServletContextEvent arg0) {//参数可以获得被监听的对象
      //		ServletContext servletContext1 = arg0.getServletContext();//返回值是被监听的对象
      //		Object servletContext2 = arg0.getSource();//等同于arg0.getServletContext()，获得的是Object类型，但实际上也是获得被监听的对象
      //		System.out.println("context创建了...");
      		//开启一个计息任务调度——每天晚上12点计息一次
      		Timer timer = new Timer();
      		//task任务，firstTime第一次执行的时间，period间隔执行的时间(单位毫秒)
      		//timer.scheduleAtFixedRate(task, firstTime, period);
      		//测试demo:从服务器启动开始每隔5秒打印“银行计息了”
      //		timer.scheduleAtFixedRate(new TimerTask() {
      //			@Override
      //			public void run() {
      //				System.out.println("银行计息了...");
      //			}
      //		}, new Date(), 5000);
      		//实际银行计息业务
      		//1.起始时间，定义成晚上12点
      		//2.间隔时间：24小时
      		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      		String currentTime = "2018-08-15 00:00:00";
      		Date date = null;
      		try {
      			date = format.parse(currentTime);
      		} catch (ParseException e) {
      			e.printStackTrace();
      		}
      		timer.scheduleAtFixedRate(new TimerTask() {
      			@Override
      			public void run() {
      				System.out.println("银行计息了...");
      			}
      		}, date, 24*60*60*1000);
      	}
      	//监听context域对象的销毁
      	@Override
      	public void contextDestroyed(ServletContextEvent arg0) {
      		System.out.println("context销毁了...");
      	}
      }
      ```

* 监听Httpsession域的创建于销毁的监听器HttpSessionListener

  * HttpSession对象的生命周期

    * 何时创建：第一次调用request.getSession时创建
    * 何时销毁：服务器关闭销毁、session过期（默认30分钟，修改默认的30分钟是在Tomcat的web.xml，修改当前项目的过期时间是在自己项目的web.xml中）、手动销毁

  * HttpSessionListener监听器的主要作用：

    * 由于每次访问网站都会默认创建session对象（jsp页面中page指令中的session属性默认为true，即被访问时创建session），可以用于计数网站访问过的人

  * 实例应用：

    * 整体流程：创建MyHttpSessionListener类实现HttpSessionListener接口并覆盖`public void sessionCreated(HttpSessionEvent arg0) `和`public void sessionDestroyed(HttpSessionEvent arg0)  `方法。并在web.xml中注册该listener，和index.jsp页面。每当访问一次index页面，即会调用一次sessionCreated方法。

    * 实例代码：

      ```java
      //web.xml
      <?xml version="1.0" encoding="UTF-8"?>
      <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
        <!-- 注册监听器 -->
        <listener>
        	<listener-class>create.MyHttpSessionListener</listener-class>
        </listener>
        <display-name>WEB23_LISTENER</display-name>
        <welcome-file-list>
          <welcome-file>index.jsp</welcome-file>
        </welcome-file-list>
      </web-app>
      
      //MyHttpSessionListener.java
      package create;
      import javax.servlet.http.HttpSessionEvent;
      import javax.servlet.http.HttpSessionListener;
      public class MyHttpSessionListener implements HttpSessionListener{
      	@Override
      	public void sessionCreated(HttpSessionEvent arg0) {
      		//获得Session对象的方法arg0.getSession()
      		String id = arg0.getSession().getId();
      		System.out.println("session创建"+id);
      		
      	}
      	@Override
      	public void sessionDestroyed(HttpSessionEvent arg0) {
      		System.out.println("session销毁");
      		
      	}
      }
      
      //index.jsp
      <%@ page language="java" contentType="text/html; charset=UTF-8"
          pageEncoding="UTF-8"%>
      <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
      <html>
      <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Insert title here</title>
      </head>
      <body>
      	index.jsp
      </body>
      </html>
      ```

* 监听ServletRequest域创建与销毁的监听器ServletRequestListener 

  * ServletRequest的生命周期
    * 创建：每一次请求都会创建request
    * 销毁：请求结束
  * 用法同上，用处不是很大，此处省略。

#### 4.监听三大域对象的属性变化的 

* 域对象的通用的方法：

  * setAttribute(name,value)
    * 触发添加属性的监听器的方法   
    * 触发修改属性的监听器的方法
  * getAttribute(name)
  * removeAttribute(name)  
    * 触发删除属性的监听器的方法

* ServletContextAttibuteListener监听器

  * 整体使用流程：定义类MyServeltContextAttributeListener实现ServeltContextAttributeListener接口并覆盖`public void attributeAdded(ServletContextAttributeEvent arg0)  ;public void attributeRemoved(ServletContextAttributeEvent arg0);public void attributeReplaced(ServletContextAttributeEvent arg0)`三个方法。在attributeAdded方法中的参数传递的ServletContextAttributeEvent实例对象中，调用getName方法获取放到域中的name，调用getValue方法获取放到域中的value；在attributeRemoved方法中的参数传递的ServletContextAttributeEvent实例对象中，调用getName方法获取被删除的域中的name，调用getValue方法获取被删除的域中的value；在attributeReplaced 方法中的参数传递的ServletContextAttributeEvent实例对象中，调用getName方法获取**修改前**的域中的name，调用getValue方法获取**修改前**的域中的value。并在web.xml中注册监听器。

  * 实例代码：

    ```java
    //MyServletContextAttributeLister.java
    package attribute;
    import javax.servlet.ServletContextAttributeEvent;
    import javax.servlet.ServletContextAttributeListener;
    public class MyServletContextAttributeListener implements ServletContextAttributeListener{
    	@Override
    	public void attributeAdded(ServletContextAttributeEvent arg0) {
    		//添加属性时的监听方法
    		System.out.println(arg0.getName());//获得放到域中的name
    		System.out.println(arg0.getValue());//获得放到域中的value
    	}
    	@Override
    	public void attributeRemoved(ServletContextAttributeEvent arg0) {
    		//移除属性时的监听方法
    		System.out.println(arg0.getName());//删除的域中的name
    		System.out.println(arg0.getValue());//删除的域中的value
    	}
    	@Override
    	public void attributeReplaced(ServletContextAttributeEvent arg0) {
    		//修改属性时的监听方法
    		System.out.println(arg0.getName());//获得修改前的域中的name
    		System.out.println(arg0.getValue());//获得修改前的域中的value
    	}
    }
    
    //web.xml
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
      <listener>
        <listener-class>attribute.MyServletContextAttributeListener</listener-class>
      </listener>
      <display-name>WEB23_LISTENER</display-name>
      <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
      </welcome-file-list>
      <servlet>
        <description></description>
        <display-name>TestMyServletContextAttributeListener</display-name>
        <servlet-name>TestMyServletContextAttributeListener</servlet-name>
        <servlet-class>attribute.TestMyServletContextAttributeListener</servlet-class>
      </servlet>
      <servlet-mapping>
        <servlet-name>TestMyServletContextAttributeListener</servlet-name>
        <url-pattern>/test1</url-pattern>
      </servlet-mapping>
    </web-app>
    
    //用于测试的接口TestMyServletContextAttributeListener.java
    package attribute;
    import java.io.IOException;
    import javax.servlet.ServletContext;
    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    public class TestMyServletContextAttributeListener extends HttpServlet {
    	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		ServletContext context = this.getServletContext();
    		//向context域中存数据
    		context.setAttribute("name", "tom");
    		//改context数据
    		context.setAttribute("name", "lucy");
    		//删context数据
    		context.removeAttribute("name");
    	}
    	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		doGet(request, response);
    	}
    }
    ```

    当调用context.setAttribute("name", "tom")时打印name tom；当调用context.setAttribute("name", "lucy")时打印name tom；当调用context.removeAttribute("name")时打印name lucy。

* HttpSessionAttributeListener监听器（同上） 

* ServletRequestAriibuteListenr监听器（同上） 

#### 5.与session中的绑定的对象相关的监听器（对象感知监听器） 

* 即将要被绑定到session中的对象有几种状态

  * 绑定状态：就一个对象被放到session域中(setAttribute)

  * 解绑状态：就是这个对象从session域中移除了(removeAttribute)

  * 钝化状态：是将session内存中的对象持久化（序列化）到磁盘

  * 活化状态：就是将磁盘上的对象再次恢复到session内存中(注意对象必须实现Serializable

    接口)

* 绑定与解绑的监听器HttpSessionBindingListener（绑在对象上的，且**不用在web.xml配置**）

  * 流程：创建对象实现HttpSessionBindingListener 接口中的`public void valueBound(HttpSessionBindingEvent arg0) `（绑定的方法，将对象放到session时触发）和`public void valueUnbound(HttpSessionBindingEvent arg0) `（解绑的方法，将对象从session中移除时触发）。

  * 实例代码：

    ```java
    //Person.java创建Person类
    package domian;
    import javax.servlet.http.HttpSessionBindingEvent;
    import javax.servlet.http.HttpSessionBindingListener;
    public class Person implements HttpSessionBindingListener{
    	private String id;
    	private String name;
    	public String getId() {
    		return id;
    	}
    	public void setId(String id) {
    		this.id = id;
    	}
    	public String getName() {
    		return name;
    	}
    	public void setName(String name) {
    		this.name = name;
    	}
    	@Override
    	public String toString() {
    		return "Person [id=" + id + ", name=" + name + "]";
    	}
    	public Person(String id, String name) {
    		super();
    		this.id = id;
    		this.name = name;
    	}
    	public Person() {
    		super();
    	}
    	@Override
    	public void valueBound(HttpSessionBindingEvent arg0) {
    		// 绑定的方法，将person对象放到session时触发
    		System.out.println("Person被绑定");
    	}
    	@Override
    	public void valueUnbound(HttpSessionBindingEvent arg0) {
    		// 解绑的方法，将person对象从session移除时触发
    		System.out.println("Person被解绑");
    	}
    }
    
    //TestPersonBindingServlet.java
    package domian;
    import java.io.IOException;
    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;
    public class TestPersonBindingServlet extends HttpServlet {
    	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		HttpSession session = request.getSession();
    		//将person对象绑定到session中
    		Person p = new Person("100","jack");
    		session.setAttribute("person",p);
    		//将person对象从session中解绑
    		session.removeAttribute("person");
    	}
    	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		doGet(request, response);
    	}
    }
    ```

    当访问接口时，代码执行到`session.setAttribute("person",p);`时输出Person被绑定；代码执行到`session.removeAttribute("person");`时输出Person被解绑。

* **钝化与活化的监听器HttpSessionActivationListener 【重要：用于服务器优化】**

  * 默认：实现了HttpSessionActivationListener（覆盖其中的`public void sessionDidActivate(HttpSessionEvent arg0)  `和`public void sessionWillPassivate(HttpSessionEvent arg0) `方法）和Serializable接口（注意必须实现Serializable接口）的对象，被放入到session后，当服务器stop时，session会被钝化到work/catalina/localhost中命名为SESSIONS.ser;当服务器start时，session会被活化到session域中。

  * 手动设置钝化时间和存储位置(通过配置文件指定对象钝化时间—对象多长时间不用被钝化）：

    * 在META-INF下创建一个context.xml

    * context.xml中的代码：

      ```java
      <?xml version="1.0" encoding="UTF-8"?>
      <Context>
       	<!-- maxIdleSwap:session中的对象多长时间不使用就钝化(单位：分钟），注意钝化和销毁的概念完全不同 -->
       	<!-- directory:钝化后的对象的文件写到磁盘的哪个目录下配置钝化的对象文件在 work/catalina/localhost/钝化文件 -->
      	<Manager className="org.apache.catalina.session.PersistentManager" maxIdleSwap="1"><!-- Manager是处理内容的对象 -->
      		<Store className="org.apache.catalina.session.FileStore" directory="storeFile" /><!-- Store是做存储的对象 -->
      	</Manager>
      </Context>
      ```

  * 实例代码：

    ```java
    //Customer.java
    package domian;
    import java.io.Serializable;
    import javax.servlet.http.HttpSessionActivationListener;
    import javax.servlet.http.HttpSessionEvent;
    public class Customer implements HttpSessionActivationListener,Serializable{
    	private String id;
    	private String name;
    	public String getId() {
    		return id;
    	}
    	public void setId(String id) {
    		this.id = id;
    	}
    	public String getName() {
    		return name;
    	}
    	public void setName(String name) {
    		this.name = name;
    	}
    	@Override
    	public String toString() {
    		return "Customer [id=" + id + ", name=" + name + "]";
    	}
    	public Customer(String id, String name) {
    		super();
    		this.id = id;
    		this.name = name;
    	}
    	public Customer() {
    		super();
    	}
    	@Override
    	public void sessionDidActivate(HttpSessionEvent arg0) {
    		//活化
    		System.out.println("customer被活化了");
    	}
    	@Override
    	public void sessionWillPassivate(HttpSessionEvent arg0) {
    		//钝化
    		System.out.println("customer被钝化了");
    	}
    }
    
    //TestCustomerActiveServlet.java 用于将customer对象放至session中
    package domian;
    import java.io.IOException;
    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;
    public class TestCustomerActiveServlet extends HttpServlet {
    	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		HttpSession session = request.getSession();
    		//将customer放到session中
    		Customer customer = new Customer("200","lucy");
    		session.setAttribute("customer", customer);
    		System.out.println("customer被放到session中");
    	}
    	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		doGet(request, response);
    	}
    }
    
    //TestCustomerActiveServlet2 用于测试session对象是否被活化
    package domian;
    import java.io.IOException;
    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;
    public class TestCustomerActiveServlet2 extends HttpServlet {
    	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		HttpSession session = request.getSession();
    		//从session域中获取customer
    		Customer customer = (Customer)session.getAttribute("customer");
    		System.out.println(customer.getName());
    	}
    	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		doGet(request, response);
    	}
    }
    ```

* 面试题：当用户很多时，怎样对服务器进行优化?

  通过session的钝化与活化，将长期不操作的session内存中的对象持久化到磁盘，为服务器腾出空间，等到用户再次开始操作时将磁盘上的对象恢复到session内存中。

### 二.邮箱服务器

#### 1.邮箱服务器的基本概念

* 邮件的客户端：可以只安装在电脑上的也可以是网页形式的

* 邮件服务器：起到邮件的接受与推送的作用

* 邮件发送的协议：

  * 协议：就是数据传输的约束
  * 接受邮件的协议：POP3、 IMAP
  * 发送邮件的协议：SMTP

* 邮箱服务器地址

  ![](/Users/wangzhe/Practice/java监听器mk图片/服务器地址.png)

#### 2.邮件发送过程

![](/Users/wangzhe/Practice/java%E7%9B%91%E5%90%AC%E5%99%A8mk%E5%9B%BE%E7%89%87/%E9%82%AE%E4%BB%B6%E7%9A%84%E5%8F%91%E9%80%81%E8%BF%87%E7%A8%8B.png)

​	过程描述：如果想利用163客户端发送邮件给腾讯邮箱用户，需要先发送给网易邮箱服务器（采用SMTP协议），网易邮箱服务器的SMTP推送给腾讯邮箱服务器的SMTP协议，腾讯邮箱服务器的SMTP将接受到的邮件存储到一个公共区域。当腾讯客户端用户想要接收邮件时，需要发请求给腾讯邮箱服务器（采用POP3协议），该请求会到公共区域取邮件，返回给用户。

#### 3.邮箱服务器的安装

* 安装使用易邮邮件服务器
* 工具 —> 服务器设置 ->设置单域名如jack.com，即用户名为：用户名@jack.com
* 新账号 —> 输入账号和密码 —> 点击确认即创建用户

#### 4.邮箱客户端的安装

* 安装Foxmail
* 邮箱 —> 新建邮箱账户 —> 写上方的电子邮件及密码并点击next —> 设置接受邮件服务器ip和发送ip均为localhost（本机测试使用）
* 新建两个用户可以互相发邮件，接受邮件即可
* 注意：想要接受时需要点击收邮件，因为客户端不是实时刷新的，是固定时间一刷新

#### 5.使用程序发送邮件

* 使用mail.jar，内部就有发邮件的API

* 使用MailUtils.java工具类实现发邮件的功能【开发时直接使用即可，不需要背】

  * 邮件发送流程
    * 创建一个程序与邮件服务器会话对象Session 
    * 创建一个Message，它相当于是邮件内容 
    * 创建Transport用于将邮件发送
  * 工具代码：

  ```java
  package mail;
  import java.util.Properties;
  import javax.mail.Authenticator;
  import javax.mail.Message;
  import javax.mail.MessagingException;
  import javax.mail.PasswordAuthentication;
  import javax.mail.Session;
  import javax.mail.Transport;
  import javax.mail.internet.AddressException;
  import javax.mail.internet.InternetAddress;
  import javax.mail.internet.MimeMessage;
  import javax.mail.internet.MimeMessage.RecipientType;
  //邮件工具包
  public class MailUtils {
  	//email:邮件发给谁（收件人）  subject:主题  emailMsg：邮件内容
  	public static void sendMail(String email,String subject, String emailMsg)
  			throws AddressException, MessagingException {
  		// 1.创建一个程序与邮件服务器会话对象 Session
  		Properties props = new Properties();
  		props.setProperty("mail.transport.protocol", "SMTP");//发邮件的协议SMTP
  		props.setProperty("mail.host", "192.168.1.109");//发送邮件的服务器地址
  		props.setProperty("mail.smtp.auth", "true");// 是否要验证，指定验证为true
  		// 创建验证器
  		Authenticator auth = new Authenticator() {
  			public PasswordAuthentication getPasswordAuthentication() {
  				return new PasswordAuthentication("sam", "111111");//发邮件的账号验证
  			}
  		};
  		//此处的session是会话对象
  		Session session = Session.getInstance(props, auth);
  		// 2.创建一个Message，它相当于是邮件内容
  		Message message = new MimeMessage(session);
  		message.setFrom(new InternetAddress("sam@wangzhe.com")); // 设置发送者
  		message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者
  		message.setSubject(subject);
  		message.setContent(emailMsg, "text/html;charset=utf-8");
  		// 3.创建Transport用于将邮件发送
  		Transport.send(message);
  	}
  }
  ```

* 调用邮件工具类发送邮件

  * 实例代码：

  ```java
  package mail;
  import javax.mail.MessagingException;
  import javax.mail.internet.AddressException;
  public class SendMailTest {
  	public static void main(String[] args) throws AddressException, MessagingException {
  		MailUtils.sendMail("jack@wangzhe.com", "测试邮件", "这是一封测试邮件");
  	}
  }
  ```

### 三.案例（定时发送生日祝福）

* Demo应用jar包

  * c3p0-0.9.1.2.jar
  * commons-dbutils-1.4.jar
  * mail.jar
  * mysql-connector-java-5.0.4-bin.jar

* 项目思路：

  * 在web应用创建后，当日12点开启任务调度，实现给当天生日的用户发送祝福邮件（本项目作为测试阶段，设置自web应用创建后，每隔10秒钟给当日生日的用户发送祝福邮件）
  * 遍历数据库查看当天过生日的人，借助mail.jar提供的API和MailUtils工具发送邮件

* 代码编写流程：

  * 创建BirthdayListener类实现ServletContextListener，重写对应方法，主要添加contextInitialized()方法中的内容，利用Timer对象的scheduleAtFixedRate()方法开启任务调度，设置第一个参数为调度任务内容，分别是查询数据库当日过生日的人，发送邮件；设置第二个参数为当前时刻，即项目创建时刻起开启任务调度；设置第三个参数为间隔时长（单位毫秒）

  ```java
  package birthday;
  import java.sql.SQLException;
  import java.text.SimpleDateFormat;
  import java.util.Date;
  import java.util.List;
  import java.util.Timer;
  import java.util.TimerTask;
  import javax.mail.MessagingException;
  import javax.mail.internet.AddressException;
  import javax.servlet.ServletContextEvent;
  import javax.servlet.ServletContextListener;
  import org.apache.commons.dbutils.QueryRunner;
  import org.apache.commons.dbutils.handlers.BeanListHandler;
  import mail.MailUtils;
  public class BirthdayListener implements ServletContextListener{
  	@Override
  	public void contextDestroyed(ServletContextEvent arg0) {
  		//销毁
  	}
  	@Override
  	public void contextInitialized(ServletContextEvent arg0) {
  		//当web应用创建开启任务调度——功能在用户生日当天发送邮件
  		Timer timer = new Timer();
  		timer.scheduleAtFixedRate(new TimerTask() {
  			@Override
  			public void run() {
  				// 为当天过生日的用户发送邮件
  				//1.获得今天过生日的人
  				SimpleDateFormat format = new SimpleDateFormat("MM-dd");
  				String currentDate = format.format(new Date());
  				System.out.println(currentDate);
  				//根据当前时间到数据库查今天过生日的人
  				QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
  				String sql = "select * from customer where birthday like ?";
  				List<Customer>customerList = null;
  				try {
  					customerList = runner.query(sql, new BeanListHandler<Customer>(Customer.class), "%"+currentDate+"%");
  				} catch (SQLException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
  				//2.发邮件
  				if(customerList!=null&&customerList.size()>0) {
  					for(Customer c :customerList) {
  						String emailMsg = "亲爱的:"+c.getRealname()+"生日快乐！";
  						try {
  							MailUtils.sendMail(c.getEmail(), "生日祝福", emailMsg);
  							System.out.println(c.getRealname()+"邮件发送完毕");
  						} catch (AddressException e) {
  							// TODO Auto-generated catch block
  							e.printStackTrace();
  						} catch (MessagingException e) {
  							// TODO Auto-generated catch block
  							e.printStackTrace();
  						}
  					}
  				}
  				
  			}
  		}, new Date(), 1000*10);
  		//实际开发中起始时间是一个固定时间
  		//实际开发中间隔时间是1天
  	}
  }
  ```

* 开发时应用到Customer实体类进行映射，MailUtils工具发送邮件，web.xml配置监听器

  ```java
  //Customer.java
  package birthday;
  public class Customer {
  	private int id;
  	private String username;
  	private String password;
  	private String realname;
  	private String birthday;
  	private String email;
  	public int getId() {
  		return id;
  	}
  	public void setId(int id) {
  		this.id = id;
  	}
  	public String getUsername() {
  		return username;
  	}
  	public void setUsername(String username) {
  		this.username = username;
  	}
  	public String getPassword() {
  		return password;
  	}
  	public void setPassword(String password) {
  		this.password = password;
  	}
  	public String getRealname() {
  		return realname;
  	}
  	public void setRealname(String realname) {
  		this.realname = realname;
  	}
  	public String getBirthday() {
  		return birthday;
  	}
  	public void setBirthday(String birthday) {
  		this.birthday = birthday;
  	}
  	public String getEmail() {
  		return email;
  	}
  	public void setEmail(String email) {
  		this.email = email;
  	}
  	@Override
  	public String toString() {
  		return "Customer [id=" + id + ", username=" + username + ", password=" + password + ", realname=" + realname
  				+ ", birthday=" + birthday + ", email=" + email + "]";
  	}
  	public Customer(int id, String username, String password, String realname, String birthday, String email) {
  		super();
  		this.id = id;
  		this.username = username;
  		this.password = password;
  		this.realname = realname;
  		this.birthday = birthday;
  		this.email = email;
  	}
  	public Customer() {
  		super();
  	}
  }
  
  //MailUtils.java
  package mail;
  import java.util.Properties;
  import javax.mail.Authenticator;
  import javax.mail.Message;
  import javax.mail.MessagingException;
  import javax.mail.PasswordAuthentication;
  import javax.mail.Session;
  import javax.mail.Transport;
  import javax.mail.internet.AddressException;
  import javax.mail.internet.InternetAddress;
  import javax.mail.internet.MimeMessage;
  import javax.mail.internet.MimeMessage.RecipientType;
  //邮件工具包
  public class MailUtils {
  	//email:邮件发给谁（收件人）  subject:主题  emailMsg：邮件内容
  	public static void sendMail(String email,String subject, String emailMsg)
  			throws AddressException, MessagingException {
  		// 1.创建一个程序与邮件服务器会话对象 Session
  		Properties props = new Properties();
  		props.setProperty("mail.transport.protocol", "SMTP");//发邮件的协议SMTP
  		props.setProperty("mail.host", "192.168.1.109");//发送邮件的服务器地址
  		props.setProperty("mail.smtp.auth", "true");// 是否要验证，指定验证为true
  		// 创建验证器
  		Authenticator auth = new Authenticator() {
  			public PasswordAuthentication getPasswordAuthentication() {
  				return new PasswordAuthentication("sam", "111111");//发邮件的账号验证
  			}
  		};
  		//此处的session是会话对象
  		Session session = Session.getInstance(props, auth);
  		// 2.创建一个Message，它相当于是邮件内容
  		Message message = new MimeMessage(session);
  		message.setFrom(new InternetAddress("sam@wangzhe.com")); // 设置发送者
  		message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者
  		message.setSubject(subject);
  		message.setContent(emailMsg, "text/html;charset=utf-8");
  		// 3.创建Transport用于将邮件发送
  		Transport.send(message);
  	}
  }
  
  //web.xml
  <?xml version="1.0" encoding="UTF-8"?>
  <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
    <listener>
      <listener-class>birthday.BirthdayListener</listener-class>
    </listener>
    <display-name>WEB23_LISTENER</display-name>
    <welcome-file-list>
      <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
  </web-app>
  ```

  

