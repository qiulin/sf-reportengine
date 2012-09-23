/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;


/**
 * @author Administrator
 *
 */
public abstract class AbstractGroupColumn implements IGroupColumn {
	
	
	private int groupingLevel; 
	private String header; 
	private Format formatter; 
	
	
	
	public AbstractGroupColumn(	String header, 
									int groupingLevel, 
									Format formatter){
		setHeader(header);
		setGroupingLevel(groupingLevel);
		setFormatter(formatter);
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.IGroupColumn#getGroupingLevel()
	 */
	public int getGroupingLevel() {
		return groupingLevel;
	}
	
	
	public void setGroupingLevel(int groupingLevel) {
		this.groupingLevel = groupingLevel;
	}

	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

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

	public Format getFormatter() {
		return formatter;
	}

	public void setFormatter(Format formatter) {
		this.formatter = formatter;
	}

}
