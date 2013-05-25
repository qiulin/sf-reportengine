/**
 * 
 */
package net.sf.reportengine;

import net.sf.reportengine.core.algorithm.LoopThroughReportInputAlgo;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;
import net.sf.reportengine.core.steps.CloseReportIOExitStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.EndReportExitStep;
import net.sf.reportengine.core.steps.FlatReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.GroupLevelDetectorStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.OpenReportIOInitStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.StartReportInitStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.core.steps.crosstab.CrosstabHeaderOutputInitStep;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.IOKeys;

/**
 * This is for internal use only. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 * @deprecated
 */
class SecondCrosstabProcessorReport extends AbstractMultiStepAlgoColumnBasedReport {
	
	private CtMetadata ctMetadata = null; 
	
	public SecondCrosstabProcessorReport(CtMetadata metadata){
		super(new LoopThroughReportInputAlgo()); 
		this.ctMetadata = metadata; 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.AbstractReport#configAlgorithmSteps()
	 */
	@Override protected void config() {
		MultiStepAlgo algorithm = getAlgorithm();
    	//AlgoContext context = algorithm.getContext();
    	
    	//setting the input/output
    	algorithm.addIn(IOKeys.REPORT_INPUT, getIn());
		algorithm.addIn(IOKeys.REPORT_OUTPUT, getOut());
    	
    	//context keys specific to a flat report
		algorithm.addIn(IOKeys.DATA_COLS, getDataColumns());
		algorithm.addIn(IOKeys.GROUP_COLS, getGroupColumns()); 
		algorithm.addIn(IOKeys.SHOW_TOTALS, getShowTotals());
		algorithm.addIn(IOKeys.SHOW_GRAND_TOTAL, getShowGrandTotal());
		//algorithm.addIn(IOKeys.CROSSTAB_METADATA, ctMetadata); 
    	
    	//adding steps to the algorithm
		algorithm.addInitStep(new OpenReportIOInitStep()); 
    	algorithm.addInitStep(new InitReportDataInitStep()); 
    	algorithm.addInitStep(new FlatReportExtractTotalsDataInitStep());
    	algorithm.addInitStep(new StartReportInitStep()); 
    	algorithm.addInitStep(new CrosstabHeaderOutputInitStep());
    	
    	//algorithm.addInitStep(new ColumnHeaderOutputInitStep("Second process "));
        
    	//algorithm.addMainStep(new ComputeColumnValuesStep());
    	algorithm.addMainStep(new GroupLevelDetectorStep());
    	
    	if(getShowTotals() || getShowGrandTotal()){
    		algorithm.addMainStep(new FlatReportTotalsOutputStep());
    		algorithm.addMainStep(new TotalsCalculatorStep());
    	}
    	
        algorithm.addMainStep(new DataRowsOutputStep());
        
        if(getGroupColumns() != null && getGroupColumns().size() > 0){
        	algorithm.addMainStep(new PreviousRowManagerStep());
        }
        
        algorithm.addExitStep(new EndReportExitStep()); 
        algorithm.addExitStep(new CloseReportIOExitStep()); 
	}
}
