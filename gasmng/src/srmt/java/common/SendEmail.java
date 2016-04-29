package srmt.java.common;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class SendEmail {
	public void SentMail(String reMail) {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo("1149676636@qq.com");
		mailMessage.setFrom("864422716@qq.com");
		mailMessage.setSubject(" Hello world via spring mail sender ");
		mailMessage.setText(" Hello world via spring mail sender ");

		senderImpl.setUsername("864422716@qq.com"); // 根据自己的情况,设置username
		senderImpl.setPassword(Constants.MAIL_PASSWORD); // 根据自己的情况, 设置password

		Properties prop = System.getProperties();
		prop.put(" mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put(" mail.smtp.timeout", "25000");
		senderImpl.setPort(465);
		senderImpl.setHost("smtp.qq.com");
		senderImpl.setJavaMailProperties(prop);
		senderImpl.setProtocol("smtp");
		// 发送邮件
		senderImpl.send(mailMessage);

		System.out.println(" 邮件发送成功.. ");
	}

	public void SentMail2(String reMail) {
		// 收件人电子邮箱
		String to = "1149676636@qq.com";

		// 发件人电子邮箱
		String from = "864422716@qq.com";

		// 指定发送邮件的主机为 localhost
		String host = "smtp.qq.com"; // QQ 邮件服务器

		// 获取系统属性
		Properties properties = System.getProperties();

		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", host);

		properties.put("mail.smtp.auth", "true");
		// 获取默认session对象
		Session session = Session.getInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("864422716@qq.com", Constants.MAIL_PASSWORD); // 发件人邮件用户名、密码
			}
		});

		try {
			// 创建默认的 MimeMessage 对象
			MimeMessage message = new MimeMessage(session);

			// Set From: 头部头字段
			message.setFrom(new InternetAddress(from));

			// Set To: 头部头字段
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: 头部头字段
			message.setSubject("This is the Subject Line!");

			// 设置消息体
			message.setText("This is actual message");

			// 发送消息
			Transport.send(message);
			System.out.println("Sent message successfully....from w3cschool.cc");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}

	public static void main(String args[]) {
		SendEmail SendEmail = new SendEmail();
		SendEmail.SentMail2("");
		System.out.println(" 邮件发送成功.. ");

	}

}