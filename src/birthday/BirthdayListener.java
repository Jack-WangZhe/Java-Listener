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
