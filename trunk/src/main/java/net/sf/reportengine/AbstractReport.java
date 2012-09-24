/*
 * Created on 22.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine;

import java.util.List;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.IAlgorithmInput;
import net.sf.reportengine.out.IReportOutput;


/**
 * <p>
 * 	Abstract report support class with the following features: 
 *  </p>
 * @author dragos balan(dragos.balan@gmail.com)
 */
public abstract class AbstractReport {
    
	/**
	 * the key used to identify the configuration columns inside the report context
	 */
	public static final String CONTEXT_KEY_DATA_COLUMNS = "net.sf.reportengine.dataColumns";
	
	/**
	 * the identifier of grouping columns inside the report context
	 */
	public final static String CONTEXT_KEY_GROUPING_COLUMNS = "net.sf.reportengine.groupingColumns";
	
    /**
     * the title of the report
     */
    private String reportTitle;
    
    /**
     * the input of the report
     */
    private IAlgorithmInput input;
    
    /**
     * the output of the report
     */
    private IReportOutput output;
    
    
    /**
     * whether the report will display totals or not 
     */
    private boolean showTotals = false; 
    
    /**
     * whether the report will display grand total
     */
    private boolean showGrandTotal = true;
    
    /**
     * whether the report will display normal data rows or not 
     */
    private boolean showDataRows = true;
   
    /**
     * the data columns
     */
    private IDataColumn[] dataColumns;
    
    /**
     * grouping columns
     */
    private IGroupColumn[] groupColumns; 
    
    /**
     * this is a constructor for 
     * a single algorithm 
     */
    public AbstractReport(){}
   
    
    /**
     * validation of the configuration and default values setter
     * @throws ConfigValidationException if the report was miss-configured
     */
    protected void validateConfig() throws ConfigValidationException {
        if(getIn() == null) throw new ConfigValidationException("The report has no input");
        if(getOut() == null) throw new ConfigValidationException("The report has no output");
    }
    
    /**
     * algorithm configuration 
     */
    protected abstract void configAlgorithmSteps();
    
    /**
     * the main execution of the algorithms 
     */
    protected abstract void executeAlgorithm();
    
    
    /**
     * Call this method for execution of the report<br> 
     * What this method does: <br>
     * <ul>
     *  <li>validates the configuration - validateConfig() method call</li>
     *  <li>configures the algorithms - configAlgorithms() method call</li>
     *  <li>opens the output - output.open()</li>
     *  <li>runs each algorithm execute() method</li>
     *  <li>closes the output - output.close()</li>
     * </ul>
     */
    public void execute(){
        validateConfig();
        configAlgorithmSteps();
        executeAlgorithm();
    }
    
    /**
     * getter for the report title
     * @return  reportTitle 
     */
    public String getReportTitle() {
        return reportTitle;
    }
    
    /**
     * report title setter
     * @param reportTitle   the title of the report
     */
    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }
    
    /**
     * setter method for report input
     * @param in
     */
    public void setIn(IAlgorithmInput in){
    	this.input = in;
    }
    
    /**
     * setter method fo report output
     * @param out
     */
    public void setOut(IReportOutput out){
    	this.output = out;
    }

	/**
	 * @return the input
	 */
	public IAlgorithmInput getIn() {
		return input;
	}

	/**
	 * @return the output
	 */
	public IReportOutput getOut() {
		return output;
	}
	
	
	/**
	 * @return the configColumns
	 */
	public IDataColumn[] getDataColumns() {
		return dataColumns;
	}


	/**
	 * @param configColumns the configColumns to set
	 */
	public void setDataColumns(IDataColumn[] dataColumns) {
		this.dataColumns = dataColumns;
	}

	
	public void setDataColumns(List<IDataColumn> dataColsList){
		if(dataColsList != null){
			setDataColumns(dataColsList.toArray(new IDataColumn[]{}));
		}
	}

	public IGroupColumn[] getGroupColumns() {
		return groupColumns;
	}


	public void setGroupColumns(IGroupColumn[] groupingColumns) {
		this.groupColumns = groupingColumns;
	}
	
	public void setGroupColumns(List<IGroupColumn> groupColsList){
		if(groupColsList != null){
			setGroupColumns(groupColsList.toArray(new IGroupColumn[]{}));
		}
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
     * @return
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
}
