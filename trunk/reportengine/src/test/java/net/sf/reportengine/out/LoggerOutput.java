/**
 * 
 */
package net.sf.reportengine.out;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Debug implementation for ReportOutput where all outputed values go to the log file at each end of line
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public class LoggerOutput implements ReportOutput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerOutput.class);
	
	private StringBuilder dataToLog = new StringBuilder();
	
	public void open() {}
	
	public void startReport(ReportProps props){
		LOGGER.debug("start report"); 
	}
	
	public void startHeaderRow(RowProps props){
		startDataRow(props); 
	}
	
	public void outputHeaderCell(CellProps cellProps){
		outputDataCell(cellProps); 
	}
	
	public void endHeaderRow(){
		endDataRow(); 
	}
	
	public void outputTitle(TitleProps titleProps){
		LOGGER.debug("Report Title: {}", titleProps.getTitle()); 
	}
	
	/**
     * empty implementation 
     */ 
    public void startDataRow(RowProps rowProperties){
        dataToLog.delete(0, dataToLog.length());
	}
	
	public void endDataRow(){
		LOGGER.debug("{}",dataToLog);
	}
	
	public void outputDataCell(CellProps cellProps) {
		dataToLog.append(" "+cellProps);
	}

	public void endReport(){
		LOGGER.debug("end report"); 
	}

	public void close() {}

}
