/**
 * 
 */
package net.sf.reportengine.samples.flat.autoconfig;

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
		
		new AutoconfigFlatReport.Builder()
			.input(input)
			.output(new HtmlOutput("./output/AutodetectFromTextInput.html"))
			.build()
		.execute(); 
	}
}
