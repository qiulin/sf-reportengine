/*
 * Created on 30.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.filter.DataOutputFilter;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.IReportOutput;

import org.apache.log4j.Logger;

/**
 * <p>
 *  Output step used mainly on Flat reports
 * </p>
 * @author dragos balan 
 */
public class DataRowsOutputStep extends AbstractReportStep {
    
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(DataRowsOutputStep.class);
	
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
    	
    	//filter the values of the current row and check if the display is allowed
    	boolean displayCurrentRow = true;
    	List<DataOutputFilter> filterList = getDataOutputFilterList();
    	if(filterList != null){
    		for(DataOutputFilter filter: filterList){
    			if(!filter.isDisplayable(currentRow)){
    				displayCurrentRow = false;
    				break;
    			}
    		}
    	}
    	
    	//if display is allowed (not filtered) then output current row
    	if(displayCurrentRow){
    		IReportOutput output = getOutput();
    	
    		output.startRow();
        
    		CellProps  cellProperties = null;
    		for (int i = 0; i < currentRow.length; i++) {
    			cellProperties = new CellProps(	currentRow[i]);
    			output.output(cellProperties);
    		}
    		output.endRow();
    	}else{
    		if(logger.isTraceEnabled()){
    			logger.trace("the row "+Arrays.toString(currentRow)+" was filtered and it will not be displayed");
    		}
    	}
    }
}
