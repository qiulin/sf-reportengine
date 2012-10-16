/*
 * Created on 27.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import java.util.HashMap;

import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.out.IReportOutput;

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
 * @author dragos balan (dragos.balan@gmail.com)
 */
public class DefaultAlgoContext implements IAlgorithmContext{
    
    /**
     * used for performance : this is the initial prediction about the 
     * content of the contextData
     */
    private final static int INITIAL_CAPACITY = 20;
    
    /**
     * a hashmap containing the context data
     */
    private HashMap<String, Object> contextData;
    
    /**
     * the input of the report
     */
    private IReportInput input;
    
    
    /**
     * the output
     */
    private IReportOutput output;
    
    
    /**
     * constructor of the class
     */
    public DefaultAlgoContext(){
        contextData = new HashMap<String, Object>(INITIAL_CAPACITY);
    }
    
    /**
     * gets the value whithin the context having the specified identifier
     * 
     * @return the object associated with the key
     */
    public Object get(String key){
        return contextData.get(key);
    }
    
    /**
     * @see net.sf.reportengine.core.algorithm.IAlgorithmContext#get(String)
     */
    public void set(String key, Object value){
        contextData.put(key,value);
    }
    
    public void setInput(IReportInput in){
        this.input = in;
    }
    
    public IReportInput getInput(){
        return input;
    }
    
    public void setOutput(IReportOutput out){
        this.output = out;
    }
    
    public IReportOutput getOutput(){
        return output;
    }
}
