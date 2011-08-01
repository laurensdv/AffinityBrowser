package org.scientificprofiling.affinitybrowser.facets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scientificprofiling.affinitybrowser.model.ListModel;
import org.scientificprofiling.affinitybrowser.model.PersonModel;

import com.extjs.gxt.ui.client.store.ListStore;

public class PersonSpace {
	private Map<String, Person> persons;
	private Map<String, Person> personByName;
	private Map<String, Person> originalPersons;
	private FacetMap personFacetMap;
	private FacetMap activatedFacetMap;
	private Map<String, List<Person>> personTagsMap;
	private Map<String, List<Person>> personConferencesMap;
	private Map<String, List<Person>> personMentionsMap;
	private ListStore<PersonModel> store;
	private List<ListModel> activatedFacetModels;

	public PersonSpace() {
		super();
		store = new ListStore<PersonModel>();
		originalPersons = new HashMap<String, Person>();
		persons = new HashMap<String, Person>();
		personByName = new HashMap<String, Person>();
		personFacetMap = newPersonFacetMap("persons");
		activatedFacetMap = newPersonFacetMap("activated facets");
		restorePersonFacetMap();
	}
	
	public PersonSpace(Set<Person> persons) {
		this();
		this.persons = new HashMap<String, Person>();
		this.originalPersons = this.persons;
		for (Person p : persons) {
			this.persons.put(p.getUri(), p);
			//this.personByName.put(p.getName(), p);
		}
	}

	public ListStore<PersonModel> getPersonStore() {
		store = new ListStore<PersonModel>();
		for (Person p : persons.values()) {
			PersonModel pm = new PersonModel(p);
			store.add(pm);
		}
		return store;
	}

	public List<PersonModel> getPersonModels() {
		ArrayList<PersonModel> list = new ArrayList<PersonModel>();
		for (Person p : persons.values()) {
			PersonModel pm = new PersonModel(p);
			list.add(pm);
		}
		return list;
	}
	public List<PersonModel> getOriginalPersons() {
		ArrayList<PersonModel> list = new ArrayList<PersonModel>();
		for (Person p : originalPersons.values()) {
			PersonModel pm = new PersonModel(p);
			list.add(pm);
		}
		return list;
	}
	private FacetMap newPersonFacetMap(String name) {
		FacetMap newFacetMap = new FacetMap(name);
		// newFacetMap.add("latitude",new FacetRange("latitude"));
		// newFacetMap.add("longitude",new FacetRange("longitude"));
		newFacetMap.add("tags", new FacetTaxonomy("tags"));
		newFacetMap.add("conferences", new FacetTaxonomy("conferences"));
		newFacetMap.add("mentions", new FacetTaxonomy("mentions"));
		return newFacetMap;
	}

	public boolean activateFacet(String facet, String value) {
		FacetTaxonomy f = (FacetTaxonomy) activatedFacetMap.getFacets().get(facet);
		//SAFETY STORE ORIGINAL PERSONS
		if (this.originalPersons.size() == 0) {
			initPersons(getPersons());
		}
		
		//AVOID ADDING SAME FACETITEM TWICE
		if(!f.getWeightedTaxonomy().keySet().contains(value)) {
			f.add(value);
			filterPersonList(facet, value);
			return true;
		} else return false;
	}

	public void deactivateFacet(String facet, String value) {
		FacetMap oldActivatedFacetMap = newPersonFacetMap("old activated facets");
		
		//SAFETY STORE ORIGINAL PERSONS
		if (this.originalPersons.size() == 0) {
			initPersons(getPersons());
		}

		// REMOVE FACET ITEM
		FacetTaxonomy f = (FacetTaxonomy) activatedFacetMap.getFacets().get(
				facet);
		f.remove(value);

		// RESET FACETS AND PERSONS
		oldActivatedFacetMap = activatedFacetMap;
		activatedFacetMap = newPersonFacetMap("activated facets");
		setPersons(this.originalPersons.values());

		// REACTIVATE ACTIVATED FACET ITEMS
		for (String facetName : oldActivatedFacetMap.getFacets().keySet()) {
			for (String facetItem : ((FacetTaxonomy) oldActivatedFacetMap
					.getFacets().get(facetName)).getWeightedTaxonomy().keySet()) {
				activateFacet(facetName, facetItem);
			}
		}
	}

	public Map<String, Person> updateRangeFacet(String facetitem, int min,
			int max) {
		return persons;
	}

	private void restorePersonFacetMap() {
		updatePersonFacetMap(persons);
	}

	private void filterPersonList(String type, String t) {
		HashSet<Person> filtered = new HashSet<Person>();

		if (personTagsMap.get(t) != null && type == "tags") {
			filtered.addAll(personTagsMap.get(t));
		}

		if (personMentionsMap.get(t) != null && type == "mentions") {
			filtered.addAll(personMentionsMap.get(t));
		}

		if (personConferencesMap.get(t) != null && type == "conferences") {
			filtered.addAll(personConferencesMap.get(t));
		}

		setPersons(filtered);
	}

	private void filterPersonListByAllFacets() {
		HashSet<Person> filtered = new HashSet<Person>();
		FacetTaxonomy tags = (FacetTaxonomy) activatedFacetMap.getFacets().get(
				"tags");
		FacetTaxonomy mentions = (FacetTaxonomy) activatedFacetMap.getFacets()
				.get("mentions");
		FacetTaxonomy conferences = (FacetTaxonomy) activatedFacetMap
				.getFacets().get("conferences");

		for (String t : tags.getTaxonomy()) {
			if (personTagsMap.get(t) != null) {
				filtered.addAll(personTagsMap.get(t));
			}
		}

		for (String m : mentions.getTaxonomy()) {
			if (personMentionsMap.get(m) != null) {
				filtered.addAll(personMentionsMap.get(m));
			}
		}

		for (String c : conferences.getTaxonomy()) {
			if (personConferencesMap.get(c) != null) {
				filtered.addAll(personConferencesMap.get(c));
			}
		}
		setPersons(filtered);
	}

	private void updatePersonFacetMap(Map<String, Person> persons) {
		personTagsMap = new HashMap<String, List<Person>>();
		personConferencesMap = new HashMap<String, List<Person>>();
		personMentionsMap = new HashMap<String, List<Person>>();

		FacetTaxonomy tags = new FacetTaxonomy("tags");
		FacetTaxonomy conferences = new FacetTaxonomy("conferences");
		FacetTaxonomy mentions = new FacetTaxonomy("mentions");
		// FacetTaxonomy mentions = (FacetTaxonomy)
		// personFacetMap.getFacets().get("mentions");

		for (Person p : persons.values()) {
			tags.add(p.getTagsSet());
			mentions.add(p.getMentionsSet());
			conferences.add(p.getConferencesSet());

			for (String t : p.getTagsSet()) {
				if (personTagsMap.get(t) == null) {
					ArrayList<Person> personlist = new ArrayList<Person>();
					personlist.add(p);
					personTagsMap.put(t, personlist);
				} else
					personTagsMap.get(t).add(p);
			}

			for (String t : p.getMentionsSet()) {
				if (personMentionsMap.get(t) == null) {
					ArrayList<Person> personlist = new ArrayList<Person>();
					personlist.add(p);
					personMentionsMap.put(t, personlist);
				} else
					personMentionsMap.get(t).add(p);
			}

			for (String t : p.getConferencesSet()) {
				if (personConferencesMap.get(t) == null) {
					ArrayList<Person> personlist = new ArrayList<Person>();
					personlist.add(p);
					personConferencesMap.put(t, personlist);
				} else
					personConferencesMap.get(t).add(p);
			}
		}

		personFacetMap.add("tags", tags);
		personFacetMap.add("conferences", conferences);
		personFacetMap.add("mentions", mentions);
	}

	public Collection<Person> getPersons() {
		return persons.values();
	}

	public Map<String, Person> getPersonsMap() {
		return persons;
	}
	
	public Person findPerson(String name) {
		return personByName.get(name);
	}

	public void setPersons(Map<String, Person> persons) {
		this.persons = persons;
		restorePersonFacetMap();
	}

	public void setPersons(Collection<Person> persons) {
		this.persons = new HashMap<String, Person>();
		for (Person p : persons) {
			this.persons.put(p.getUri(), p);
			//this.personByName.put(p.getName(), p);
		}
		restorePersonFacetMap();
	}

	public void initPersons(Collection<Person> persons) {
		setPersons(persons);
		this.originalPersons = new HashMap<String, Person>();
		for (Person p : persons) {
			this.originalPersons.put(p.getUri(), p);
			this.personByName.put(p.getName(), p);
		}
	}
	public FacetMap getPersonFacetMap() {
		return personFacetMap;
	}

	public FacetMap getActivatedFacets() {
		return activatedFacetMap;
	}

	public void setPersonFacetMap(FacetMap personFacetMap) {
		this.personFacetMap = personFacetMap;
	}

}
