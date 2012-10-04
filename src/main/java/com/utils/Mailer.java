package com.utils;
import javax.mail.*;
import javax.mail.internet.*;



import java.util.*;
/**
  * A simple email sender class.
  */
public class Mailer
{
	
 	   
	
    public static void send(String sender, String from, String to , String subject, String body)
  		  {
  		    try
  		    {
  		      Properties props = System.getProperties();
  		      // -- Attaching to default Session, or we could start a new one --
  		      props.put("mail.smtp.host", "localhost");
  		      Session session = Session.getDefaultInstance(props, null);
  		      // -- Create a new message --
  		      Message msg = new MimeMessage(session);
  		      // -- Set the FROM and TO fields --
  		      String setFrom =  sender + "<"+from+">";
  		      msg.setFrom(new InternetAddress(setFrom));
  		      msg.setRecipients(Message.RecipientType.TO,
  		        InternetAddress.parse(to, false));
  		      // -- We could include CC recipients too --
  		      // if (cc != null)
  		      // msg.setRecipients(Message.RecipientType.CC
  		      // ,InternetAddress.parse(cc, false));
  		      // -- Set the subject and body text --
  		      msg.setSubject(subject);
  		      msg.setContent(body, "text/html; charset=ISO-8859-1");
  		      // -- Set some other header information --
  		      msg.setHeader("X-Mailer", "LOTONtechEmail");
  		      msg.setSentDate(new Date());
  		      // -- Send the message --
  		      Transport.send(msg);
  		      
  		    }
  		    catch (Exception ex)
  		    {
  		    	ex.printStackTrace();
  		    }
  		  }
  		}

