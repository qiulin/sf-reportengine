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
 * @author dragos balan
 *
 */
public class UnsortedGroupValues {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FlatReport flatReport = new FlatReport.Builder()
			.title("Mothly Expenses")
			.sortValues()			//let reportengine sort the group values by himself
			.input(new TextInput("./inputData/unsorted_expenses.csv",","))
			.output(new HtmlOutput("./output/monthlyExpensesFromUnsortedInput.html"))
			.addGroupColumn(new DefaultGroupColumn.Builder(0).header("Month").build())
			.addDataColumn(new DefaultDataColumn.Builder(1).header("On What?").build())
			.addDataColumn(new DefaultDataColumn.Builder(2).header("Amount").useCalculator(Calculators.SUM).build())
			.build();
		
		//just to be visible
		flatReport.execute();
	}
}
