/*
 * Created on 30.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.out.RowProps;

/**
 * <p>
 *  Output step used mainly on Flat reports
 * </p>
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public class DataRowsOutputStep extends AbstractReportStep {
    
    /**
	 * 
     * constructor
     *
     */
    public DataRowsOutputStep(){}
    
	/**
     * execute. Constructs a cell for each value and sends it to output
     */
    public void execute(NewRowEvent rowEvent) {
    	String[] currentRow = getFormattedCellValues();
    	IReportOutput output = getOutput();
    	
		output.startRow(new RowProps(ReportContent.CONTENT_DATA));
    
		CellProps  cellProperties = null;
		for (int i = 0; i < currentRow.length; i++) {
			cellProperties = new CellProps.Builder(currentRow[i]).build();
			output.output(cellProperties);
		}
		output.endRow();
    }
}
