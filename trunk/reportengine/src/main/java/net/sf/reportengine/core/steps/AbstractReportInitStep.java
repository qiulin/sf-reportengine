/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.List;
import java.util.Map;

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
public abstract class AbstractReportInitStep extends AbstractInitStep {
	
	/**
	 * 
	 * @return
	 */
	public ReportInput getReportInput(){
		return (ReportInput)getAlgoContext().get(ContextKeys.LOCAL_REPORT_INPUT); 
	}
	
	/**
     * getter for the output of the report
     * @return
     */
    public ReportOutput getReportOutput(){
    	return (ReportOutput)getAlgoContext().get(ContextKeys.LOCAL_REPORT_OUTPUT); 
    }
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getDataColumnsLength()}
     * @return
     */
    public List<DataColumn> getDataColumns(Map<IOKeys, Object> inputParams){
    	return (List<DataColumn>)inputParams.get(IOKeys.DATA_COLS); 
    }
    
    /**
     * 
     * @return
     */
    public int getDataColumnsLength(Map<IOKeys, Object> inputParams){
    	List<DataColumn> dataCols = getDataColumns(inputParams); 
    	return dataCols != null ? dataCols.size() : 0; 
    }
    
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getGroupColumns()}
     * 
     * @return
     */
    public List<GroupColumn> getGroupColumns(Map<IOKeys, Object> inputParams){
    	return (List<GroupColumn>)inputParams.get(IOKeys.GROUP_COLS); 
    }
    
    public int getGroupColumnsLength(Map<IOKeys, Object> inputParams){
    	List<GroupColumn> groupColumns = getGroupColumns(inputParams); 
    	return groupColumns != null ? groupColumns.size() : 0; 
    }
    
    public Boolean getShowTotals(Map<IOKeys, Object> inputParams){
    	return (Boolean)inputParams.get(IOKeys.SHOW_TOTALS); 
    }
    
    public Boolean getShowGrandTotal(Map<IOKeys, Object> inputParams){
    	return (Boolean)inputParams.get(IOKeys.SHOW_GRAND_TOTAL); 
    }
    
    public String getReportTitle(Map<IOKeys, Object> inputParams){
    	return (String)inputParams.get(IOKeys.REPORT_TITLE); 
    }
}
