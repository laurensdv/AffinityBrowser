package org.scientificprofiling.affinitybrowser.client;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.Element;

public class AffinityBrowserViewport extends Viewport {
	private int index = 0;
	private AffinityDetailTabPanel south;
	
	protected void onRender(Element target, int i) {
		super.onRender(target, i);
		final BorderLayout layout = new BorderLayout();
		setLayout(layout);
		setStyleAttribute("padding", "10px");

		//LAYOUT
		ContentPanel north = new ContentPanel();
		ContentPanel west = new AffinityFacetsPanel();
		ContentPanel center = new ContentPanel();
		ContentPanel east = new ContentPanel();
		south = new AffinityDetailTabPanel();
		 
		//NORTH
		Text label3 = new Text("Brows Affinities");
		label3.addStyleName("pad-text");
		label3.setStyleAttribute("backgroundColor", "white");
		label3.setBorders(true);
		north.add(label3);

		//CENTER
	     center.setHeading("Results");  
	     center.setLayout(new RowLayout(Orientation.VERTICAL));  
	   
	     Text label1 = new Text("Test Label 1");  
	     label1.addStyleName("pad-text");  
	     label1.setStyleAttribute("backgroundColor", "white");  
	     label1.setBorders(true);  
	   
	     Text label2 = new Text("Test Label 2");  
	     label2.addStyleName("pad-text");  
	     label2.setStyleAttribute("backgroundColor", "white");  
	     label2.setBorders(true); 
	     
	     //MapViewContainer mapview = new MapViewContainer();
	   
	     center.add(label1, new RowData(1, -1, new Margins(4)));  
	     center.add(label2, new RowData(1, 1, new Margins(0, 4, 0, 4)));  

		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(5);

		Button add = new Button("Add Tab");
		add.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				addTab();
				south.setSelection(south.getItem(index - 1));
			}
		});
		hp.add(add);

		ToggleButton toggle = new ToggleButton("Enable Tab Context Menu");
		toggle.addListener(Events.Toggle, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent be) {
				south.setCloseContextMenu(((ToggleButton) be.getButton())
						.isPressed());
			}
		});
		toggle.toggle(true);
		hp.add(toggle);
		center.add(hp,new RowData(1, -1, new Margins(4)));

		while (index < 7) {
			addTab();
		}
		
		//SOUTH
		south.setSelection(south.getItem(6));
		
		

		//LAYOUTDATA
		BorderLayoutData northData = new BorderLayoutData(LayoutRegion.NORTH,
				100);
		northData.setCollapsible(true);
		northData.setFloatable(true);
		northData.setHideCollapseTool(true);
		northData.setSplit(true);
		northData.setMargins(new Margins(0, 0, 5, 0));

		BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST, 150);
		westData.setSplit(true);
		westData.setCollapsible(true);
		westData.setMargins(new Margins(0, 5, 0, 0));

		BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
		centerData.setMargins(new Margins(0));

		BorderLayoutData eastData = new BorderLayoutData(LayoutRegion.EAST, 150);
		eastData.setSplit(true);
		eastData.setCollapsible(true);
		eastData.setMargins(new Margins(0, 0, 0, 5));

		BorderLayoutData southData = new BorderLayoutData(LayoutRegion.SOUTH,
				100);
		southData.setSplit(true);
		southData.setCollapsible(true);
		southData.setFloatable(true);
		southData.setMargins(new Margins(5, 0, 0, 0));

		add(north, northData);
		add(west, westData);
		add(center, centerData);
		add(east, eastData);
		add(south, southData);
	}
	
	private void addTab() {
		TabItem item = new TabItem();
		item.setText("New Tab " + ++index);
		item.setClosable(index != 1);
		item.addText("Tab Body " + index);
		item.addStyleName("pad-text");
		south.add(item);
	}


}
