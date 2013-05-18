/*
 * Created on 30.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm.steps;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoInput;
import net.sf.reportengine.core.algorithm.AlgoResult;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.util.InputKeys;


/**
 * abstract implementation for the IAlgorithmStep providing 
 * an algorithmContext and default implementations for init(), exit() methods.
 * 
 * @author dragos balan(dragos.balan@gmail.com)
 */
public abstract class AbstractAlgorithmStep implements AlgorithmMainStep {
    
    /**
     * this is a reference to the report context
     */
    private ReportContext algorithmContext;
    
    /**
     * 
     */
    private Map<InputKeys, Object> algoInputAsMap; 
    
    /**
     * default implementation for AlgorithmInitStep.init() method
     * which only sets the algorithm context  
     * 
     */
    public void init(Map<InputKeys, Object> algoInput, ReportContext algoContext){
        this.algorithmContext = algoContext;    
        this.algoInputAsMap = algoInput; 
    }
    
    /**
     * just an empty implementation for exit 
     * @see net.sf.reportengine.core.algorithm.AlgorithmMainStep#exit()
     */
    public void exit(Map<InputKeys,Object> algoInput, ReportContext context) {}
    
    
    /**
     * getter for the context
     * @return
     */
    protected ReportContext getContext(){
    	return algorithmContext;
    }
    
    
    protected Map<InputKeys, Object> getInput(){
    	return algoInputAsMap; 
    }
    
    
    public Map<String, Object> getResultsMap(){
    	return null; 
    }

}
