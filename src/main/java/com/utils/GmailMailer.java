package com.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.util.StringUtils;

import com.utils.logging.MyLogger;

public class GmailMailer {

	final static String username = "marcelo.oliveira@extjs.com";
	final static String password = "gurupi123";
	private static MyLogger logger = new MyLogger(GmailMailer.class, "gmailMailer");
	
	public static void send (String sendername, String from , String[] to, String subject , String body){
	Properties props = new Properties();
	//props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.host", "localhost");
	//props.put("mail.smtp.port", "587");
	props.put("mail.smtp.port", "25"); 
	props.put("mail.debug", "true"); 
	props.put("mail.smtp.auth", "true"); 
	props.put("mail.smtp.starttls.enable","true"); 
	props.put("mail.smtp.EnableSSL.enable","true");

	props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
	props.setProperty("mail.smtp.socketFactory.fallback", "false");   
	props.setProperty("mail.smtp.port", "465");   
	props.setProperty("mail.smtp.socketFactory.port", "465"); 

	Session session = Session.getInstance(props,
	  new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	  });

	try {

		Message message = new MimeMessage(session);
		String setFrom =  sendername + "<"+from+">";
		message.setFrom(new InternetAddress(setFrom));
		message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(StringUtils.arrayToCommaDelimitedString(to)));
		message.setSubject(subject);
		message.setContent(body,"text/html; charset=ISO-8859-1");
		Transport.send(message);

	} catch (MessagingException e) {
		logger.logError(e.getMessage(), e);
		throw new RuntimeException(e);
	}
}
}
