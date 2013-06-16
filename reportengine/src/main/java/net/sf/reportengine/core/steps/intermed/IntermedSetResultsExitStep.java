/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

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
	protected void executeExit() {
		ReportOutput output = getReportOutput(); 
		if(output instanceof IntermediateCrosstabOutput){
			addResult(	IOKeys.INTERMEDIATE_OUTPUT_FILE, 
						((IntermediateCrosstabOutput)output).getSerializedOutputFile()); 
		}else{
			throw new IllegalStateException("the output found under IOKeys.INTERMEDIATE_OUTPUT_FILE is not of type IntermediateCrosstabOutput.class"); 
		}
	}

}
