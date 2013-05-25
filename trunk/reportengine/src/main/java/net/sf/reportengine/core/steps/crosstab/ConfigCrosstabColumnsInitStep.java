/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.SecondProcessDataColumn;
import net.sf.reportengine.config.SecondProcessDataColumnFromOriginalDataColumn;
import net.sf.reportengine.config.SecondProcessGroupColumn;
import net.sf.reportengine.config.SecondProcessTotalColumn;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.core.steps.AbstractCrosstabInitStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.CtMetadata;

/**
 * @author dragos balan
 *
 */
public class ConfigCrosstabColumnsInitStep extends AbstractCrosstabInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AbstractInitStep#executeInit()
	 */
	@Override
	protected void executeInit() {
		
		List<DataColumn> newDataCols = constructDataColumnsForSecondProcess(getCrosstabMetadata(), 
																			getDataColumns(), 
																			getShowTotals(), 
																			getShowGrandTotal());
		getAlgoContext().set(ContextKeys.INTERNAL_DATA_COLS, newDataCols); 
		
		List<GroupColumn> newGroupCols = constructGroupColumnsForSecondProcess(getGroupColumns()); 
		getAlgoContext().set(ContextKeys.INTERNAL_GROUP_COLS, newGroupCols); 
	}

	/**
	 * creates a list of group columns for the second report based on the original group columns
	 * 
	 * @param originalGroupCols
	 * @return	a list of group columns necessary to the second processing
	 */
	protected List<GroupColumn> constructGroupColumnsForSecondProcess(List<GroupColumn> originalGroupCols){
		List<GroupColumn> result = null; 
		if(originalGroupCols != null && originalGroupCols.size() > 0){
			result = new ArrayList<GroupColumn>(originalGroupCols.size());
			for (GroupColumn originalGroupColumn : originalGroupCols) {
				result.add(new SecondProcessGroupColumn(originalGroupColumn));
			}
		}
		return result; 
	}
	
	/**
	 * creates a list of DataColumn objects from 
	 * 
	 * 1. original data columns 
	 * 2. columns needed for displaying the values under the header values ( computed form crosstab-data) 
	 * 3. (if needed ) columns needed to display the totals and grand total 
	 * 
	 * @param crosstabMetadata
	 * @param originalDataColumns
	 * @param hasTotals
	 * @param hasGrandTotal
	 * @return	a list data columns necessary to the second process
	 */
	protected List<DataColumn> constructDataColumnsForSecondProcess(	CtMetadata crosstabMetadata, 
																		List<DataColumn> originalDataColumns, 
																		boolean hasTotals, 
																		boolean hasGrandTotal){
		int dataColsCount = crosstabMetadata.getDataColumnCount(); 
		int headerRowsCount = crosstabMetadata.getHeaderRowsCount(); 
		
		List<DataColumn> resultDataColsList = new ArrayList<DataColumn>();
		
		//first we add the original data columns (those declared by the user in his configuration)
		for(int i=0; i < originalDataColumns.size(); i++){
			resultDataColsList.add(new SecondProcessDataColumnFromOriginalDataColumn(originalDataColumns.get(i), i));
		}

		// then we construct the columns 
		for(int column=0; column < dataColsCount; column++){
		
			//construct total columns 
			if(hasTotals){
				//we start bottom to top (from last header row -1 to first header row) 
				for(int row=headerRowsCount-2; row >= 0; row--){
					int colspan = crosstabMetadata.getColspanForLevel(row); 
				
					if(column != 0 && (column % colspan)==0){
						int[] positionForCurrentTotal = new int[row+1]; //new int[headerRowsCount-1];
					
						for(int j=0; j < positionForCurrentTotal.length; j++){
							positionForCurrentTotal[j] = ((column-1) / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
						}
		
						resultDataColsList.add(
								new SecondProcessTotalColumn(	positionForCurrentTotal, 
																Calculators.SUM, 
																null, 
																"Total column="+column+ ",colspan= "+colspan));
					}
				}//end for
			}//end if has totals

			//data columns coming from data columns
			int[] positionForCurrentColumn = new int[headerRowsCount];
			for(int j=0; j < headerRowsCount; j++){
				positionForCurrentColumn[j] = (column / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
			}
		
			resultDataColsList.add(new SecondProcessDataColumn(positionForCurrentColumn, Calculators.SUM, null)); 
		}//end for columns
		
		//at the end we add one more total 
		if(hasTotals){
			//final total columns
			for(int row=headerRowsCount-2; row >= 0; row--){
				int colspan = crosstabMetadata.getColspanForLevel(row); 
			
				if(dataColsCount!= 0 && (dataColsCount % colspan)==0){
					int[] positionForCurrentTotal = new int[row+1];
			
					for(int j=0; j < positionForCurrentTotal.length; j++){
						positionForCurrentTotal[j] = ((dataColsCount-1) / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
					}
					resultDataColsList.add(new SecondProcessTotalColumn(positionForCurrentTotal, Calculators.SUM, null, "Total column="+(dataColsCount)+ ",colspan= "+colspan));
				}
			}
		}//end if has totals

		// .. and finally the grand total
		if(hasGrandTotal){
			resultDataColsList.add(new SecondProcessTotalColumn(null, Calculators.SUM, null, "Grand Total")); 
		}

		return resultDataColsList; 
	}
}
