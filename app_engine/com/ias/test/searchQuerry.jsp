package com.ias.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;


public class SearchQueryy extends HttpServlet
{
	Vector<Record> records;
	DatastoreService datastore;
	@Override
	public void init() {
		datastore = DatastoreServiceFactory.getDatastoreService();
		records = new Vector<Record>();
	//	imageDirectory = getServletContext().getInitParameter("file-upload"); 
	}
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	HashMap<String,Integer> hdupli = Upload.hmp;
	
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
    	
	 String Location = req.getParameter("SearchBox");
     Location = Location.toLowerCase().trim();
     int num,i ;
     //res.getWriter().println(Location);
     //if(hdupli.containsKey(Location))
     {
     	//num = hdupli.get(Location);
     	//for(i=1;i<=num;i++)
     	{
     		Key k = KeyFactory.createKey("Person", Location + "_" +  Integer.toString(1));

     	//	res.getWriter().println("Locati" + k.toString());
     		//try {
     			
				//Entity Person = datastore.get(k);
				//res.getWriter().println("Try\t" + k.toString());
				
				//BlobKey blobKey = new BlobKey((String) Person.getProperty("Image"));
		
				
				Query q = new Query("Person");
				q.setFilter(Query.FilterOperator.EQUAL.of("Location", Location));
				
				// blobstoreService.serve(blobKey, res);
				
				PreparedQuery pq = datastore.prepare(q);


				for (Entity result : pq.asIterable()) 
				{
					records.add(new Record(result));
					
					String src = "http://1-dot-glass-chemist-761.appspot.com/serve?blob-key="+result.getProperty("Image");
				  res.getWriter().println(
						  "</br>Location: " + result.getProperty("Location") + "</br>Rating:  " +result.getProperty("rating"));// +"</br> URL= &nbsp&nbsp"  +  "1-dot-glass-chemist-761.appspot.com/serve?blob-key="+result.getProperty("Image") );	
				  res.getWriter().print("</br></br><img src = "+ src +"  height=\"200\" width=\"200\" /></br></br></br>");
				}

				
				
				//res.getWriter().println("Location: " + Person.getProperty("Location") + "\nRating:  " +"\t URL= \t" + Person.getProperty("rating") +  "1-dot-glass-chemist-761.appspot.com/serve?blob-key="+Person.getProperty("Image") );	
				 //		res.sendRedirect("/serve?blob-key=" + person.getProperty("Image"));
				
				
	/*		} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				String t  ="Catch\t" + k.toString();
				t += (e.getMessage());
				res.getWriter().println(t);
				//res.getWriter().println("Catch\t" + k.toString());
				
				
				//e.printStackTrace();
			}
*/
     	}
     }
     //else
     {
    	 
     }
     
     
     
     
    }
}


