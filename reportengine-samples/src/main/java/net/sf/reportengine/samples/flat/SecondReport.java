/**
 * 
 */
package net.sf.reportengine.samples.flat;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.HorizontalAlign;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * this report is the same as {@link FirstReport} only that 
 * the first and the second column are horizontally-aligned to left 
 * and the third (Amount column) is right aligned 
 * 
 * @author dragos balan
 *
 */
public class SecondReport {

	public static void main(String[] args) {
		FlatReport flatReport = new FlatReport();
		flatReport.setReportTitle("My first expenses report");
	
		//the input
		TextInput reportInput = new TextInput("./inputData/expenses.csv",",");
		flatReport.setIn(reportInput);
	
		//the output
		HtmlOutput reportOutput = new HtmlOutput("./output/myFormattedOutput.html");
		flatReport.setOut(reportOutput);
		
		//columns configuration
		DefaultDataColumn monthColumn = new DefaultDataColumn(); 
		monthColumn.setHeader("Month"); 
		monthColumn.setInputColumnIndex(0); 
		monthColumn.setHorizAlign(HorizontalAlign.LEFT); 
		flatReport.addDataColumn(monthColumn);	
		
		DefaultDataColumn destinationColumn = new DefaultDataColumn(); 
		destinationColumn.setHeader("Money spent on .."); 
		destinationColumn.setInputColumnIndex(1); 
		destinationColumn.setHorizAlign(HorizontalAlign.LEFT); 
		flatReport.addDataColumn(destinationColumn);
		
		DefaultDataColumn amountColumn = new DefaultDataColumn(); 
		amountColumn.setHeader("Amount"); 
		amountColumn.setInputColumnIndex(2); 
		amountColumn.setHorizAlign(HorizontalAlign.RIGHT); 
		flatReport.addDataColumn(amountColumn);
		
		//report execution
		flatReport.execute();
	}
}
