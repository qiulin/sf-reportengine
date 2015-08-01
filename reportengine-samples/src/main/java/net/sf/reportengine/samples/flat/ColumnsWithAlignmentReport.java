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
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * this report is the same as {@link FirstReport} only that 
 * the first and the second column are horizontally-aligned to left 
 * and the third (Amount column) is right aligned 
 * 
 * @author dragos balan
 *
 */
public class ColumnsWithAlignmentReport {
	
	public static void main(String[] args) throws IOException{
		FlatTable table = new FlatTable.Builder()
			.input(new TextTableInput("./inputData/expenses.csv",","))
			.addDataColumn(new DefaultDataColumn.Builder(0)
									.header("Month")
									.horizAlign(HorizAlign.LEFT)
									.build())	
			.addDataColumn(new DefaultDataColumn.Builder(1)
									.header("Spent on ?")
									.horizAlign(HorizAlign.LEFT)
									.build())
			.addDataColumn(new DefaultDataColumn.Builder(2)
									.header("Amount")
									.horizAlign(HorizAlign.RIGHT)
									.build())
			
			.build(); 
		
		new Report.Builder(new HtmlReportOutput(new FileWriter("./output/myFormattedOutput.html")))
			.add(new ReportTitle("Report with columns aligned programatically"))
			.add(table)
			.build()
		.execute();
	}
}
