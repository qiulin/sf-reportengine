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
package net.sf.reportengine.core.steps.intermed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.CrosstabData;
import net.sf.reportengine.config.CrosstabHeaderRow;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.steps.AbstractReportInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.core.steps.intermed.ConstrIntermedDataColsInitStep.IntermDataColumnFromCrosstabData;
import net.sf.reportengine.core.steps.intermed.ConstrIntermedGrpColsInitStep.IntermGroupColFromCtDataCol;
import net.sf.reportengine.core.steps.intermed.ConstrIntermedGrpColsInitStep.IntermGroupColFromHeaderRow;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 *
 * @deprecated replace this with ConstrIntermedDataColsInitStep and ConstrIntermedGrpColsInitStep
 */
public class ConfigIntermedColsInitStep extends AbstractReportInitStep<List<GroupColumn>>{
	
	
	
	public StepResult<List<GroupColumn>> init(StepInput stepInput) {
		List<DataColumn> originalCtDataCols = getDataColumns(stepInput); 
		List<GroupColumn> originalCtGroupingCols = getGroupColumns(stepInput); 
		List<CrosstabHeaderRow> originalCtHeaderRows = 
				(List<CrosstabHeaderRow>)stepInput.getAlgoInput(IOKeys.CROSSTAB_HEADER_ROWS);
		
		
		List<GroupColumn> newGroupCols = transformGroupingCrosstabConfigInFlatReportConfig(
					originalCtGroupingCols, 
					originalCtDataCols, 
					originalCtHeaderRows); 
		
		//getAlgoContext().set(ContextKeys.INTERNAL_GROUP_COLS, newGroupCols);		
		
		CrosstabData originalCtData = (CrosstabData)stepInput.getAlgoInput(IOKeys.CROSSTAB_DATA); 
		//getAlgoContext().set(ContextKeys.INTERNAL_DATA_COLS, 
		//		transformCrosstabDataIntoDataColumns(originalCtData));
		
		return new StepResult<List<GroupColumn>>(ContextKeys.INTERNAL_GROUP_COLS, newGroupCols); 
		//this is just to comply with the return type ( normally it should return two values)
	}
	
	/**
	 * 
	 * @param crosstabData
	 * @return
	 */
	private List<DataColumn> transformCrosstabDataIntoDataColumns(CrosstabData crosstabData){
		List<DataColumn> intermediateDataCols = new ArrayList<DataColumn>(1);
		intermediateDataCols.add(new IntermDataColumnFromCrosstabData(crosstabData)); 
		return intermediateDataCols; 
	}
	
	/**
	 * transforms the original grouping + data + header columns into intermediate group columns
	 * 
	 * 1. from 0 to original groupCols.length we copy the original group columns
	 * 2. from groupCols.length to groupCols.lenght + dataCols.length we copy construct group columns from data columns
	 * 3. then we copy the header rows (of course transformed as groupCols)
	 * 
	 * @param originalCtGroupingCols
	 * @param originalCtDataCols
	 * @param originalCtHeaderRows
	 * @return
	 */
	List<GroupColumn> transformGroupingCrosstabConfigInFlatReportConfig(	List<? extends GroupColumn> originalCtGroupingCols, 
																			List<? extends DataColumn> originalCtDataCols, 
																			List<? extends CrosstabHeaderRow> originalCtHeaderRows){
		
		int originalGroupColsLength = originalCtGroupingCols != null ? originalCtGroupingCols.size(): 0;
		int originalDataColsLength = originalCtDataCols != null ? originalCtDataCols.size() : 0 ; 
		
//		LOGGER.debug("transforming grouping crosstab config into flat intermediary report: ");
//		LOGGER.debug("origCtGroupingCols={}", originalCtGroupingCols);
//		LOGGER.debug("originalCtDataRows={}", originalCtDataCols);
//		LOGGER.debug("originalCtHeaderRows={}",originalCtHeaderRows);
		
		int intermedGroupColsLength = originalGroupColsLength + originalDataColsLength + originalCtHeaderRows.size() -1;
		List<GroupColumn> result = new ArrayList<GroupColumn>(intermedGroupColsLength);
		
		//from 0 to original groupCols.length we copy the original group columns
		if(originalGroupColsLength > 0){
			//System.arraycopy(originalCtGroupingCols, 0, result, 0, originalCtGroupingCols.size());
			for(int i=0; i<originalCtGroupingCols.size(); i++){
				//result[i] = originalCtGroupingCols.get(i);
				result.add(originalCtGroupingCols.get(i));
			}
		}
		
		//from groupCols.length to groupCols.lenght + dataCols.length we copy construct group columns from data columns
		for(int i=0; i < originalDataColsLength; i++){
			//result[originalGroupColsLength+i] = new IntermGroupColFromCtDataCol(originalCtDataCols.get(i), originalGroupColsLength+i);
			result.add(new IntermGroupColFromCtDataCol(originalCtDataCols.get(i), originalGroupColsLength+i));
		}
		
		//then we copy the header rows (of course transformed as groupCols)
		//we don't need any grouping for the last header row (that's why we have headerRows.length-1 below
		for (int i = 0; i < originalCtHeaderRows.size()-1; i++) {
			//result[originalGroupColsLength+originalDataColsLength+i] =
			result.add(
					new IntermGroupColFromHeaderRow(	originalCtHeaderRows.get(i), 
														originalGroupColsLength+originalDataColsLength+i));
		}
		
		return result; 
	}
	
	
	
	
}