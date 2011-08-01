package org.scientificprofiling.affinitybrowser.model;

import org.scientificprofiling.affinitybrowser.facets.Person;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.google.gwt.maps.client.geom.LatLng;

public class PersonModel extends BaseModel {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public PersonModel() {
  }
  
  public PersonModel(Person p) {
	    set("name", p.getName());
	    set("uri", p.getUri());
	    set("screen", p.getScreen());
	    set("imgurl", p.getImgurl());
	    set("location", p.getLocation());
	    set("point",p.getPoint());
	    set("description", p.getDescription());
	    set("tags", p.getCommonTags());
	    set("mentions", p.getCommonMentions());
	    set("conferences", p.getCommonConferences());
	    set("person",p);
  }
  
  public PersonModel(String name) {
	  set("name", name);
  }
  
  public Person getPerson() {
	  return get("person");
  }

  public PersonModel(String name, String uri, String imgurl, String location, int tags, int mentions, int conferences) {
    set("name", name);
    set("uri", uri);
    set("location", location);
    set("tags", tags);
    set("mentions", mentions);
    set("conferences",conferences);
    set("imgurl",imgurl);
  }
  
  public String getScreen() {
	  return get("screen");
  }
  
  public String getName() {
	    return get("name");
	  }
  
  public String getUri() {
	    return get("uri");
	  }

  public String getLocation() {
    return get("location");
  }
  public LatLng getPoint() {
	  return get("point");
  }

  public int getTags() {
    return get("tags");
  }

  public int getMentions() {
    return get("mentions");
  }

  public int getConferences() {
    return get("conferences");
  }

  public String toString() {
    return getName();
  }

}
