/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
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
		return (ReportInput)getAlgoInput().get(IOKeys.REPORT_INPUT); 
	}
	
	/**
     * getter for the output of the report
     * @return
     */
    public ReportOutput getReportOutput(){
    	return extractRepOutputFromParameters(getAlgoInput(), getAlgoContext()); 
    }
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getReportOutput()}
     * @param algoInput
     * @param algoContext
     * @return
     */
    protected ReportOutput extractRepOutputFromParameters(	Map<IOKeys, Object> algoInput, 
															AlgoContext algoContext){
		return (ReportOutput)algoInput.get(IOKeys.REPORT_OUTPUT); 
	}
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getDataColumns()}
     * {@link #getDataColumnsLength()}
     * 
     * @param algoInput
     * @param algoContext
     * @return
     */
    protected List<DataColumn> extractDataColsFromParameters(	Map<IOKeys, Object> algoInput, 
																AlgoContext algoContext){
		return (List<DataColumn>)algoInput.get(IOKeys.DATA_COLS); 
	}
    
    public List<DataColumn> getDataColumns(){
    	return extractDataColsFromParameters(getAlgoInput(), getAlgoContext()); 
    }
    
    public int getDataColumnsLength(){
    	return getDataColumns() != null ? getDataColumns().size() : 0; 
    }
    
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getGroupColumns()}
     * {@link #getGroupColumnsLength()}
     * 
     * @param algoInput
     * @param algoContext
     * @return
     */
    protected List<GroupColumn> extractGroupColsFromParameters(	Map<IOKeys, Object> algoInput, 
																AlgoContext algoContext){
		return (List<GroupColumn>)algoInput.get(IOKeys.GROUP_COLS); 
	}
    
    public List<GroupColumn> getGroupColumns(){
    	return extractGroupColsFromParameters(getAlgoInput(), getAlgoContext()); 
    }
    
    public int getGroupColumnsLength(){
    	return getGroupColumns() != null ? getGroupColumns().size() : 0; 
    }
    
    public Boolean getShowTotals(){
    	return (Boolean)getAlgoInput().get(IOKeys.SHOW_TOTALS); 
    }
    
    public Boolean getShowGrandTotal(){
    	return (Boolean)getAlgoInput().get(IOKeys.SHOW_GRAND_TOTAL); 
    }
}
