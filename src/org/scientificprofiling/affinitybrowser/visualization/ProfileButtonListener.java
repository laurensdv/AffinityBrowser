package org.scientificprofiling.affinitybrowser.visualization;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;

public class ProfileButtonListener extends SelectionListener<ButtonEvent> {
	private String screenName;
	public ProfileButtonListener(String screenName) {
		this.screenName = screenName;
	}
	@Override
	public void componentSelected(ButtonEvent ce) {
		com.google.gwt.user.client.Window.open("http://www.twitter.com/"+screenName, "_blank", "");
		
	}

}
