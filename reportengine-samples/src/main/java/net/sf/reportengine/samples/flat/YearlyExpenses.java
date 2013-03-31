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
 * @author dragos
 *
 */
public class YearlyExpenses {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FlatReport flatReport = new FlatReport();
		flatReport.setTitle("Yearly expenses report");
		flatReport.setShowTotals(true); 
		
		//the input
		flatReport.setIn(new TextInput("./inputData/yearlyExpenses.txt","\t"));
	
		//the output
		HtmlOutput reportOutput = new HtmlOutput("./output/yearlyExpensesReport.html");
		flatReport.setOut(reportOutput);
		
		//groups configuration
		flatReport.addGroupColumn(new DefaultGroupColumn("Year",0, 0));
		flatReport.addGroupColumn(new DefaultGroupColumn("Month", 1, 1)); 
		
		//data columns
		flatReport.addDataColumn(new DefaultDataColumn("Spent on",2, Calculators.COUNT));
		flatReport.addDataColumn(new DefaultDataColumn("Amount",3, Calculators.SUM));
		
		flatReport.execute();
	}

}
