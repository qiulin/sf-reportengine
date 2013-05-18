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
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.out.TitleProps;
import net.sf.reportengine.util.InputKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * basic output for report title and report column header 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class ColumnHeaderOutputInitStep implements AlgorithmInitStep{
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ColumnHeaderOutputInitStep.class);
	
	/**
	 * the title of the report
	 */
    private String reportTitle;
    
    
    /**
     * 
     */
    public ColumnHeaderOutputInitStep(){
        this(null);
    }
    
    /**
     * 
     * @param title
     */
    public ColumnHeaderOutputInitStep(String title){
        this.reportTitle = title;
    }
    
    /**
     * 
     */
    public void init(Map<InputKeys, Object> algoInput, ReportContext reportContext){
    	LOGGER.trace("initializing the column header step: output title and column headers");
    	List<DataColumn> dataCols = (List<DataColumn>)algoInput.get(InputKeys.DATA_COLS);
    	List<GroupColumn> groupCols = (List<GroupColumn>)algoInput.get(InputKeys.GROUP_COLS); 
    	
    	int outputColumnsCnt = dataCols.size() + (groupCols != null ? groupCols.size() : 0); 
    	
    	//output the title
    	ReportOutput output = (ReportOutput)algoInput.get(InputKeys.REPORT_OUTPUT);
        if(reportTitle != null){
        	output.outputTitle(new TitleProps(reportTitle, outputColumnsCnt));
        }
        
        //output the report column headers
        final int rowNumber = 0;
        output.startHeaderRow(new RowProps(rowNumber));
        CellProps cellProps = null;
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
        for (DataColumn dataColumn: dataCols) {
            cellProps = new CellProps.Builder(dataColumn.getHeader())
            						.colspan(1)
            						.horizAlign(HorizAlign.CENTER)
            						.rowNumber(rowNumber)
            						.build();
            output.outputHeaderCell(cellProps);
        }
        
        output.endHeaderRow();
    }
}
