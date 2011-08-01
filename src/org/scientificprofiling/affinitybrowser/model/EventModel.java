package org.scientificprofiling.affinitybrowser.model;


import com.extjs.gxt.ui.client.data.BaseModel;
import com.google.gwt.maps.client.geom.LatLng;

public class EventModel extends BaseModel {

  public EventModel() {
  }

  public EventModel(String name, String url, String location, LatLng point, String description, String keywords) {
    set("name", name);
    set("url", url);
    set("location", location);
    set("point", point);
    set("keywords", keywords);
    set("description", description);
  }
  
  public String getName() {
	    return get("name");
	  }

  public String getLocation() {
    return get("location");
  }

  public LatLng getPoint() {
	    return get("point");
	  }

  public String getKeywords() {
    return get("keywords");
  }

  public String getDescription() {
    return get("description");
  }

  public String toString() {
    return getName();
  }

}
