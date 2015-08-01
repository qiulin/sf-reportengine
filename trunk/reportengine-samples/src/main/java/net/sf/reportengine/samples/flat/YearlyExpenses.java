/**
 * 
 */
package net.sf.reportengine.samples.flat;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.Report;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * 
 * 
 * @author dragos balan
 */
public class YearlyExpenses {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		
		FlatTable table = new FlatTable.Builder()
			.input(new TextTableInput("./inputData/yearlyExpenses.txt","\t"))
			
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
			
			.build(); 
			
		new Report.Builder(new HtmlReportOutput(new FileWriter("./output/yearlyExpensesReport.html")))
			.add(new ReportTitle("Yearly expenses report"))
			.add(table)
			.build()
		.execute();
	}
}
