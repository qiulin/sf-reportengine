/**
 * 
 */
package net.sf.reportengine.samples;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.ReportBuilder;
import net.sf.reportengine.components.PivotTable;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultPivotData;
import net.sf.reportengine.config.DefaultPivotHeaderRow;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * this is your first pivot table report
 */
public class FirstPivotTableReport {

	public static void main(String[] args) throws IOException {

		PivotTable table = new PivotTable.Builder()
				.input(new TextTableInput("./input/expenses.csv", ","))
				.addDataColumn(new DefaultDataColumn("Month", 0))
				.addHeaderRow(new DefaultPivotHeaderRow(1))
				.pivotData(new DefaultPivotData(2)).build();

		new ReportBuilder(new HtmlReportOutput(new FileWriter(
				"./target/ExpensesPivot.html")))
				.add(new ReportTitle(
						"This is my first report with a pivot table"))
				.add(table).build().execute();

	}
}
