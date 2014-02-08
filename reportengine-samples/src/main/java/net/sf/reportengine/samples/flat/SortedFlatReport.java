/**
 * 
 */
package net.sf.reportengine.samples.flat;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.Html5Output;

/**
 * @author dragos balan
 *
 */
public class SortedFlatReport {
	
	public static void main(String[] args) {
		FlatReport flatReport = new FlatReport.Builder()
			.title("Report sorted by amount column")
			.input(new TextInput("./inputData/expenses.csv",","))
			.output(new Html5Output("./output/sortedReport.html"))
			.addDataColumn(new DefaultDataColumn.Builder(0).header("Month").build())
			.addDataColumn(new DefaultDataColumn.Builder(1).header("Spent on").build())
			.addDataColumn(new DefaultDataColumn.Builder(2).header("Amount").sortAsc().build())
			.build();
		
		flatReport.execute();
	}
}
