/**
 * 
 */
package net.sf.reportengine.samples.flat;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.Html5Output;

/**
 * This report shows only the total rows. 
 * No normal data rows are shown
 * 
 * @author dragos balan
 *
 */
public class NoDataRowsFlatReport {
	
	public static void main(String...args){
		new FlatReport.Builder()
			.title("Mothly Expenses (report showing only total rows)")
			
			//normal data rows will not be shown
			.showDataRows(false)
			
			.input(new TextInput("./inputData/expenses.csv",","))
			.output(new Html5Output("./output/avgMonthlyExpensesNoDataRows.html"))
			
			//we group for each month
			.addGroupColumn(new DefaultGroupColumn.Builder(0).header("Month").build())
			
			//and we only display the average for the third column
			.addDataColumn(new DefaultDataColumn.Builder(2).header("Amount").useCalculator(GroupCalculators.AVG).build()) 
			.build()
		.execute();
	}
}
