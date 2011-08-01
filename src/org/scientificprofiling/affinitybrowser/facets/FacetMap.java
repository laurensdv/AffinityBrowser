package org.scientificprofiling.affinitybrowser.facets;

import java.util.HashMap;

public class FacetMap {
	protected String name;
	protected HashMap<String,Facet> facets;
	
	public FacetMap(String name) {
		this.facets = new HashMap<String,Facet>();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, Facet> getFacets() {
		return facets;
	}
	public void add(String name, Facet facet) {
		facets.put(name, facet);
	}
	public void setFacets(HashMap<String, Facet> facets) {
		this.facets = facets;
	}
}
