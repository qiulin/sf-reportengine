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
 * This report shows only the total rows. 
 * No normal data rows are shown
 * 
 * @author dragos balan
 *
 */
public class NoDataRowsFlatReport {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String...args){
		FlatReport flatReport = new FlatReport();
		
		//report configuration
		flatReport.setReportTitle("Mothly Expenses report");
		flatReport.setShowTotals(true);
		flatReport.setShowDataRows(false);
		
		//the input
		flatReport.setIn(new TextInput("./inputData/expenses.csv",","));
	
		//the output
		flatReport.setOut(new HtmlOutput("./output/avgMonthlyExpensesNoDataRows.html"));
		
		//group columns config
		flatReport.addGroupColumn(new DefaultGroupColumn("Month",0,0)); 
		
		//columns configuration
		//flatReport.addDataColumn(new DefaultDataColumn("On What?",1));
		flatReport.addDataColumn(new DefaultDataColumn("Amount",2,Calculators.AVG)); 
		
		flatReport.execute();
	}
}
