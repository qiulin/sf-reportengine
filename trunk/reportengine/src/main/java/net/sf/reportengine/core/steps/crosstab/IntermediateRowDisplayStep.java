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
package net.sf.reportengine.core.steps.crosstab;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.RowProps;

/**
 * This is used only for debug 
 * 
 * @author dragos balan 
 * 
 */
public class IntermediateRowDisplayStep extends AbstractCrosstabStep<String, String, String> {
	
	
	public StepResult<String> init(StepInput stepInput){
		return StepResult.NO_RESULT; 
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.AbstractReportStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public StepResult<String> execute(NewRowEvent newRowEvent, StepInput stepInput) {
		int groupingLevel = getGroupingLevel(stepInput); 
		
		if(groupingLevel >= 0){
			//if grouping level changed
			if(groupingLevel < getGroupColumns(stepInput).size()){
				//if grouping level changed for the GROUPING COLUMNS
				displayIntermediateDebugInfo(stepInput, getIntermediateRow(stepInput));
			}else{
				//if grouping level changed for the crosstabHeaderRows 
				
			}
		}else{
			//grouping level not changed
			
		}
		
		return StepResult.NO_RESULT; 
	}
	
	
	public StepResult<String> exit(StepInput stepInput){
		displayIntermediateDebugInfo(stepInput, getIntermediateRow(stepInput));
		return StepResult.NO_RESULT; 
	}
	
	private void displayIntermediateDebugInfo(StepInput stepInput, IntermediateReportRow intermediateRow){
		ReportOutput output = getReportOutput(stepInput); 
		
		output.startDataRow(new RowProps());
		output.outputDataCell(new CellProps.Builder("Intermediate row:").build());
		for (IntermediateDataInfo element : intermediateRow.getIntermComputedDataList().getDataList()) {
			output.outputDataCell(new CellProps.Builder(element.toString()).build());
		}		
		output.endDataRow(); 
	}
}
