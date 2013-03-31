/**
 * 
 */
package net.sf.reportengine.samples.output;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.ExcelOutput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.out.StaxReportOutput;

/**
 * this is a simple example of how to use multiple outputs
 * 
 * @author dragos balan
 *
 */
public class MultipleOutputsSample {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FlatReport flatReport = new FlatReport();
		flatReport.setTitle("Report having multiple output");
	
		//the input
		flatReport.setIn(new TextInput("./inputData/expenses.csv",","));
	
		//the output
		OutputDispatcher outputDispatcher = new OutputDispatcher(); 
		outputDispatcher.registerOutput(new ExcelOutput("./output/multipleOutput.xls")); 
		outputDispatcher.registerOutput(new HtmlOutput("./output/multipleOutput.html"));
		outputDispatcher.registerOutput(new StaxReportOutput("./output/multipleOutput.xml")); 
		flatReport.setOut(outputDispatcher);
		
		//columns configuration
		flatReport.addDataColumn(new DefaultDataColumn("Month",0));	
		flatReport.addDataColumn(new DefaultDataColumn("Spent on",1));
		flatReport.addDataColumn(new DefaultDataColumn("Amount",2));
		
		flatReport.execute();
	}
	
}
