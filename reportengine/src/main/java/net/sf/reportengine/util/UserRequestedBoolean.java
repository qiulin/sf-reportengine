/**
 * 
 */
package net.sf.reportengine.util;

/**
 * This is a utility enumeration for default boolean values which might interfere with the user's choice. 
 * Example: 
 * 		A flat report doesn't show by default the totals (showTotals = FALSE_NOT_REQUESTED_BY_USER) 
 * 		The user adds a dataColumn (with a groupCalculator) -> the report builder decides it can show totals (showTotals = TRUE_NOT_REQUESTED_BY_USER)
 * 		The user wants to hide the totals (showTotals = TRUE_REQUESTED_BY_USER)
 * 		The user adds another dataColumn with a groupCalculator -> the report builder is blocked to the previously set value
 * 		
 * @author dragos balan
 *
 */
public enum UserRequestedBoolean {
	
	//this is the default true without being requested by the user
	TRUE_NOT_REQUESTED_BY_USER(true, false), 
	
	//default false without being requested by the user
	FALSE_NOT_REQUESTED_BY_USER(false, false), 
	
	//true requested by the user
	TRUE_REQUESTED_BY_USER(true, true), 
	
	//false requested by the user
	FALSE_REQUESTED_BY_USER(false, true);
	
	private boolean value; 
	private boolean isUserRequested; 
	
	private UserRequestedBoolean(boolean value, boolean isRequestedByUser){
		this.value = value; 
		this.isUserRequested = isRequestedByUser; 
	}
	
	public boolean getValue(){
		return value; 
	}
	
	public boolean isRequestedByUser(){
		return isUserRequested; 
	}
	
	public boolean isTrue(){
		return (value == true); 
	}
	
	public boolean isFalse(){
		return (value == false); 
	}
}
