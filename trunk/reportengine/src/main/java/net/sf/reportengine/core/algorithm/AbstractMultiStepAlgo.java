/**
 * 
 */
package net.sf.reportengine.core.algorithm;

import java.util.LinkedList;
import java.util.List;

import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmMainStep;

/**
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public abstract class AbstractMultiStepAlgo extends AbstractAlgo implements MultiStepAlgo {
    
    /**
     * A list containing <code>net.sf.reportengine.algorithm.IAlgorithmInitStep</cide>s 
     * to be performed only once (at the begining of the algorithm)
     */
    private List<AlgorithmInitStep> initSteps = new LinkedList<AlgorithmInitStep>();//TODO: why linked list;
    
    /**
     * A list containing all the steps <code>net.sf.reportengine.algorithm.IAlgoritmStep</code>
     */
    private List<AlgorithmMainStep> mainSteps = new LinkedList<AlgorithmMainStep>();
    
    /**
     * A list containing <code>net.sf.reportengine.algorithm.IAlgorithmExitStep</code>s 
     * to be performed only once (at the begining of the algorithm)
     */
    private List<AlgorithmExitStep> exitSteps = new LinkedList<AlgorithmExitStep>();;
    
    /**
     * the context of the report (holding important values)
     */
    private AlgoContext algorithmContext = new DefaultAlgorithmContext();
    
    
    /**
     * default empty 
     */
    public AbstractMultiStepAlgo(){
    	
    }
    
    /**
     * adds a new step to the algorithm
     * @param newStep   the new step to be added to the report algorithm
     */
    public void addMainStep(AlgorithmMainStep newStep){
        mainSteps.add(newStep);        
    }
    
    /**
     * adds a new init step to the algorithm
     * @param initStep  the step to be added
     */
    public void addInitStep(AlgorithmInitStep initStep){
        initSteps.add(initStep);
    }
    
    /**
     * adds a new exit step to the algorithm
     * @param exitStep  the step to be added
     */
    public void addExitStep(AlgorithmExitStep exitStep){
        exitSteps.add(exitStep);
    }

    public List<AlgorithmInitStep> getInitSteps(){
    	return initSteps;
    }
    
    public List<AlgorithmExitStep> getExitSteps(){
    	return exitSteps;
    }
    
    public List<AlgorithmMainStep> getMainSteps(){
    	return mainSteps;
    }
    
    public AlgoContext getContext(){
    	return algorithmContext; 
    }
}
