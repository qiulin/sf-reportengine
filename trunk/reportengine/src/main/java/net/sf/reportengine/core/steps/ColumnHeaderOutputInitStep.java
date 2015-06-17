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
package net.sf.reportengine.core.steps;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.out.TitleProps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * basic output for report title and report column header 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 * @deprecated
 */
public class ColumnHeaderOutputInitStep extends AbstractReportInitStep<String>{
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ColumnHeaderOutputInitStep.class);
	
    
    /**
     * 
     */
    public  StepResult<String> init(StepInput input){
    	LOGGER.trace("initializing the column header step: output title and column headers");
    	
    	int outputColumnsCnt = getDataColumnsLength(input) + getGroupColumnsLength(input); 
    	
    	//output the title
    	ReportOutput output = getReportOutput(input);
        if(getReportTitle(input) != null){
        	output.outputTitle(new TitleProps(getReportTitle(input), outputColumnsCnt));
        }
        
        //output the report column headers
        final int rowNumber = 0;
        output.startHeaderRow(new RowProps(rowNumber));
        CellProps cellProps = null;
        List<GroupColumn> groupCols = getGroupColumns(input); 
        if(groupCols != null){
	        for (GroupColumn groupColumn : groupCols) {
				cellProps = new CellProps.Builder(groupColumn.getHeader())
										.colspan(1)
										.horizAlign(HorizAlign.CENTER)
										.rowNumber(rowNumber)
										.build();
				output.outputHeaderCell(cellProps);
			}
        }
        
        List<DataColumn> dataCols = getDataColumns(input); 
        for (DataColumn dataColumn: dataCols) {
            cellProps = new CellProps.Builder(dataColumn.getHeader())
            						.colspan(1)
            						.horizAlign(HorizAlign.CENTER)
            						.rowNumber(rowNumber)
            						.build();
            output.outputHeaderCell(cellProps);
        }
        
        output.endHeaderRow();
        
        return StepResult.NO_RESULT; 
    }
}
