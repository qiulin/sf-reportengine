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
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * This report shows only the total rows. 
 * No normal data rows are shown
 * 
 * @author dragos balan
 *
 */
public class NoDataRowsFlatReport {
	
	public static void main(String...args) throws IOException{
		FlatTable table = new FlatTable.Builder()
			//normal data rows will not be shown
			.showDataRows(false)
			.input(new TextTableInput("./inputData/expenses.csv",","))
			
			//we group for each month
			.addGroupColumn(new DefaultGroupColumn.Builder(0).header("Month").build())
			
			//and we only display the average for the third column
			.addDataColumn(new DefaultDataColumn.Builder(2)
				.header("Amount")
				.useCalculator(GroupCalculators.AVG)
				.build()) 
			.build(); 
		
		new Report.Builder(new HtmlReportOutput(new FileWriter("./output/avgMonthlyExpensesNoDataRows.html")))
			.add(new ReportTitle("Monthly expensed (report showing only total rows"))
			.add(table)
			.build()
		.execute();
	}
}
