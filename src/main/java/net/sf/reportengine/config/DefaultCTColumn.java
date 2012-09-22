/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.calc.ICalculator;

/**
 * @author dragos
 * @deprecated as of 0.4 use IGroupingColumn, ICrosstabaHeaderRow, IDataColumn
 */
public class DefaultCTColumn extends DefaultColumn implements ICtColumn{
	
	/**
	 * 
	 */
	private boolean showInHeader;
	
	/**
	 * 
	 */
	public DefaultCTColumn() {
		this(false);
	}
	
	public DefaultCTColumn(boolean showInHeader){
		this(showInHeader, 0);
	}

	/**
	 * @param inputColIndex
	 */
	public DefaultCTColumn(int inputColIndex) {
		this(false, inputColIndex);
	}
	
	/**
	 * @param inputColIndex
	 */
	public DefaultCTColumn(boolean showInHeader, int inputColIndex) {
		this(showInHeader, "Column "+inputColIndex, inputColIndex);
	}

	/**
	 * @param header
	 * @param inputColIndex
	 */
	public DefaultCTColumn(String header, int inputColIndex) {
		this(false, header, inputColIndex);
	}
	
	/**
	 * @param header
	 * @param inputColIndex
	 */
	public DefaultCTColumn(	boolean showInHeader, 
							String header, 
							int inputColIndex) {
		this(showInHeader, header, inputColIndex, NO_GROUP_COLUMN);
	}
	
	/**
	 * @param header
	 * @param inputColIndex
	 * @param groupingOrder
	 */
	public DefaultCTColumn(	String header, 
							int inputColIndex, 
							int groupingOrder) {
		this(false, header, inputColIndex, groupingOrder);
	}
	
	
	/**
	 * @param header
	 * @param inputColIndex
	 * @param groupingOrder
	 */
	public DefaultCTColumn(	boolean showInHeader, 
							String header, 
							int inputColIndex, 
							int groupingOrder) {
		this(showInHeader, header, inputColIndex, groupingOrder, null);
	}

	
	
	/**
	 * @param header
	 * @param inputColIndex
	 * @param calculator
	 */
	public DefaultCTColumn(	String header, 
							int inputColIndex,
							ICalculator calculator) {
		this(false, header, inputColIndex, calculator);
	}
	
	
	/**
	 * @param showInHeader
	 * @param header
	 * @param inputColIndex
	 * @param calculator
	 */
	public DefaultCTColumn(	boolean showInHeader, 
							String header, 
							int inputColIndex,
							ICalculator calculator) {
		this(showInHeader, header, inputColIndex, NO_GROUP_COLUMN, calculator);
	}
	
	/**
	 * @param header
	 * @param inputColIndex
	 * @param groupingOrder
	 * @param calculator
	 */
	public DefaultCTColumn(	String header, 
							int inputColIndex, 
							int groupingOrder,
							ICalculator calculator) {
		this(false, header, inputColIndex, groupingOrder, calculator);
	}
	
	/**
	 * @param showInHeader
	 * @param header
	 * @param inputColIndex
	 * @param groupingOrder
	 * @param calculator
	 */
	public DefaultCTColumn(	boolean showInHeader, 
							String header, 
							int inputColIndex, 
							int groupingOrder,
							ICalculator calculator) {
		this(showInHeader, header, inputColIndex, groupingOrder, calculator, null);
	}
	
	/**
	 * @param header
	 * @param inputColIndex
	 * @param groupingOrder
	 * @param calculator
	 * @param outFormat
	 */
	public DefaultCTColumn(	String header, 
							int inputColIndex, 
							int groupingOrder,
							ICalculator calculator, 
							Format outFormat) {
		this(false, header, inputColIndex, groupingOrder, calculator, outFormat);
	}
	
	
	/**
	 * @param showInHeader
	 * @param header
	 * @param inputColIndex
	 * @param groupingOrder
	 * @param calculator
	 * @param outFormat
	 */
	public DefaultCTColumn(	boolean showInHeader,
							String header, 
							int inputColIndex, 
							int groupingOrder,
							ICalculator calculator, 
							Format outFormat) {
		super(header, inputColIndex, groupingOrder, calculator, outFormat);
		this.showInHeader = showInHeader;
	}

	/**
	 * @return the showInHeader
	 */
	public boolean isShownInHeader() {
		return showInHeader;
	}

	/**
	 * @param showInHeader the showInHeader to set
	 */
	public void setShowInHeader(boolean showInHeader) {
		this.showInHeader = showInHeader;
	}
}
