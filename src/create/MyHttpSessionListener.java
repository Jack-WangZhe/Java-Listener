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
