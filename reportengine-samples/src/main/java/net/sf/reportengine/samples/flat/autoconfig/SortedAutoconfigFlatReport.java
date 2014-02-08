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
public class SortedAutoconfigFlatReport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TextInput input = new TextInput("./inputData/employees.csv",","); 
		input.setFirstLineHeader(true); 
		
		AutoconfigFlatReport report = new AutoconfigFlatReport.Builder()
			.input(input)
			.output(new Html5Output("./output/SortedEmployeesByName.html"))
			.build(); 
		
		//requesting this columns to be sorted will trigger 
		//the sorting mechanism inside the report algorithms
		report.forColumn("Firstname").sortAsc(); 
		
		report.execute(); 
	}

}
