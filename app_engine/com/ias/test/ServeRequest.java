package com.ias.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class ServeRequest extends HttpServlet{
	
	DatastoreService datastore;
	@Override
	public void init() {
		datastore = DatastoreServiceFactory.getDatastoreService(); 
	}
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	HashMap<String,Integer> hdupli = Upload.hmp;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/html");

		PrintWriter out = resp.getWriter();
		out.println("<h1>" + "Hi Naveen" + "</h1>");

		resp.getWriter().write("Hi DO GET IS WORKING");
	}

	
	
	
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
    	
	 String Location = req.getParameter("Location");
     Location = Location.toLowerCase().trim();
     
     Key k = KeyFactory.createKey("Person", Location + "_" +  Integer.toString(1));

		
		Query q = new Query("Person");
		q.setFilter(Query.FilterOperator.EQUAL.of("Location", Location));
		
		PreparedQuery pq = datastore.prepare(q);

		String activityString ="";
		for (Entity result : pq.asIterable()) 
		{
			String src = "http://1-dot-glass-chemist-761.appspot.com/serve?blob-key="+result.getProperty("Image");
	
			Location = (String) result.getProperty("Location") ;
			String Rating = (String) result.getProperty("rating") ;
			
			activityString += Location+"@#"+Rating+"@#"+src+"@_@"; 
			
		}
		
		PrintWriter out = res.getWriter();
		out.print(activityString);
	
    }
}
