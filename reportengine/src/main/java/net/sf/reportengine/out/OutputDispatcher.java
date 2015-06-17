/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
