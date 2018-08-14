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
