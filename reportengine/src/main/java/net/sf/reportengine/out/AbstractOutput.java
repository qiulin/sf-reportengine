/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.reportengine.out;


/**
 * An abstract implementation for ReportOutput containing basic functionality for 
 * marking the opening/closing reports, null values replacement, counting rows. 
 * 
 * For better functionality it is recommended to extend one of the two children  
 * {@link AbstractByteOutput} or {@link AbstractCharOutput}
 * 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @see {@link AbstractByteOutput}, {@link AbstractCharOutput}
 * @since 0.2
 */
public abstract class AbstractOutput implements ReportOutput {
    
	/**
	 * error message displayed when open() was not used
	 */
	public final static String OUTPUT_NOT_OPEN = "Output not ready! Please call open() method.";
	
	
    /**
     * whether or not to replace null values with white spaces
     */
    private String nullsReplacement ;
    
    /**
     * isOpen flag 
     */
    private boolean isOpen = false; 
    
    
    /**
     * constructor
     */
    public AbstractOutput(){}
    
    
    /**
     * checks null & other impossible to print values
     */
    protected String purifyData(Object val) {
    	String result = val != null ? val.toString() : nullsReplacement;
        return result;
    }
    
    /**
     * getter for nulls replacement
     * @return  the replacement for null values
     */
    public String getNullsReplacement() {
        return nullsReplacement;
    }

    
    /**
     * setter for nulls replacement
     * @param nullsReplacement
     */
    public void setNullsReplacement(String nullsReplacement) {
        this.nullsReplacement = nullsReplacement;
    }

	/**
	 * use it to find if the output was open
	 * 
	 * @return	true if the report was open or false otherwise
	 */
	protected boolean isOutputOpen(){
		return isOpen; 
	}
	
	/**
	 * marks this output as being open
	 */
	protected void markAsOpen(){
		isOpen = true; 
	}
	
	/**
	 * marks this output as closed
	 */
	protected void markAsClosed(){
		isOpen = false; 
	}
	
	/**
	 * throws an exception if the report output is not open
	 */
	protected void ensureOutputIsOpen(){
		if(!isOpen) throw new ReportOutputException(OUTPUT_NOT_OPEN); 
	}
}
