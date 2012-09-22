/*
 * Created on 20.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.IAlgorithm;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.ComputeColumnValuesStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.FlatReportExtractDataInitStep;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.GroupingLevelDetectorStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.filter.DataOutputFilter;
import net.sf.reportengine.filter.ITotalsOutputFilter;

/**
 * <p>
 * This is the main utility class for flat reports. 
 * </p>
 * 
 * @author dragos balan
 * @since 0.2
 */
public class FlatReport extends AbstractOneIterationReport {
    
	
	public final static String CONTEXT_KEY_SHOW_TOTALS = "net.sf.reportengine.showTotals";
	public final static String CONTEXT_KEY_SHOW_GRAND_TOTAL = "net.sf.reportengine.showGrandTotal";
	public final static String CONTEXT_KEY_SUBTOTALS_OUTPUT = "net.sf.reportengine.subtotaltOut";
	public final static String CONTEXT_KEY_DATA_OUT_FILTERS = "net.sf.reportengine.dataFilters";
	public final static String CONTEXT_KEY_TOTAL_OUT_FILTERS = "net.sf.reportengine.totalFilters";
    
    
    /**
     * the list of data output filters
     */
    private List<DataOutputFilter> dataOutputFilterList = null;
    
    
    /**
     * the list of data output filters
     */
    private List<ITotalsOutputFilter> totalsOutputFilterList = null;
    
    /**
     * constructor
     *
     */
    public FlatReport(){}
    
    
    /**
     * convenience method 
     */
    protected void validateConfig() throws ConfigValidationException {
        if(getIn() == null) throw new ConfigValidationException("The report has no input");
        if(getOut() == null) throw new ConfigValidationException("The report has no output");
    }
    
    /**
     * algorithm configuration 
     */
    protected void configAlgorithmSteps(){
    	IAlgorithm algorithm = getAlgorithm();
    	IAlgorithmContext context = algorithm.getContext();
    	
    	//preparing the context of the report algorithm 
    	algorithm.setIn(getIn());
    	algorithm.setOut(getOut());
    	
    	context.set(CONTEXT_KEY_DATA_COLUMNS, getDataColumns());
    	context.set(CONTEXT_KEY_GROUPING_COLUMNS, getGroupingColumns());
    	context.set(CONTEXT_KEY_SHOW_TOTALS, Boolean.valueOf(getShowTotals()));
    	context.set(CONTEXT_KEY_SHOW_GRAND_TOTAL, Boolean.valueOf(getShowGrandTotal()));
    	context.set(CONTEXT_KEY_DATA_OUT_FILTERS, dataOutputFilterList);
    	
    	//adding steps to the algorithm
    	algorithm.addInitStep(new FlatReportExtractDataInitStep());
    	algorithm.addInitStep(new ColumnHeaderOutputInitStep(getReportTitle()));
        
    	algorithm.addMainStep(new ComputeColumnValuesStep());
    	algorithm.addMainStep(new GroupingLevelDetectorStep());
    	
        if(getShowTotals() || getShowGrandTotal()){
        	algorithm.addMainStep(new FlatReportTotalsOutputStep());
        	algorithm.addMainStep(new TotalsCalculatorStep());
        }
        
        if(getShowDataRows()){
        	algorithm.addMainStep(new DataRowsOutputStep());
        }
        
        if(getGroupingColumns() != null && getGroupingColumns().length > 0){
        	algorithm.addMainStep(new PreviousRowManagerStep());
        }
    }


	/**
	 * 
	 * @param filter
	 */
	public void addDataOutputFilter(DataOutputFilter filter){
		if(dataOutputFilterList == null){
			dataOutputFilterList = new ArrayList<DataOutputFilter>();
		}
		dataOutputFilterList.add(filter);
	}
    
	/**
	 * 
	 * @param filterList
	 */
	public void setDataOutputFilterList(List<DataOutputFilter> filterList){
		this.dataOutputFilterList = filterList;
	}
	
	/**
	 * 
	 * @param filter
	 */
	public void addTotalOutputFilter(ITotalsOutputFilter filter){
		if(totalsOutputFilterList == null){
			totalsOutputFilterList = new ArrayList<ITotalsOutputFilter>();
		}
		totalsOutputFilterList.add(filter);
	}
    
	/**
	 * 
	 * @param filterList
	 */
	public void setTotalsOutputFilterList(List<ITotalsOutputFilter> filterList){
		this.totalsOutputFilterList = filterList;
	}
}
