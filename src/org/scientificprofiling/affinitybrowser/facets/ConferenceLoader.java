package org.scientificprofiling.affinitybrowser.facets;

import gdurelle.tagcloud.client.tags.TagCloud;
import gdurelle.tagcloud.client.tags.WordTag;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;
import org.scientificprofiling.affinitybrowser.client.MyService;
import org.scientificprofiling.affinitybrowser.client.MyServiceAsync;
import org.scientificprofiling.affinitybrowser.facets.PersonLoader.myJsonCallback;
import org.scientificprofiling.affinitybrowser.server.MyServiceImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ConferenceLoader {
	private WordTag targetTag;
	private String conferenceUrl;
	private String url;
	private TagCloud targetCloud;

	public void setConferenceUrl(String conferenceCode, WordTag target, TagCloud targetCl) {
		MyServiceAsync myService = (MyServiceAsync) GWT.create(MyService.class);
		targetTag = target;
		this.targetCloud = targetCl;
		conferenceUrl = new String("");
		url = "http://api.semanticprofiling.net/event.php?code="+conferenceCode;
		AsyncCallback callback = new AsyncCallback() {

		    public void onFailure(Throwable caught) {
		   targetTag.setLink("http://www.wikicfp.com");
		      System.out.println(caught.toString());
		    }

			public void onSuccess(Object result) {
				JSONObject productsObj = JSONParser.parseLenient((String)result).isObject();
				System.out.println(result.toString());
				try {
					String conferenceUrlTemp = productsObj.get("url").isString().stringValue();
					if(conferenceUrlTemp != null) {
						targetTag.setLink(conferenceUrlTemp);
						targetCloud.refresh();
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
			}
		  };
		  
		  myService.getResponse(url,conferenceUrl,callback);
		
	}
}
