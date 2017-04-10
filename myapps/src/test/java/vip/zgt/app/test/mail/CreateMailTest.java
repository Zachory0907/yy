package vip.zgt.app.test.mail;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CreateMailTest {

	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session); // 创建邮件对象
		// 发件人：邮箱， 昵称， 昵称编码
		message.setFrom(new InternetAddress("361525718@send.com", "USER_AA", "UTF-8"));
		// 收件人：.TO为收件人，.CC为抄送，.Bcc为密送
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("add", "name", "UTF-8"));
		message.setSubject("邮件主题", "UTF-8");
		message.setContent("邮件正文", "text/html;charset=UTF-8");
		message.setSentDate(new Date());
		message.saveChanges();
		OutputStream out = new FileOutputStream("D:\\Email.eml");
		message.writeTo(out);
		out.flush();
		out.close();
	}
}
