package com.ias.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class UploadImage extends HttpServlet {
	public static HashMap<String, Integer> hmp = new HashMap<String, Integer>();
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	DatastoreService datastore;
	static String imageDirectory = null;
	static Key personKey = null;

	public void init() {
		datastore = DatastoreServiceFactory.getDatastoreService();
		// imageDirectory = getServletContext().getInitParameter("file-upload");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/html");

		PrintWriter out = resp.getWriter();
		out.println("<h1>" + "Hi Naveen" + "</h1>");

		resp.getWriter().write("Hi DO GET IS WORKING");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		@SuppressWarnings("deprecation")
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		BlobKey blobKey = blobs.get("image");

		boolean isMultiPart = ServletFileUpload.isMultipartContent(req);
		if (isMultiPart) {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			HashMap<String, String> formFileds = new HashMap<String, String>();
			try {
				List<FileItem> items = upload.parseRequest(req);

				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();

					if (item.isFormField()) {
						formFileds.put(item.getFieldName(), item.getString());
					}
				}

			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String Location = req.getParameter("Location");
			Location = Location.toLowerCase().trim();
			int num;
			if (hmp.containsKey(Location)) {
				num = hmp.get(Location) + 1;
				hmp.put(Location, num);
			} else {
				num = 1;
				hmp.put(Location, 1);
			}
			
			
			Query q = new Query("Person");
			q.setFilter(Query.FilterOperator.EQUAL.of("Location", Location));
			
			PreparedQuery pq = datastore.prepare(q);

			//String script="<script src='myScript.js'></script>";
			
			int state = 1 ;
			
			for (Entity result : pq.asIterable()) 
			{		
				state = state + 1 ;
			}

			
			

			if (blobKey == null) {
				res.sendRedirect("/");
			} else {

				Entity person = new Entity("Person", Location + "_"
						+ Integer.toString(state));
				person.setProperty("rating", req.getParameter("rating"));
				person.setProperty("Location", Location);
				person.setProperty("dirttype", req.getParameter("dirttype"));
				person.setProperty("longitude",req.getParameter("longitude"));
				person.setProperty("latitude",req.getParameter("latitude"));
				person.setProperty("Image", blobKey.getKeyString());
				datastore.put(person);

				res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
			}
		} else {
			// String error="no content";
			res.getWriter().print("no content");

		}

	}
}
