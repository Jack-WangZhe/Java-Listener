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
