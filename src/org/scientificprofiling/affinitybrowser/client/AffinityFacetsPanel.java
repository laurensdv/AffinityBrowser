package org.scientificprofiling.affinitybrowser.client;

 import com.extjs.gxt.ui.client.data.BaseModelData;  
 import com.extjs.gxt.ui.client.data.ModelData;  
 import com.extjs.gxt.ui.client.data.ModelIconProvider;  
 import com.extjs.gxt.ui.client.store.TreeStore;  
 import com.extjs.gxt.ui.client.util.IconHelper;  
 import com.extjs.gxt.ui.client.widget.ContentPanel;  
 import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;  
 import com.extjs.gxt.ui.client.widget.layout.FitLayout;  
 import com.extjs.gxt.ui.client.widget.layout.FlowLayout;  
 import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;  
 import com.google.gwt.user.client.Element;  
 import com.google.gwt.user.client.ui.AbstractImagePrototype; 

 public class AffinityFacetsPanel extends ContentPanel {
 		public AffinityFacetsPanel() {
 		}  
	   
	   @Override  
	   protected void onRender(Element parent, int index) {  
	     super.onRender(parent, index);  
	     setLayout(new FlowLayout(10));  
	    
	     setHeading("Affinities");  
	     setBodyBorder(false);  
	   
	     setLayout(new AccordionLayout());    
	   
	     ContentPanel cp = new ContentPanel();  
	     cp.setAnimCollapse(false);  
	     cp.setBodyStyleName("pad-text");  
	     cp.setHeading("Locations");  
	     add(cp);  
	     
	     cp = new ContentPanel();  
	     cp.setAnimCollapse(false);  
	     cp.setBodyStyleName("pad-text");  
	     cp.setHeading("Mentions");  
	     add(cp);  
	     
	     cp = new ContentPanel();  
	     cp.setAnimCollapse(false);  
	     cp.setBodyStyleName("pad-text");  
	     cp.setHeading("Conferences");  
	     add(cp);  
	     
	     cp = new ContentPanel();  
	     cp.setAnimCollapse(false);  
	     cp.setBodyStyleName("pad-text");  
	     cp.setHeading("Tags");  
	     add(cp);  
	     
	     setSize(200, 325);   
	   }  
	   
	   private ModelData newItem(String text, String iconStyle) {  
	     ModelData m = new BaseModelData();  
	     m.set("name", text);  
	     m.set("icon", iconStyle);  
	     return m;  
	   }  
	   
	 }  