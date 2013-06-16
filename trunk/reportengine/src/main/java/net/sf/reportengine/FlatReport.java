/*
 * Created on 20.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.core.algorithm.AlgorithmContainer;
import net.sf.reportengine.core.algorithm.LoopThroughReportInputAlgo;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;
import net.sf.reportengine.core.steps.CloseReportIOExitStep;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.ConfigReportIOInitStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.ConfigFlatIOInitStep;
import net.sf.reportengine.core.steps.EndReportExitStep;
import net.sf.reportengine.core.steps.ExternalSortPreparationStep;
import net.sf.reportengine.core.steps.FlatReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.GroupLevelDetectorStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.OpenReportIOInitStep;
import net.sf.reportengine.core.steps.OpenReportInputInitStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.StartReportInitStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.IOKeys;
import net.sf.reportengine.util.ReportUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * ReportInput input = new TextInput(new FileInputStream("input.txt"));
 * flatReport.setIn(input);
 *
 * //output configuration
 * ReportOutput output = new ExcelOutput(new FileOutputStream("output.xls")); 
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
 * @see ReportInput
 * @see ReportOutput
 * @see DataColumn
 * @see GroupColumn
 * 
 * @author dragos balan
 * @since 0.2
 */
public class FlatReport extends AbstractColumnBasedReport {
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FlatReport.class);
    
	/**
	 *  the container for two potential algorithms : 
	 *   1. the sorting algorithm 
	 *   2. the reporting algorithm
	 */
	private AlgorithmContainer reportAlgoContainer = new AlgorithmContainer(); 
	
    /**
     * default constructor
     */
    public FlatReport(){
    }
    
    
    /**
     * reportAlgo configuration 
     */
    @Override protected void config(){
    	LOGGER.trace("configuring flat report"); 
    	
    	//preparing the context of the report reportAlgo 
    	reportAlgoContainer.addIn(IOKeys.REPORT_INPUT, getIn());
    	reportAlgoContainer.addIn(IOKeys.REPORT_OUTPUT, getOut());
    	reportAlgoContainer.addIn(IOKeys.DATA_COLS, getDataColumns()); 
    	reportAlgoContainer.addIn(IOKeys.GROUP_COLS, getGroupColumns()); 
    	reportAlgoContainer.addIn(IOKeys.SHOW_TOTALS, getShowTotals()); 
    	reportAlgoContainer.addIn(IOKeys.SHOW_GRAND_TOTAL, getShowGrandTotal()); 
    	reportAlgoContainer.addIn(IOKeys.HAS_GROUP_VALUES_ORDERED, hasGroupValuesSorted()); 
    	
    	if(!hasGroupValuesSorted()){
    		reportAlgoContainer.addAlgo(configSortingAlgo()); 
    	}
    	reportAlgoContainer.addAlgo(configReportAlgo()); 
    }
    
    /**
     * 
     * @return
     */
    private Algorithm configSortingAlgo(){
    	MultiStepAlgo sortingAlgo = new LoopThroughReportInputAlgo(); 
    	
    	//init steps
    	sortingAlgo.addInitStep(new ConfigReportIOInitStep()); 
    	sortingAlgo.addInitStep(new OpenReportInputInitStep()); 
    	
    	//main steps
    	sortingAlgo.addMainStep(new ExternalSortPreparationStep()); 
    	return sortingAlgo; 
    }
    
    /**
     * 
     * @return
     */
    private Algorithm configReportAlgo(){
    	MultiStepAlgo reportAlgo = new LoopThroughReportInputAlgo(); 
    	reportAlgo.addInitStep(new ConfigFlatIOInitStep()); 
    	reportAlgo.addInitStep(new OpenReportIOInitStep()); 
    	reportAlgo.addInitStep(new InitReportDataInitStep()); 
    	reportAlgo.addInitStep(new FlatReportExtractTotalsDataInitStep());//TODO: only when report has totals
    	reportAlgo.addInitStep(new StartReportInitStep()); 
    	reportAlgo.addInitStep(new ColumnHeaderOutputInitStep(getTitle()));
        
    	//then we add the main steps
    	reportAlgo.addMainStep(new GroupLevelDetectorStep());
    	
        if(getShowTotals() || getShowGrandTotal()){
        	reportAlgo.addMainStep(new FlatReportTotalsOutputStep());
        	reportAlgo.addMainStep(new TotalsCalculatorStep());
        }
        
        if(getShowDataRows()){
        	reportAlgo.addMainStep(new DataRowsOutputStep());
        }
        
        if(getGroupColumns() != null && getGroupColumns().size() > 0){
        	reportAlgo.addMainStep(new PreviousRowManagerStep());
        }
        
        reportAlgo.addExitStep(new EndReportExitStep()); 
        reportAlgo.addExitStep(new CloseReportIOExitStep()); 
        
        return reportAlgo; 
    }
    

	@Override protected void validate() {
		LOGGER.trace("validating flat report"); 
		//validate non null input and output
		super.validate(); 
        
        List<DataColumn> dataColumns = getDataColumns(); 
        if(dataColumns == null || dataColumns.size() == 0){
        	throw new ConfigValidationException("The report needs at least one data column to work properly"); 
        }
        
        //if totals are needed then check if any Calculators have been added to ALL DataColumns
		if(	(getShowTotals() || getShowGrandTotal()) 
			&& !ReportUtils.atLeastOneDataColumHasCalculators(dataColumns)){
			throw new ConfigValidationException("Please configure a Calculator to at least one DataColumn in order to display totals");
		}
	}


	/**
     * Call this method for execution of the report<br> 
     * What this method does: <br>
     * <ul>
     *  <li>validates the configuration - validateConfig() method call</li>
     *  <li>opens the output - output.open()</li>
     *  <li>runs each reportAlgo execute() method</li>
     *  <li>closes the output - output.close()</li>
     * </ul>
     */
    @Override public void execute(){
    	validate(); 
        config();
        reportAlgoContainer.execute(); 
    }
}
