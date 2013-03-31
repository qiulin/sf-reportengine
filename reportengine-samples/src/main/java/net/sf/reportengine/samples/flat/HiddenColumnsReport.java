/**
 * 
 */
package net.sf.reportengine.samples.flat;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * This report shows how you can display only some columns. 
 * The input names.txt contains 4 columns but the final report will contain only 2 of them
 * 
 * @author dragos balan
 *
 */
public class HiddenColumnsReport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FlatReport flatReport = new FlatReport();
		flatReport.setTitle("This report shows only 2 out of 4 columns");
	
		//the input
		flatReport.setIn(new TextInput("./inputData/names.txt","\t"));
	
		//the output
		flatReport.setOut(new HtmlOutput("./output/hiddenCols.html"));
		
		//data columns ( as you can see two columns are missing. the column 0 and 2 are missing)
		flatReport.addDataColumn(new DefaultDataColumn("Firstname", 1));
		flatReport.addDataColumn(new DefaultDataColumn("Lastname", 3)); 
		
		//report execution
		flatReport.execute();
	}
}
