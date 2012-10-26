/*
 * Created on 05.11.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.util.ContextKeys;

import org.apache.log4j.Logger;

/**
 * basic output for report title and report column header 
 * 
 * @author dragos balan
 * @since 0.2
 */
public class ColumnHeaderOutputInitStep implements IAlgorithmInitStep{
    
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(ColumnHeaderOutputInitStep.class);
	
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
    
    public ColumnHeaderOutputInitStep(String title){
        this.reportTitle = title;
    }
    
    public void init(IReportContext reportContext){
    	logger.trace("initializing the column header step: output title and column headers");
    	IDataColumn[] dataCols = (IDataColumn[])reportContext.get(ContextKeys.CONTEXT_KEY_DATA_COLUMNS);
    	IGroupColumn[] groupCols = (IGroupColumn[])reportContext.get(ContextKeys.CONTEXT_KEY_GROUPING_COLUMNS); 
    	
    	int outputColumnsCnt = dataCols.length + (groupCols != null ? groupCols.length : 0); 
    	
    	//output the title
    	IReportOutput output = (IReportOutput)reportContext.getOutput();
        if(reportTitle != null){
        	output.startRow(new RowProps(ReportContent.CONTENT_REPORT_TITLE));
            CellProps titleCellProps = new CellProps( reportTitle, outputColumnsCnt, ReportContent.CONTENT_REPORT_TITLE);
            output.output(titleCellProps);
            output.endRow();
        }
        
        //output the report column headers
        output.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS));
        CellProps cellProps = null;
        if(groupCols != null){
	        for (IGroupColumn groupColumn : groupCols) {
				cellProps = new CellProps(groupColumn.getHeader(), 1, ReportContent.CONTENT_COLUMN_HEADERS);
				output.output(cellProps);
			}
        }
        for (IDataColumn dataColumn: dataCols) {
            cellProps = new CellProps(dataColumn.getHeader(), 1, ReportContent.CONTENT_COLUMN_HEADERS);
            output.output(cellProps);
        }
        
        output.endRow();
    }
}
