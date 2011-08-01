package org.scientificprofiling.affinitybrowser.client;

import gdurelle.tagcloud.client.tags.TagCloud;
import gdurelle.tagcloud.client.tags.WordTag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.scientificprofiling.affinitybrowser.facets.ConferenceLoader;
import org.scientificprofiling.affinitybrowser.facets.FacetTaxonomy;
import org.scientificprofiling.affinitybrowser.facets.Person;
import org.scientificprofiling.affinitybrowser.facets.PersonCachedRemoteLoader;
import org.scientificprofiling.affinitybrowser.facets.PersonLoader;
import org.scientificprofiling.affinitybrowser.facets.PersonRemoteLoader;
import org.scientificprofiling.affinitybrowser.facets.PersonSpace;
import org.scientificprofiling.affinitybrowser.facets.UserScreensLoader;
import org.scientificprofiling.affinitybrowser.model.Action;
import org.scientificprofiling.affinitybrowser.model.ListModel;
import org.scientificprofiling.affinitybrowser.model.PersonModel;
import org.scientificprofiling.affinitybrowser.model.ScreenNameModel;
import org.scientificprofiling.affinitybrowser.visualization.AffinityPlotPanel;
import org.scientificprofiling.affinitybrowser.visualization.ProfileButtonListener;

import com.extjs.gxt.charts.client.model.charts.dots.BaseDot;
import com.extjs.gxt.charts.client.model.charts.dots.SolidDot;
import com.extjs.gxt.themes.client.Slate;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.TabPanelEvent;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Params;
import com.extjs.gxt.ui.client.util.ThemeManager;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.filters.GridFilters;
import com.extjs.gxt.ui.client.widget.grid.filters.NumericFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AffinityBrowser implements EntryPoint {
	final String API_KEY = "ABQIAAAAlkQgayYedgJkJyASrxCM5BTDLrkUcBnRupLV5NbtLCxgc_RluxQTsGPalVfRsLgp_-uSe0Vz68LPog";
	final String USER_NAME = "mebner";

	private int index = 0;
	private Viewport viewport;
	private AffinityFacetsPanel affinities;
	private AffinityPlotPanel ap;
	private Grid<PersonModel> grid;
	private TabPanel southPanel;
	private ContentPanel south;
	private ContentPanel mapcontainer;
	private ColumnModel cm;
	private ArrayList<ListModel> indices;
	private MapWidget mapWidget;
	private Marker marker;
	private LayoutContainer gridHolder;
	private ConferenceLoader cl;
	protected PersonSpace ps;
	protected PersonCachedRemoteLoader pl;
	private MessageBox box;
	private ListStore<ScreenNameModel> screens;
	private ListStore<Action> actions;
	private Button load, evaluate;
	private String currentUser = USER_NAME;
	private Window window;
	

	public void onModuleLoad() {
		ThemeManager.register(Slate.SLATE);
		GXT.setDefaultTheme(Slate.SLATE, true);
		//
		
		actions = new ListStore<Action>();
		//MessageBox.info("Message",
		//		"Here comes the Scientific Affinity Browser", null);
		viewport = new Viewport();
		final BorderLayout layout = new BorderLayout();
		ps = new PersonSpace();
		cl = new ConferenceLoader();
		pl = new PersonCachedRemoteLoader("mstrohm",this);
		viewport.setLayout(layout);
		viewport.setStyleAttribute("padding", "10px");

		affinities = new AffinityFacetsPanel();
		
		// MAP
		
		mapcontainer = new ContentPanel();
		mapcontainer.setHeaderVisible(false);
		mapcontainer.setHeading("Map");
		mapcontainer.setLayout(new FitLayout());
		
		Maps.loadMapsApi(API_KEY, "2", false, new Runnable() {
		      public void run() {
		        buildUi();
		      }
		    });

		// LAYOUT
		ContentPanel north = new ContentPanel();
		north.setHeaderVisible(false);
		ContentPanel west = affinities;
		ContentPanel center = new ContentPanel();
		ContentPanel east = new ContentPanel();
		south = new ContentPanel();
		southPanel = new AffinityDetailTabPanel();

		// WEST
		indices = new ArrayList<ListModel>();

		// CENTER
		center.setHeading("Results");
		center.setLayout(new RowLayout(Orientation.VERTICAL));

		Text label2 = new Text("Results Map");
		label2.addStyleName("pad-text");
		label2.setStyleAttribute("backgroundColor", "white");
		label2.setBorders(true);

		Text label1 = new Text("Result Grid");
		label1.addStyleName("pad-text");
		label1.setStyleAttribute("backgroundColor", "white");
		label1.setBorders(true);

		// center.add(label1, new RowData(1, -1, new Margins(4)));
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("name", "Name", 150));
		// columns.add(new ColumnConfig("uri", "Uri", 150));
		columns.add(new ColumnConfig("location", "Location", 150));
		columns.add(new ColumnConfig("mentions", "Mentions", 80));
		columns.add(new ColumnConfig("tags", "Tags", 80));
		columns.add(new ColumnConfig("conferences", "Conferences", 80));
		columns.add(new ColumnConfig("description", "Description", 250));

		cm = new ColumnModel(columns);
		
		gridHolder = new LayoutContainer(new FitLayout());

		initGrid();
		
		center.add(gridHolder, new RowData(0, 0.5, new Margins(4)));
		
		ContentPanel mapHp = new ContentPanel();
		mapHp.setLayout(new FillLayout(Orientation.HORIZONTAL));
		mapHp.setHeaderVisible(false);
		mapHp.setFrame(false);
		mapHp.setBorders(false);

		ContentPanel mapCp = new ContentPanel();
		mapCp.setLayout(new FitLayout());
		mapCp.setFrame(false);
		mapCp.setBorders(false);
		mapCp.setHeaderVisible(false);
		// mapCp.setSize(400, 400);

		ap = new AffinityPlotPanel(this);
		ap.setHeaderVisible(false);
		ap.setFrame(false);
		ap.setBorders(false);
		ap.setLayout(new FitLayout());
		// ap.setSize(500,400);

		mapCp.add(mapcontainer);
		mapHp.add(ap);
		mapHp.add(mapCp);
		center.add(mapHp, new RowData(1, 0.5, new Margins(4)));

		// SOUTH
		// south.setSelection(south.getItem(6));
		// south.setCollapsed(true);
		south.setHeading("Person Details");
		south.setLayout(new FitLayout());
		south.add(southPanel);
		addTab();

		// LAYOUTDATA
		BorderLayoutData northData = new BorderLayoutData(LayoutRegion.NORTH,
				60);
		northData.setMaxSize(100);
		northData.setFloatable(true);
		northData.setHideCollapseTool(true);
		northData.setSplit(false);
		northData.setMargins(new Margins(0, 0, 5, 0));

		BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST, 200);
		westData.setMaxSize(300);
		westData.setMinSize(150);
		westData.setSplit(true);
		westData.setCollapsible(true);
		westData.setMargins(new Margins(0, 5, 0, 0));

		BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
		centerData.setMargins(new Margins(0));

		BorderLayoutData eastData = new BorderLayoutData(LayoutRegion.EAST, 125);
		eastData.setSplit(true);
		eastData.setCollapsible(true);
		eastData.setMargins(new Margins(0, 0, 0, 5));

		BorderLayoutData southData = new BorderLayoutData(LayoutRegion.SOUTH,
				275);
		southData.setMinSize(200);
		southData.setSplit(true);
		southData.setCollapsible(true);
		southData.setFloatable(true);
		southData.setMargins(new Margins(5, 0, 0, 0));
		north.setLayout(new FitLayout());

		viewport.add(north, northData);
		Text label3 = new Text("Browse Affinities");
		label3.addStyleName("pad-text");
		label3.setStyleAttribute("backgroundColor", "white");
		// hp.add(toggle);
		// north.add(label3);
		
		Image logo = new Image("tempLogo.png");
		logo.setWidth("250px");
		logo.setHeight("50px");
		HorizontalPanel hp = new HorizontalPanel();
		VerticalPanel vp = new VerticalPanel();
		HorizontalPanel hp2 = new HorizontalPanel();
		HorizontalPanel hp1 = new HorizontalPanel();
		hp.setHeight("60px");
		hp.setTableWidth("100%");
		hp.setTableHeight("60px");
		hp1.setTableHeight("100%");
		vp.setTableHeight("100%");
		hp2.setTableWidth("100%");
		hp2.setTableHeight("100%");
		
		north.add(hp);

		//hp.setTableWidth("100%");
		// It only applies to widgets added after this property is set.

		hp1.setHorizontalAlign(HorizontalAlignment.LEFT);
		hp1.setVerticalAlign(VerticalAlignment.BOTTOM);
		hp1.setSpacing(2);
		
		vp.add(new Html("<div class=text style='padding:2px; font-size:smaller; color: grey;'>&copy 2010-2011 by <a href=\"http://www.semanticprofiling.net\">Laurens De Vocht</a></div>"));
		vp.add(hp1);
		
		hp2.setHorizontalAlign(HorizontalAlignment.RIGHT);
		hp2.setVerticalAlign(VerticalAlignment.TOP);
		hp2.add(logo);
		
		hp.add(vp);
		hp.add(hp2);
		
		hp.setSpacing(4);

		//People Combobox
		screens = new ListStore<ScreenNameModel>();  
		   
		ComboBox<ScreenNameModel> combo = new ComboBox<ScreenNameModel>();  
		combo.setEmptyText("Select a Twitter User...");  
		combo.setDisplayField("name");  
		combo.setWidth(175);  
		combo.setStore(screens);  
		combo.setTypeAhead(true);
		combo.addSelectionChangedListener(new SelectionChangedListener<ScreenNameModel>() {
		      public void selectionChanged(SelectionChangedEvent<ScreenNameModel> se) {
		        ScreenNameModel screenModel = se.getSelectedItem();
		        pl.setUserScreenName(screenModel.getScreen());
		        actions.add(new Action(currentUser,"S-"+pl.getUserScreenName()));
		      }
		    });
		new UserScreensLoader().setUserScreens(screens);
		//combo.setTriggerAction(TriggerAction.ALL);  
		hp1.add(combo);
		
		// add buttons
		load = new Button("Load Persons");
		load.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				pl.load();
				currentUser = pl.getUserScreenName();
				initGrid();
				grid.reconfigure(pl.getStore(), cm);
				//load.setEnabled(false);
				actions.add(new Action(currentUser,"C-LP"));
			}
		});

		hp1.add(load);
		
		hp1.add(new Button("Register", new SelectionListener<ButtonEvent>() {  
		      public void componentSelected(ButtonEvent ce) {
		    	  actions.add(new Action(currentUser,"C-R"));  
		        final MessageBox box = MessageBox.prompt("Register New User", "Please enter your Twitter username:");  
		        box.addCallback(new Listener<MessageBoxEvent>() {  
		          public void handleEvent(MessageBoxEvent be) {
		        	String input = be.getValue();
		        	String result;
		            Info.display("MessageBox", "You entered '{0}'", new Params(input));  
		            String url = "http://api.semanticprofiling.net/register.php?user="+input;
		            actions.add(new Action(currentUser,"E-RNU-" +input));
		            MyServiceAsync myService = (MyServiceAsync) GWT.create(MyService.class);
		            AsyncCallback callback = new AsyncCallback() {

		    		    public void onFailure(Throwable caught) {
		    		    	System.out.println(caught.toString());
		    		    }

		    			public void onSuccess(Object result) {
		    				MessageBox.info("Registration Result", result.toString(), new Listener<MessageBoxEvent>(){
		                        public void handleEvent(MessageBoxEvent arg0) {}
		                	});
		    				actions.add(new Action(currentUser,"R-" +result.toString()));
		    			}
		    		  };
		    		  
		    		  myService.getResponse(url,new String(),callback);
		          
		          }  
		        });  
		      }  
		    }));  

		window = new Window();  
	    window.setSize(500, 500);  
	    window.setPlain(true);  
	    window.setModal(true);  
	    window.setBlinkModal(true);  
	    window.setHeading("Evaluation");  
	    window.setLayout(new FitLayout());  
	    window.setScrollMode(Scroll.AUTOY);
	    	      
		evaluate = new Button("Evaluate");
		evaluate.setEnabled(false);
		evaluate.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Info.display("Evaluate", pl.getUserScreenName());
				actions.add(new Action(currentUser,"C-E"));
				window.show();
			}
		});
		hp1.add(evaluate);

		ToggleButton toggle = new ToggleButton("Enable Tab Context Menu");
		toggle.addListener(Events.Toggle, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent be) {
				southPanel.setCloseContextMenu(((ToggleButton) be.getButton())
						.isPressed());
			}
		});
		toggle.toggle(true);
		viewport.add(west, westData);
		viewport.add(center, centerData);
		// viewport.add(east, eastData);
		viewport.add(south, southData);

		RootPanel.get().add(viewport);
		// south.collapse();
	}
	public void windowSetup() {
//		window.addWindowListener(new WindowListener() {  
//	      @Override  
//	      public void windowHide(WindowEvent we) {  
//	        Button open = we.getWindow().getData("open");  
//	        open.focus();
//	      }  
//	    });
	    window.add(new FeedBackForm(pl.getUserScreenName(),pl.getPersons(),actions));
	    window.addButton(new Button("Close", new SelectionListener<ButtonEvent>() {  
	        @Override  
	        public void componentSelected(ButtonEvent ce) {  
	          window.hide();
	        }  
	      }));  
	      window.setFocusWidget(window.getButtonBar().getItem(0));
	      evaluate.setEnabled(true);
	}
	private void initGrid() {
		grid = new Grid<PersonModel>(pl.getStore(), cm);
		affinities.removeAll();
		gridHolder.removeAll();
		gridHolder.add(grid);
		grid.setBorders(true);
		grid.setAutoExpandColumn("description");
		grid.setStripeRows(true);
		
		grid.addListener(Events.CellClick, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				GridEvent ge = (GridEvent) be;
				TabItem item = new TabItem();
				PersonModel p = (PersonModel) ge.getModel();
				item.setText(p.getName());
				actions.add(new Action(currentUser,"C-RG-"+p.getName()));
				item.addStyleName("pad-text");
				item.setClosable(true);
				item.setLayout(new ColumnLayout());
				item.addListener(Events.Close, new Listener<TabPanelEvent>() {
					public void handleEvent(TabPanelEvent arg0) {
						TabItem item = arg0.getItem();
						actions.add(new Action(currentUser,"C-CT-"+item.getText()));
					}

				});
				String uri = p.getUri();
				Person person = ps.getPersonsMap().get(uri);
				ContentPanel persondetailPanel = new ContentPanel();
				ContentPanel mentionsPanel = new ContentPanel();
				ContentPanel tagsPanel = new ContentPanel();
				ContentPanel conferencesPanel = new ContentPanel();

				persondetailPanel.setHeading("Details");
				persondetailPanel
						.setLayout(new RowLayout(Orientation.VERTICAL));
				Button add = new Button("On Twitter");
				add.addSelectionListener(new ProfileButtonListener(person.getScreen()));
				try {
					persondetailPanel.add(new Image(person.getImgurl()),
							new RowData(-1, -1, new Margins(4)));
					persondetailPanel.add(new Text(person.getName()),
							new RowData(-1, -1, new Margins(4)));
					persondetailPanel.add(new Text(person.getLocation()),
							new RowData(-1, -1, new Margins(4)));
					persondetailPanel.add(new Text(person.getDescription()),
							new RowData(-1, -1, new Margins(4)));
					persondetailPanel.add(add,
							new RowData(-1, -1, new Margins(4)));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				mentionsPanel.setHeading("Mentions");
				tagsPanel.setHeading("Tags");
				conferencesPanel.setHeading("Conferences");

				if (person != null) {
					mentionsPanel
							.add(generateTagCloud("http://www.twitter.com/",(HashMap<String, Integer>) person
									.getMentions()));
					tagsPanel
							.add(generateTagCloud("http://www.tagdef.com/",(HashMap<String, Integer>) person
									.getTags()));
					conferencesPanel
							.add(generateTagCloud("http://www.wikicfp.com",(HashMap<String, Integer>) person
									.getConferences()));
				} else {
					Info.display("Adding person", "Not found");
				}

				item.add(persondetailPanel, new ColumnData(.34));
				item.add(mentionsPanel, new ColumnData(.22));
				item.add(tagsPanel, new ColumnData(.22));
				item.add(conferencesPanel, new ColumnData(.22));

				southPanel.add(item);
				int count = southPanel.getItemCount();
				southPanel.setSelection(southPanel.getItem(count - 1));
			}
		});
		grid.addListener(Events.CellClick, new Listener<BaseEvent>() {
			
			public void handleEvent(BaseEvent be) {
				GridEvent ge = (GridEvent) be;
				PersonModel p = (PersonModel) ge.getModel();
				for(BaseDot d : ap.getDots()) {
	            	if(d instanceof SolidDot && d.get("uri")==p.getUri()) {
	            		((SolidDot)d).setColour("#FF7F50");
	            	} else if (d instanceof SolidDot)
	            		((SolidDot)d).setColour("0000FF");
	            }
				LatLng l = p.getPoint();
				if (l.getLatitude() != 0 && l.getLongitude() != 0) {
			    	MarkerOptions opts = MarkerOptions.newInstance();
			    	//opts.setIcon(icon);
			    	opts.setClickable(true);
			    	opts.setDraggable(false);
			    	opts.setTitle(p.getName());
			    	opts.setIcon(Icon.newInstance("http://chart.apis.google.com/chart?cht=mm&chs=32x32&chco=FFFFFF,FF7F50,000000&ext=.png"));
			    	if(marker != null) mapWidget.removeOverlay(marker);
			    	marker = new Marker(l, opts);
			    	marker.addMarkerClickHandler(new MarkerClickHandler() {

						public void onClick(MarkerClickEvent event) {
							Marker m = event.getSender();
							loadTab(ps.findPerson(m.getTitle()));
							actions.add(new Action(currentUser,"C-MM-"+m.getTitle()));
						}
			    		
			    	});
			    	mapWidget.addOverlay(marker);
			    	mapWidget.setCenter(l);
			    	mapcontainer.layout();
				} else {
					mapWidget.removeOverlay(marker);
					mapcontainer.layout();
				}
	            ap.update();
	            mapcontainer.layout();
				
			}
	    });
		gridHolder.layout();
	}
	public PersonSpace getPersonSpace() {
		return ps;
	}
	public void loadTab(Person person) {
		TabItem item = new TabItem();
		item.addListener(Events.Close, new Listener<TabPanelEvent>() {
			public void handleEvent(TabPanelEvent arg0) {
				TabItem item = arg0.getItem();
				actions.add(new Action(currentUser,"C-CT-"+item.getText()));
			}

		});

		item.setText(person.getName());
		item.addStyleName("pad-text");
		item.setClosable(true);
		item.setLayout(new ColumnLayout());
		
		ContentPanel persondetailPanel = new ContentPanel();
		ContentPanel mentionsPanel = new ContentPanel();
		ContentPanel tagsPanel = new ContentPanel();
		ContentPanel conferencesPanel = new ContentPanel();

		persondetailPanel.setHeading("Details");
		persondetailPanel
				.setLayout(new RowLayout(Orientation.VERTICAL));

		Button add = new Button("On Twitter");
		add.addSelectionListener(new ProfileButtonListener(person.getScreen()));
		try {
			persondetailPanel.add(new Image(person.getImgurl()),
					new RowData(-1, -1, new Margins(4)));
			persondetailPanel.add(new Text(person.getName()),
					new RowData(-1, -1, new Margins(4)));
			persondetailPanel.add(new Text(person.getLocation()),
					new RowData(-1, -1, new Margins(4)));
			persondetailPanel.add(new Text(person.getDescription()),
					new RowData(-1, -1, new Margins(4)));
			persondetailPanel.add(add,
					new RowData(-1, -1, new Margins(4)));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		mentionsPanel.setHeading("Mentions");
		tagsPanel.setHeading("Tags");
		conferencesPanel.setHeading("Conferences");

		if (person != null) {
			mentionsPanel
					.add(generateTagCloud("http://www.twitter.com/", (HashMap<String, Integer>) person
							.getMentions()));
			tagsPanel
					.add(generateTagCloud("http://www.tagdef.com/", (HashMap<String, Integer>) person
							.getTags()));
			conferencesPanel
					.add(generateTagCloud("http://www.wikicfp.com", (HashMap<String, Integer>) person
							.getConferences()));
		} else {
			Info.display("Adding person", "Not found");
		}

		item.add(persondetailPanel, new ColumnData(.34));
		item.add(mentionsPanel, new ColumnData(.22));
		item.add(tagsPanel, new ColumnData(.22));
		item.add(conferencesPanel, new ColumnData(.22));

		southPanel.add(item);
		int count = southPanel.getItemCount();
		southPanel.setSelection(southPanel.getItem(count - 1));
	}
	public String getCurrentUser() {
		return pl.getUserScreenName();
	}
	public ListStore<Action> getActions() {
		return actions;
	}
	public void updateMap(Collection<Person> persons) {
		for (Person p : persons) {
			LatLng l = p.getPoint();
			if (l.getLatitude() != 0 && l.getLongitude() != 0) {
		    	MarkerOptions opts = MarkerOptions.newInstance();
		    	//opts.setIcon(icon);
		    	opts.setClickable(true);
		    	opts.setDraggable(false);
		    	opts.setTitle(p.getName());
		    	opts.setIcon(Icon.newInstance("http://chart.apis.google.com/chart?cht=mm&chs=32x32&chco=FFFFFF,0000FF,000000&ext=.png"));
		    	Marker marker = new Marker(l, opts);
		    	marker.addMarkerClickHandler(new MarkerClickHandler() {

					public void onClick(MarkerClickEvent event) {
						Marker m = event.getSender();
						loadTab(ps.findPerson(m.getTitle()));
						actions.add(new Action(currentUser,"C-MM-"+m.getTitle()));
					}
		    		
		    	});
		    	mapWidget.addOverlay(marker);
			}

		}
		mapcontainer.layout();
	}
    
	public void updateFacets(Collection<Person> collection) {
		ps.setPersons(collection);
		ap.addDots(ps.getPersonModels());
		affinities.removeAll();

		for (String s : ps.getPersonFacetMap().getFacets().keySet()) {
			if (!s.contentEquals("latitude") && !s.contentEquals("longitude")) {
				ContentPanel cp = new ContentPanel();
				cp.setAnimCollapse(false);
				cp.setBodyStyleName("pad-text");
				cp.setLayout(new FitLayout());
				cp.setHeading(s);
				cp.setScrollMode(Scroll.AUTOY);

				FacetTaxonomy f = (FacetTaxonomy) ps.getPersonFacetMap()
						.getFacets().get(s);

				ListStore<ListModel> store = new ListStore<ListModel>();

				for (String string : f.getWeightedTaxonomy().keySet()) {
					int weight = f.getWeightedTaxonomy().get(string);
					if (weight > 1)
						store.add(new ListModel(string, weight));
				}

				List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
				
				final CheckBoxSelectionModel<ListModel> sm = new CheckBoxSelectionModel<ListModel>();  
			    // selection model supports the SIMPLE selection mode  
			    // sm.setSelectionMode(SelectionMode.SIMPLE);  
			  
			    configs.add(sm.getColumn());

				ColumnConfig columnName = new ColumnConfig();
				columnName.setId("name");
				columnName.setWidth(90);
				columnName.setHeader("Name");
				configs.add(columnName);

				ColumnConfig columnFreq = new ColumnConfig();
				columnFreq.setId("frequency");
				columnFreq.setWidth(50);
				columnFreq.setHeader("#");
				configs.add(columnFreq);

				ColumnModel columnModel = new ColumnModel(configs);

				GridFilters filters = new GridFilters();
				filters.setLocal(true);

				NumericFilter filter = new NumericFilter("frequency");
				StringFilter nameFilter = new StringFilter("name");

				filters.addFilter(filter);
				filters.addFilter(nameFilter);

				// final ListView<ListModel> view = new ListView<ListModel>() {
				// @Override
				// protected ListModel prepareData(ListModel model) {
				// String s = model.get("name");
				// model.set("shortName", Format.ellipse(s, 15));
				// return model;
				// }
				// };

				final Grid<ListModel> view = new Grid<ListModel>(store,
						columnModel);
				// view.getView().setForceFit(true);
				// view.setStyleAttribute("borderTop", "none");
				// view.setAutoExpandColumn("name");
				view.setBorders(true);
				view.setStripeRows(false);
				view.setColumnLines(false);
				view.addPlugin(filters);

				// view.setStore(store);
				// view.setTemplate(getTemplate());
				// view.setDisplayProperty("name");
				// view.setItemSelector("div.thumb-wrap");
				view.addListener(Events.CheckChange, new Listener<BaseEvent>() {
					public void handleEvent(BaseEvent be) {
						GridEvent<ListModel> ge = (GridEvent<ListModel>) be;
						Info.display("Checked",((ListModel) ge
								.getModel()).getName());
						}
					
				});
				view.addListener(Events.CellClick,
						new Listener<BaseEvent>() {
							public void handleEvent(BaseEvent be) {

								GridEvent<ListModel> ge = (GridEvent<ListModel>) be;
								if (!indices.contains((ListModel) ge.getModel())) {
									String facetItem = ((ListModel) ge
											.getModel()).getName();
									String facet = ((ContentPanel) view
											.getParent()).getHeading();
									final MessageBox box = MessageBox.wait(
											"Progress",
											"Activating Facet: "
													+ facetItem
													+ ", please wait...",
											"Filtering...");
									if (ps.activateFacet(facet, facetItem)) {
										indices.add((ListModel) ge.getModel());
										// ps.setPersons(filteredPersons);
										Scheduler.get().scheduleDeferred(new Command() {
											public void execute() {
												updateFacets(ps.getPersons());
												grid.reconfigure(ps.getPersonStore(),
														cm);
												updateMap(ps.getPersons());
												
											}
											});
										actions.add(new Action(currentUser,"A-"+facet+"-"+facetItem));
										box.close();
										Info.display(
												"Activated Item:",
												((ListModel) ge.getModel())
														.getName()
														+ "in "
														+ ((ContentPanel) view
																.getParent())
																.getHeading());
									}
								} else {
									String facetItem = ((ListModel) ge
											.getModel()).getName();
									String facet = ((ContentPanel) view
											.getParent()).getHeading();
									final MessageBox box = MessageBox.wait(
													"Progress", "Deactivating Facet: "
															//+ facetItem
															+ ", please wait...",
													"Filtering...");
	
									ps.deactivateFacet(facet, facetItem);
									indices.remove(indices
											.indexOf((ListModel) ge.getModel()));
									// ps.setPersons(filteredPersons);
									Scheduler.get().scheduleDeferred(new Command() {
										public void execute() {
											updateFacets(ps.getPersons());
											grid.reconfigure(ps.getPersonStore(),
													cm);
											updateMap(ps.getPersons());
										}
										});
									actions.add(new Action(currentUser,"D-"+facet+"-"+facetItem));
									box.close();
									Info.display(
											"Deactivated Item:",
											((ListModel) ge.getModel())
													.getName()
													+ "in "
													+ ((ContentPanel) view
															.getParent())
															.getHeading());
								}

							}

						});

				// view.getSelectionModel().addListener(Events.CheckChange,
				// new Listener<CheckChangedEvent<ListModel>>() {
				//
				// public void handleEvent(CheckChangedEvent<ListModel> be) {
				// Info.display("Checked Items", "There are " +
				// view.getChecked().size()
				// + " items checked!");
				// }
				// });

				columnName.setRenderer(new GridCellRenderer<ListModel>() {
					public Object render(
							ListModel model,
							String property,
							com.extjs.gxt.ui.client.widget.grid.ColumnData config,
							int rowIndex, int colIndex,
							ListStore<ListModel> store, Grid<ListModel> grid) {
						if (indices.contains((ListModel) model))
							config.style = "background-color: aquamarine;";
						else
							config.style = "background-color: none";
						return model.get(property);
					}
				});

				columnFreq.setRenderer(new GridCellRenderer<ListModel>() {
					public Object render(
							ListModel model,
							String property,
							com.extjs.gxt.ui.client.widget.grid.ColumnData config,
							int rowIndex, int colIndex,
							ListStore<ListModel> store, Grid<ListModel> grid) {
						if (indices.contains((ListModel) model))
							config.style = "background-color: aquamarine;";
						else
							config.style = "background-color: none";
						return model.get(property);
					}
				});

				cp.add(view);
				affinities.add(cp);
			}
		}
		affinities.layout();
	}

	public void initPersons(Set<Person> persons) {
		ps.initPersons(persons);
	}

	private native String getTemplate() /*-{ 
										return ['<tpl for=".">', 
										'<div class="thumb-wrap" id="{name}">', 
										'<span class="x-editable">{shortName}</span></div>', 
										'</tpl>', 
										'<div class="x-clear"></div>'].join(""); 
										
										}-*/;

	private TagCloud generateTagCloud(String baseurl, HashMap<String, Integer> tags) {
		int frequency = 1;
		String base = baseurl;
		TagCloud temp = new TagCloud();
		for (String s : tags.keySet()) {
			frequency = tags.get(s);
			WordTag sTag = new WordTag(s);
			if(baseurl.contentEquals("http://www.wikicfp.com"))
				new ConferenceLoader().setConferenceUrl(s,sTag,temp);
			else sTag.setLink(base + s);
			sTag.setNumberOfOccurences(frequency);
			temp.addWord(sTag);
		}
		return temp;
	}
	
	private void buildUi() {
	    mapWidget = new MapWidget();
	    mapWidget.setSize("100%", "100%");
	    // Add some controls for the zoom level
	    mapWidget.addControl(new SmallMapControl());
	    mapWidget.setZoomLevel(4);
	    mapWidget.setCenter(LatLng.newInstance(50,4));
	    
	    //MarkerOptions opts = MarkerOptions.newInstance();
        //opts.setIcon(icon);
        //opts.setClickable(true);
        //opts.setDraggable(false);
        //opts.setTitle("p.getName()");
        //opts.setIcon(Icon.newInstance("http://chart.apis.google.com/chart?cht=mm&chs=32x32&chco=FFFFFF,FF0000,000000&ext=.png"));
        marker = null;
        //mapWidget.addOverlay(marker);
        //MarkerManager mgr = new MarkerManager.newInstance(); 

	    // Add the map to the HTML host page
	    mapcontainer.add(mapWidget);
	    mapcontainer.layout();
	  }

	private void addTab() {
		TabItem item = new TabItem();
		item.setText(" Getting started");
		item.setClosable(true);
		item.add(new Html("<div class=text style='padding:5px'; font-size:smaller> Select a Twitter user in the top left input box.<br />" +
				" Click on the Load People button.<br />" +
				" Click any person in the Results grid to view more details about someone.<br />" +
				" (De)Activate Affinities by ticking the checkboxes in the Affinity Facets Panel.<br />" +
				" After exploring the results, please click the Evaluate button and fill out the evaluation form. Thank you!<br /><br />" +
				" <emph>Notes</emph> <br />" +
				" 1. If you can't find a Twitter user, click the Register button and enter the Twitter username.<br />" +
				" 2. If you would like to load persons for a Twitter user again, you first need to refresh the browser window. <br /></div>"));
		item.addStyleName("pad-text");
		southPanel.add(item);
	}
}
