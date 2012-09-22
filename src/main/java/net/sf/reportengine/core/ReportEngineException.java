/*
 * Created on 01.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core;

public class ReportEngineException extends Exception {
    
    
    public ReportEngineException(String message){
        super(message);
    }
    
    public ReportEngineException(Throwable cause){
        super(cause);
    }
    
    public ReportEngineException(String message, Throwable cause){
        super(message, cause);
    }
    
    

}
