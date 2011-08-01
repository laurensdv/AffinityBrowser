package org.scientificprofiling.affinitybrowser.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.Radio; 

public class LikertScaleGroup extends RadioGroup {
	public LikertScaleGroup(String n) {
		super(n);
		Radio radio1 = new Radio();
		Radio radio2 = new Radio();
		Radio radio3 = new Radio();
		Radio radio4 = new Radio();
		Radio radio5 = new Radio();
		
		radio1.setBoxLabel("Strongly disagree");
		radio2.setBoxLabel("Disagree");
		radio3.setBoxLabel("Neither");
		radio4.setBoxLabel("Agree");
		radio5.setBoxLabel("Strongly agree");
		
		radio1.setName("1");
		radio2.setName("2");
		radio3.setName("3");
		radio4.setName("4");
		radio5.setName("5");
		
		radio1.setItemId("1");
        radio1.setValueAttribute("1");
        
		radio2.setItemId("2");
        radio2.setValueAttribute("2");
        
		radio3.setItemId("3");
        radio3.setValueAttribute("3");
        radio3.setValue(true);
        
		radio4.setItemId("4");
        radio4.setValueAttribute("4");
        
		radio5.setItemId("5");
        radio5.setValueAttribute("5");

		
		add(radio1);
		add(radio2);
		add(radio3);
		add(radio4);
		add(radio5);
		
		setLabelStyle("font-weight: bold");
	}
}
