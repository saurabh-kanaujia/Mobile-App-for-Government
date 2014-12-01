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
     
     Key k = KeyFactory.createKey("Person", Location + "_" +  Integer.toString(1));

		
		Query q = new Query("Person");
		q.setFilter(Query.FilterOperator.EQUAL.of("Location", Location));
		
		PreparedQuery pq = datastore.prepare(q);

		//String script="<script src='myScript.js'></script>";
		String script = "";
		int state = 1 ;
		
		for (Entity result : pq.asIterable()) 
		{
			records.add(new Record(result));

			
			
			state = state + 1 ;
			String src = "http://1-dot-glass-chemist-761.appspot.com/serve?blob-key="+result.getProperty("Image");
	
			Location = (String) result.getProperty("Location") ;
			String Rating = (String) result.getProperty("rating") ;
			String dirt =  (String) result.getProperty("dirttype") ;
			String lat =  (String) result.getProperty("latitude") ;
			String lng =  (String) result.getProperty("longitude") ;
			
			
			if(lat == null)
				lat = "--" ;
			if(lng == null)
				lng = "--" ;
			
			script += Location + "@_@" + dirt +"@_@" + Rating+ "@_@" + lat + "@_@" + lng + "@_@" + src + "@_@";
			
			
			//	    res.getWriter().println(
			
		//		  "</br>Location: " + result.getProperty("Location") + "</br>Rating:  " +result.getProperty("rating"));// +"</br> URL= &nbsp&nbsp"  +  "1-dot-glass-chemist-761.appspot.com/serve?blob-key="+result.getProperty("Image") );	
		 //   res.getWriter().print("</br></br><img src = "+ src +"  height=\"200\" width=\"200\" /></br></br></br>");
		}
		
		String htm="<!DOCTYPE html>" 
				+ "<html lang=\"en\" xml:lang=\"en\">" 
				+ "<head>" 
				+ "<meta charset=\"utf-8\">" 
				+ "<title>Search Result</title>" 
				+ "<script type=\"text/javascript\">" 
				+ "var string;" 
				+ "var urls;" 
				+ "var slideimages;" 
				+ "var len;" 
				+ "var cur;" 
				+ "var tokens;" 
				+ "function setting()" 
				+ "{" 
				+ "var num0 = '<h1 align=\"center\" > Search Results  </h1>';" 
				+ "var num1 = '<div align=\"center\">' ;" 
				+ "var num2 = '<button type=\"button\" id=\"bu2\" name=\"prev\" onclick=\"prev()\" style=\"margin-right:20px\"><img src=\"https://christianadrianto.files.wordpress.com/2008/09/left-arrow.png\" width=45 height=45/></button>';" 
				+ "var num3 = '<img src=\"\" id=\"slide\" width=600 height=356 alt=\"No Image\" style=\"border:2px solid #707070\"' + '/' + '>' ;" 
				+ "var num4 = '<button type=\"button\" id=\"bu1\" name=\"next\" onclick=\"next()\" style=\"margin-left:20px\"><img src=\"http://4vector.com/i/free-vector-green-right-arrow-clip-art_116717_Green_Right_Arrow_clip_art_medium.png\" width=45 height=45/></button>' ;"
				+ "var num5 = '</div>' ;" 
				+ "var num6 = '<div id=\"loc\"  align=\"center\"></div>';"
				+ "var num7 = '<div id=\"dirt\" align=\"center\"></div>';"
				+ "var num8 = '<div id=\"rat\" align=\"center\"></div>';"
				+ "var num9 = '<div id=\"lat\" align=\"center\"></div>';"
				+ "var num10 = '<div id=\"lng\" align=\"center\"></div>';"
				+ "var num =num0+ num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10;" 
				+ "string=document.getElementById(\"id1\").value;"
				+ "tokens=string.split(\"@_@\");"
				+ "urls= string.split(\"@_@\");" 
				+ "slideimages = new Array();" 
				+ "len= (urls.length-1)/6 - 1;" 
				+ "cur=0;" 
				+ "for(i=0;i<=len;i++)" 
				+ "{" 
				+ "" 
				+ "slideimages[i] = new Image();" 
				+ "slideimages[i].src = urls[(i*6)+5];" 
				+ "}" 
				+ "cur=0;" 
				+ "document.write(num) ;" 
				+ "document.getElementById(\"bu2\").style.visibility = \"hidden\";" 
				+ "document.getElementById(\"slide\").src = slideimages[0].src;"
				+ "document.getElementById(\"loc\").innerHTML = \"<b>Location: </b>\" + urls[0];"
				+ "document.getElementById(\"dirt\").innerHTML = \"<b>Dirt Type: </b>\" + urls[1];"
				+ "document.getElementById(\"rat\").innerHTML = \"<b>Rating: </b>\" + urls[2];"
				+ "document.getElementById(\"lat\").innerHTML = \"<b>Latitude: </b>\" + urls[3];"
				+ "document.getElementById(\"lng\").innerHTML = \"<b>Longitude: </b>\" + urls[4];"
				+ "}" 
				+ "" 
				+ "" 
				+ "function check()" 
				+ "{" 
				+ "if(cur < len )" 
				+ "{" 
				+ "document.getElementById(\"bu1\").style.visibility = \"visible\";" 
				+ "}" 
				+ "else " 
				+ "document.getElementById(\"bu1\").style.visibility = \"hidden\";" 
				+ "if(cur > 0 )" 
				+ "{" 
				+ "document.getElementById(\"bu2\").style.visibility = \"visible\";" 
				+ "" 
				+ "}" 
				+ "else " 
				+ "document.getElementById(\"bu2\").style.visibility = \"hidden\";" 
				+ "}" 
				+ "" 
				+ "function prev()" 
				+ "{" 
				+ "if(cur>0)" 
				+ "{" 
				+ "document.getElementById(\"slide\").src = slideimages[cur-1].src;"
				+ "document.getElementById(\"loc\").innerHTML = \" <b>Location: </b>\" + urls[((cur-1)*6)+0];"
				+ "document.getElementById(\"dirt\").innerHTML = \"<b>Dirt Type: </b>\" + urls[((cur-1)*6)+1];"
				+ "document.getElementById(\"rat\").innerHTML = \"<b>Rating: </b>\" + urls[((cur-1)*6)+2];"
				+ "document.getElementById(\"lat\").innerHTML = \"<b>Latitude: </b>\" + urls[((cur-1)*6)+3];"
				+ "document.getElementById(\"lng\").innerHTML = \"<b>Longitude: </b>\" + urls[((cur-1)*6)+4];"
				+ "cur--;" 
				+ "check();" 
				+ "}" 
				+ "else " 
				+ "{" 
				+ "check();" 
				+ "}" 
				+ "}" 
				+ "" 
				+ "function next()" 
				+ "{" 
				+ "if(cur<(len))" 
				+ "{" 
				+ "document.getElementById(\"slide\").src = slideimages[cur+1].src;"
				+ "document.getElementById(\"loc\").innerHTML = \"<b>Location: </b>\" + urls[((cur+1)*6)+0];"
				+ "document.getElementById(\"dirt\").innerHTML = \"<b>Dirt Type: </b>\" + urls[((cur+1)*6)+1];"
				+ "document.getElementById(\"rat\").innerHTML = \"<b>Rating: </b>\" + urls[((cur+1)*6)+2];"
				+ "document.getElementById(\"lat\").innerHTML = \"<b>Latitude: </b>\" + urls[((cur+1)*6)+3];"
				+ "document.getElementById(\"lng\").innerHTML = \"<b>Longitude: </b>\" + urls[((cur+1)*6)+4];"
				+ "cur++;" 
				+ "check();" 
				+ "}" 
				+ "else" 
				+ "{" 
				+ "check();" 
				+ "}" 
				+ "}" 
				+ "" 
				+ "</script>" 
				+ "</head>" 
				+ "<body onload=\"setting()\"    >" 
				+ "<input type = \"hidden\" id=\"id1\" value=\""+script+"\"/>"
				+ "</body></html>";
		res.setContentType("text/html"); 
		PrintWriter out = res.getWriter();
		out.println(htm);
    }
}

