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
