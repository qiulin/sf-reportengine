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
 * The first report containing a group column. 
 * The month column is declared as a group column so 
 * after each change in this column the totals will 
 * be displayed on the other columns.
 */
public class FirstGroupReport {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		FlatTable flatTable = new FlatTable.Builder()
			.input(new TextTableInput("./inputData/expenses.csv",","))
			.addGroupColumn(new DefaultGroupColumn.Builder(0).header("Month").build())
			.addDataColumn(new DefaultDataColumn.Builder(1).header("On What?").build())
			.addDataColumn(new DefaultDataColumn.Builder(2).header("Amount").useCalculator(GroupCalculators.SUM).build())
			.build();
		
		
		//just to be visible
		new Report.Builder(new HtmlReportOutput(new FileWriter("./output/avgMonthlyExpensesUsingGroups.html")))
			.add(new ReportTitle("Monthly Expenses"))
			.add(flatTable)
			.build()
		.execute();
	}
}
