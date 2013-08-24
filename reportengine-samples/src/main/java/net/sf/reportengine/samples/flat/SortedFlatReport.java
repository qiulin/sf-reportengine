/**
 * 
 */
package net.sf.reportengine.samples.flat;

import net.sf.reportengine.AutoconfigFlatReport;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * @author dragos
 *
 */
public class SortedFlatReport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TextInput input = new TextInput("./inputData/employees.csv",","); 
		input.setFirstLineHeader(true); 
		
		AutoconfigFlatReport report = new AutoconfigFlatReport.Builder()
			.input(input)
			.output(new HtmlOutput("./output/SortedEmployeesByName.html"))
			.build(); 
		
		report.forColumn("Firstname").sortAsc(); 
		
		report.execute(); 
	}

}
