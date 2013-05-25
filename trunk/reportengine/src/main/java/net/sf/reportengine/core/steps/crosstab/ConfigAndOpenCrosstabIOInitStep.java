/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import net.sf.reportengine.core.steps.AbstractCrosstabInitStep;
import net.sf.reportengine.in.IntermediateCrosstabReportInput;
import net.sf.reportengine.in.ReportInputException;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class ConfigAndOpenCrosstabIOInitStep extends AbstractCrosstabInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AbstractInitStep#executeInit()
	 */
	@Override
	protected void executeInit() {
		try{
			File previousAlgoSerializedOutput = (File)getAlgoInput().get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			IntermediateCrosstabReportInput newReportInput = new IntermediateCrosstabReportInput(
					new FileInputStream(previousAlgoSerializedOutput)); 
			
			getAlgoInput().put(IOKeys.REPORT_INPUT, newReportInput); //TODO: one shouldn't be able to modify 
			//the input keys. This has to be removed 
			
			
			newReportInput.open(); 
			getReportOutput().open(); 
		}catch(FileNotFoundException fnfExc){
			throw new ReportInputException(fnfExc); 
		}
	}
}
