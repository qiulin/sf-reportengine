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
 * @author dragos balan
 *
 */
public class UnsortedGroupValues {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		
		FlatTable table = new FlatTable.Builder()
			.sortValues()//let reportengine sort the group values by himself
			.input(new TextTableInput("./inputData/unsorted_expenses.csv",","))
			.addGroupColumn(new DefaultGroupColumn.Builder(0).header("Month").build())
			.addDataColumn(new DefaultDataColumn.Builder(1).header("On What?").build())
			.addDataColumn(new DefaultDataColumn.Builder(2).header("Amount").useCalculator(GroupCalculators.SUM).build())
			.build();
		
		//just to be visible
		new Report.Builder(new HtmlReportOutput(new FileWriter("./output/monthlyExpensesFromUnsortedInput.html")))
			.add(new ReportTitle("Monthly Expenses"))
			.add(table)
			.build()
		.execute();
	}
}
