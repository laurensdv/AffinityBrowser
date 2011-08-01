package org.scientificprofiling.affinitybrowser.model;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.widget.form.Time;

public class Action extends BaseModel{	
		 public Action(String s, String t) {
			 	long time = new Date().getTime()/1000;
			 	String timeString = ""+time;
			 	timeString = timeString.substring(timeString.length()-3);
			    set("name", s);
			    set("action", "t"+timeString+"-"+t);
			    set("timestamp", time);
			 }
		 public String getName() {
			 return get("name");
		 }

		 public String getAction() {
			 return get("action");
		 }

		 public long getTimesTamp() {
			 return get("timestamp");
		 }
}
