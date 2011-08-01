package org.scientificprofiling.affinitybrowser.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FormSubmitAsync {
	public void emailForm(String formData, AsyncCallback<String> callback);
}
