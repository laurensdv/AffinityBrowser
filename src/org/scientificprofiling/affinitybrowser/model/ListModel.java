package org.scientificprofiling.affinitybrowser.model;

import org.scientificprofiling.affinitybrowser.facets.Person;

import com.extjs.gxt.ui.client.data.BaseModel;

public class ListModel extends BaseModel {
 public ListModel(String s, int n) {
    set("name", s);
    set("frequency", n);
 }
 
 public ListModel(String s) {
	    set("name", s);
	    set("frequency", 1);
	 }
 
 public String getName() {
	    return get("name");
	  }
 
 public String getFrequency() {
	    return get("name");
	  }
 @Override
 public boolean equals(Object compareObj) {
	 if (!(compareObj instanceof ListModel)) // Is the object being compared also a Person?
		    return false;
	 
	 return ((ListModel) compareObj).getName() == this.getName();
 }
}
