/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class IntermOriginalGroupValuesList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5509029486650665830L;
	
	private List<Object> groupValues; 
	
	
	public IntermOriginalGroupValuesList(){
		groupValues = new ArrayList<Object>(); 
	}
	
	public void addGroupValue(Object grpValue){
		groupValues.add(grpValue); 
	}
	
	public void empty(){
		groupValues.clear(); 
	}
	
	public List<Object> getGroupValues(){
		return groupValues; 
	}
	
	public Object getLastGroupValue(){
		return groupValues.get(groupValues.size()-1);
	}
	
	public boolean equals(Object another){
		boolean result = false; 
		if(another instanceof IntermOriginalGroupValuesList){
			IntermOriginalGroupValuesList anotherAsIntermCtGroupList = (IntermOriginalGroupValuesList)another; 
			result = groupValues.equals(anotherAsIntermCtGroupList.getGroupValues()); 
		}
		return result; 
	}
	
	public String toString(){
		StringBuilder result = new StringBuilder("IntermOriginalGroupValuesList["); 
		result.append(groupValues.toString());
		result.append("]"); 
		return result.toString(); 
	}
	
}
