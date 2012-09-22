/**
 * 
 */
package net.sf.reportengine.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import net.sf.reportengine.core.calc.ICalculator;

import org.apache.log4j.Logger;

/**
 * date column 
 * 
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.3.0
 * @deprecated as of 0.4 this should be replaced by DateDataColumn
 */
public class DateColumn extends DefaultColumn {
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(DateColumn.class);
	
	/**
	 * the calendar for inputDates
	 */
	private final static Calendar GMT_CALENDAR = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	
	/**
	 * the format of the input date
	 */
	private SimpleDateFormat inputDateFormat;
	//private SimpleDateFormat outputDateFormat;
	
	/**
	 * 
	 */
	public DateColumn() {
	}

	/**
	 * @param inputColIndex
	 */
	public DateColumn(int inputColIndex, String inputFormat, String outputFormat) {
		this("Column "+inputColIndex, inputColIndex, inputFormat, outputFormat);
	}

	/**
	 * @param header
	 * @param inputColIndex
	 */
	public DateColumn(	String header, 
						int inputColIndex, 
						String inputFormat, 
						String outputFormat) {
		this(header, inputColIndex, NO_GROUP_COLUMN, inputFormat, outputFormat);
	}

	/**
	 * @param header
	 * @param inputColIndex
	 * @param groupingOrder
	 */
	public DateColumn(	String header, 
						int inputColIndex, 
						int groupingOrder, 
						String inputFormat, 
						String outputFormat) {
		this(header, inputColIndex, groupingOrder, null, inputFormat, outputFormat);
	}

	/**
	 * @param header
	 * @param inputColIndex
	 * @param calculator
	 */
	public DateColumn(	String header, 
						int inputColIndex, 
						ICalculator calculator,
						String inputFormat, 
						String outputFormat) {
		this(header, inputColIndex, NO_GROUP_COLUMN, calculator, inputFormat, outputFormat);
	}

	

	/**
	 * @param header
	 * @param inputColIndex
	 * @param groupingOrder
	 * @param calculator
	 * @param format
	 */
	public DateColumn(	String header, 
						int inputColIndex, 
						int groupingOrder,
						ICalculator calculator, 
						String inputMask, 
						String outputMask) {
		
		super(header, inputColIndex, groupingOrder, calculator, null);
		
		setOutFormatMask(outputMask);
		
		setInputFormatMask(inputMask);
	}
	
	
	@Override
	public String formatValue(Object rawValue) {
		String result = "";
		try {
			if(rawValue != null){
				Date date = inputDateFormat.parse(rawValue.toString());
				result = super.formatValue(date);
			}
		} catch (ParseException e) {
			logger.error("Error parsing value "+rawValue+" formatted as "+inputDateFormat);
			throw new RuntimeException(e);
		}		
		return result;
	}
	
	public void setOutFormatMask(String formatMask){
		SimpleDateFormat outputDateFormat = new SimpleDateFormat(formatMask);
		outputDateFormat.setCalendar(GMT_CALENDAR);
		setOutFormat(outputDateFormat);
	}
	
	public void setInputFormatMask(String formatMask){
		this.inputDateFormat = new SimpleDateFormat(formatMask);
		this.inputDateFormat.setCalendar(GMT_CALENDAR);
	}
	
	
}
