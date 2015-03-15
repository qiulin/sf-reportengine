/*
 * Created on 27.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmMainStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This multi step algorithm performs the following operations : 
 *  1. opens the input
 *  2. loops through the reportInput and executes the algorithm steps
 *  3. closes the input
 * </p>
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2 
 */
public abstract class OpenLoopCloseInputAlgo extends AbstractMultiStepAlgo {
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenLoopCloseInputAlgo.class);	
    
	/**
	 * builds the report input from the input parameters
	 * 
	 * @param inputParams the map of input parameters
	 * @return	the input stream which will be looped
	 */
	protected abstract ReportInput buildReportInput(Map<IOKeys, Object> inputParams); 
    
    
    public Map<IOKeys, Object> execute(Map<IOKeys, Object> inputParams) {
    	LOGGER.debug("creating the context");
    	AlgoContext context = new DefaultAlgorithmContext();
    	
    	LOGGER.debug("opening report input");
    	ReportInput reportInput = buildReportInput(inputParams); 
    	reportInput.open(); 
    	
    	LOGGER.debug("start looping");
        Map<IOKeys, Object> result = new EnumMap<IOKeys, Object>(IOKeys.class); 
    	
        //execution of the init steps
        for(AlgorithmInitStep initStep: getInitSteps()){
        	StepResult stepResult = initStep.init(new StepInput(inputParams, context));
        	if(!StepResult.NO_RESULT.equals(stepResult)){
        		if(!ContextKeys.SKIP_CONTEXT_KEY.equals(stepResult.getKey())){
        			context.set(stepResult.getKey(), stepResult.getValue());
        		}
        		if(!IOKeys.NO_KEY.equals(stepResult.getIOKey())){
        			result.put(stepResult.getIOKey(), stepResult.getValue()); 
        		}
        	}
        }
            
        executeMainSteps(inputParams, reportInput, context);
        
        //calling the exit for all registered steps
        //result.putAll(executeExitSteps(inputParams, context));
        for(AlgorithmExitStep exitStep: getExitSteps()){
        	StepResult stepResult = exitStep.exit(new StepInput(inputParams, context)); 
        	if(!StepResult.NO_RESULT.equals(stepResult)){
        		if(!ContextKeys.SKIP_CONTEXT_KEY.equals(stepResult.getKey())){
        			context.set(stepResult.getKey(), stepResult.getValue());
        		}
        		if(!IOKeys.NO_KEY.equals(stepResult.getIOKey())){
        			result.put(stepResult.getIOKey(), stepResult.getValue()); 
        		}
        	}
        }
        
        result.putAll(extractResultsFromSteps());
        
        LOGGER.debug("end loop. Closing algorithm input...");
        reportInput.close(); 
        
        return result; 
    } 
    
    
    /**
     * 2. for each input row of data executes the execute method
     * 3. calls the exit method for each main step
     */
    protected void executeMainSteps(Map<IOKeys, Object> inputParams, ReportInput reportInput, AlgoContext context) {
    	List<AlgorithmMainStep> mainSteps = getMainSteps();
    	//ReportInput reportInput = (ReportInput)getContext().get(ContextKeys.LOCAL_REPORT_INPUT);
    	
    	//call init for each step
        for(AlgorithmMainStep mainStep: mainSteps){
            mainStep.init(inputParams, context);
        } 
        
        //iteration through input data (row by row)
        while(reportInput.hasMoreRows()){
        	
            //get the current data row 
            List<Object> currentRow = reportInput.nextRow();
            LOGGER.trace("executing algo steps for input row {}", currentRow);
            
            //then we pass the dataRow through all the report steps
            for(AlgorithmMainStep algoStep: mainSteps){
            	algoStep.execute(new NewRowEvent(currentRow));
            }
        }
        
        //call exit
        for(AlgorithmMainStep mainStep: mainSteps){
           mainStep.exit();
        }
    }
    
    /**
     * calls the exit method
     */
//    protected Map<IOKeys, Object> executeExitSteps(Map<IOKeys, Object> inputParams, AlgoContext context){
//    	List<AlgorithmExitStep> exitSteps = getExitSteps();
//    	
//    	Map<IOKeys, Object> result = new EnumMap<IOKeys, Object>(IOKeys.class); 
//    	for(AlgorithmExitStep exitStep: exitSteps){
//    		result.putAll(exitStep.exit(null));
//    	}
//    	return result; 
//    } 
    
    /**
     * 
     * @return
     */
    private Map<IOKeys, Object> extractResultsFromSteps(){
    	Map<IOKeys, Object> result = new HashMap<IOKeys, Object>(); 
    	
    	for (AlgorithmMainStep step : getMainSteps()) {
			if(step.getResultsMap() != null){
				result.putAll(step.getResultsMap()); 
			}
		}
    	
    	return result; 
    }
    
//    private ReportInput getReportInput(Map<IOKeys, Object> inputParams){
//    	return (ReportInput)inputParams.get(reportInputParamKey);
//    }
}
