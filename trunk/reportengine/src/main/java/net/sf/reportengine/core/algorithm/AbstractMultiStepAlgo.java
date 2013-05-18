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
     * A list containing all the steps <code>net.sf.reportengine.algorithm.IAlgoritmStep</code>
     */
    private List<AlgorithmMainStep> mainSteps;
    
    /**
     * A list containing <code>net.sf.reportengine.algorithm.IAlgorithmInitStep</cide>s 
     * to be performed only once (at the begining of the algorithm)
     */
    private List<AlgorithmInitStep> initSteps;
    
    /**
     * A list containing <code>net.sf.reportengine.algorithm.IAlgorithmExitStep</code>s 
     * to be performed only once (at the begining of the algorithm)
     */
    private List<AlgorithmExitStep> exitSteps;
    
    /**
     * the context of the report (holding important values)
     */
    private ReportContext algorithmContext = new DefaultReportContext();
    
    /**
     * Initializes all steps lists and the algorithm context
     */
    public AbstractMultiStepAlgo(){
        this.initSteps = new LinkedList<AlgorithmInitStep>();//TODO: why linked list
        this.mainSteps = new LinkedList<AlgorithmMainStep>();
        this.exitSteps = new LinkedList<AlgorithmExitStep>();
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

    
//    protected void openInput(){
//    	algorithmContext.getInput().open();
//    }
//    
//    protected void closeInput(){
//    	algorithmContext.getInput().close();
//    }
//    
//    protected void openOutput(){
//    	algorithmContext.getOutput().open();
//    }
//    
//    protected void closeOutput(){
//    	algorithmContext.getOutput().close();
//    }
    
    public List<AlgorithmInitStep> getInitSteps(){
    	return initSteps;
    }
    
    public List<AlgorithmExitStep> getExitSteps(){
    	return exitSteps;
    }
    
    public List<AlgorithmMainStep> getMainSteps(){
    	return mainSteps;
    }
    
//    protected ReportInput getInput(){
//    	return algorithmContext.getInput();
//    }
    
    public ReportContext getContext(){
    	return algorithmContext; 
    }
}
