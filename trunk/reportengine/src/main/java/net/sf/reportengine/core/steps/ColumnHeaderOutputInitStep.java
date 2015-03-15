/*
 * Created on 05.11.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.out.TitleProps;
import net.sf.reportengine.util.IOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * basic output for report title and report column header 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
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
