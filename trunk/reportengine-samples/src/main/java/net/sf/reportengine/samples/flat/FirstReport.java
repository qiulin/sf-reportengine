/**
 * 
 */
package net.sf.reportengine.samples.flat;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.Report;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;


/**
 * this is your first report having the following steps 
 * 
 * 1. construct a flat report
 * 2. adds an input to my report
 * 3. adds an output 
 * 4. configures the columns of my report
 * 5. executes the report
 */
public class FirstReport {

	public static void main(String[] args) throws IOException {
		
		FlatTable flatTable = new FlatTable.Builder()
			.input(new TextTableInput("./inputData/expenses.csv",","))
			.addDataColumn(new DefaultDataColumn.Builder(0).header("Month").build())
			.addDataColumn(new DefaultDataColumn.Builder(1).header("Spent on").build())
			.addDataColumn(new DefaultDataColumn.Builder(2).header("Amount").build())
			.build();
		
		//FileWriter is used just for demo
		Report report = new Report.Builder(new HtmlReportOutput(new FileWriter("./output/myFirstReport.html")))
				.add(new ReportTitle("My first report"))
				.add(flatTable)
				.build();
		
		report.execute(); 
		 
	}
}
