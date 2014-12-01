package com.ias.test;



import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class Config implements ServletContextListener{
		
	public static DatastoreService dataStore;
	public static String SERVER_KEY;
	//public static Entity sensorData;
	//public static Entity deviceMap;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Server Closes");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Server began");
		dataStore = DatastoreServiceFactory.getDatastoreService();
		SERVER_KEY = "AABBCC";
	}
	
	

}
