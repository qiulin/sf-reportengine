/**
 * 
 */
package net.sf.reportengine.core.steps.ct;

import net.sf.reportengine.AbstractReport;
import net.sf.reportengine.CrossTabReport;
import net.sf.reportengine.config.IColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.util.CrossTabCoefficients;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.Locator;

import org.apache.log4j.Logger;

/**
 * @author dragos balan (dragos.balan@gmail.com)
 * @version $Revision$
 * $log$
 */
public class CTOutputHeaderInitStep implements IAlgorithmInitStep {
    
    /**
     * the one and only logger
     */
    private static final Logger logger = Logger.getLogger(CTOutputHeaderInitStep.class);
    
    /**
     * report title
     */
    private String reportTitle = null;
    
    /**
     * the init method requested for by IAlgorithmInitStep
     */
    public void init(IAlgorithmContext reportContext){
        logger.debug("initializing the column header step: output title and column headers");
        
        //IColumn[] configColumns = (IColumn[])reportContext.get(AbstractReport.CONTEXT_KEY_DATA_COLUMNS);
        //CrossTabCoefficients coefficients = (CrossTabCoefficients)reportContext.get(CTReportHeaderTemplateInitStep.CONTEXT_KEY_CT_COEFICIENTS);
        CtMetadata ctMetadata = (CtMetadata)reportContext.get(CrossTabReport.CONTEXT_KEY_CROSSTAB_METADATA);
        
        String[][] headerTemplate = (String[][])reportContext.get(CTReportHeaderTemplateInitStep.CONTEXT_KEY_CT_HEADER_TEMPLATE);
        
        //TODO: the following 6 rows should be optimized. Their only meaning is to compute the column count
        IDataColumn[] dataColumns = (IDataColumn[])reportContext.get(AbstractReport.CONTEXT_KEY_DATA_COLUMNS);
		IGroupColumn[] groupColumns = (IGroupColumn[])reportContext.get(AbstractReport.CONTEXT_KEY_GROUPING_COLUMNS);
        
		int colCount = dataColumns.length;
		if(groupColumns != null){
			colCount += groupColumns.length; 
		}
		
        //output the title
        IReportOutput output = (IReportOutput)reportContext.getOutput();
        if(reportTitle != null){
            output.startRow();
            CellProps titleCellProps = new CellProps(reportTitle, 
            								colCount,
            								1, 
            								ReportContent.CONTENT_REPORT_TITLE);
            output.output(titleCellProps);
            output.endRow();
        }
        
        //output the report column headers
        CellProps cellProps = null;
        int colspan;
        for (int row = 0; row < ctMetadata.getHeaderRowsCount(); row++) {
            output.startRow();
            colspan = ctMetadata.getColspanWhenTotals(row);
            for(int col=0; col < colCount; ){
               cellProps = new CellProps(headerTemplate[row][col], 
                                          colspan, 
                                          1, 
                                          ReportContent.CONTENT_COLUMN_HEADERS);
                output.output(cellProps);
                if(	Locator.SPACE.equals(headerTemplate[row][col]) 
                	|| Locator.TOTAL.equals(headerTemplate[row][col])){
                    col++;
                }else{
                    col += colspan;
                }
            }
            output.endRow();
        }
    }
}
