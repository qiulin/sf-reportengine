/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.util.ContextKeys;

/**
 * this step gathers all sortable data in an Comparable[] array. 
 * For each NewRowEvent it gathers the values from columns and creates the Comparable[] array.
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.9
 */
public class DetectSortableDataStep extends AbstractReportStep {

	private List<DataColumn> dataCols;
	private List<GroupColumn> groupCols;
	
	
	private List<Integer> sortableDataColsIndexes = new ArrayList<Integer>(); 
	
	
	@Override public void init(ReportContext reportContext){
		super.init(reportContext); 
		
		dataCols = getDataColumns(); 
		groupCols = getGroupingColumns(); 
		
		for (int i=0; i <dataCols.size(); i++) {
			DataColumn dataCol = dataCols.get(i);
			if(dataCol.getOrderLevel() != -1){
				sortableDataColsIndexes.add(i);
			}
		}
		
		reportContext.set(ContextKeys.SORTABLE_DATA_COLS_INDEXES, sortableDataColsIndexes); 
	}
	
	@Override public void execute(NewRowEvent rowEvent) {
		
		Object[] newRow = new Object[groupCols.size() + dataCols.size()]; 
		
		//iterate the group cols
		for (int i=0; i< groupCols.size(); i++) {
			GroupColumn groupCol = groupCols.get(i); 
			//for group cols the group level is also the order level
			Object value = groupCol.getValue(rowEvent);
			
			newRow[i] = value; 
		}
		
		for (int i=0; i< dataCols.size(); i++) {
			DataColumn dataCol = dataCols.get(i);
			
			newRow[groupCols.size()+i] = dataCol.getValue(rowEvent); 
		}
		
		//so the new row is ready for  comparisons
		getContext().set(ContextKeys.ARRANGED_FOR_SORT_ROW, newRow); 
	}
}
