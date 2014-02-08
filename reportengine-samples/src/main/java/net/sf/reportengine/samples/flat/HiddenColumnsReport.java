/**
 * 
 */
package net.sf.reportengine.samples.flat;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.Html5Output;

/**
 * This report shows how you can display only some columns. 
 * The input names.txt contains 4 columns but the final report will contain only 2 of them
 * 
 * @author dragos balan
 *
 */
public class HiddenColumnsReport {

	public static void main(String[] args) {
		
		new FlatReport.Builder()
			.title("This report shows only 2 out of 4 columns")
			
			//this input has more than two columns
			.input(new TextInput("./inputData/names.txt","\t"))
			.output(new Html5Output("./output/hiddenCols.html"))
			
			//even though the input has more than two columns we only declare two of them: 
			//the second ( having index 1) and the fourth ( index 3)
			.addDataColumn(new DefaultDataColumn.Builder(1).header("FirstName").build())
			.addDataColumn(new DefaultDataColumn.Builder(3).header("LastName").build())
			.build()
		.execute();
	}
}
