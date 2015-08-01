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
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * This report shows how you can display only some columns. 
 * The input names.txt contains 4 columns but the final report will contain only 2 of them
 * 
 * @author dragos balan
 *
 */
public class HiddenColumnsReport {

	public static void main(String[] args) throws IOException{
		
		FlatTable table = new FlatTable.Builder()
			//this input has more than two columns
			.input(new TextTableInput("./inputData/names.txt","\t"))
			//even though the input has more than two columns we only declare two of them: 
			//the second ( having index 1) and the fourth ( index 3)
			.addDataColumn(new DefaultDataColumn.Builder(1).header("FirstName").build())
			.addDataColumn(new DefaultDataColumn.Builder(3).header("LastName").build())
			.build(); 
		
		new Report.Builder(new HtmlReportOutput(new FileWriter("./output/hiddenCols.html")))
			.add(new ReportTitle("This report shows only 2 out of 4 columns"))
			.add(table)
			.build()
		.execute();
	}
}
