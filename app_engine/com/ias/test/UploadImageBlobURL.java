package com.ias.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class UploadImageBlobURL extends HttpServlet {

	BlobstoreService blobstoreService;

	public void init() {

		blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.print(blobstoreService.createUploadUrl("/UploadImage"));
	}

}
