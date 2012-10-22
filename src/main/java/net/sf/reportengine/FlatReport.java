/*
 * Created on 20.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine;

import java.util.List;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.IReportAlgorithm;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.ComputeColumnValuesStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.FlatReportExtractDataInitStep;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.GroupingLevelDetectorStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.filter.DataOutputFilter;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.out.IReportOutput;

/**
 * <p>
 * This is a normal tabular report (don't get confused by its name) whose layout will look like: 
 * <table>
 * 	<tr><td><b>column 1</b></td><td><b>column 2</b></td><td><b>column 3</b></td></tr>
 * 	<tr><td>data 11</td><td>data 12</td><td>data 13</td></tr>
 *  <tr><td>data 21</td><td>data 22</td><td>data 23</td><tr>
 *  <tr><td>data 31</td><td>data 32</td><td>data 33</td><tr>
 * </table>
 *
 * Each flat report needs three elements configured: 
 * <ul>
 * 	<li>input</li>
 * 	<li>column configuration</li> 
 * 	<li>output</li>
 * </ul>
 * A simple report example can be: 
 * <pre>
 * {@code
 * FlatReport flatReport = new FlatReport();	
 * 
 * //input configuration
 * IReportInput input = new StreamReportInput(new FileInputStream("input.txt"));
 * flatReport.setIn(input);
 *
 * //output configuration
 * IReportOutput output = new ExcelOutput(new FileOutputStream("output.xls")); 
 * flatReport.setOut(output);
 *
 * //columns configuration
 * flatReport.addDataColumn(new DefaultDataColumn("Country", 0)); 
 * flatReport.addDataColumn(new DefaultDataColumn("City", 1)); 
 * flatReport.addDataColumn(new DefaultDataColumn("Population", 2)); 
 *
 * //start execution
 * flatReport.execute();
 *}
 * </pre>
 * </p>
 * 
 * @see IReportInput
 * @see IReportOutput
 * @see IDataColumn
 * @see IGroupColumn
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
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
     * default constructor
     */
    public FlatReport(){}
    
    
    /**
     * convenience method 
     */
    @Override protected void validateConfig() {
        super.validateConfig(); //this one checks the Input and the output
        
        if(getDataColumns() == null || getDataColumns().length == 0){
        	throw new ConfigValidationException("Any report needs at least one data column to work properly"); 
        }
    }
    
    /**
     * algorithm configuration 
     */
    @Override protected void configAlgorithmSteps(){
    	IReportAlgorithm algorithm = getAlgorithm();
    	IAlgorithmContext context = algorithm.getContext();
    	
    	//preparing the context of the report algorithm 
    	algorithm.setIn(getIn());
    	algorithm.setOut(getOut());
    	
    	context.set(CONTEXT_KEY_DATA_COLUMNS, getDataColumns());
    	context.set(CONTEXT_KEY_GROUPING_COLUMNS, getGroupColumns());
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
        
        if(getGroupColumns() != null && getGroupColumns().length > 0){
        	algorithm.addMainStep(new PreviousRowManagerStep());
        }
    }
}
