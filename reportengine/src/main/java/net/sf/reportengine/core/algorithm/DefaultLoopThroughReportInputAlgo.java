package net.sf.reportengine.core.algorithm;

import java.util.Map;

import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.util.IOKeys;


/**
 * This is basically a {@link LoopThroughReportInputAlgo} which identifies the input from inputParams map by the IOKey.REPORT_INPUT key. 
 * As any other {@link LoopThroughReportInputAlgo} this algo performes the following actions: 
 * 1. opens the report input 
 * 2. loops through the report input and calls the steps.execute methods
 * 3. closes the input
 * 
 * @author dragos balan
 *
 */
public class DefaultLoopThroughReportInputAlgo extends LoopThroughReportInputAlgo {

	@Override
	protected ReportInput buildReportInput(Map<IOKeys, Object> inputParams) {
		return (ReportInput) inputParams.get(IOKeys.REPORT_INPUT); 
	}

}
