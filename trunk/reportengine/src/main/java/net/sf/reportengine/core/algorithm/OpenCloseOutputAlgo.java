/**
 * 
 */
package net.sf.reportengine.core.algorithm;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public abstract class OpenCloseOutputAlgo extends OpenLoopCloseInputAlgo {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenCloseOutputAlgo.class);	
	
	
	protected abstract ReportOutput buildReportOutput(Map<IOKeys, Object> inputParams);
	
	/**
	 * 
	 */
	public Map<IOKeys, Object> execute(Map<IOKeys, Object> inputParams) {
		ReportOutput output = buildReportOutput(inputParams); 
		LOGGER.debug("opening the output"); 
		output.open();
		
		Map<IOKeys, Object> result = super.execute(inputParams); 
		
		LOGGER.debug("closing the output");
		output.close();
		
		return result; 
	}

}
