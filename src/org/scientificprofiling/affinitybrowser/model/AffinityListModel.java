package org.scientificprofiling.affinitybrowser.model;

import com.extjs.gxt.ui.client.data.BaseModel;

public class AffinityListModel extends BaseModel {
	 public AffinityListModel(String s, String t) {
		    set("name", s);
		    set("type", t);
		 }
		 
		 public String getName() {
			    return get("name");
			  }
		 
		 public String getType() {
			    return get("type");
			  }
}
