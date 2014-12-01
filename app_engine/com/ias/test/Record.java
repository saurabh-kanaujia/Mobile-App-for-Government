package com.ias.test;

import com.google.appengine.api.datastore.Entity;

public class Record{
	String url;
	String location;
	String rating;
	public Record(Entity e)
	{
		url = e.getProperty("Image").toString();
		location = e.getProperty("Location").toString();
		rating = e.getProperty("rating").toString();
	}
	
}
