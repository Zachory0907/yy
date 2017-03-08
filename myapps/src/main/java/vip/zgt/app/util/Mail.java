package vip.zgt.app.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import vip.zgt.app.model.MailModel;


public class Mail {
	
	private static Properties props = new Properties();
	
	static{
		try {
			props = Utils.getProperties("/mail.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String receiveMailAccount = "269505902@qq.com";

	public static void sendMsg(MailModel mm) throws Exception {
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log
		MimeMessage message = createMimeMessage(session, mm);
		Transport transport = session.getTransport();
		transport.connect(props.getProperty("myEmailAccount"), props.getProperty("myEmailPassword"));
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	public static MimeMessage createMimeMessage(Session session, MailModel mm) throws Exception {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(props.getProperty("myEmailAccount"), props.getProperty("myEmailNickname"), "UTF-8"));
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mm.getReceiverAccount(), mm.getReceiverNickname(), "UTF-8"));
		message.setSubject(mm.getSenderSubject(), "UTF-8");
		message.setContent(mm.getSenderContent(), "text/html;charset=UTF-8");
		message.setSentDate(new Date());
		message.saveChanges();
		return message;
	}
}
