package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.calc.Calculator;

/**
 * Abstract implementation for DataColumn. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3 
 * @see DataColumn, DefaultDataColumn
 */
public abstract class AbstractDataColumn implements DataColumn {
	
	/**
	 *  the column header
	 */
	private String header; 
	
	/**
	 * the formatter
	 */
	private Format formatter; 
	
	/**
	 * the calculator of this column
	 */
	private Calculator calculator; 
	
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
	public AbstractDataColumn(String header, Calculator calculator){
		this(header, calculator, null); 
	}
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param formatter
	 */
	public AbstractDataColumn(	String header, 
								Calculator calculator, 
								Format formatter){
		this(header, calculator, formatter, HorizAlign.CENTER);
	}
	
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param formatter
	 * @param horizAlign
	 */
	public AbstractDataColumn(	String header, 
								Calculator calculator, 
								Format formatter, 
								HorizAlign horizAlign){
		this(header, calculator, formatter, horizAlign, NO_SORTING); 
	}
	
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param formatter
	 * @param horizAlign
	 * @param sortLevel
	 */
	public AbstractDataColumn(	String header, 
								Calculator calculator, 
								Format formatter, 
								HorizAlign horizAlign, 
								int sortLevel){
		this(header, calculator, formatter, horizAlign, VertAlign.MIDDLE, sortLevel);  
	}
	
	
	/**
	 * 
	 * @param header		the header of this column
	 * @param calculator	the calculator
	 * @param formatter		the formatter 
	 * @param horizAlign	the horizontal alignment
	 * @param vertAlign		the vertical alignment
	 * @param sortLevel	the sorting level
	 */
	public AbstractDataColumn(	String header, 
								Calculator calculator, 
								Format formatter, 
								HorizAlign horizAlign,
								VertAlign vertAlign, 
								int orderLevel){
		this(header, calculator, formatter, horizAlign, vertAlign, orderLevel, SortType.ASC);  
	}
	
	
	/**
	 * 
	 * @param header		the header of this column
	 * @param calculator	the calculator
	 * @param formatter		the formatter 
	 * @param horizAlign	the horizontal alignment
	 * @param vertAlign		the vertical alignment
	 * @param sortLevel	the sorting level 
	 * @param sortType		the sorting type (asc, desc)
	 */
	public AbstractDataColumn(	String header, 
								Calculator calculator, 
								Format formatter, 
								HorizAlign horizAlign,
								VertAlign vertAlign, 
								int sortLevel, 
								SortType sortType){
		setHeader(header); 
		setFormatter(formatter); 
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
			if(formatter != null){
				result = formatter.format(value);
			}else{
				result = value.toString();
			}
		}
		return result; 
	}
	
	/**
	 * getter for this column's calculator (if any)
	 */
	public Calculator getCalculator() {
		return calculator;
	}
	
	/**
	 * 
	 * @param calculator
	 */
	public void setCalculator(Calculator calculator) {
		this.calculator = calculator;
	}
	
	
	/**
	 * sets a formatter for this column. 
	 * All values of this column will be formatted using this formatter when 
	 * the report engine calls {@link #getFormattedValue(Object)}
	 * 
	 * @param formatter
	 */
	public void setFormatter(Format formatter) {
		this.formatter = formatter;
	}
	
	/**
	 * getter for the formatter
	 * @return
	 */
	public Format getFormatter(){
		return formatter; 
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
