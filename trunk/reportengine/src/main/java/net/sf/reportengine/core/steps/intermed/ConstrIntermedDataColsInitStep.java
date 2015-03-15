package net.sf.reportengine.core.steps.intermed;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.reportengine.config.CrosstabData;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.SortType;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.GroupCalculator;
import net.sf.reportengine.core.steps.AbstractReportInitStep;
import net.sf.reportengine.core.steps.FlatReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

public class ConstrIntermedDataColsInitStep extends AbstractReportInitStep<List<DataColumn>>{
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConstrIntermedDataColsInitStep.class);
	
	
	public StepResult<List<DataColumn>> init(StepInput input) {
		
		CrosstabData originalCtData = (CrosstabData)input.getAlgoInput(IOKeys.CROSSTAB_DATA); 
		
		List<DataColumn> dataColumns = transformCrosstabDataIntoDataColumns(originalCtData); 
		LOGGER.debug("constructed intermediate data columns {}", dataColumns);
		return new StepResult<List<DataColumn>>(ContextKeys.INTERNAL_DATA_COLS, dataColumns);
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
	 * 
	 * @author dragos balan
	 *
	 */
	static final class IntermDataColumnFromCrosstabData implements DataColumn{
		
		/**
		 * the original cross tab data on which this data column is based
		 */
		private final CrosstabData crosstabData;
		
		/**
		 * 
		 * @param crosstabData
		 */
		public IntermDataColumnFromCrosstabData(CrosstabData crosstabData){
			this.crosstabData = crosstabData; 
		}
		
		public String getHeader() {
			return "CrosstabDataColumn"; //normally this should never be called (except when debugging reports)
		}

		public String getFormattedValue(Object value) {
			return crosstabData.getFormattedValue(value);
		}
		
		public String getFormattedTotal(Object value){
			return crosstabData.getFormattedTotal(value); 
		}

		public Object getValue(NewRowEvent newRowEvent) {
			return crosstabData.getValue(newRowEvent);
		}

		public GroupCalculator getCalculator() {
			return crosstabData.getCalculator();
		}

		public HorizAlign getHorizAlign() {
			return crosstabData.getHorizAlign(); 
		}
		
		public VertAlign getVertAlign() {
			return crosstabData.getVertAlign(); 
		}
		
		public int getSortLevel() {
			return NO_SORTING; 
		}

		public SortType getSortType() {
			return SortType.ASC; 
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "IntermDataColumnFromCrosstabData [crosstabData="
					+ crosstabData + "]";
		}
		
		
	}
}
