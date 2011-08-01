package org.scientificprofiling.affinitybrowser.facets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacetTaxonomy extends Facet {
	protected String name;
	protected List<String> taxonomy;
	protected Map<String, Integer> weightedTaxonomy;
	
	private static void incrementValue(Map<String, Integer> counters, String toAdd) {
	    Integer currValue = counters.get(toAdd);
	    if (currValue == null)
	        counters.put(toAdd, 1);
	    else
	        counters.put(toAdd, currValue+1);
	}

	private static void decrementValue(Map<String, Integer> counters, String toRemove) {
	    Integer currValue = counters.get(toRemove);
	    if (currValue == 1)
	        counters.remove(toRemove);
	    else
	        counters.put(toRemove, currValue-1);
	}
	public FacetTaxonomy (String name) {
		this.name = name;
		taxonomy = new ArrayList<String>();
		weightedTaxonomy = new HashMap<String,Integer>();
	}
	
	public List<String> getTaxonomy() {
		return taxonomy;
	}
	public void add(String item) {
		taxonomy.add(item);
		incrementValue(weightedTaxonomy,item);
		Collections.sort(taxonomy);
	}
	public void add(Collection<String> items) {
		for(String item : items) {
			taxonomy.add(item);
			incrementValue(weightedTaxonomy,item);
		}
		Collections.sort(taxonomy);
	}
	public String getName() {
		return name;
	}
	public Map<String, Integer> getWeightedTaxonomy() {
		return weightedTaxonomy;
	}
	public void remove(String item){
		taxonomy.remove(item);
		weightedTaxonomy.remove(item);
		Collections.sort(taxonomy);
	}
	@Override
	protected boolean match(Object input) {
		if (input instanceof String) {
			return Collections.binarySearch(taxonomy,(String)input)>=0;
		}
		else return false;
	}
}
