/*
 * Created on Aug 8, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * dispatches the output to the outputters
 * </p> 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.3
 */
public class OutputDispatcher implements ReportOutput{
    
    private List<ReportOutput> outputtersList;
    
    public OutputDispatcher(){
        outputtersList = new ArrayList<ReportOutput>(3);
    }

    public void open(){
        for(ReportOutput output: outputtersList){
            output.open();
        }
    }
    
    public void outputTitle(TitleProps titleProps){
    	for(ReportOutput output: outputtersList){
            output.outputTitle(titleProps);
        }
    }
    
    public void startReport(ReportProps reportProps){
    	for(ReportOutput output: outputtersList){
            output.startReport(reportProps);
        }
    }
    
    public void endReport(){
    	for(ReportOutput output: outputtersList){
            output.endReport();
        }
    }
    
    
    public void startHeaderRow(RowProps rowProperties){
    	for(ReportOutput output: outputtersList){
            output.startHeaderRow(rowProperties);
        }
    }
    
    public void endHeaderRow(){
    	for(ReportOutput output: outputtersList){
            output.endHeaderRow();
        }
    }
    
    public void outputHeaderCell(CellProps cellProps){
    	for(ReportOutput output: outputtersList){
            output.outputHeaderCell(cellProps);
        }
    }
    
    public void close(){
    	for(ReportOutput output: outputtersList){
            output.close();
        }
    }
    
    public void startDataRow(RowProps rowProperties){
    	for(ReportOutput output: outputtersList){
            output.startDataRow(rowProperties);
        }
    }
    
    public void endDataRow(){
    	for(ReportOutput output: outputtersList){
            output.endDataRow();
        }
    }
    
    public void outputDataCell(CellProps cellProps){
    	for(ReportOutput output: outputtersList){
            output.outputDataCell(cellProps);
        }
    }
    
//    public void emptyCell(int colspan, int contentType){
//        ListIterator outputIter = outputtersList.listIterator();
//        CellProps emptyValuedCellProps = null;
//        while(outputIter.hasNext()){
//            ReportOutput output = (ReportOutput)outputIter.next();
//            emptyValuedCellProps = new CellProps(output.getWhiteSpace(), colspan, contentType);
//            output.output(emptyValuedCellProps);
//        }
//    }
    
//    public void emptyCell(int colspan){
//    	emptyCell(colspan, ReportConstants.CONTENT_DATA);
//    }
    
    public void registerOutput(ReportOutput out){
        this.outputtersList.add(out);
    }
    
    public boolean hasOutputters(){
        return outputtersList.size() > 0;
    }

	public String getWhiteSpace() {
		return " ";
	}
}
