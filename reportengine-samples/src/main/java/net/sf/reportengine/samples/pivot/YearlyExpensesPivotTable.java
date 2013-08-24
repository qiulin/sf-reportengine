/**
 * 
 */
package net.sf.reportengine.samples.pivot;

import net.sf.reportengine.CrossTabReport;
import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.ReportOutput;

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
		
			.crosstabData(new DefaultCrosstabData(3, Calculators.SUM))
			.showGrandTotal(false)
			.build()
		.execute();
	}
}
