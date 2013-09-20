/*
 * Created on Aug 8, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Use this output method when you need to output to different formats </p>
 *  
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class OutputDispatcher implements ReportOutput{
    
	/**
	 * the list of different outputs
	 */
    private List<ReportOutput> outputersList;
    
    /**
     * creates an array of ReportOutput(s)
     */
    public OutputDispatcher(){
        outputersList = new ArrayList<ReportOutput>(3);
    }

    /**
     * calls the same method for all configured outputs
     */
    public void open(){
        for(ReportOutput output: outputersList){
            output.open();
        }
    }
    
    /**
     * calls the same method for all configured outputs
     */
    public void outputTitle(TitleProps titleProps){
    	for(ReportOutput output: outputersList){
            output.outputTitle(titleProps);
        }
    }
    
    /**
     * calls the same method for all configured outputs
     */
    public void startReport(ReportProps reportProps){
    	for(ReportOutput output: outputersList){
            output.startReport(reportProps);
        }
    }
    
    /**
     * calls the same method for all configured outputs
     */
    public void endReport(){
    	for(ReportOutput output: outputersList){
            output.endReport();
        }
    }
    
    /**
     * calls the same method for all configured outputs
     */
    public void startHeaderRow(RowProps rowProperties){
    	for(ReportOutput output: outputersList){
            output.startHeaderRow(rowProperties);
        }
    }
    
    /**
     * calls the same method for all configured outputs
     */
    public void endHeaderRow(){
    	for(ReportOutput output: outputersList){
            output.endHeaderRow();
        }
    }
    
    /**
     * calls the same method for all configured outputs
     */
    public void outputHeaderCell(CellProps cellProps){
    	for(ReportOutput output: outputersList){
            output.outputHeaderCell(cellProps);
        }
    }
    
    /**
     * calls the same method for all configured outputs
     */
    public void close(){
    	for(ReportOutput output: outputersList){
            output.close();
        }
    }
    
    /**
     * calls the same method for all configured outputs
     */
    public void startDataRow(RowProps rowProperties){
    	for(ReportOutput output: outputersList){
            output.startDataRow(rowProperties);
        }
    }
    
    /**
     * calls the same method for all configured outputs
     */
    public void endDataRow(){
    	for(ReportOutput output: outputersList){
            output.endDataRow();
        }
    }
    
    /**
     * calls the same method for all configured outputs
     */
    public void outputDataCell(CellProps cellProps){
    	for(ReportOutput output: outputersList){
            output.outputDataCell(cellProps);
        }
    }
    
    /**
     * registers the given report output
     * 
     * @param out the report output
     */
    public void registerOutput(ReportOutput out){
        this.outputersList.add(out);
    }
    
    /**
     * true if any output has been registered 
     * @return
     */
    public boolean hasOutputters(){
        return outputersList.size() > 0;
    }
}
