/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package net.sf.reportengine.out;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test implementation for ReportOutput where all output values go to the log file at each end of line
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
