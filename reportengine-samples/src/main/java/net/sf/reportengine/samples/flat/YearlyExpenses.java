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
			.output(new Html5Output("./output/yearlyExpensesReport.html"))
			
			//groups configuration
			.addGroupColumn(new DefaultGroupColumn.Builder(0)
										.header("Year")
										.level(0)
										.build())
			.addGroupColumn(new DefaultGroupColumn.Builder(1)
										.header("Month")
										.level(1)
										.build())
		
			//data columns
			.addDataColumn(new DefaultDataColumn.Builder(2)
									.header("Spent on")
									.useCalculator(GroupCalculators.COUNT)
									.build())
			.addDataColumn(new DefaultDataColumn.Builder(3)
									.header("Amount")
									.useCalculator(GroupCalculators.SUM, "%.2f")
									.build())
			
			.build().execute();
	}
}
