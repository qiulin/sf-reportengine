/**
 * 
 */
package net.sf.reportengine.samples.flat.customColumns;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * This report uses the {@link FullNameCustomDataColumn} custom created column
 * 
 * @author dragos balan
 */
public class CustomColumnFlatReport {

	public static void main(String[] args) {
		new FlatReport.Builder()
			.title("My custom columns report")
			.input(new TextInput("./inputData/names.txt","\t"))
			.output(new HtmlOutput("./output/customColumnsResult.html"))
			
			//this is a custom data column
			.addDataColumn(new FullNameCustomDataColumn())
			
			//another custom data column
			.addDataColumn(new SumCustomColumn())
		.build()
		.execute(); 
	}
}
