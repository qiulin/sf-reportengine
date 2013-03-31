/**
 * 
 */
package net.sf.reportengine.samples.output;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.ExcelOutput;

/**
 * this is a simple example of how to create excel output
 * 
 * @author dragos balan
 *
 */
public class ExcelOutputSample {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FlatReport flatReport = new FlatReport();
		flatReport.setTitle("Excel output Report");
	
		//the input
		flatReport.setIn(new TextInput("./inputData/expenses.csv",","));
	
		//the output
		flatReport.setOut(new ExcelOutput("./output/myFirstExcelReport.xls"));
		
		//columns configuration
		flatReport.addDataColumn(new DefaultDataColumn("Month",0));	
		flatReport.addDataColumn(new DefaultDataColumn("Spent on",1));
		flatReport.addDataColumn(new DefaultDataColumn("Amount",2));
		
		flatReport.execute();
	}
}
