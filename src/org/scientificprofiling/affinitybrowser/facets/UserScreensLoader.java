package org.scientificprofiling.affinitybrowser.facets;

import org.scientificprofiling.affinitybrowser.model.ScreenNameModel;
import org.scientificprofiling.affinitybrowser.client.MyService;
import org.scientificprofiling.affinitybrowser.client.MyServiceAsync;

import com.extjs.gxt.ui.client.store.ListStore;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UserScreensLoader {
	private String response;
	private String url;
	private ListStore<ScreenNameModel> target;
	public void setUserNames(ListStore<ScreenNameModel> targetModel) {
		MyServiceAsync myService = (MyServiceAsync) GWT.create(MyService.class);
		target = targetModel;
		//url = "http://api.semanticprofiling.net/alluserNames.php";
		url = "http://api.semanticprofiling.net/cached_profiles/allusers.json";
		AsyncCallback callback = new AsyncCallback() {

		    public void onFailure(Throwable caught) {
		      System.out.println(caught.toString());
		    }

			public void onSuccess(Object result) {
				JSONObject namesObject = JSONParser.parseLenient((String)result).isObject();
				for(String screen : namesObject.keySet()) {
					target.add(new ScreenNameModel(screen, namesObject.get(screen).isString().stringValue()));
				}
				System.out.println(result.toString());
				try {
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
			}
		  };
		  
		  myService.getResponse(url,response,callback);
	}

	public void setUserScreens(ListStore<ScreenNameModel> targetModel) {
		MyServiceAsync myService = (MyServiceAsync) GWT.create(MyService.class);
		target = targetModel;
		url = "http://api.semanticprofiling.net/allusers.php";
		AsyncCallback callback = new AsyncCallback() {

		    public void onFailure(Throwable caught) {
		      System.out.println(caught.toString());
		    }

			public void onSuccess(Object result) {
				JSONArray screensArray = JSONParser.parseLenient((String)result).isArray();
				for(int i = 0; i<screensArray.size(); i++) {
					target.add(new ScreenNameModel(screensArray.get(i).isString().stringValue()));
				}
				System.out.println(result.toString());
				try {
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
			}
		  };
		  
		  myService.getResponse(url,response,callback);
	}
}
