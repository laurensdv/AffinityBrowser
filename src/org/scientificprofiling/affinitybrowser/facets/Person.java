package org.scientificprofiling.affinitybrowser.facets;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.maps.client.geom.LatLng;

public class Person {
	private String uri;
	private String name;
	private String screen;
	private String description;
	private String location;
	private String imgurl;
	private LatLng point;
	private Map<String, Integer> mentions;
	private Map<String, Integer> tags;
	private Map<String, Integer> conferences;
	
	private int commonMentions = 0, commonTags = 0,commonConferences = 0;
	
	public Person(String uri, String name, String screen, String location,  String image, String description,
			Map<String,Integer> mentions, Map<String,Integer> conferences, Map<String,Integer> tags) {
		super();
		this.uri = uri;
		this.name = name;
		this.screen = screen;
		this.location = location;
		this.imgurl = image;
		this.description = description;
		this.mentions = mentions;
		this.tags = tags;
		this.conferences = conferences;
		this.point = LatLng.newInstance(0,0);
		extractPoint();
	}
	
	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public int getCommonMentions() {
		return commonMentions;
	}

	public void setCommonMentions(int commonMentions) {
		this.commonMentions = commonMentions;
	}

	public int getCommonTags() {
		return commonTags;
	}

	public void setCommonTags(int commonTags) {
		this.commonTags = commonTags;
	}

	public int getCommonConferences() {
		return commonConferences;
	}

	public void setCommonConferences(int commonConferences) {
		this.commonConferences = commonConferences;
	}
	
	
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	//TODO extract latitude and longitude from location string
	private void extractPoint() {
		try {
			JSONValue locationValue = JSONParser.parseLenient(location);
			JSONObject locationObject = locationValue.isObject();
			String locationKey = (String) (locationObject.keySet()).toArray()[0];
			locationObject = locationObject.get(locationKey).isObject();
			JSONObject locationLongObject = locationObject.get("http://www.w3.org/2003/01/geo/wgs84_pos#long").isArray().get(0).isObject();
			JSONObject locationLatObject = locationObject.get("http://www.w3.org/2003/01/geo/wgs84_pos#lat").isArray().get(0).isObject();
			JSONObject locationLabelObject = locationObject.get("http://www.w3.org/2000/01/rdf-schema#label").isArray().get(0).isObject();
			double longitude = Double.valueOf(locationLongObject.get("value").isString().stringValue());
			double latitude = Double.valueOf(locationLatObject.get("value").isString().stringValue());
			this.location = locationLabelObject.get("value").isString().stringValue();
			this.point = LatLng.newInstance(latitude,longitude);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Person() {
		// TODO Auto-generated constructor stub
	}

	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
		extractPoint();
	}
	public LatLng getPoint() {
		return point;
	}
	public void setPoint(LatLng point) {
		this.point = point;
	}
	public Set<String> getMentionsSet() {
		return mentions.keySet();
	}

	public Set<String> getTagsSet() {
		return tags.keySet();
	}
	public Set<String> getConferencesSet() {
		return conferences.keySet();
	}
	
	public Map<String, Integer> getMentions() {
		return mentions;
	}

	public Map<String, Integer> getTags() {
		return tags;
	}
	public Map<String, Integer> getConferences() {
		return conferences;
	}

}
