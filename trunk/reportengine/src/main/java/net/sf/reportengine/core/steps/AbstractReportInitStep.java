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
/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractReportInitStep<U> extends AbstractInitStep<U> {
	
	/**
	 * 
	 * @return
	 */
	public TableInput getReportInput(StepInput stepInput){
		return (TableInput)stepInput.getContextParam(ContextKeys.LOCAL_REPORT_INPUT); 
	}
	
	/**
     * getter for the output of the report
     * @return
     */
    public ReportOutput getReportOutput(StepInput stepInput){
    	return (ReportOutput)stepInput.getContextParam(ContextKeys.LOCAL_REPORT_OUTPUT); 
    }
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getDataColumnsLength()}
     * @return
     */
    public List<DataColumn> getDataColumns(StepInput stepInput){
    	return (List<DataColumn>)stepInput.getAlgoInput(IOKeys.DATA_COLS); 
    }
    
    /**
     * 
     * @return
     */
    public int getDataColumnsLength(StepInput stepInput){
    	List<DataColumn> dataCols = getDataColumns(stepInput); 
    	return dataCols != null ? dataCols.size() : 0; 
    }
    
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getGroupColumns()}
     * 
     * @return
     */
    public List<GroupColumn> getGroupColumns(StepInput stepInput){
    	return (List<GroupColumn>)stepInput.getAlgoInput(IOKeys.GROUP_COLS); 
    }
    
    public int getGroupColumnsLength(StepInput stepInput){
    	List<GroupColumn> groupColumns = getGroupColumns(stepInput); 
    	return groupColumns != null ? groupColumns.size() : 0; 
    }
    
    public Boolean getShowTotals(StepInput stepInput){
    	return (Boolean)stepInput.getAlgoInput(IOKeys.SHOW_TOTALS); 
    }
    
    public Boolean getShowGrandTotal(StepInput stepInput){
    	return (Boolean)stepInput.getAlgoInput(IOKeys.SHOW_GRAND_TOTAL); 
    }
    
    public String getReportTitle(StepInput stepInput){
    	return (String)stepInput.getAlgoInput(IOKeys.REPORT_TITLE); 
    }
}
