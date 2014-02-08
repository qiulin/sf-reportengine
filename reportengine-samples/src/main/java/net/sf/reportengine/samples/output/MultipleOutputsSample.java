/**
 * 
 */
package net.sf.reportengine.samples.output;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.ExcelOutput;
import net.sf.reportengine.out.Html5Output;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.out.PdfOutput;
import net.sf.reportengine.out.StaxReportOutput;

/**
 * this is a simple example of how to use multiple outputs
 * 
 * @author dragos balan
 *
 */
public class MultipleOutputsSample {
	
	public static void main(String[] args) {
		
		//the output
		OutputDispatcher outputDispatcher = new OutputDispatcher(); 
		outputDispatcher.registerOutput(new ExcelOutput("./output/multipleOutput.xls")); 
		outputDispatcher.registerOutput(new Html5Output("./output/multipleOutput.html"));
		outputDispatcher.registerOutput(new StaxReportOutput("./output/multipleOutput.xml")); 
		outputDispatcher.registerOutput(new PdfOutput("./output/multipleOutput.pdf")); 
		
		new FlatReport.Builder()
			.title("This report creates excel, html, xml and pdf results")
			.input(new TextInput("./inputData/expenses.csv",","))
			.output(outputDispatcher)
			.addDataColumn(new DefaultDataColumn("Month",0))
			.addDataColumn(new DefaultDataColumn("Spent on",1))
			.addDataColumn(new DefaultDataColumn("Amount",2))
			.build()
		.execute();
	}
	
}
