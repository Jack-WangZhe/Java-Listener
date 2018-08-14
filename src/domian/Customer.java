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
