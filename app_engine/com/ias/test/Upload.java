package com.ias.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import com.google.appengine.api.datastore.Key;

public class Upload extends HttpServlet {
	/**
	 * 
	 */
	public static HashMap<String, Integer> hmp = new HashMap<String, Integer>();
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	DatastoreService datastore;
	static String imageDirectory = null;
	static Key personKey = null;

	@Override
	public void init() {
		datastore = DatastoreServiceFactory.getDatastoreService();
		// imageDirectory = getServletContext().getInitParameter("file-upload");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		BlobKey blobKey = blobs.get("myFile");

		// Entity person = new Entity("Person", req.getParameter("Location"));

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

		// Key k = KeyFactory.createKey("Person", Location + "_" +
		// Integer.toString(num) );
		// res.getWriter().println("Catch\t" + k.toString());

		if (blobKey == null) {
			res.sendRedirect("/");
		} else {

			Entity person = new Entity("Person", Location + "_"
					+ Integer.toString(num));
			person.setProperty("rating", req.getParameter("rating"));
			person.setProperty("Location", req.getParameter("Location"));
			person.setProperty("Image", blobKey.getKeyString());
			datastore.put(person);

			res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
		}

	}
}
