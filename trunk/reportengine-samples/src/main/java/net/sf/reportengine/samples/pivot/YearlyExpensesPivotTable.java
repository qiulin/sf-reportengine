/**
 * 
 */
package net.sf.reportengine.samples.pivot;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.Report;
import net.sf.reportengine.components.PivotTable;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * 
 * @author dragos balan
 *
 */
public class YearlyExpensesPivotTable {

	public static void main(String[] args) throws IOException {
		PivotTable pivotTable = new PivotTable.Builder()
			.input(new TextTableInput("./inputData/yearlyExpenses.txt", "\t"))
			
			.addGroupColumn(new DefaultGroupColumn("Year", 0, 0))
			.addDataColumn(new DefaultDataColumn("Month", 1))
		
			.addHeaderRow(new DefaultCrosstabHeaderRow(2))
		
			.crosstabData(new DefaultCrosstabData.Builder(3)
								.useCalculator(GroupCalculators.SUM, "%.2f")
								.build())
			.showGrandTotal()
			.showTotals()
			.build(); 
		
		new Report.Builder(new HtmlReportOutput(new FileWriter("./output/yearlyPivot.html")))
			.add(new ReportTitle("Yearly expenses arranged as a pivot table"))
			.add(pivotTable)
			.build()
		.execute();
	}
}
