/**
 * 
 */
package net.sf.reportengine.samples.flat;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.Html5Output;

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
		FlatReport flatReport = new FlatReport.Builder()
			.title("My first report")
			.input(new TextInput("./inputData/expenses.csv",","))
			.output(new Html5Output("./output/myFirstReport.html"))
			.addDataColumn(new DefaultDataColumn.Builder(0).header("Month").build())
			.addDataColumn(new DefaultDataColumn.Builder(1).header("Spent on").build())
			.addDataColumn(new DefaultDataColumn.Builder(2).header("Amount").build())
			.build();
		
		flatReport.execute();
	}
}
