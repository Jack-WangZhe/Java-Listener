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
