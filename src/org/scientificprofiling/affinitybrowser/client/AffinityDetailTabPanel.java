package org.scientificprofiling.affinitybrowser.client;

 
import com.extjs.gxt.ui.client.widget.TabPanel; 
import com.google.gwt.user.client.Element;  

public class AffinityDetailTabPanel extends TabPanel {
	   @Override  
	   protected void onRender(Element parent, int pos) {  
	     super.onRender(parent, pos);  

	     setMinTabWidth(115);  
	     setResizeTabs(true);  
	     setAnimScroll(true);  
	     setTabScroll(true);  
	     setCloseContextMenu(true);  

	   }  
}
