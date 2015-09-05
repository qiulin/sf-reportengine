/**
 * 
 */
package net.sf.reportengine.samples;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.ReportBuilder;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.FlatTableBuilder;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * This report shows only the total rows. 
 * No data rows are shown
 * 
 * @author dragos balan
 *
 */
public class NoDataRowsFlatReport {
	
	public static void main(String...args) throws IOException{
		FlatTable table = new FlatTableBuilder(new TextTableInput("./input/expenses.csv",","))
			//normal data rows will not be shown
			.showDataRows(false)
			//we group for each month
			.addGroupColumn(new DefaultGroupColumn.Builder(0).header("Month").build())
			//and we only display the average for the third column
			.addDataColumn(new DefaultDataColumn.Builder(2)
				.header("Amount")
				.useCalculator(GroupCalculators.AVG)
				.build()) 
			.build(); 
		
		new ReportBuilder(new HtmlReportOutput(new FileWriter("./target/avgMonthlyExpensesNoDataRows.html")))
			.add(new ReportTitle("Monthly expensed (report showing only total rows"))
			.add(table)
			.build()
		.execute();
	}
}
