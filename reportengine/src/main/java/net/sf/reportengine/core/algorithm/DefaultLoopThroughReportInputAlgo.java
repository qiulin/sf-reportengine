package net.sf.reportengine.core.algorithm;

import java.util.Map;

import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.util.IOKeys;

public class DefaultLoopThroughReportInputAlgo extends LoopThroughReportInputAlgo {

	@Override
	protected ReportInput buildReportInput(Map<IOKeys, Object> inputParams) {
		return (ReportInput) inputParams.get(IOKeys.REPORT_INPUT); 
	}

}
