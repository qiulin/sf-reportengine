/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.core.steps.AbstractReportExitStep;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class IntermedSetResultsExitStep extends AbstractReportExitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AbstractExitStep#executeExit()
	 */
	@Override
	protected Map<IOKeys, Object> executeExit(Map<IOKeys, Object> inputParams) {
		Map<IOKeys, Object> result = Algorithm.EMPTY_READ_ONLY_PARAMS_MAP; 
		ReportOutput output = getReportOutput(); 
		if(output instanceof IntermediateCrosstabOutput){
			result = new EnumMap<IOKeys, Object>(IOKeys.class); 
			result.put(	IOKeys.INTERMEDIATE_OUTPUT_FILE, 
						((IntermediateCrosstabOutput)output).getSerializedOutputFile()); 
		}else{
			throw new IllegalStateException("the output found under IOKeys.INTERMEDIATE_OUTPUT_FILE is not of type IntermediateCrosstabOutput.class"); 
		}
		
		return result; 
	}

}
