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
import net.sf.reportengine.in.ReportInput;
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
public abstract class LoopThroughReportInputAlgo extends AbstractMultiStepAlgo {
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoopThroughReportInputAlgo.class);	
    
	/**
	 * builds the report input from the input parameters
	 * 
	 * @param inputParams the map of input parameters
	 * @return	the input stream which will be looped
	 */
	protected abstract ReportInput buildReportInput(Map<IOKeys, Object> inputParams); 
    
    
    public Map<IOKeys, Object> execute(Map<IOKeys, Object> inputParams) {
    	LOGGER.trace("opening report input");
    	ReportInput reportInput = buildReportInput(inputParams); 
    	reportInput.open(); 
    	
    	LOGGER.trace("start looping algorithm");
        Map<IOKeys, Object> result = new EnumMap<IOKeys, Object>(IOKeys.class); 
    	
        //execution of the init steps
        result.putAll(executeInitSteps(inputParams));
            
        executeMainSteps(inputParams, reportInput);
        
        //calling the exit for all registered steps
        result.putAll(executeExitSteps(inputParams));
        
        result.putAll(extractResultsFromSteps());
        
        LOGGER.trace("closing algorithm input");
        reportInput.close(); 
        
        return result; 
    } 
    
    
    /**
     * execution of init method for each init step
     */
    protected Map<IOKeys, Object> executeInitSteps(Map<IOKeys, Object> inputParams) {
    	List<AlgorithmInitStep> initSteps = getInitSteps();
    	
    	Map<IOKeys, Object> result = new EnumMap<IOKeys, Object>(IOKeys.class);
    	
        for(AlgorithmInitStep initStep: initSteps){
            result.putAll(initStep.init(inputParams, getContext()));
        } 
        return result; 
    } 
    
    /**
     * 2. for each input row of data executes the execute method
     * 3. calls the exit method for each main step
     */
    protected void executeMainSteps(Map<IOKeys, Object> inputParams, ReportInput reportInput) {
    	List<AlgorithmMainStep> mainSteps = getMainSteps();
    	//ReportInput reportInput = (ReportInput)getContext().get(ContextKeys.LOCAL_REPORT_INPUT);
    	
    	//call init for each step
        for(AlgorithmMainStep mainStep: mainSteps){
            mainStep.init(inputParams, getContext());
        } 
        
        //iteration through input data (row by row)
        while(reportInput.hasMoreRows()){
        	
            //get the current data row 
            List<Object> currentRow = reportInput.nextRow();
                
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
    protected Map<IOKeys, Object> executeExitSteps(Map<IOKeys, Object> inputParams){
    	List<AlgorithmExitStep> exitSteps = getExitSteps();
    	
    	Map<IOKeys, Object> result = new EnumMap<IOKeys, Object>(IOKeys.class); 
    	for(AlgorithmExitStep exitStep: exitSteps){
    		result.putAll(exitStep.exit(inputParams, getContext()));
    	}
    	
    	return result; 
    } 
    
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
