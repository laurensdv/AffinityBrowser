package org.scientificprofiling.affinitybrowser.model;

import com.extjs.gxt.ui.client.data.BaseModel;

public class ScreenNameModel extends BaseModel {
	public ScreenNameModel(String s) {
	    set("screen", s);
	    set("name", s);
	 }
	
	public ScreenNameModel(String s, String name) {
	    this(s);
	    set("name", name);
	 }
	
	public String getScreen() {
		return get("screen");
	}
	
	public String getName() {
		return get("name");
	}
}
