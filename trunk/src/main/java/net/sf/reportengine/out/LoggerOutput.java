/**
 * 
 */
package net.sf.reportengine.out;

import org.apache.log4j.Logger;

/**
 * Debug implementation for IReportOutput where all outputed values go to the log file at each end of line
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 *
 */
public class LoggerOutput extends AbstractOutput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(LoggerOutput.class);
	
	private StringBuffer dataToLog = new StringBuffer();
	
	/**
     * empty implementation 
     */ 
    public void startRow(){
        super.startRow();
        dataToLog.delete(0, dataToLog.length());
	}
	
	public void endRow(){
		logger.debug(dataToLog);
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.AbstractOutput#output(net.sf.reportengine.out.CellProps)
	 */
	@Override
	public void output(CellProps cellProps) {
		dataToLog.append(" "+cellProps);
	}

}
