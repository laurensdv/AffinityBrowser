package org.scientificprofiling.affinitybrowser.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.scientificprofiling.affinitybrowser.facets.Person;
import org.scientificprofiling.affinitybrowser.model.Action;
import org.scientificprofiling.affinitybrowser.model.ListModel;
import org.scientificprofiling.affinitybrowser.model.PersonModel;
import org.scientificprofiling.affinitybrowser.model.VType;
import org.scientificprofiling.affinitybrowser.model.VTypeValidator;

import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;  
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.store.ListStore;  
import com.extjs.gxt.ui.client.widget.CheckBoxListView;
import com.extjs.gxt.ui.client.widget.LayoutContainer;  
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Slider;  
import com.extjs.gxt.ui.client.widget.VerticalPanel;  
import com.extjs.gxt.ui.client.widget.button.Button;  
import com.extjs.gxt.ui.client.widget.form.CheckBox;  
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;  
import com.extjs.gxt.ui.client.widget.form.ComboBox;  
import com.extjs.gxt.ui.client.widget.form.DateField;  
import com.extjs.gxt.ui.client.widget.form.DualListField;
import com.extjs.gxt.ui.client.widget.form.DualListField.Mode;
import com.extjs.gxt.ui.client.widget.form.FieldSet;  
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;  
import com.extjs.gxt.ui.client.widget.form.FormPanel;  
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.Radio;  
import com.extjs.gxt.ui.client.widget.form.RadioGroup;  
import com.extjs.gxt.ui.client.widget.form.SliderField;  
import com.extjs.gxt.ui.client.widget.form.SpinnerField;  
import com.extjs.gxt.ui.client.widget.form.TextArea;  
import com.extjs.gxt.ui.client.widget.form.TextField;  
import com.extjs.gxt.ui.client.widget.form.TimeField;  
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;  
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;  
import com.extjs.gxt.ui.client.widget.layout.FormData;  
import com.extjs.gxt.ui.client.widget.layout.FormLayout;  
import com.google.gwt.i18n.client.NumberFormat;  
import com.google.gwt.user.client.Element;  

public class FeedBackForm extends LayoutContainer {
	 private VerticalPanel vp;  
	  
	  private FormData formData;
	  private String user;
	  private ListStore<PersonModel> results;
	  private ListStore<Action> actions;
	  private FormPanel simple;
	  private TextField<String> nonRelCount, relCount, nonRel, rel, action;
	  private ListField<PersonModel>  from, to;
	  private ListField<Action> actionView;
	  private Button b;
	  
	  public FeedBackForm(String user, Set<Person> results, ListStore<Action> actions) {
		  this.user = user;
		  this.results = new ListStore<PersonModel>();
		  for(Person p : results) {
			  this.results.add(new PersonModel(p));
		  }
		  this.actions = actions;
	  }
	  
	  
	  @Override  
	  protected void onRender(Element parent, int index) {  
	    super.onRender(parent, index);  
	    formData = new FormData("-20");  
	    createForm1();    
	  }  
	  
	  private void createForm1() {  
	    simple = new FormPanel();  
	    simple.setFrame(false);
	    simple.setHeaderVisible(false);
	    simple.setWidth("100%");
	    simple.setLabelAlign(LabelAlign.TOP);
	    
	    simple.setAction("/affinitybrowser/FormSubmitServlet");
        simple.setMethod(Method.POST);
        simple.addListener(Events.Submit, new Listener<FormEvent>() {
			public void handleEvent(FormEvent be) {
                MessageBox.info("Form Submission", be.getResultHtml(), new Listener<MessageBoxEvent>(){
                    public void handleEvent(MessageBoxEvent arg0) {
                    	b.setEnabled(false);
                    }
                	});
            	}
			});
        
        Radio radio1 = new Radio();  
        radio1.setBoxLabel("Male");
        radio1.setName("m");
		radio1.setItemId("m");
        radio1.setValueAttribute("m");
        radio1.setValue(true);
      
        Radio radio2 = new Radio();  
        radio2.setBoxLabel("Female");  
        radio2.setName("f");
		radio2.setItemId("f");
        radio2.setValueAttribute("f");
        radio2.setName("f");
        
	    TextField<String> twitterUser = new TextField<String>();  
	    twitterUser.setFieldLabel("Twitter User");
	    twitterUser.setWidth("150px");
	    twitterUser.setVisible(false);
	    twitterUser.setEmptyText(user);
	    twitterUser.setValue(user);
	    twitterUser.setName("user");
	    twitterUser.getFocusSupport().setPreviousId(simple.getButtonBar().getId());  
	    simple.add(twitterUser, formData);  
	  
	    TextField<String> firstName = new TextField<String>();  
	    firstName.setFieldLabel("Name");
	    firstName.setName("name");
	    firstName.setAllowBlank(false);  
	    firstName.getFocusSupport().setPreviousId(simple.getButtonBar().getId());  
	    simple.add(firstName, formData);
	    
	    TextField<String> age = new TextField<String>();  
	    age.setFieldLabel("Age");
	    age.setName("age");
	    age.setAllowBlank(false);  
	    age.getFocusSupport().setPreviousId(simple.getButtonBar().getId());
	    age.setValidator(new VTypeValidator(VType.NUMERIC));
	    simple.add(age, formData);  
	  
        RadioGroup group = new RadioGroup("sex");  
        group.setFieldLabel("Sex");
        group.setName("sex");
        group.add(radio1);  
        group.add(radio2); 
        simple.add(group);
        
	    TextField<String> location = new TextField<String>();  
	    location.setFieldLabel("Location (City, Country)");
	    location.setName("location");
	    location.setAllowBlank(false);  
	    location.getFocusSupport().setPreviousId(simple.getButtonBar().getId());  
	    simple.add(location, formData);
        
	    TextField<String> company = new TextField<String>();  
	    company.setFieldLabel("Affiliation, Institution or Company name");
	    company.setName("company");
	    company.setAllowBlank(false);  
	    company.getFocusSupport().setPreviousId(simple.getButtonBar().getId());  
	    simple.add(company, formData);
        
	    TextField<String> email = new TextField<String>();  
	    email.setFieldLabel("Email (optional)");  
	    email.setAllowBlank(true);
	    email.setName("email");
	    email.setValidator(new VTypeValidator(VType.EMAIL));
	    email.getFocusSupport().setPreviousId(simple.getButtonBar().getId());
	    simple.add(email, formData);
	  
//	    DateField date = new DateField();  
//	    date.setFieldLabel("Birthday");
//	    date.setName("date");
//	    simple.add(date, formData);  
	  
//		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
//		final CheckBoxSelectionModel<PersonModel> sm = new CheckBoxSelectionModel<PersonModel>();  
//  	    columns.add(sm.getColumn());
//		columns.add(new ColumnConfig("name", "Name", 150));
//		
//		ColumnModel columnModel = new ColumnModel(columns);
//		
////		final Grid<PersonModel> view = new Grid<PersonModel>(results,columnModel);
////		view.setBorders(true);
////		view.setStripeRows(false);
////		view.setColumnLines(false);
////		view.setFieldLabel("");
		
		final DualListField<PersonModel> view = new DualListField<PersonModel>();
		view.setMode(Mode.INSERT);
		from = view.getFromList();
	    ListStore<PersonModel> target = new ListStore<PersonModel>();  
	    from.setStore(results);
	    from.setDisplayField("name");
	    from.setValueField("name");
	    from.setName("nonRelevantUsers");
	    to = view.getToList();
	    to.setStore(target);
	    to.setDisplayField("name");
	    to.setName("relevantUsers");
	    to.setValueField("name");
	    view.setFieldLabel("Select all (both known and new) persons relevant or of interest to you");
	    view.setName("relevantUsers");
	    
	    simple.add(view, formData);
	    
	    actionView = new ListField<Action>();
	    actionView.setStore(actions);
	    actionView.setDisplayField("action");
	    actionView.setFieldLabel("Recorded Actions");
	    actionView.setName("actions");
	    actionView.setValueField("action");
	    actionView.setVisible(false);
	    simple.add(actionView, formData);
	    
	    nonRelCount = new TextField<String>();
	    nonRelCount.setVisible(false);
	    nonRelCount.setName("nonRelevantUsersCount");

	    relCount = new TextField<String>();
	    relCount.setVisible(false);
	    relCount.setName("relevantUsersCount");
	    
	    simple.add(relCount);
	    simple.add(nonRelCount);
	    
	    LikertScaleGroup sf = new LikertScaleGroup("q0");  
	    sf.setFieldLabel("The application of the concept affinity is a great asset in this context");
	    sf.setName("q0");
	    simple.add(sf, formData);  
	    
	    sf = new LikertScaleGroup("q1"); 
	    sf.setFieldLabel("The system shows me through its different views clearly the affinities between people");
	    sf.setName("q1");
	    simple.add(sf, formData);  
	    
	    sf = new LikertScaleGroup("q2"); 
	    sf.setFieldLabel("I understand why the persons are plotted on an affinity plot and displayed on a map");
	    sf.setName("q2");
	    simple.add(sf, formData);
	    
	    sf = new LikertScaleGroup("q3");  
	    sf.setFieldLabel("Deactivating an affinity filter works within reasonable time");
	    sf.setName("q3");
	    simple.add(sf, formData);
	    
	    sf = new LikertScaleGroup("q4");    
	    sf.setFieldLabel("Activating an affinity filter works with a reasonable time");
	    sf.setName("q4");
	    simple.add(sf, formData);
	    
	    sf = new LikertScaleGroup("q5");    
	    sf.setFieldLabel("The system does never do anything unexpected or reacts in a strange way");
	    sf.setName("q5");
	    simple.add(sf, formData);
	    
	    sf = new LikertScaleGroup("q6");   
	    sf.setFieldLabel("It is easy to understand the convention between the views used in the system");
	    sf.setName("q6");
	    simple.add(sf, formData);
	    
	    sf = new LikertScaleGroup("q7"); 
	    sf.setFieldLabel("The information displayed such that it is not too much to confuse or distract me");
	    sf.setName("q7");
	    simple.add(sf, formData);
	    
	    sf = new LikertScaleGroup("q8");     
	    sf.setFieldLabel("The additional information about persons presented is relevant to what I want to find");
	    sf.setName("q8");
	    simple.add(sf, formData);
	    
	    sf = new LikertScaleGroup("q9");   
	    sf.setFieldLabel("The details about a person correspond with what they talk about and do in practice");
	    sf.setName("q9");
	    simple.add(sf, formData);
	    
	    sf = new LikertScaleGroup("q10");   
	    sf.setFieldLabel("There are enough new relevant persons whom I consider contacting.");
	    sf.setName("q10");
	    simple.add(sf, formData);
	    
	    sf = new LikertScaleGroup("q11");    
	    sf.setFieldLabel("The fact that the results displayed can change every day is clear");
	    sf.setName("q11");
	    simple.add(sf, formData);
	    
	    sf = new LikertScaleGroup("q12"); 
	    sf.setFieldLabel("The system makes data from Twitter more useful to find relevant researchers");
	    sf.setName("q12");
	    simple.add(sf, formData);
	    
	    BoundedTextArea remarks = new BoundedTextArea();  
	    remarks.setPreventScrollbars(true);  
	    remarks.setFieldLabel("Positive and negative remarks about the user interface");
	    remarks.setMaxLength(255);
	    remarks.setName("remarks");
	    remarks.setPreventScrollbars(false);
	    simple.add(remarks, formData);
	    
	    BoundedTextArea comments = new BoundedTextArea();  
	    comments.setPreventScrollbars(true);  
	    comments.setFieldLabel("General comments");
	    comments.setMaxLength(255);
	    comments.setName("comments");
	    comments.setPreventScrollbars(false);
	    simple.add(comments, formData);  
	  
	    b = new Button("Submit");  
	    simple.addButton(b);  
	    //simple.addButton(new Button("Cancel"));  
	  
	    simple.setButtonAlign(HorizontalAlignment.CENTER);
	    
	    FormButtonBinding binding = new FormButtonBinding(simple);  
	    binding.addButton(b);
	    b.addSelectionListener(new SelectionListener<ButtonEvent>(){
			@Override
			public void componentSelected(ButtonEvent ce) {
				from.getListView().getSelectionModel().selectAll();
				to.getListView().getSelectionModel().selectAll();
				actionView.getListView().getSelectionModel().selectAll();
			    relCount.setEmptyText(""+to.getListView().getItemCount());
			    nonRelCount.setEmptyText(""+from.getListView().getItemCount());
				simple.submit();				
			}});
	  
	    add(simple);  
	  }  
}
