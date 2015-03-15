/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.in.ReportInput;
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
	public ReportInput getReportInput(StepInput stepInput){
		return (ReportInput)stepInput.getContextParam(ContextKeys.LOCAL_REPORT_INPUT); 
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
