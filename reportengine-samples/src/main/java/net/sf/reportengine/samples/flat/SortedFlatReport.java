/**
 * 
 */
package net.sf.reportengine.samples.flat;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.Report;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * @author dragos balan
 *
 */
public class SortedFlatReport {
	
	public static void main(String[] args) throws IOException {
		
		FlatTable table = new FlatTable.Builder()
			.input(new TextTableInput("./inputData/expenses.csv",","))
			.addDataColumn(new DefaultDataColumn.Builder(0).header("Month").build())
			.addDataColumn(new DefaultDataColumn.Builder(1).header("Spent on").build())
			.addDataColumn(new DefaultDataColumn.Builder(2).header("Amount").sortAsc().build())
		.build();
		
		new Report.Builder(new HtmlReportOutput(new FileWriter("./output/sortedReport.html")))
			.add(new ReportTitle("Report sorted by amount"))
			.add(table)
			.build()
		.execute();
	}
}
