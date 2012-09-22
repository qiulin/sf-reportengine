/*
 * Created on 27.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import net.sf.reportengine.core.ReportEngineException;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmMainStep;

/**
 * <p>
 * this is the base interface for all the reports
 * </p> 
 * @author dragos balan (dragos.balan@gmail.com)
 */
public interface IAlgorithm {
    
    /**
     * sets the input of the report.
     * @param input	
     */     
    public void setIn(IAlgorithmInput input);
    
    /**
     * add an init step to the algorithm
     * @param initStep
     */
    public void addInitStep(IAlgorithmInitStep initStep);
    
    /**
     * adds a main step to the algorithm
     * @param step  the main step to be added
     */
    public void addMainStep(IAlgorithmMainStep step);
    
    /**
     * 
     * @param exitStep
     */
    public void addExitStep(IAlgorithmExitStep exitStep);
    
    /**
     * executes the report and displays it
     */
    public void executeAlgorithm() throws ReportEngineException;
    
    /**
     * sets the output of the report
     * @param out
     */
    public void setOut(IAlgorithmOutput out);
    
    
    public IAlgorithmContext getContext();
}
