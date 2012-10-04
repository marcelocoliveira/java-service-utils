package com.utils.spring;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.utils.logging.MyLogger;

public class Context extends AbstractContext {


	private static MyLogger logger = new MyLogger(Context.class);
	
	private static void init()  {
		try {
			logger.logInfo("Initializing the Spring application context for Console...");
			String[] configLocations = {"classpath:/spring/ApplicationContext.xml"};
			sContext = new ClassPathXmlApplicationContext(configLocations);
			logger.logInfo("Spring application context for Console initalized");
		} catch (Exception ex) {
			logger.logError("Spring application context for Console is NOT loaded" + ex);
			ex.printStackTrace();
		}
	}

	private Context() {
	}

	public static SessionFactory getSessionFactory ()  {
		if (sFactory == null) {
			sContext = null;
			ApplicationContext ctx = getContext();
			if (ctx != null) {
			SessionFactory session = (SessionFactory) ctx.getBean("sessionFactory");
			sFactory = session;
			} 
		}
		return sFactory;
			
	}
	public static ApplicationContext getContext()  {
		if (sContext == null) {
			init();
		}
		return sContext;
	}
	
	
}