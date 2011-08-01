package org.scientificprofiling.affinitybrowser.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface MyServiceAsync {
  public void getResponse(String s, String result, AsyncCallback<String> callback);
}
