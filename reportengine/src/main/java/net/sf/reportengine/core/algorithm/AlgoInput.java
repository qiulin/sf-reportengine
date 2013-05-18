/**
 * 
 */
package net.sf.reportengine.core.algorithm;

import net.sf.reportengine.util.InputKeys;

/**
 * @author dragos balan
 *
 */
public class AlgoInput {
	
	private Object value;
	private InputKeys name; 
	
	public AlgoInput(Object value, InputKeys name){
		this.value = value; 
		this.name = name; 
	}
	
	public AlgoInput(AlgoResult result){
		this.value = result.getValue(); 
		this.name = InputKeys.valueOf(result.getName()); 
	}
	
	public Object getValue(){
		return value; 
	}
	
	public InputKeys getName(){
		return name; 
	}
	
	public String toString(){
		return new StringBuilder("AlgoInput[")
					.append("name=").append(name)
					.append(", value=").append(value)
					.toString(); 
	}
}
