/**
 * 
 */
package net.sf.reportengine.samples.flat.customColumns;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.Html5Output;

/**
 * @author dragos balan
 *
 */
public class MovingAverageReport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			new FlatReport.Builder()
				.title("This report contains a column with the computed Moving Average")
				.input(new TextInput("./inputData/EURUSD_2007-2009_FirstHours.txt","\t"))
				.output(new Html5Output("./output/movingAverage.html"))
				.addDataColumn(new DefaultDataColumn.Builder(0).header("Date").build())
				.addDataColumn(new DefaultDataColumn.Builder(1).header("Hour").build())
				.addDataColumn(new DefaultDataColumn.Builder(5).header("Price").build())
				.addDataColumn(new MovingAverageColumn("Moving AVG", 10, 5))
				.build()
			.execute();
	}

}
