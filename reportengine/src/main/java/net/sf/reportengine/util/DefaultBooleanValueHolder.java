/**
 * 
 */
package net.sf.reportengine.util;

/**
 * @author dragos balan
 *
 */
public enum DefaultBooleanValueHolder {
	
	DEFAULT_TRUE(true, false), 
	DEFAULT_FALSE(false, false), 
	USER_REQUESTED_TRUE(true, true), 
	USER_REQUESTED_FALSE(false, true);
	
	private boolean value; 
	private boolean isUserRequested; 
	
	private DefaultBooleanValueHolder(boolean value, boolean isRequestedByUser){
		this.value = value; 
		this.isUserRequested = isRequestedByUser; 
	}
	
	public boolean getValue(){
		return value; 
	}
	
	public boolean isRequesteByUser(){
		return isUserRequested; 
	}
}
