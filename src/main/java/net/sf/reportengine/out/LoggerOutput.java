/**
 * 
 */
package net.sf.reportengine.out;

import org.apache.log4j.Logger;

/**
 * Debug implementation for IReportOutput where all outputed values go to the log file at each end of line
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public class LoggerOutput implements IReportOutput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(LoggerOutput.class);
	
	private StringBuilder dataToLog = new StringBuilder();
	
	/**
     * empty implementation 
     */ 
    public void startRow(){
        dataToLog.delete(0, dataToLog.length());
	}
	
	public void endRow(){
		logger.debug(dataToLog);
	}
	
	public void output(CellProps cellProps) {
		dataToLog.append(" "+cellProps);
	}

	public void open() {}

	public void close() {}

}
