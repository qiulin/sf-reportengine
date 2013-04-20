/*
 * Created on 05.11.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.util.ContextKeys;

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
    public void init(ReportContext reportContext){
    	LOGGER.trace("initializing the column header step: output title and column headers");
    	List<DataColumn> dataCols = (List<DataColumn>)reportContext.get(ContextKeys.DATA_COLUMNS);
    	List<GroupColumn> groupCols = (List<GroupColumn>)reportContext.get(ContextKeys.GROUP_COLUMNS); 
    	
    	int outputColumnsCnt = dataCols.size() + (groupCols != null ? groupCols.size() : 0); 
    	
    	//output the title
    	IReportOutput output = (IReportOutput)reportContext.getOutput();
        if(reportTitle != null){
        	output.startRow(new RowProps(ReportContent.REPORT_TITLE, 0));
            CellProps titleCellProps = new CellProps.Builder(reportTitle)
            									.colspan(outputColumnsCnt)
            									.contentType(ReportContent.REPORT_TITLE)
            									.horizAlign(HorizAlign.CENTER)
            									.rowNumber(0)
            									.build();
            output.output(titleCellProps);
            output.endRow();
        }
        
        //output the report column headers
        final int rowNumber = 0;
        output.startRow(new RowProps(ReportContent.COLUMN_HEADER, rowNumber));
        CellProps cellProps = null;
        if(groupCols != null){
	        for (GroupColumn groupColumn : groupCols) {
				cellProps = new CellProps.Builder(groupColumn.getHeader())
										.colspan(1)
										.contentType(ReportContent.COLUMN_HEADER)
										.horizAlign(HorizAlign.CENTER)
										.rowNumber(rowNumber)
										.build();
				output.output(cellProps);
			}
        }
        for (DataColumn dataColumn: dataCols) {
            cellProps = new CellProps.Builder(dataColumn.getHeader())
            						.colspan(1)
            						.contentType(ReportContent.COLUMN_HEADER)
            						.horizAlign(HorizAlign.CENTER)
            						.rowNumber(rowNumber)
            						.build();
            output.output(cellProps);
        }
        
        output.endRow();
    }
}
