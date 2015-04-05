/**
 * 
 */
package net.sf.reportengine.core.steps.neo;

import net.sf.reportengine.core.steps.AbstractReportInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.out.neo.NewReportOutput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractOutputInitStep<U> extends AbstractReportInitStep<U> {

	/**
	 * 
	 */
	public <T> void  outputOneValue(StepInput stepInput, String templateName, String rootModelName, T value){
		getNewReportOutput(stepInput).output(templateName, rootModelName, value); 
	}
	
	public void outputNoValue(StepInput stepInput, String templateName){
		getNewReportOutput(stepInput).output(templateName); 
	}
	
	/**
     * getter for the output of the report
     * @return
     */
    public NewReportOutput getNewReportOutput(StepInput stepInput){
    	//return (NewReportOutput)stepInput.getContextParam(ContextKeys.NEW_LOCAL_REPORT_OUTPUT); 
    	return (NewReportOutput)stepInput.getAlgoInput(IOKeys.NEW_REPORT_OUTPUT); 
    }

}
