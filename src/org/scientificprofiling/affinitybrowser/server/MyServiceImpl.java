package org.scientificprofiling.affinitybrowser.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.api.urlfetch.URLFetchServicePb.URLFetchService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.scientificprofiling.affinitybrowser.client.MyService;

@SuppressWarnings("serial")
public class MyServiceImpl extends RemoteServiceServlet implements
    MyService {
	private String responseText;

public String getResponse(String s, String result) {
	responseText = new String("");
	String response = new String("");
	URL toGet;
	try {
		toGet = new URL(s);
		URLConnection yc = toGet.openConnection();
		yc.setConnectTimeout(0);
		yc.setDoOutput(true);
	    BufferedReader in = new BufferedReader(
	                            new InputStreamReader(
	                            yc.getInputStream()));
	    String inputLine;

	    while ((inputLine = in.readLine()) != null) 
	        response+=inputLine;
	    in.close();
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    responseText = response;
    result = responseText;
    return responseText;
}
}
