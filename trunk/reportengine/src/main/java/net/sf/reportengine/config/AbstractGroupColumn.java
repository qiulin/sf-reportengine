/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;


/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public abstract class AbstractGroupColumn implements GroupColumn {
	
	/**
	 * 
	 */
	private int groupingLevel; 
	
	/**
	 * 
	 */
	private String header; 
	
	/**
	 * 
	 */
	private Format formatter; 
	
	/**
	 * 
	 */
	private HorizAlign horizAlign; 
	
	/**
	 * 
	 */
	private VertAlign vertAlign;
	
	/**
	 * 
	 */
	private boolean showDuplicates; 
	
	/**
	 * 
	 */
	private SortType sortType = SortType.NONE; 
	
	/**
	 * 
	 * @param header
	 * @param groupingLevel
	 * @param formatter
	 * @param horizAlign
	 * @param showDuplicates
	 */
	public AbstractGroupColumn(	String header, 
								int groupingLevel, 
								Format formatter, 
								HorizAlign horizAlign, 
								boolean showDuplicates){
		this(header, groupingLevel, formatter, horizAlign, VertAlign.MIDDLE, showDuplicates); 
	}
	
	/**
	 * 
	 * @param header
	 * @param groupingLevel
	 * @param formatter
	 * @param horizAlign
	 * @param vertAlign
	 * @param showDuplicates
	 */
	public AbstractGroupColumn(	String header, 
								int groupingLevel, 
								Format formatter, 
								HorizAlign horizAlign, 
								VertAlign vertAlign, 
								boolean showDuplicates){
		this(header, groupingLevel, formatter, horizAlign, vertAlign, showDuplicates, SortType.NONE/*no sorting*/); 
	}
	
	
	/**
	 * 
	 * @param header
	 * @param groupingLevel
	 * @param formatter
	 * @param horizAlign
	 * @param vertAlign
	 * @param showDuplicates
	 * @param sortType
	 */
	public AbstractGroupColumn(	String header, 
								int groupingLevel, 
								Format formatter, 
								HorizAlign horizAlign, 
								VertAlign vertAlign, 
								boolean showDuplicates, 
								SortType sortType){
		setHeader(header);
		setGroupingLevel(groupingLevel);
		setFormatter(formatter);
		setHorizAlign(horizAlign); 
		setVertAlign(vertAlign); 
		setShowDuplicates(showDuplicates); 
		setSortType(sortType); 
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.GroupColumn#getGroupingLevel()
	 */
	public int getGroupingLevel() {
		return groupingLevel;
	}
	
	/**
	 * 
	 * @param groupingLevel
	 */
	public void setGroupingLevel(int groupingLevel) {
		this.groupingLevel = groupingLevel;
	}

	/**
	 * 
	 */
	public String getHeader() {
		return header;
	}
	
	/**
	 * 
	 * @param header
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	
	/**
	 * 
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
	 * 
	 * @return
	 */
	public Format getFormatter() {
		return formatter;
	}
	
	/**
	 * 
	 * @param formatter
	 */
	public void setFormatter(Format formatter) {
		this.formatter = formatter;
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
	 * 
	 */
	public boolean showDuplicates(){
		return showDuplicates; 
	}
	
	/**
	 * 
	 * @param flag
	 */
	public void setShowDuplicates(boolean flag){
		this.showDuplicates = flag; 
	}
	
	/**
	 * 
	 */
	public SortType getSortType(){
		return sortType; 
	}
	
	/**
	 * 
	 * @param sortType
	 */
	public void setSortType(SortType sortType){
		this.sortType = sortType; 
	}
}