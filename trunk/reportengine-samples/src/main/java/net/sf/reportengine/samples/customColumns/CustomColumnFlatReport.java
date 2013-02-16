/**
 * 
 */
package net.sf.reportengine.samples.customColumns;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * This report uses the {@link FullNameCustomDataColumn} custom created column
 * 
 * @author dragos balan
 */
public class CustomColumnFlatReport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FlatReport flatReport = new FlatReport();
		flatReport.setReportTitle("My custom columns report");
	
		//the input
		TextInput reportInput = new TextInput("./inputData/names.txt","\t");
		flatReport.setIn(reportInput);
	
		//the output
		HtmlOutput reportOutput = new HtmlOutput("./output/customColumnsResult.html");
		flatReport.setOut(reportOutput);
		
		//columns configuration
		flatReport.addDataColumn(new FullNameCustomDataColumn());	
		flatReport.addDataColumn(new SumCustomColumn());
		
		flatReport.execute();
	}
}
