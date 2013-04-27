/*
 * Created on 27.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import java.util.EnumMap;

import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;

/**
 * 
 *<p>
 * this class holds important values of the current running report. You may call it "carrier" of
 * important report data between report steps.
 * 
 * For detailed information about any values within the report context please 
 * consult the documentation of each key . Also the hairs of this class should 
 * provide details to the values stored right next to the documentation of the key.
 * </p>
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public class DefaultReportContext implements ReportContext{
    
	/**
     * an enumMap containing the context data
     */
    private EnumMap<ContextKeys, Object> contextData;
    
    /**
     * the input of the report
     */
    private ReportInput input;
    
    
    /**
     * the output
     */
    private ReportOutput output;
    
    
    /**
     * constructor of the class
     */
    public DefaultReportContext(){
        contextData = new EnumMap<ContextKeys, Object>(ContextKeys.class);
    }
    
    /**
     * gets the value whithin the context having the specified identifier
     * 
     * @return the object associated with the key
     */
    public Object get(ContextKeys key){
        return contextData.get(key);
    }
    
    /**
     * @see net.sf.reportengine.core.algorithm.ReportContext#get(String)
     */
    public void set(ContextKeys key, Object value){
        contextData.put(key,value);
    }
    
    public void setInput(ReportInput in){
        this.input = in;
    }
    
    public ReportInput getInput(){
        return input;
    }
    
    public void setOutput(ReportOutput out){
        this.output = out;
    }
    
    public ReportOutput getOutput(){
        return output;
    }
}
