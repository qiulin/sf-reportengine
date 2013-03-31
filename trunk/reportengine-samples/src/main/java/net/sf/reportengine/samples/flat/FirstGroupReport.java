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
		FlatReport flatReport = new FlatReport();
		flatReport.setShowTotals(true);
		flatReport.setShowGrandTotal(false); 
		flatReport.setTitle("Mothly Expenses report");
		
		//define the input
		TextInput reportInput = new TextInput("./inputData/expenses.csv",",");
		flatReport.setIn(reportInput);
	
		//define the output
		HtmlOutput reportOutput = new HtmlOutput("./output/avgMonthlyExpensesWithGroups.html");
		flatReport.setOut(reportOutput);
		
		//group column configuration
		flatReport.addGroupColumn(new DefaultGroupColumn("Month", 0, 0));
		
		//data columns configuration
		flatReport.addDataColumn(new DefaultDataColumn("On What?",1));
		flatReport.addDataColumn(new DefaultDataColumn("Amount", 2, Calculators.AVG));
		
		//start executing the report
		flatReport.execute();
	}
}
