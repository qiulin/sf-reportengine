/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;

/**
 * @author dragos balan (dragos.balan@gmail.com)
 * @deprecated as of version 0.4 you should use one of IGroupColumns or IDataColumns classes
 */
public abstract class AbstractColumn implements IColumn {
	
	/**
	 * used when you don't need this column to take part in any groupings
	 */
	public final static int NO_GROUP_COLUMN = -1;
	
	/**
	 * the column header
	 */
	private String header = "Column";
	
	
	/**
	 * the grouping order (default no grouping)
	 */
	private int groupingOrder = NO_GROUP_COLUMN; //no grouping by default
	
	/**
	 * the calculator for this column
	 */
	private ICalculator calculator = null; // no default calculator
	
	/**
	 * the formatter of the value
	 */
	private Format outFormat = null;
	
	/**
	 * show totals on each change in this column
	 * This has no effect if no there is no grouping order defined on this column
	 */
	private boolean showTotalsOnChange = true;
	
	
	/**
	 * default constructor
	 */
	public AbstractColumn(){
		this("Column");
	}
	
	public AbstractColumn(String header){
		this(header, NO_GROUP_COLUMN, null);
	}
	
	public AbstractColumn(String header, ICalculator calculator){
		this(header, NO_GROUP_COLUMN, calculator);
	}
	
	public AbstractColumn(String header, int groupingOrder){
		this(header, groupingOrder, null);
	}
	
	public AbstractColumn(String header, int groupingOrder, ICalculator calculator){
		this(header, groupingOrder, calculator, null);
	}
	
	public AbstractColumn(String header, int groupingOrder, ICalculator calculator, Format outFormat){
		setHeader(header);
		setGroupingOrder(groupingOrder);
		setCalculator(calculator);
		setOutFormat(outFormat);
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.IColumn#getHeader()
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * setter for the header of the column
	 * @param header
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	
	/**
	 * returns the grouping order for this column
	 */
	public int getGroupingOrder() {
		return groupingOrder;
	}
	
	/**
	 * sets the grouping order for this column
	 * 
	 * @param groupingOrder
	 */
	public void setGroupingOrder(int groupingOrder) {
		this.groupingOrder = groupingOrder;
	}

	/**
	 * returns the calculator used for this column
	 */
	public ICalculator getCalculator() {
		return calculator;
	}

	/**
	 * setter for the calculator of this column
	 * @param calculator
	 */
	public void setCalculator(ICalculator calculator) {
		this.calculator = calculator;
	}

	/**
	 * @return the format
	 */
	public Format getOutFormat() {
		return outFormat;
	}

	/**
	 * @param format the format to set
	 */
	public void setOutFormat(Format format) {
		this.outFormat = format;
	}
	
	/**
	 * 
	 */
	public String getFormattedValue(NewRowEvent newRowEvent) {
		Object rawValue = getRawValue(newRowEvent);
		return formatValue(rawValue);
	}
	
	/**
	 * 
	 * @param newRowEvent
	 * @return
	 */
	public abstract Object getRawValue(NewRowEvent newRowEvent);
	
	
	/**
	 * 
	 * @param rawVal
	 * @return
	 */
	public String formatValue(Object rawVal){
		String result = "";
		if(rawVal != null){
			if(getOutFormat() != null){
				result = getOutFormat().format(rawVal);
			}else{
				result = rawVal.toString();
			}
		}
		return result;
	}

	/**
	 * returns the showTotalsOnChange flag
	 * 
	 * @return the showTotalsOnChange
	 */
	public boolean isShowTotalsOnChange() {
		return showTotalsOnChange;
	}

	/**
	 * sets to true if you want to see totals on each change on the values of this column
	 * This has no effect if no there is no grouping order defined on this column
	 * 
	 * @param showTotalsOnChange the showTotalsOnChange to set
	 */
	public void setShowTotalsOnChange(boolean showTotalsOnChange) {
		this.showTotalsOnChange = showTotalsOnChange;
	}
}
