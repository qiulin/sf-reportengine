/**
 * 
 */
package net.sf.reportengine.scenarios;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.in.ArrayReportInput;

/**
 * @author dragos balan
 *
 */
public class ScenarioFormatedValues {
	
	public static final Calendar calendar;
	static {
	    calendar = GregorianCalendar.getInstance();
	    calendar.set(1776, 6, 4, 0, 0, 1);
	} 
	
	
	public static final ReportInput INPUT = new ArrayReportInput(new Object[][]{
			new Object[]{Integer.valueOf(0), "1000", 	Integer.valueOf(101), calendar.getTime()}, 
			new Object[]{Integer.valueOf(1), "20000", 	Integer.valueOf(200), calendar.getTime()},
			new Object[]{Integer.valueOf(2), "3000", 	Integer.valueOf(300), calendar.getTime()},
			new Object[]{Integer.valueOf(3), "400000", 	Integer.valueOf(400), calendar.getTime()},
			new Object[]{Integer.valueOf(4), "5000", 	Integer.valueOf(500), calendar.getTime()},
			new Object[]{Integer.valueOf(5), "6000000", Integer.valueOf(600), calendar.getTime()},
			new Object[]{Integer.valueOf(6), "7000", 	Integer.valueOf(700), calendar.getTime()},
			new Object[]{Integer.valueOf(7), "80000000",Integer.valueOf(800), calendar.getTime()},
	});
	
	public static final List<GroupColumn> GROUP_COLUMNS = Arrays.asList(
			new GroupColumn[]{
				new DefaultGroupColumn("Formatted group value", 0, 0, "%d")
			}
	); 
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList( 
	new DataColumn[]{
			new DefaultDataColumn.Builder(1)
				.header("Formatted String")
				.valuesFormatter("formatted string %s")
				.build(),  
		new DefaultDataColumn.Builder(2)
				.header("Formatted Integer")
				.useCalculator(Calculators.MAX, "formatted totals %f")
				.valuesFormatter("formatted integer %d")
				.build(),
		new DefaultDataColumn.Builder(3)
				.header("Formatted Date")
				.valuesFormatter("formatted date as %tD")
				.build()
	});
}
