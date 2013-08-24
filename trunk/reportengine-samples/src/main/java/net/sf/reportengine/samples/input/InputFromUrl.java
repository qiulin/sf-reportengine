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
	
	
	public static void main(String[] args) {
		try {
			URL fileUrl = new URL("http://svn.code.sf.net/p/reportengine/code/trunk/reportengine-samples/inputData/expenses.csv");
			
			new FlatReport.Builder()
				.title("This report gets its input data from an URL")
				.input(new TextInput(new InputStreamReader(fileUrl.openStream()),","))
				.output(new HtmlOutput("./output/outReportFromUrl.html"))
				.addDataColumn(new DefaultDataColumn("Month",0))	
				.addDataColumn(new DefaultDataColumn("Spent on",1))
				.addDataColumn(new DefaultDataColumn("Amount",2))
				.build()
			.execute();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
