/**
 * 
 */
package net.sf.reportengine.samples.flat;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * The first report containing a group column. 
 * The month column is declared as a group column so 
 * after each change in this column the totals will 
 * be displayed on the other columns.
 */
public class FirstGroupReport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FlatReport flatReport = new FlatReport.Builder()
			.title("Average Mothly Expenses")
			//.showGrandTotal(false)
			.input(new TextInput("./inputData/expenses.csv",","))
			.output(new HtmlOutput("./output/avgMonthlyExpensesUsingGroups.html"))
			.addGroupColumn(new DefaultGroupColumn.Builder(0).header("Month").build())
			.addDataColumn(new DefaultDataColumn.Builder(1).header("On What?").build())
			.addDataColumn(new DefaultDataColumn.Builder(2).header("Amount").useCalculator(Calculators.AVG).build())
			.build();
		
		//just to be visible
		flatReport.execute();
	}
}
