/**
 * 
 */
package net.sf.reportengine.samples.autodetect;

import net.sf.reportengine.AutoconfigFlatReport;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * @author dragos balan
 *
 */
public class AutoconfigFlatReportFromTextToPdf {
	

	public static void main(String... args){
		
		TextInput input = new TextInput("./inputData/employees.csv",","); 
		input.setFirstLineHeader(true); 
		
		AutoconfigFlatReport report = new AutoconfigFlatReport(); 
		report.setIn(input); 
		report.setOut(new HtmlOutput("./output/AutodetectFromTextInput.html")); 
		
		report.execute(); 
	}
}
