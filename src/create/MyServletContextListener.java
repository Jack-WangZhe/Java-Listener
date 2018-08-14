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
