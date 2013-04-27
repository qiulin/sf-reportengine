/*
 * Created on 27.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import java.util.List;

import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmMainStep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This is a basic implementation for <code>IReportEngine</code>
 * and its main purpose it's to make things simpler for the classes 
 * derived from it
 * </p>
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2 
 */
public class OneLoopAlgorithm extends AbstractAlgorithm {
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OneLoopAlgorithm.class);	
    
    
    /**
     * constructor
     * @param context   the report context
     */
    public OneLoopAlgorithm(){
        super();
    }    
    
    
    /**
     * implementation for net.sf.reportengine.core.IReportEngine.execute();
     */
    public void executeAlgorithm() {
    	LOGGER.trace("algorithm executing one iteration ");
        
    	if(getContext().getInput() == null){
    		throw new RuntimeException("Null input exception");
        }
                     
        //opening input
        openInput();
        
        openOutput();
            
        //execution of the init steps
        executeInitSteps();
            
        executeMainSteps();
        
        //calling the exit for all registered steps
        executeExitSteps();
        
        closeOutput();
        
        closeInput();
    } 
    
    /**
     * execution of init method for each init step
     */
    protected void executeInitSteps() {
    	ReportContext context = getContext();
    	List<AlgorithmInitStep> initSteps = getInitSteps();
    	
        for(AlgorithmInitStep initStep: initSteps){
            initStep.init(context);
        } 
    } 
    
    /**
     * 2. for each input row of data executes the execute method
     * 3. calls the exit method for each main step
     */
    protected void executeMainSteps() {
    	ReportContext context = getContext();
    	List<AlgorithmMainStep> mainSteps = getMainSteps();
    	
    	//call init for each step
        for(AlgorithmMainStep mainStep: mainSteps){
            mainStep.init(context);
        } 
        
        //iteration through input data (row by row)
        while(context.getInput().hasMoreRows()){
        	
            //get the current data row 
            Object[] currentRow = context.getInput().nextRow();
                
            //then we pass the dataRow through all the report steps
            for(AlgorithmMainStep algoStep: mainSteps){
            	algoStep.execute(new NewRowEvent(currentRow));
            }
        }
        
        //call exit
        for(AlgorithmMainStep mainStep: mainSteps){
           mainStep.exit(context);
        }
    }
    
    /**
     * calls the exit method
     */
    protected void executeExitSteps(){
    	List<AlgorithmExitStep> exitSteps = getExitSteps();
    	for(AlgorithmExitStep exitStep: exitSteps){
    	   exitStep.exit(getContext());
    	}    
    } 
}
