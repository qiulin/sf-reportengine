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
		CrossTabReport report = new CrossTabReport(); 
		
		ReportInput input = new TextInput("./inputData/yearlyExpenses.txt", "\t");
		report.setIn(input); 
		
		ReportOutput output = new HtmlOutput("./output/yearlyPivot.html"); 
		report.setOut(output);
		
		//set up the group and data columns
		report.addGroupColumn(new DefaultGroupColumn("Year", 0, 0)); 
		report.addDataColumn(new DefaultDataColumn("Month", 1)); 
		
		//set up the header rows
		report.addHeaderRow(new DefaultCrosstabHeaderRow(2));
		
		//set up the crosstab data
		report.setCrosstabData(new DefaultCrosstabData(3, Calculators.SUM));
		report.setShowTotals(true); 
		report.setShowGrandTotal(false); 
		
		//report execution
		report.execute();
	}
}
