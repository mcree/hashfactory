package org.hashfactory.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class Client {

	private static ApplicationContext applicationContext;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		setApplicationContext(new AnnotationConfigApplicationContext(ClientContextConfig.class));
		
		new Client().run(args);
	}
	
	private void run(String[] args) {
		CmdLine.parseAndRun(args, applicationContext);
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	private static void setApplicationContext(ApplicationContext applicationContext) {
		Client.applicationContext = applicationContext;
	}

}
