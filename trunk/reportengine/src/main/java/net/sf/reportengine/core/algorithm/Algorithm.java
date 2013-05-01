/*
 * Created on 27.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmMainStep;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;

/**
 * <p>
 * 	this is the base interface for all report algorithm
 * </p> 
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public interface Algorithm {
    
    /**
     * sets the input of the report.
     * @param input	
     */     
    public void setIn(ReportInput input);
    
    /**
     * add an init step to the algorithm
     * @param initStep
     */
    public void addInitStep(AlgorithmInitStep initStep);
    
    /**
     * adds a main step to the algorithm
     * @param step  the main step to be added
     */
    public void addMainStep(AlgorithmMainStep step);
    
    /**
     * 
     * @param exitStep
     */
    public void addExitStep(AlgorithmExitStep exitStep);
    
    /**
     * executes the report and displays it
     */
    public void execute();
    
    /**
     * sets the output of the report
     * @param out
     */
    public void setOut(ReportOutput out);
    
    /**
     * getter for the context
     * @return
     */
    public ReportContext getContext();
}
