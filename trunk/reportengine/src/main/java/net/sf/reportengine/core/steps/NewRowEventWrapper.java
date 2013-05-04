/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.io.Serializable;

import net.sf.reportengine.core.algorithm.NewRowEvent;

/**
 * 
 * helper class for serialization. 
 * It contains a isLast member helping with de-serialization (detect end of the objectStream)
 * 
 * @author dragos
 *
 */
public final class NewRowEventWrapper implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1530225702536578647L;
	
	/**
	 * 
	 */
	private boolean isLast = false; 
	
	/**
	 * 
	 */
	private NewRowEvent newRowEvent; 
	
	
	/**
	 * 
	 * @param newRow
	 * @param isLast
	 */
	public NewRowEventWrapper(NewRowEvent newRow, boolean isLast){
		this.newRowEvent = newRow; 
		this.isLast = isLast; 
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isLast(){
		return isLast; 
	}
	
	/**
	 * 
	 * @return
	 */
	public NewRowEvent getNewRowEvent(){
		return newRowEvent; 
	}
	
	
	public boolean equals(Object another){
		boolean result = false; 
		if(another instanceof NewRowEventWrapper){
			NewRowEventWrapper anotherAsNREW = (NewRowEventWrapper)another; 
			return this.isLast() == anotherAsNREW.isLast() && this.newRowEvent.equals(anotherAsNREW.getNewRowEvent()); 
		}
		
		return result; 
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder("NewRowEventWrapper[isLast="); 
		builder.append(isLast).append(", newRowEvent=").append(newRowEvent.toString());
		return builder.toString(); 
	}
}
