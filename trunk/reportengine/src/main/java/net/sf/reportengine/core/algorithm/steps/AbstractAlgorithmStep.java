/*
 * Created on 30.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm.steps;

import net.sf.reportengine.core.algorithm.ReportContext;


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
     * default implementation for AlgorithmInitStep.init() method
     * which only sets the algorithm context  
     * 
     */
    public void init(ReportContext algoContext){
        this.algorithmContext = algoContext;        
    }
    
    /**
     * just an empty implementation for exit 
     * @see net.sf.reportengine.core.algorithm.AlgorithmMainStep#exit()
     */
    public void exit(ReportContext context) {}
    
    
    /**
     * getter for the context
     * @return
     */
    protected ReportContext getContext(){
    	return algorithmContext;
    }
    

}
