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
		CrossTabReport crosstabReport = new CrossTabReport.Builder()
			.input(new TextInput("./inputData/expenses.csv", ","))
			.output(new HtmlOutput("./output/expensesPivot.html"))
			.addDataColumn(new DefaultDataColumn("Month", 0))
			.addHeaderRow(new DefaultCrosstabHeaderRow(1))
			.crosstabData(new DefaultCrosstabData(2))
		.build();
		
		crosstabReport.execute(); 
		
	}
}
