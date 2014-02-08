/**
 * 
 */
package net.sf.reportengine.samples.flat.autoconfig;

import net.sf.reportengine.AutoconfigFlatReport;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.Html5Output;

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
			.output(new Html5Output("./output/AutodetectFromTextInput.html"))
			.build()
		.execute(); 
	}
}
