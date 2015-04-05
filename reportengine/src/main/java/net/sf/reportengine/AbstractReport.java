/*
 * Created on 22.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine;

import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.out.ReportOutput;


/**
 * <p>Abstract report support class covering basic functionality for reports</p>
 * <p>
 * 	the features provided to the children are:
 * <ul>
 * 	<li>title</li>
 * 	<li>input</li>
 * 	<li>output</li>
 * 	<li>dataColumns</li>
 *  <li>groupColumns</li>
 *  <li>show methods</li>	
 *  <li>validation of input/output</li>
 * </ul> 
 * 
 * When extending this class you should only override 
 * <ul>
 * 	<li>{@link #validate()} - basic input/output functionality already provided so you may consider using super.validateConfig()</li>
 * 	<li>{@link #config()}</li>
 * 	<li>{@link #execute()}</li>
 * <ul>
 * </p>
 *  
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 * @see FlatReport
 * @see CrossTabReport
 */
abstract class AbstractReport {
	
    /**
     * the title of the report
     */
    private String reportTitle;
    
    /**
     * the input of the report
     */
    private TableInput input;
    
    /**
     * the output of the report
     */
    private ReportOutput output;
    
    /**
     * whether the report will display totals or not 
     */
    private boolean showTotals = false; 
    //false is assigned initially because the report may not have group columns therefore no totals to display. 
    //As soon as you add to the report (via the fluent builder)a data column containing a group calculator  
    //the value is re-calculated to true 
    //If the data column is not added via fluent api then this value remains the same
    
    /**
     * whether the report will display grand total
     */
    private boolean showGrandTotal = false;
    //false is assigned initially because the report may not have group columns therefore no totals to display. 
    //As soon as you add to the report (via the fluent builder)a data column containing a group calculator  
    //the value is re-calculated to true 
    //If the data column is not added via fluent api then this value remains the same 
    
    
    /**
     * whether the report will display normal data rows or not 
     */
    private boolean showDataRows = true;
   
    /**
     * Whether or not data from the input is already sorted. 
     * By default we'll assume this flag is set to true because 
     * the sorting will slow the reporting process. 
     */
    private boolean valuesSorted = true; 
    
    /**
     * this is a constructor for 
     * a single algorithm 
     */
    public AbstractReport(){
    }
   
    /**
     * use this method to configure your report
     */
    protected abstract void config();
    
    
    /**
     * use this method to validate your report
     */
    protected void validate(){
    	if(input == null) throw new ConfigValidationException("The report has no input");
        if(output == null) throw new ConfigValidationException("The report has no output");
    }
    
    /**
     * call this method to execute the report.
     */
    public abstract void execute();
    
    /**
     * getter for the report title
     * @return  reportTitle 
     */
    public String getTitle() {
        return reportTitle;
    }
    
    /**
     * report title setter
     * @param reportTitle   the title of the report
     */
    public void setTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }
    
    /**
     * setter method for report input
     * @param in
     */
    public void setIn(TableInput in){
    	this.input = in;
    }
    
    /**
     * setter method for report output
     * @param out
     */
    public void setOut(ReportOutput out){
    	this.output = out;
    }

	/**
	 * @return the input
	 */
	public TableInput getIn() {
		return input;
	}

	/**
	 * @return the output
	 */
	public ReportOutput getOut() {
		return output;
	}
	
	 /**
     * Usually when setting totals you should also set some calculators 
     * otherwise the default calculators (Count) will be shown
     * @param flag  true if you want to show the results of aggregation (sums, counts, max etc)
     */
    public void setShowTotals(boolean flag){
        this.showTotals = flag;
    }
    
    /**
     * show totals getter
     * @return  true if this report has to show totals
     */
    public boolean getShowTotals(){
        return showTotals;
    }
    
    
    /**
     * setter for showDataRows
     * @param flag  true if you want to show data rows 
     */
    public void setShowDataRows(boolean flag){
        this.showDataRows = flag;
    }
    
    /**
     * show data rows getter
     * @return  true if this report has to show data rows
     */
    public boolean getShowDataRows(){
        return showDataRows;
    }
    
    /**
     * getter for showGrandTotal flag
     * @return true if this report will show the grand totals
     */
    public boolean getShowGrandTotal(){
    	return showGrandTotal;
    }
    
    /**
     * setter for showGrandTotal flag
     * @param flag
     */
    public void setShowGrandTotal(boolean flag){
    	this.showGrandTotal = flag;
    }

	/**
	 * @return the valuesSorted
	 */
	public boolean hasValuesSorted() {
		return valuesSorted;
	}

	/**
	 * Tells the report if your input already has the values sorted. 
	 * If you need sorting to be done by the reportengine then set this flag to false (of course therea's a big performance penalty) 
	 * @param valuesSorted the valuesSorted to set
	 */
	public void setValuesSorted(boolean valuesAlreadySorted) {
		this.valuesSorted = valuesAlreadySorted;
	}
}
