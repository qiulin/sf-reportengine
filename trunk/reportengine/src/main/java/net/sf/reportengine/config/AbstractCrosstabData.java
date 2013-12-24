/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.calc.GroupCalculator;


/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public abstract class AbstractCrosstabData implements CrosstabData {
	
	/**
	 * 
	 */
	private String valuesFormatter; 
	
	/**
	 * 
	 */
	private String totalsFormatter; 
	
	/**
	 * 
	 */
	private GroupCalculator calculator; 
	
	/**
	 * the horizontal alignment for this crosstab data
	 */
	private HorizAlign horizAlign; 
	
	/**
	 * the vertical alignment for this crosstab data
	 */
	private VertAlign vertAlign; 
	
	/**
	 * 
	 * @param calc
	 * @param formatter
	 * @param horizAlign
	 */
	public AbstractCrosstabData(GroupCalculator calc, 
								String valuesFormatter, 
								HorizAlign horizAlign){
		this(calc, valuesFormatter, horizAlign, VertAlign.MIDDLE); 
	}
	
	/**
	 * 
	 * @param calc
	 * @param valuesFormatter
	 * @param horizAlign
	 * @param vertAlign
	 */
	public AbstractCrosstabData(GroupCalculator calc, 
								String  valuesFormatter, 
								HorizAlign horizAlign, 
								VertAlign vertAlign){
		this(calc, valuesFormatter, null, horizAlign, vertAlign); 
	}
	
	
	/**
	 * 
	 * @param calc
	 * @param formatter
	 * @param horizAlign
	 * @param vertAlign
	 */
	public AbstractCrosstabData(GroupCalculator calc, 
								String  valuesFormatter, 
								String totalsFormatter, 
								HorizAlign horizAlign, 
								VertAlign vertAlign){
		setCalculator(calc);
		setValuesFormatter(valuesFormatter);
		setTotalsFormatter(totalsFormatter); 
		setHorizAlign(horizAlign); 
		setVertAlign(vertAlign); 
	}
	
	/**
	 * 
	 */
	public String getFormattedValue(Object unformattedValue) {
		String result = "";
		if(unformattedValue != null){
			if(valuesFormatter != null){
				result = String.format(valuesFormatter, unformattedValue);
			}else{
				result = unformattedValue.toString();
			}
		}
		return result; 
	}
	
	/**
	 * 
	 */
	public String getFormattedTotal(Object unformattedValue) {
		String result = "";
		if(unformattedValue != null){
			if(totalsFormatter != null){
				result = String.format(totalsFormatter, unformattedValue);
			}else{
				result = unformattedValue.toString();
			}
		}
		return result; 
	}
	
	
	public String getValuesFormatter() {
		return valuesFormatter;
	}

	public void setValuesFormatter(String formatter) {
		this.valuesFormatter = formatter;
	}
	
	public String getTotalsFormatter() {
		return totalsFormatter;
	}

	public void setTotalsFormatter(String formatter) {
		this.totalsFormatter = formatter;
	}
	
	
	public GroupCalculator getCalculator() {
		return calculator;
	}

	public void setCalculator(GroupCalculator calc) {
		this.calculator = calc;
	}

	/**
	 * @return the horizAlign
	 */
	public HorizAlign getHorizAlign() {
		return horizAlign;
	}

	/**
	 * @param horizAlign the horizAlign to set
	 */
	public void setHorizAlign(HorizAlign horizAlign) {
		this.horizAlign = horizAlign;
	}
	
	/**
	 * @return the vertical alignment
	 */
	public VertAlign getVertAlign() {
		return vertAlign;
	}

	/**
	 * @param vertAlign the vertical alignment
	 */
	public void setVertAlign(VertAlign vertAlign) {
		this.vertAlign = vertAlign;
	}
}