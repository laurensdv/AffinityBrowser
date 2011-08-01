package org.scientificprofiling.affinitybrowser.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("FormSubmitServlet")
public interface FormSubmit extends RemoteService {
	public String emailForm(String formData);
}
