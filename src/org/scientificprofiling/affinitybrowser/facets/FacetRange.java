package org.scientificprofiling.affinitybrowser.facets;

public class FacetRange extends Facet {
	protected String name;
	protected int min, max;
	
	public FacetRange(String name) {
		this.name = name;
		this.min = 0;
		this.max = 180;
	}
	
	public FacetRange(String name,int min,int max) {
		this.name = name;
		this.min = min;
		this.max = max;
	}
	
	public void defineRange(int min, int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	protected boolean match(Object input) {
		if (input instanceof Float) {
			return (Float)input > min && (Float)input < max;
		}
		else return false;
	}

}
