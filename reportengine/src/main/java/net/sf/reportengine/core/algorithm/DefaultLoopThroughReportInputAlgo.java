package net.sf.reportengine.core.algorithm;

import java.util.Map;

import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.IOKeys;


/**
 * This is basically a {@link OpenLoopCloseInputAlgo} which identifies the input from inputParams map by the IOKey.REPORT_INPUT key. 
 * As any other {@link OpenLoopCloseInputAlgo} this algo performes the following actions: 
 * 1. opens the report input 
 * 2. loops through the report input and calls the steps.execute methods
 * 3. closes the input
 * 
 * @author dragos balan
 *
 */
public class DefaultLoopThroughReportInputAlgo extends OpenLoopCloseInputAlgo {

	@Override
	protected TableInput buildReportInput(Map<IOKeys, Object> inputParams) {
		return (TableInput) inputParams.get(IOKeys.REPORT_INPUT); 
	}

}
