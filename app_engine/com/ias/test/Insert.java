package com.ias.test;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.PrintWriter;


import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServicePb.BlobstoreService;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


//import com.google.appengine.demo.repo.PMF;

import javax.servlet.http.*;

import org.apache.commons.io.IOUtils;
//import com.google.appengine.api.blobstore.BlobstoreService.MAX_BLOB_FETCH_SIZE ;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;



import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;


final class PMF {
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {}

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}







@PersistenceCapable(identityType = IdentityType.APPLICATION)
class Movie {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String title;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
    private String imageType;

    @Persistent
    private Blob image;

    //...

    public Long getId() {
        return key.getId();
    }

    public String getTitle() {
        return title;
    }

    public String getImageType() {
        return imageType;
    }

    public byte[] getImage() {
        if (image == null) {
            return null;
        }

        return image.getBytes();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void setImage(byte[] bytes) {
        this.image = new Blob(bytes);
    }

    //...
}







@SuppressWarnings("serial")
public class Insert extends HttpServlet {
	
	@Persistent
	 Blob picValue;
	static int maxFileSize = 1024 * 1024;
	   static int maxMemSize = 4 * 1024;
	   File file ;

	DatastoreService datastore;
	static String imageDirectory = null;
	static Key personKey = null;
	@Override
	public void init() {
		datastore = DatastoreServiceFactory.getDatastoreService();
		imageDirectory = getServletContext().getInitParameter("file-upload"); 
	}
	
//	@PersistenceCapable(identityType = IdentityType.APPLICATION)

	
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		resp.setContentType("text/plain");
//		resp.getWriter().println("Hello , world");
		
//		resp.getWriter().println("Name: ");
//		 URLFetchService fetchService =
//		            URLFetchServiceFactory.getURLFetchService();
//
//		        // Fetch the image at the location given by the url query string parameter
//		        HTTPResponse fetchResponse = fetchService.fetch(new URL(
//		                req.getParameter("pic")));
//		        
//		        resp.getWriter().println("Name: " + fetchResponse.toString());
		        
		  /*      String fetchResponseContentType = null;
		        for (HTTPHeader header : fetchResponse.getHeaders()) {
		            // For each request header, check whether the name equals
		            // "Content-Type"; if so, store the value of this header
		            // in a member variable
		            if (header.getName().equalsIgnoreCase("content-type")) {
		                fetchResponseContentType = header.getValue();
		                break;
		            }
		        }
		        
		        if (fetchResponseContentType != null) {
		            // Create a new Movie instance
		            Movie movie = new Movie();
		            movie.setTitle(req.getParameter("pic"));
		            movie.setImageType(fetchResponseContentType);

		            // Set the movie's promotional image by passing in the bytes pulled
		            // from the image fetched via the URL Fetch service
		            movie.setImage(fetchResponse.getContent());

		            //...

		            PersistenceManager pm = PMF.get().getPersistenceManager();
		            try {
		                // Store the image in App Engine's datastore
		                pm.makePersistent(movie);
		            } finally {
		                pm.close();
		            }
		        }

		
		*/
		
		Entity person = new Entity("Person", req.getParameter("name"));
		
		List<String> l = new ArrayList<String>();
		
		person.setProperty("hd",l);
		
		//byte [] image ;
	/*	person.setProperty("name", req.getParameter("name"));
		person.setProperty("age", Integer.parseInt(req.getParameter("age")));
		//image= self.request.POST["pic"].value;
		//person.setP
		image = req.getParameter("pic").getBytes() ;
		picValue = new Blob(image) ;
		
	//	BlobstoreService blobstoreService  = new BlobstoreService();
				
		datastore.put(person);

		
		
		
		if(personKey == null)
			personKey = person.getKey();
		try {
			Entity retrievedPerson = datastore.get(personKey);
			
			String title = req.getParameter("pic");
	        //Blob movie = new Blob(title.getBytes());

	        
            resp.getOutputStream().write(title.getBytes());

			//resp.setContentType("text/plain");
		resp.getWriter().println("Name: "+retrievedPerson.getProperty("name")+" Age: "+retrievedPerson.getProperty("age") + "pic" + retrievedPerson.getProperty("pic"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		*/
		
		resp.setContentType("text/html");
	      PrintWriter out = resp.getWriter( );
	      DiskFileItemFactory factory = new DiskFileItemFactory();
	      // maximum size that will be stored in memory
	      factory.setSizeThreshold(maxMemSize);
	      // Location to save data that is larger than maxMemSize.
	      factory.setRepository(new File("/tmp"));

	      // Create a new file upload handler
	      ServletFileUpload upload = new ServletFileUpload(factory);
	      // maximum file size to be uploaded.
	      upload.setSizeMax( maxFileSize );

	      try{ 
	      // Parse the request to get file items.
	      List<FileItem> fileItems = upload.parseRequest(req);
		
	      // Process the uploaded file items
	      Iterator<FileItem> i = fileItems.iterator();

	      out.println("<html>");
	      out.println("<head>");
	      out.println("<title>Servlet upload</title>");  
	      out.println("</head>");
	      out.println("<body>");
	      while ( i.hasNext () ) 
	      {
	         FileItem fi = (FileItem)i.next();
	         if ( !fi.isFormField () )	
	         {
	            // Get the uploaded file parameters
	            String fileName = fi.getName();
	            // Write the file
	            if( fileName.lastIndexOf("/") >= 0 ){
	               file = new File( imageDirectory + 
	               fileName.substring( fileName.lastIndexOf("/"))) ;
	            }else{
	               file = new File( imageDirectory + 
	               fileName.substring(fileName.lastIndexOf("/")+1)) ;
	            }
	            fi.write( file ) ;
	            out.println("Uploaded Filename: " + fileName + "<br>");
	         }
	      }
	      out.println("</body>");
	      out.println("</html>");
	   }catch(Exception ex) {
	       System.out.println(ex);
	   }
	}
	
	
	
}
