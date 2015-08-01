/**
 * 
 */
package net.sf.reportengine.samples.pivot;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.Report;
import net.sf.reportengine.components.PivotTable;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * this is my first pivot table report
 */
public class FirstPivotTableReport {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		
		PivotTable table= new PivotTable.Builder()
			.input(new TextTableInput("./inputData/expenses.csv", ","))
			.addDataColumn(new DefaultDataColumn("Month", 0))
			.addHeaderRow(new DefaultCrosstabHeaderRow(1))
			.crosstabData(new DefaultCrosstabData(2))
		.build();
		
		new Report.Builder(new HtmlReportOutput(new FileWriter("./output/expensesPivot.html")))
			.add(new ReportTitle("my first pivot table"))
			.add(table)
			.build()
		.execute(); 
		
	}
}
