/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author balan
 *
 */
public class IntermOriginalDataColsList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -759637117853620406L;
	
	
	private List<Object> dataValues; 
	
	public IntermOriginalDataColsList(){
		this.dataValues = new ArrayList<Object>();
	}
	
	public void addDataColumnValue(Object dataValue){
		dataValues.add(dataValue); 
	}
	
	public void empty(){
		dataValues.clear(); 
	}
	
	public List<Object> getDataValues(){
		return dataValues; 
	}
	
	public boolean equals(Object another){
		boolean result = false; 
		if(another instanceof IntermOriginalDataColsList){
			IntermOriginalDataColsList anotherAsIntermDataColumnList = (IntermOriginalDataColsList)another; 
			result = dataValues.equals(anotherAsIntermDataColumnList.getDataValues()); 
		}
		return result; 
	}
	
	public String toString(){
		StringBuilder result = new StringBuilder("IntermOriginalCtData["); 
		result.append(dataValues.toString());
		result.append("]"); 
		return result.toString(); 
	}
}
