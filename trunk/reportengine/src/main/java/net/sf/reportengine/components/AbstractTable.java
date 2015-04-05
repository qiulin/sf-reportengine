/**
 * 
 */
package net.sf.reportengine.components;

import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.in.TableInput;


/**
 * @author dragos balan
 *
 */
public abstract class AbstractTable implements ReportComponent {

    /**
     * the input of the report
     */
    private final TableInput input;
    
    /**
     * whether the report will display totals or not 
     */
    private boolean showTotals = false; 
    //false is assigned initially because the report may not have group columns therefore no totals to display. 
    //As soon as you add to the table (via the fluent builder)a data column containing a group calculator  
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
     * 
     * @param input
     * @param showTotals
     * @param showGrandTotal
     * @param showDataRows
     * @param valuesSorted
     */
    public AbstractTable(TableInput input, boolean showTotals, boolean showGrandTotal, boolean showDataRows, boolean valuesSorted){
    	this.input = input; 
    	this.showTotals = showTotals; 
    	this.showGrandTotal = showGrandTotal;
    	this.showDataRows = showDataRows; 
    	this.valuesSorted = valuesSorted; 
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
    }
    
    
    /**
     * setter method for report input
     * @param in
     */
//    public void setIn(ReportInput in){
//    	this.input = in;
//    }
    
	/**
	 * @return the input
	 */
	public TableInput getInput() {
		return input;
	}

	/**
     * Usually when setting totals you should also set some calculators 
     * otherwise the default calculators (Count) will be shown
     * @param flag  true if you want to show the results of aggregation (sums, counts, max etc)
     */
//    public void setShowTotals(boolean flag){
//        this.showTotals = flag;
//    }
    
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
//    public void setShowDataRows(boolean flag){
//        this.showDataRows = flag;
//    }
    
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
//    public void setShowGrandTotal(boolean flag){
//    	this.showGrandTotal = flag;
//    }

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
//	public void setValuesSorted(boolean valuesAlreadySorted) {
//		this.valuesSorted = valuesAlreadySorted;
//	}
}
