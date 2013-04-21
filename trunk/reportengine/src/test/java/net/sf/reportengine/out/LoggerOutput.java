/**
 * 
 */
package net.sf.reportengine.out;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Debug implementation for IReportOutput where all outputed values go to the log file at each end of line
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public class LoggerOutput implements IReportOutput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoggerOutput.class);
	
	private StringBuilder dataToLog = new StringBuilder();
	
	
	public void outputTitle(TitleProps titleProps){
		LOGGER.debug(titleProps.getTitle()); 
	}
	
	/**
     * empty implementation 
     */ 
    public void startRow(RowProps rowProperties){
        dataToLog.delete(0, dataToLog.length());
	}
	
	public void endRow(){
		LOGGER.debug("{}",dataToLog);
	}
	
	public void output(CellProps cellProps) {
		dataToLog.append(" "+cellProps);
	}

	public void open() {}

	public void close() {}

}
