/**
 * 
 */
package net.sf.reportengine.samples.flat;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.HtmlOutput;

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

	public static void main(String[] args) {
		FlatReport flatReport = new FlatReport();
		flatReport.setReportTitle("My first expenses report");
	
		//the input
		TextInput reportInput = new TextInput("./inputData/expenses.csv",",");
		flatReport.setIn(reportInput);
	
		//the output
		HtmlOutput reportOutput = new HtmlOutput("./output/myFirstReport.html");
		flatReport.setOut(reportOutput);
		
		//columns configuration
		flatReport.addDataColumn(new DefaultDataColumn("Month",0));	
		flatReport.addDataColumn(new DefaultDataColumn("Spent on",1));
		flatReport.addDataColumn(new DefaultDataColumn("Amount",2));
		
		flatReport.execute();
	}
}
