package net.sf.reportengine.core.steps.crosstab;

import java.util.List;

import net.sf.reportengine.config.CrosstabHeaderRow;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.DefaultDistinctValuesHolder;
import net.sf.reportengine.util.DistinctValuesHolder;
import net.sf.reportengine.util.IOKeys;


/**
 * Mainly this step detects the distinct values appearing on crosstabHeadersRows.
 * Another task of this step is to construct the intermediate crosstab data info object 
 * containing the value of the crosstabData and the position of the crosstabData 
 * relative to the crosstab header rows. Few details about this array below: 
 *
 * this is an array holding the coordinates for the current crosstabData 
 * relative to the rows of the header. For instance, let's assume we have the following 
 * row header: 
 * 		level 0: key=Region		distinctValues= North, South, East, West
 * 		level 1: key=Sex		distinctValues=	Males, Females
 * 
 * and let's assume the current row is : Argentina, East, Females, 100 
 * (where Argentina is on a group column and North and Females are declared on headerRows, 100 - crosstab data) 
 * 
 * for this row the  relative position to the row header will be : 
 * 	index 0: 2 ( because East is the third in the Region distinct values)
 *  index 1: 1 ( because Females is the second in the Sex distinct values)
 *  
 *  so the array will look like [2,1]
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class DistinctValuesDetectorStep extends AbstractCrosstabStep<DistinctValuesHolder, IntermediateDataInfo, String> {
	
	
	/**
	 * the distinct values holder
	 */
	private DistinctValuesHolder distinctValuesHolder = null; 
	
	
    public StepResult<DistinctValuesHolder> init(StepInput stepInput){
        List<CrosstabHeaderRow> headerRows = getCrosstabHeaderRows(stepInput);
        distinctValuesHolder = new DefaultDistinctValuesHolder(headerRows);
        //getAlgoContext().set(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER, distinctValuesHolder);
        //addResult(IOKeys.DISTINCT_VALUES_HOLDER, distinctValuesHolder); 
        return new StepResult<DistinctValuesHolder>(	ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER, 
        													distinctValuesHolder, 
        													IOKeys.DISTINCT_VALUES_HOLDER); 
    }
	
	/**
	 * 
	 */
	public StepResult<IntermediateDataInfo> execute(NewRowEvent newRowEvent, StepInput stepInput) {
		List<CrosstabHeaderRow> headerRows = getCrosstabHeaderRows(stepInput);
		
		//first we take care of the distinct values that might occur 
		int indexAfterInsertion = -1; 
		int[] currDataValueRelativePositionToHeaderValues = new int[headerRows.size()]; 
		
		for (int i = 0; i < headerRows.size(); i++) {
			CrosstabHeaderRow headerRow = headerRows.get(i);
			
			//add value even if it's not a different value we call this method 
			//for getting the index of the value in the distinct values array
			indexAfterInsertion = distinctValuesHolder.addValueIfNotExist(i, headerRow.getValue(newRowEvent));
			
			//once we have the position we add it in the relative position array
			currDataValueRelativePositionToHeaderValues[i] = indexAfterInsertion; 
		}
		
		IntermediateDataInfo intermDataInfo = new IntermediateDataInfo(getCrosstabData(stepInput).getValue(newRowEvent), 
																		currDataValueRelativePositionToHeaderValues); 
//		getAlgoContext().set(ContextKeys.INTERMEDIATE_CROSSTAB_DATA_INFO, 
//						new IntermediateDataInfo(getCrosstabData(stepInput).getValue(newRowEvent), 
//												currDataValueRelativePositionToHeaderValues)); 
		return new StepResult<IntermediateDataInfo>(ContextKeys.INTERMEDIATE_CROSSTAB_DATA_INFO, intermDataInfo); 
	}
	
	public StepResult<String> exit(StepInput stepInput) {
		return StepResult.NO_RESULT;
	}
	
	
}