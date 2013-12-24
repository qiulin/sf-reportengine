package net.sf.reportengine.config;

import net.sf.reportengine.core.calc.GroupCalculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract implementation for DataColumn. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3 
 * @see DataColumn, DefaultDataColumn
 */
public abstract class AbstractDataColumn implements DataColumn {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDataColumn.class);
	
	/**
	 *  the column header
	 */
	private String header; 
	
	/**
	 * the formatter for the values of this column
	 */
	private String valuesFormatter; 
	
	/**
	 * the formatter for the totals in this column
	 */
	private String totalFormatter; 
	
	/**
	 * the calculator of this column
	 */
	private GroupCalculator calculator; 
	
	/**
	 * the horizontal alignment
	 */
	private HorizAlign horizAlign; 
	
	/**
	 * the vertical align
	 */
	private VertAlign vertAlign; 
	
	/**
	 * the sorting level 
	 */
	private int sortLevel = NO_SORTING;
	
	/**
	 * 
	 */
	private SortType sortType = SortType.ASC; 
	
	/**
	 * 
	 * @param header
	 */
	public AbstractDataColumn(String header){
		this(header, null); 
	}
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 */
	public AbstractDataColumn(String header, GroupCalculator calculator){
		this(header, calculator, null); 
	}
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param valuesFormatter
	 */
	public AbstractDataColumn(	String header, 
								GroupCalculator calculator, 
								String valuesFormatter){
		this(header, calculator, valuesFormatter, HorizAlign.CENTER);
	}
	
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param valuesFormatter
	 * @param horizAlign
	 */
	public AbstractDataColumn(	String header, 
								GroupCalculator calculator, 
								String valuesFormatter, 
								HorizAlign horizAlign){
		this(header, calculator, valuesFormatter, horizAlign, NO_SORTING); 
	}
	
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param valuesFormatter
	 * @param horizAlign
	 * @param sortLevel
	 */
	public AbstractDataColumn(	String header, 
								GroupCalculator calculator, 
								String valuesFormatter, 
								HorizAlign horizAlign, 
								int sortLevel){
		this(header, calculator, valuesFormatter, horizAlign, VertAlign.MIDDLE, sortLevel);  
	}
	
	
	/**
	 * 
	 * @param header		the header of this column
	 * @param calculator	the calculator
	 * @param valuesFormatter		the valuesFormatter 
	 * @param horizAlign	the horizontal alignment
	 * @param vertAlign		the vertical alignment
	 * @param sortLevel	the sorting level
	 */
	public AbstractDataColumn(	String header, 
								GroupCalculator calculator, 
								String valuesFormatter, 
								HorizAlign horizAlign,
								VertAlign vertAlign, 
								int sortLevel){
		this(header, calculator, valuesFormatter, horizAlign, vertAlign, sortLevel, SortType.ASC);  
	}
	
	
	/**
	 * 
	 * @param header		the header of this column
	 * @param calculator	the calculator
	 * @param valuesFormatter		the valuesFormatter 
	 * @param horizAlign	the horizontal alignment
	 * @param vertAlign		the vertical alignment
	 * @param sortLevel	the sorting level 
	 * @param sortType		the sorting type (asc, desc)
	 */
	public AbstractDataColumn(	String header, 
								GroupCalculator calculator, 
								String valuesFormatter,
								HorizAlign horizAlign,
								VertAlign vertAlign, 
								int sortLevel, 
								SortType sortType){
		this(header, calculator, valuesFormatter, null, horizAlign, vertAlign, sortLevel, sortType); 
	}
	
	
	/**
	 * 
	 * @param header		the header of this column
	 * @param calculator	the calculator
	 * @param valuesFormatter		the valuesFormatter 
	 * @param totalsFormatter		the valuesFormatter 
	 * @param horizAlign	the horizontal alignment
	 * @param vertAlign		the vertical alignment
	 * @param sortLevel	the sorting level 
	 * @param sortType		the sorting type (asc, desc)
	 */
	public AbstractDataColumn(	String header, 
								GroupCalculator calculator, 
								String valuesFormatter,
								String totalsFormatter, 
								HorizAlign horizAlign,
								VertAlign vertAlign, 
								int sortLevel, 
								SortType sortType){
		setHeader(header); 
		setValuesFormatter(valuesFormatter); 
		setTotalsFormatter(totalsFormatter); 
		setCalculator(calculator); 
		setHorizAlign(horizAlign); 
		setVertAlign(vertAlign); 
		setSortLevel(sortLevel);
		setSortType(sortType); 
	}
	
	/**
	 * getter for the column header
	 */
	public String getHeader() {
		return header;
	}
	
	/**
	 * setter for this column's header
	 * @param header
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	
	
	/**
	 * returns the formatted value
	 */
	public String getFormattedValue(Object value) {
		String result = "";
		if(value != null){
			if(valuesFormatter != null){
				//LOGGER.trace("formatting "+value+" as class "+value.getClass().getSimpleName());
				result = String.format(valuesFormatter, value);
			}else{
				result = value.toString();
			}
		}
		return result; 
	}
	
	/**
	 * 
	 */
	public String getFormattedTotal(Object totalValue){
		String result = "";
		if(totalValue != null){
			if(totalFormatter != null){
				result = String.format(totalFormatter, totalValue); 
			}else{
				result = totalValue.toString(); 
			}
		}
		return result; 
	}
	
	
	
	/**
	 * getter for this column's calculator (if any)
	 */
	public GroupCalculator getCalculator() {
		return calculator;
	}
	
	/**
	 * 
	 * @param calculator
	 */
	public void setCalculator(GroupCalculator calculator) {
		this.calculator = calculator;
	}
	
	
	/**
	 * sets a valuesFormatter for this column. 
	 * All values of this column will be formatted using this valuesFormatter when 
	 * the report engine calls {@link #getFormattedValue(Object)}
	 * 
	 * @param valuesFormatter
	 */
	public void setValuesFormatter(String formatter) {
		this.valuesFormatter = formatter;
	}
	
	/**
	 * getter for the valuesFormatter
	 * @return
	 */
	public String getValuesFormatter(){
		return valuesFormatter; 
	}
	
	
	/**
	 * sets a formatter for totals displayed on this column. 
	 * All values of this column will be formatted when 
	 * the report engine calls {@link #getFormattedTotal(Object)}
	 * 
	 * @param valuesFormatter
	 */
	public void setTotalsFormatter(String formatter) {
		this.totalFormatter = formatter;
	}
	
	/**
	 * getter for the valuesFormatter
	 * @return
	 */
	public String getTotalsFormatter(){
		return totalFormatter; 
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
	
	/**
	 * The order level is the 
	 * @return
	 */
	public int getSortLevel(){
		return sortLevel;
	}
	
	/**
	 * 
	 * @param sortLevel
	 */
	public void setSortLevel(int sortLevel){
		this.sortLevel = sortLevel; 
	}
	
	/**
	 * 
	 * @param sortType
	 */
	public void setSortType(SortType sortType){
		this.sortType = sortType; 
	}
	
	/**
	 * Asc or Desc
	 * @return
	 */
	public SortType getSortType(){
		return sortType; 
	}
}
