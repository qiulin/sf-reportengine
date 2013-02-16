/**
 * 
 */
package net.sf.reportengine.samples.input;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * this report gets its data from an url
 * 
 * @author dragos balan
 *
 */
public class InputFromUrl {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FlatReport flatReport = new FlatReport();
			flatReport.setReportTitle("My first expenses report");
		
			//the input
			URL fileUrl = new URL("http://svn.code.sf.net/p/reportengine/code/samples/inputData/expenses.csv");
			TextInput reportInput = new TextInput(new InputStreamReader(fileUrl.openStream()),",");
			flatReport.setIn(reportInput);
		
			//the output
			HtmlOutput reportOutput = new HtmlOutput("./output/outReportFromUrl.html");
			flatReport.setOut(reportOutput);
			
			//columns configuration
			flatReport.addDataColumn(new DefaultDataColumn("Month",0));	
			flatReport.addDataColumn(new DefaultDataColumn("Spent on",1));
			flatReport.addDataColumn(new DefaultDataColumn("Amount",2));
			
			flatReport.execute();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
