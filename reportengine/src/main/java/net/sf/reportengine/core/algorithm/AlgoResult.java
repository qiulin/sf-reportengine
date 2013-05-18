/**
 * 
 */
package net.sf.reportengine.core.algorithm;

/**
 * @author dragos
 *
 */
public class AlgoResult {
	
	private Object value;
	private String name; 
	
	public AlgoResult(Object value, String name){
		this.value = value; 
		this.name = name; 
	}
	
	public Object getValue(){
		return value; 
	}
	
	public String getName(){
		return name; 
	}
	
	public String toString(){
		return new StringBuilder("AlgoResult[")
					.append("name=").append(name)
					.append(", value=").append(value)
					.toString(); 
	}
}
