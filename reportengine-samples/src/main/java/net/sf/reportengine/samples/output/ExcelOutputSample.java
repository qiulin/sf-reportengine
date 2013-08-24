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
	
	public static void main(String[] args) {
		
		new FlatReport.Builder()
			.title("Excel output Report")
			.input(new TextInput("./inputData/expenses.csv",","))
			.output(new ExcelOutput("./output/myFirstExcelReport.xls"))
			.addDataColumn(new DefaultDataColumn("Month",0))	
			.addDataColumn(new DefaultDataColumn("Spent on",1))
			.addDataColumn(new DefaultDataColumn("Amount",2))
			.build()
		.execute();
	}
}
