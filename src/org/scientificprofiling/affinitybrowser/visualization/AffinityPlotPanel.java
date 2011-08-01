package org.scientificprofiling.affinitybrowser.visualization;

import java.util.ArrayList;
import java.util.List;

import org.scientificprofiling.affinitybrowser.client.AffinityBrowser;
import org.scientificprofiling.affinitybrowser.model.Action;
import org.scientificprofiling.affinitybrowser.model.ListModel;
import org.scientificprofiling.affinitybrowser.model.PersonModel;

import com.extjs.gxt.charts.client.model.ChartModel;
import com.extjs.gxt.charts.client.model.Text;
import com.extjs.gxt.charts.client.model.ToolTip;
import com.extjs.gxt.charts.client.model.ToolTip.MouseStyle;
import com.extjs.gxt.charts.client.model.axis.XAxis;
import com.extjs.gxt.charts.client.model.axis.YAxis;
import com.extjs.gxt.charts.client.model.charts.ScatterChart;
import com.extjs.gxt.charts.client.Chart;
import com.extjs.gxt.charts.client.model.charts.dots.BaseDot;
import com.extjs.gxt.charts.client.model.charts.dots.SolidDot;
import com.extjs.gxt.charts.client.model.charts.dots.Star;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.charts.client.event.*;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.google.gwt.user.client.Element;

public class AffinityPlotPanel extends ContentPanel {
	protected List<BaseDot> dots;
	protected Chart chart;
	protected ChartModel cm;
	protected int maxMentions = 2;
	protected int maxConferences = 2;
	protected int maxTags = 2;
	protected AffinityBrowser parentContainer;
	
	public AffinityPlotPanel(AffinityBrowser parent) {
		this.parentContainer = (AffinityBrowser)parent;
	}

	public void onRender(Element parent, int index) {
		super.onRender(parent, index);
		// Create a scatter chart visualization.
		String url = "gxt/chart/open-flash-chart.swf";
		
		dots = new ArrayList<BaseDot>();
		Star me = new Star();
		//me.setXY(0, 0);
		//me.setTooltip("Me " + me.getSize());
		//sdots.add(me);
		chart = new Chart(url);
		// setLayout(new FitLayout());
		// setFrame(true);
		setHeading("Affnity Plot");
		chart.setBorders(true);
		chart.setChartModel(getScatterChartModel());
		
		add(chart);
	};

	public ChartModel getScatterChartModel() {
		// Create a ChartModel with the Chart Title and some style attributes
		cm = new ChartModel("Affinity Plot",
				"font-size: 14px; font-family:      Verdana; text-align: center;");
		XAxis xa = new XAxis();
		// set the maximum, minimum and the step value for the X axis
		xa.setRange(0,100,20);
		cm.setXAxis(xa);
		cm.setXLegend(new Text("Common Tags (%)", "font-size: 12px; font-family: Verdana; text-align: center;"));
		cm.setYLegend(new Text("Common Mentions (%)", "font-size: 12px; font-family: Verdana; text-align: center;"));
		//cm.setBackgroundColour("#FFFFFF");
		YAxis ya = new YAxis();
		// Add the labels to the Y axis
		ya.setRange(0,100,20);
		cm.setYAxis(ya);
		ScatterChart schart = new ScatterChart();
		schart.addChartListener(new ChartListener() {
		      public void chartClick(ChartEvent ce) {
		          BaseDot d = (BaseDot) ce.getDataType();
		          if (d.get("uri") != null) {
		            parentContainer.loadTab(parentContainer.getPersonSpace().getPersonsMap().get(d.get("uri")));
		            parentContainer.getActions().add(new Action(parentContainer.getCurrentUser(),"C-PD-"+d.getTooltip()));
		          }
		        }
		});
		schart.addPoints(dots);
		schart.setAnimateOnShow(true);
		schart.setTooltip("#val#");
		cm.addChartConfig(schart);
		cm.setTooltipStyle(new ToolTip(MouseStyle.FOLLOW));
		return cm;
	}

	public void addDots(List<PersonModel> personModels) {
		dots = new ArrayList<BaseDot>();
		Star me = new Star();

		for (PersonModel personModel : personModels) {
			if (personModel.getConferences() > maxConferences) 
				maxConferences = personModel.getConferences();
			if (personModel.getMentions() > maxMentions)
				maxMentions = personModel.getMentions();
			if (personModel.getTags() > maxTags)
				maxTags = personModel.getTags();
		}
		
		me.setXY(100, 100);

		for (PersonModel personModel : personModels) {
			//int mentionsSize = personModel.getPerson().getMentionsSet().size();
			//int conferencesSize = personModel.getPerson().getConferencesSet().size();
			//int tagsSize = personModel.getPerson().getTagsSet().size();
				
			int x = personModel.getTags(); //TODO: change to conferences
			int y = personModel.getMentions();
			if (x != maxTags && y != maxMentions) {
				BaseDot bDot = new SolidDot();
				bDot.setXY(Math.log(1+x)/Math.log(1+maxTags)*100, Math.log(1+y)/Math.log(1+maxMentions)*100);
				bDot.setTooltip(personModel.getName());
				bDot.set("uri",personModel.getUri());
				dots.add(bDot);
			} else {
				me.set("uri",personModel.getUri());
				me.setTooltip(personModel.getName());
			}
		}
		dots.add(me);
		chart.setChartModel(getScatterChartModel());
	}
	public void update() {
		chart.setChartModel(getScatterChartModel());
	}
	public List<BaseDot> getDots() {
		return dots;
	}

	public void setDots(List<BaseDot> dots) {
		this.dots = dots;
	}
}
