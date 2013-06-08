/**
 * 
 */
package net.sf.reportengine.samples.pivot;

import net.sf.reportengine.CrossTabReport;
import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.ReportOutput;

/**
 * this is my first pivot table report
 */
public class FirstPivotTableReport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CrossTabReport classUnderTest = new CrossTabReport(); 
		
		ReportInput input = new TextInput("./inputData/expenses.csv", ",");
		classUnderTest.setIn(input); 
		
		ReportOutput output = new HtmlOutput("./output/xpensesPivot.html"); 
		classUnderTest.setOut(output);
		
		//set up the data columns
		classUnderTest.addDataColumn(new DefaultDataColumn("Month", 0)); 
		
		//set up the header rows
		classUnderTest.addHeaderRow(new DefaultCrosstabHeaderRow(1));
		
		//set up the crosstab data
		classUnderTest.setCrosstabData(new DefaultCrosstabData(2));
		
		//report execution
		classUnderTest.execute();
	}
}
