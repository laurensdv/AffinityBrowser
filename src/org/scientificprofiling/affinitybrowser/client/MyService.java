package org.scientificprofiling.affinitybrowser.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("myService")
public interface MyService extends RemoteService {
  public String getResponse(String s, String result);
}
