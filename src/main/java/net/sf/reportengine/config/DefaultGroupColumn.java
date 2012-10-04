/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;

/**
 * 
 * Grouping Column based on a single input column
 * 
 * @author dragos balan
 * @since 0.4
 */
public class DefaultGroupColumn extends AbstractGroupColumn {
	
	/**
	 * the index of the input column
	 */
	private int inputColumnIndex; 
	
	public DefaultGroupColumn(){
		this(0);
	}
	
	public DefaultGroupColumn(int inputColumnIndex){
		this("Column "+inputColumnIndex, inputColumnIndex, 0);
	}
	
	public DefaultGroupColumn(int inputColumnIndex, int groupingLevel){
		this("Column "+inputColumnIndex, inputColumnIndex, groupingLevel);
	}
		
	public DefaultGroupColumn(String header, int inputColumnIndex, int groupingLevel){
		this(header, inputColumnIndex, groupingLevel, null);
	}
	
	public DefaultGroupColumn(String header, int inputColumnIndex, int groupingLevel, Format formatter){
		super(header, groupingLevel, formatter);
		setInputColumnIndex(inputColumnIndex);
	}
	

	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.IGroupColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		return newRowEvent.getInputDataRow()[inputColumnIndex];
	}

	public int getInputColumnIndex() {
		return inputColumnIndex;
	}

	public void setInputColumnIndex(int inputColumnIndex) {
		this.inputColumnIndex = inputColumnIndex;
	}

}
