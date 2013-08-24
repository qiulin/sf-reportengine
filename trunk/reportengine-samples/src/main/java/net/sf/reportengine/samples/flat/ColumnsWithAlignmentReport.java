/**
 * 
 */
package net.sf.reportengine.samples.flat;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * this report is the same as {@link FirstReport} only that 
 * the first and the second column are horizontally-aligned to left 
 * and the third (Amount column) is right aligned 
 * 
 * @author dragos balan
 *
 */
public class ColumnsWithAlignmentReport {

	public static void main(String[] args) {
		new FlatReport.Builder()
			.title("Report with columns aligned programatically")
			.input(new TextInput("./inputData/expenses.csv",","))
			.output(new HtmlOutput("./output/myFormattedOutput.html"))
			
			//the month column
			.addDataColumn(new DefaultDataColumn.Builder(0).header("Month").horizAlign(HorizAlign.LEFT).build())	
			.addDataColumn(new DefaultDataColumn.Builder(1).header("Spent on ?").horizAlign(HorizAlign.LEFT).build())
			.addDataColumn(new DefaultDataColumn.Builder(2).header("Amount").horizAlign(HorizAlign.RIGHT).build())
			
			.build()
		.execute();
	}
}
