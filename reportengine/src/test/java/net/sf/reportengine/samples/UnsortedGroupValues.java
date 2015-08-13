/**
 * 
 */
package net.sf.reportengine.samples;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.ReportBuilder;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * When using group columns, the data in those columns needs to be sorted
 * (otherwise the reportengine will see a change of group everywhere) In this
 * example, the input data for the flat table is not sorted and the reportengine
 * is informed about this by using the sortValues() method in the FlatTable
 * builder class.
 */
public class UnsortedGroupValues {

	public static void main(String[] args) throws IOException {

		FlatTable table = new FlatTable.Builder()
				.sortValues()
				// inform reportengine that it has to sort the values
				.input(new TextTableInput("./input/unsortedExpenses.csv", ","))
				.addGroupColumn(
						new DefaultGroupColumn.Builder(0).header("Month")
								.horizAlign(HorizAlign.LEFT).build())
				.addDataColumn(
						new DefaultDataColumn.Builder(1).header("On What?")
								.horizAlign(HorizAlign.LEFT).build())
				.addDataColumn(
						new DefaultDataColumn.Builder(2).header("Amount")
								.useCalculator(GroupCalculators.SUM)
								.horizAlign(HorizAlign.RIGHT).build()).build();

		// build and execute the report
		new ReportBuilder(new HtmlReportOutput(new FileWriter(
				"./target/MonthlyExpensesFromUnsortedInput.html")))
				.add(new ReportTitle("Monthly Expenses")).add(table).build()
				.execute();
	}
}
