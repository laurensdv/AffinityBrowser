package org.scientificprofiling.affinitybrowser.facets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;
import org.scientificprofiling.affinitybrowser.client.AffinityBrowser;
import org.scientificprofiling.affinitybrowser.client.MyService;
import org.scientificprofiling.affinitybrowser.client.MyServiceAsync;
import org.scientificprofiling.affinitybrowser.model.PersonModel;

import com.extjs.gxt.ui.client.data.ModelType;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.ProgressBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class PersonCachedRemoteLoader {
	private String userScreenName;
	private ListStore<PersonModel> store;
	private Set<Person> persons;
	private Set<String> uris;
	private AffinityBrowser parent;
	private Map<String, Map<String, Integer>> commonalities;
	protected ModelType type;
	private int i, size, attempts = 0;
	private MessageBox box;
	private ProgressBar bar;

	public PersonCachedRemoteLoader(String screen, AffinityBrowser parent) {
		type = new ModelType();
		type.addField("Uri", "uri");
		type.addField("Name", "name");
		type.addField("Location", "location");
		type.addField("Description", "description");
		type.addField("Mentions", "mentions");
		type.addField("Tags", "tags");
		type.addField("Conferences", "scientific_events");
		type.addField("Friends", "friends");
		
		this.userScreenName = screen;

		this.parent = parent;

		commonalities = new HashMap<String, Map<String, Integer>>();
		persons = new HashSet<Person>();
		uris = new HashSet<String>();
		store = new ListStore<PersonModel>();
	}

	public Set<Person> getPersons() {
		return persons;
	}

	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}
	
	public String getUserScreenName() {
		return this.userScreenName;
	}

	public ListStore<PersonModel> getStore() {
		return store;
	}

	public void info() {
		MessageBox.info("Loading Json", "JSON", null);
	}

	public void load() {
		box = MessageBox.progress("Please wait", "Loading persons...",  "Initializing...");  
		bar = box.getProgressBar();
		
		commonalities = new HashMap<String, Map<String, Integer>>();
		persons = new HashSet<Person>();
		uris = new HashSet<String>();
		store = new ListStore<PersonModel>();
		
		MyServiceAsync myService = (MyServiceAsync) GWT.create(MyService.class);
		
		String url = "http://api.semanticprofiling.net/cached_discovery/"+userScreenName+".json";
		String response = "";
		AsyncCallback callback = new AsyncCallback() {

			public void onFailure(Throwable caught) {
				// MessageBox.alert("Error", "Error: " + exception, null);
				System.out.println("Error " + caught);
			}

			public void onSuccess(Object response) {
				JSONObject productsObj = JSONParser.parseLenient((String)response).isObject();
				try {
					JSONObject tags = productsObj.get("tags").isObject();
					HashMap<String, Integer> tagCommonalities = new HashMap<String, Integer>();
					if (tags != null)
						for (String t : tags.keySet()) {
							//double count = tags.get(t).isNumber().doubleValue();
							//tagCommonalities.put(t, (int)count);
							String count = tags.get(t).isString().stringValue();
							tagCommonalities.put(t, Integer.valueOf(count));
							uris.add(t);

						}
					commonalities.put("tags", tagCommonalities);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				try {
					JSONObject conferences = productsObj.get("scientific_events")
							.isObject();
					HashMap<String, Integer> conferencesCommonalities = new HashMap<String, Integer>();
					if (conferences != null)
						for (String t : conferences.keySet()) {
							double count = conferences.get(t).isNumber()
									.doubleValue();
							conferencesCommonalities.put(t,
									(int)count);
							uris.add(t);
						}
					commonalities.put("conferences", conferencesCommonalities);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				try {
					JSONObject mentions = productsObj.get("mentions")
							.isObject();
					HashMap<String, Integer> mentionsCommonalities = new HashMap<String, Integer>();
					if (mentions != null)
						for (String t : mentions.keySet()) {
							String count = mentions.get(t).isString()
									.stringValue();
							mentionsCommonalities.put(t, Integer.valueOf(count));
							uris.add(t);

						}
					commonalities.put("mentions", mentionsCommonalities);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				Set<MyServiceAsync> myServices = new HashSet<MyServiceAsync>();
				String profileBase = "http://api.semanticprofiling.net/cached_profiles/";
				i = 0;
				size = uris.size();
			
				for (String uri : uris) {
					String reply = "";
					String adaptedUri = uri.substring(uri.indexOf("=") + 1);
					MyServiceAsync myService = (MyServiceAsync) GWT.create(MyService.class);
					myServices.add(myService);
					myService.getResponse(profileBase+adaptedUri+".json",reply,new myJsonCallback());
					
				}

			}

		};
		
		myService.getResponse(url,response,callback);
	}

	protected class myJsonCallback implements AsyncCallback<String> {
		public void onSuccess(String result) {
			try {
				JSONObject response = JSONParser.parseLenient((String)result).isObject();
				Person p = parseJsonData(response);
				String uri = p.getUri();

				try {
					Integer conferencesCommonalities = commonalities.get(
							"conferences").get(uri);
					p.setCommonConferences(conferencesCommonalities);
				} catch (Exception e) {
					//Info.display("No Conferences", p.getName());
				}

				try {
					Integer mentionsCommonalities = commonalities.get(
							"mentions").get(uri);
					p.setCommonMentions(mentionsCommonalities);
				} catch (Exception e) {
					//Info.display("No mentions", p.getName());
				}

				try {
					Integer tagsCommonalities = commonalities.get("tags").get(
							uri);
					p.setCommonTags(tagsCommonalities);
				} catch (Exception e) {
					//Info.display("No tags", p.getName());
				}

				PersonModel pm = new PersonModel(p);
				store.add(pm);
				persons.add(p);
				i++;
				bar.updateProgress((double)i / (double)size , (int) i*100 / size + "% Complete");
				if (i == size) {
					parent.initPersons(getPersons());
					parent.updateFacets(getPersons());
					parent.updateMap(getPersons());
					parent.windowSetup();
		            box.close();  
		            Info.display("Message", i+ " Persons were loaded", "");
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
				i++;
				if (i == size) {
					parent.initPersons(getPersons());
					parent.updateFacets(getPersons());
					parent.updateMap(getPersons());
					box.close();  
		            Info.display("Message", "Persons were loaded", "");
				}
			}
		}

		public void onFailure(Throwable exception) {
			// MessageBox.alert("Error", "Error: " + exception, null);
			System.out.println("Error " + exception);
			i++;
			if (i == size) {
				parent.initPersons(getPersons());
				parent.updateFacets(getPersons());
				parent.updateMap(getPersons());
				box.close();  
	            Info.display("Message", "Persons were loaded", "");
			}
		}
	}

	private Person parseJsonData(JSONValue value) {
		JSONObject productsObj = value.isObject();

		String name = productsObj.get("name").isString().stringValue();
		String screen = productsObj.get("screen_name").isString().stringValue();
		String uri = productsObj.get("uri").isString().stringValue();
		String image = (productsObj.get("image").isArray().isArray()).get(0)
				.isString().stringValue();
		String location = "No location";

		Set<String> mentionsList = new HashSet<String>();
		Set<String> tagsList = new HashSet<String>();
		Set<String> conferencesList = new HashSet<String>();

		HashMap<String, Integer> mentionsMap = new HashMap<String, Integer>();
		HashMap<String, Integer> tagsMap = new HashMap<String, Integer>();
		HashMap<String, Integer> conferencesMap = new HashMap<String, Integer>();

		try {
			location = productsObj.get("location").isString().stringValue();
		} catch (Exception e) {
			JSONObject locationObject = productsObj.get("location").isObject();
			location = locationObject.toString();
		}

		JSONArray descriptionArray = productsObj.get("description").isArray();
		String description = descriptionArray.get(0).isString().stringValue();

		JSONObject tags = productsObj.get("tags").isObject();
		JSONObject mentions = productsObj.get("mentions").isObject();
		JSONObject conferences = productsObj.get("scientific_events")
				.isObject();

		if (mentions != null) {
			mentionsList = mentions.keySet();
			for (String m : mentionsList) {
				mentionsMap.put(
						m,
						Integer.valueOf((int) mentions.get(m).isNumber()
								.doubleValue()));
			}
		}

		if (tags != null) {
			tagsList = tags.keySet();
			for (String t : tagsList) {
				tagsMap.put(
						t,
						Integer.valueOf((int) tags.get(t).isNumber()
								.doubleValue()));
			}

		}
		if (conferences != null) {
			conferencesList = conferences.keySet();
			for (String c : conferencesList) {
				conferencesMap.put(
						c,
						Integer.valueOf((int) conferences.get(c).isNumber()
								.doubleValue()));
			}
		}

		Person p = new Person(uri, name, screen, location, image, description,
				mentionsMap, conferencesMap, tagsMap);

		return p;
	}

	private List<String> jsonToString(JSONArray jsonArray) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < jsonArray.size(); i++) {
			list.add(jsonArray.get(i).isString().stringValue());
		}
		return list;
	}

}
