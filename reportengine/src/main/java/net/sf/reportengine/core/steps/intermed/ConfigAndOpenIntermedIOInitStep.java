/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class ConfigAndOpenIntermedIOInitStep extends AbstractInitStep {

	/**
	 * creates a new intermediary output 
	 * opens the original input 
	 * opens the newly created intermediary output
	 */
	@Override protected void executeInit() {
		//first we set the output
		IntermediateCrosstabOutput output = new IntermediateCrosstabOutput(); 
		getAlgoContext().set(ContextKeys.INTERMEDIATE_OUTPUT, output); 
		
		//then we open input and the new output
		((ReportInput)getAlgoInput().get(IOKeys.REPORT_INPUT)).open(); 
		output.open(); 
		
		addResult(IOKeys.INTERMEDIATE_OUTPUT_FILE, output.getSerializedOutputFile()); 
	}

}
