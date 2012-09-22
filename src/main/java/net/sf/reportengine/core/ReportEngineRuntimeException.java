/*
 * Created on 04.12.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core;

public class ReportEngineRuntimeException extends RuntimeException {

    public ReportEngineRuntimeException(){
        super();
    }
    
    public ReportEngineRuntimeException(Throwable source){
        super(source);
    }
    
    public ReportEngineRuntimeException(String message){
        super(message);
    }
    
    public ReportEngineRuntimeException(String message, Throwable cause){
        super(message, cause);
    }
    

}
