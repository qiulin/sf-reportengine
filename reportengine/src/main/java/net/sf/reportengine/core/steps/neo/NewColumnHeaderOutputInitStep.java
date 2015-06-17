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
package net.sf.reportengine.core.steps.neo;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.RowProps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 *
 */
public class NewColumnHeaderOutputInitStep extends AbstractOutputInitStep<String> {

	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(NewColumnHeaderOutputInitStep.class);
	
	
	public final static String START_HEADER_ROW_TEMPLATE = "startHeaderRow.ftl";
	public final static String START_HEADER_MODEL_NAME = "rowProps"; 
	
	public final static String END_HEADER_ROW_TEMPLATE = "endHeaderRow.ftl";
	
	
	public final static String HEADER_CELL_TEMPLATE = "headerCell.ftl";
    public final static String HEADER_CELL_MODEL_NAME = "cellProps";  
    /**
     * 
     */
    public  StepResult<String> init(StepInput input){
    	LOGGER.trace("initializing the column header step: output title and column headers");
    	
    	//int outputColumnsCnt = getDataColumnsLength(input) + getGroupColumnsLength(input); 
    	
    	//output the title
    	//NewReportOutput output = getNewReportOutput(input);
//        if(getReportTitle(input) != null){
//        	output.outputTitle(new TitleProps(getReportTitle(input), outputColumnsCnt));
//        }
        
        //output the report column headers
        final int rowNumber = 0;
        //output.startHeaderRow(new RowProps(rowNumber));
        
        outputOneValue(input, START_HEADER_ROW_TEMPLATE, START_HEADER_MODEL_NAME, new RowProps(rowNumber));
        
        CellProps cellProps = null;
        List<GroupColumn> groupCols = getGroupColumns(input); 
        if(groupCols != null){
	        for (GroupColumn groupColumn : groupCols) {
				cellProps = new CellProps.Builder(groupColumn.getHeader())
										.colspan(1)
										.horizAlign(HorizAlign.CENTER)
										.rowNumber(rowNumber)
										.build();
				//output.outputHeaderCell(cellProps);
				outputOneValue(input, HEADER_CELL_TEMPLATE, HEADER_CELL_MODEL_NAME, cellProps); 
			}
        }
        
        List<DataColumn> dataCols = getDataColumns(input); 
        for (DataColumn dataColumn: dataCols) {
            cellProps = new CellProps.Builder(dataColumn.getHeader())
            						.colspan(1)
            						.horizAlign(HorizAlign.CENTER)
            						.rowNumber(rowNumber)
            						.build();
            //output.outputHeaderCell(cellProps);
            outputOneValue(input, HEADER_CELL_TEMPLATE, HEADER_CELL_MODEL_NAME, cellProps);
        }
        
        //output.endHeaderRow();
        outputNoValue(input, END_HEADER_ROW_TEMPLATE); 
        
        return StepResult.NO_RESULT; 
    }
}
