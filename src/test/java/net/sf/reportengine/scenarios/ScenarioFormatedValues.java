/**
 * 
 */
package net.sf.reportengine.scenarios;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.in.MemoryReportInput;

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
	
	
	public static final IReportInput INPUT = new MemoryReportInput(new Object[][]{
			new Object[]{Integer.valueOf(0), "1000", 	Integer.valueOf(100), calendar.getTime()}, 
			new Object[]{Integer.valueOf(1), "20000", 	Integer.valueOf(200), calendar.getTime()},
			new Object[]{Integer.valueOf(2), "3000", 	Integer.valueOf(300), calendar.getTime()},
			new Object[]{Integer.valueOf(3), "400000", 	Integer.valueOf(400), calendar.getTime()},
			new Object[]{Integer.valueOf(4), "5000", 	Integer.valueOf(500), calendar.getTime()},
			new Object[]{Integer.valueOf(5), "6000000", Integer.valueOf(600), calendar.getTime()},
			new Object[]{Integer.valueOf(6), "7000", 	Integer.valueOf(700), calendar.getTime()},
			new Object[]{Integer.valueOf(7), "80000000",Integer.valueOf(800), calendar.getTime()},
	});
	
	public static final List<IDataColumn> DATA_COLUMNS = Arrays.asList( 
	new IDataColumn[]{
		new DefaultDataColumn("Non formatted", 0),
		new DefaultDataColumn("Formatted String", 1, null, null){
			@Override
			public String getFormattedValue(Object value){
				return String.format("the value %s is formatted", value); 
			}
		},
		new DefaultDataColumn("Formatted Integer", 2, null, NumberFormat.getCurrencyInstance()),
		new DefaultDataColumn("Formatted Date", 3, null, SimpleDateFormat.getDateInstance(DateFormat.SHORT))
	});
}
