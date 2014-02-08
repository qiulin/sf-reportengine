/**
 * 
 */
package net.sf.reportengine.samples.pivot;

import net.sf.reportengine.CrossTabReport;
import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * 
 * @author dragos balan
 *
 */
public class YearlyExpensesPivotTable {

	public static void main(String[] args) {
		new CrossTabReport.Builder()
			.title("Yearly expenses arranged as a pivot table")
			.input(new TextInput("./inputData/yearlyExpenses.txt", "\t"))
			.output(new HtmlOutput("./output/yearlyPivot.html"))
			
			.addGroupColumn(new DefaultGroupColumn("Year", 0, 0))
			.addDataColumn(new DefaultDataColumn("Month", 1))
		
			.addHeaderRow(new DefaultCrosstabHeaderRow(2))
		
			.crosstabData(new DefaultCrosstabData.Builder(3)
								.useCalculator(GroupCalculators.SUM, "%.2f")
								.build())
			.showGrandTotal()
			.showTotals()
			.build()
		.execute();
	}
}
