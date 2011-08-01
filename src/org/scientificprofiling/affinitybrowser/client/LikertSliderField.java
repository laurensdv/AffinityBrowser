package org.scientificprofiling.affinitybrowser.client;

import java.util.HashMap;

import org.scientificprofiling.affinitybrowser.facets.Person;
import org.scientificprofiling.affinitybrowser.model.PersonModel;
import org.scientificprofiling.affinitybrowser.visualization.ProfileButtonListener;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SliderEvent;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Slider;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.SliderField;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Image;

public class LikertSliderField extends SliderField {
	private Slider slider;
	
	public LikertSliderField(Slider sliderArgument) {
		super(sliderArgument);
		this.slider = sliderArgument;
	    slider.setMinValue(1);
	    slider.setMaxValue(5);
	    slider.setIncrement(1);
	    slider.setValue(3);
	    slider.setMessage("Neither agree or disagree");
	    slider.setWidth("150px");
		slider.addListener(Events.Change, new Listener<SliderEvent>() {
			public void handleEvent(SliderEvent se) {
				switch(se.getNewValue()) {
				case 1: ((Slider)se.getComponent()).setMessage("Strongly disagree");
					break;
				case 2: ((Slider)se.getComponent()).setMessage("Disagree");
					break;
				case 3: ((Slider)se.getComponent()).setMessage("Neither agree or disagree");
					break;
				case 4: ((Slider)se.getComponent()).setMessage("Agree");
					break;
				case 5: ((Slider)se.getComponent()).setMessage("Strongly agree");
					break;
				}
			}
		});
	}
	
	protected void onRender(Element parent, int index) {  
		super.onRender(parent,index);
	}
}
