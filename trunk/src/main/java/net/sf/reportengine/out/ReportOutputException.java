/*
 * Created on Aug 7, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.core.ReportEngineRuntimeException;

public class ReportOutputException extends ReportEngineRuntimeException{
    
    public ReportOutputException(String message){
        super(message);
    }
    
    public ReportOutputException(Throwable cause){
        super(cause);
    }
    
    public ReportOutputException(String message, Throwable cause){
        super(message, cause);
    }

}
