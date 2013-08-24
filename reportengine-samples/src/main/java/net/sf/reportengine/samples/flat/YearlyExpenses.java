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
 * 
 * 
 * @author dragos balan
 */
public class YearlyExpenses {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new FlatReport.Builder()
			.title("Yearly expenses report")
			.input(new TextInput("./inputData/yearlyExpenses.txt","\t"))
			.output(new HtmlOutput("./output/yearlyExpensesReport.html"))
			
			//groups configuration
			.addGroupColumn(new DefaultGroupColumn("Year",0, 0))
			.addGroupColumn(new DefaultGroupColumn("Month", 1, 1))
		
			//data columns
			.addDataColumn(new DefaultDataColumn("Spent on",2, Calculators.COUNT))
			.addDataColumn(new DefaultDataColumn("Amount",3, Calculators.SUM))
			
			.build()
		.execute();
	}

}
