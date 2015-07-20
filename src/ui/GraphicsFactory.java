package ui;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.google.gson.JsonObject;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.Histogram;
import com.xeiam.xchart.StyleManager.ChartType;
import com.xeiam.xchart.StyleManager.LegendPosition;

import connections.executivePanelRequests;

public class GraphicsFactory {
	
	private static final String LABEL_TOTAL_PRODUCTS = "Total Products";
	private static final String LABEL_FAILURES = "Failed Products";
	private static final String[] FAILURE_TABLE_COLUMN_NAMES = {"ProductID", "Process-runtime", "Average drilling heat",
		"Max drilling heat", "Average drilling speed", "Max drilling speed", "Average milling heat",
		"Max milling heat", "Average milling speed", "Max milling speed"};
	
	private executivePanelRequests epr;
	
	public GraphicsFactory(executivePanelRequests epr) {
		this.epr = epr;
	}


	static Chart getFirstGraphic(){
		Chart chart = new ChartBuilder().chartType(ChartType.Bar).width(800).height(600).title("Score Histogram").xAxisTitle("Mean").yAxisTitle("Count").build();
		
		ArrayList<Number> firstData = new ArrayList<Number>();
		ArrayList<Number> secondData = new ArrayList<Number>();
		
		firstData.add(1);
		firstData.add(2);
		firstData.add(3);
		firstData.add(4);
		firstData.add(5);
		firstData.add(6);
		
		secondData.add(0,3);
		secondData.add(1,4);
		secondData.add(2,5);
		secondData.add(3,6);
		secondData.add(0,3);
		secondData.add(4,8);
		
		Histogram histogram1 = new Histogram(firstData, 30, -30, 30);
		Histogram histogram2 = new Histogram(secondData, 30, -30, 30);
		chart.addSeries("histogram 1", histogram1.getxAxisData(), histogram1.getyAxisData());
		chart.addSeries("histogram 2", histogram2.getxAxisData(), histogram2.getyAxisData());
		 
		// Customize Chart
		chart.getStyleManager().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyleManager().setBarWidthPercentage(.96);
		chart.getStyleManager().setBarsOverlapped(true);
		
		return chart;
	}

	
	 ChartPanel getProductFailureBarchart(){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		JsonObject obj = epr.getProductionOverviewData();
		
		dataset.addValue(100.0, LABEL_TOTAL_PRODUCTS, "15:00");
		dataset.addValue(120.0, LABEL_TOTAL_PRODUCTS, "16:00");
		dataset.addValue(113.0, LABEL_TOTAL_PRODUCTS, "17:00");
		dataset.addValue(124.0, LABEL_TOTAL_PRODUCTS, "18:00");
		
		dataset.addValue(10.0, LABEL_FAILURES, "15:00");
		dataset.addValue(12.0, LABEL_FAILURES, "16:00");
		dataset.addValue(28.0, LABEL_FAILURES, "17:00");
		dataset.addValue(5.0, LABEL_FAILURES, "18:00");
		
		
		JFreeChart chart = ChartFactory.createBarChart("Production Overview", "Result", "Amount of Products", dataset, PlotOrientation.VERTICAL,
				true, true, false);
		
		//Area ChartTest
		//JFreeChart chart = ChartFactory.createAreaChart("Test", "abc", "def", dataset, PlotOrientation.VERTICAL, true, false, false);
		
				
		ChartPanel panel = new ChartPanel(chart);
		return panel;
	}
	 
	 JTable getFailureTable(){
		 //epr.getFailureTableData();
		 //somehow change the table --> header missing
		 Number[][] rowData = {{1,2,3,4,5,6,7,8,9,0},{0,1,2,3,4,5,6,7,8,9}};
		 JTable table = new JTable(rowData, FAILURE_TABLE_COLUMN_NAMES);
		 
		 return table;
	 }
}
