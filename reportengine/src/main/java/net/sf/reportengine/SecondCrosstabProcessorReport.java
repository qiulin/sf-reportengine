/**
 * 
 */
package net.sf.reportengine;

import net.sf.reportengine.core.algorithm.AlgoInput;
import net.sf.reportengine.core.algorithm.LoopThroughReportInputAlgo;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.steps.CloseReportIOExitStep;
import net.sf.reportengine.core.steps.ComputeColumnValuesStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.EndReportExitStep;
import net.sf.reportengine.core.steps.FlatReportExtractDataInitStep;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.GroupingLevelDetectorStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.OpenReportIOInitStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.StartReportInitStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.core.steps.crosstab.CrosstabHeaderOutputInitStep;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.InputKeys;

/**
 * This is for internal use only. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
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
    	//ReportContext context = algorithm.getContext();
    	
    	//setting the input/output
    	algorithm.addIn(new AlgoInput(getIn(), InputKeys.REPORT_INPUT));
		algorithm.addIn(new AlgoInput(getOut(), InputKeys.REPORT_OUTPUT));
    	
    	//context keys specific to a flat report
		algorithm.addIn(new AlgoInput(getDataColumns(), InputKeys.DATA_COLS));
		algorithm.addIn(new AlgoInput(getGroupColumns(), InputKeys.GROUP_COLS)); 
		algorithm.addIn(new AlgoInput(getShowTotals(), InputKeys.SHOW_TOTALS));
		algorithm.addIn(new AlgoInput(getShowGrandTotal(), InputKeys.SHOW_GRAND_TOTAL));
		algorithm.addIn(new AlgoInput(ctMetadata, InputKeys.CROSSTAB_METADATA)); 
    	
    	//adding steps to the algorithm
		algorithm.addInitStep(new OpenReportIOInitStep()); 
    	algorithm.addInitStep(new InitReportDataInitStep()); 
    	algorithm.addInitStep(new FlatReportExtractDataInitStep());
    	algorithm.addInitStep(new StartReportInitStep()); 
    	algorithm.addInitStep(new CrosstabHeaderOutputInitStep());
    	
    	//algorithm.addInitStep(new ColumnHeaderOutputInitStep("Second process "));
        
    	algorithm.addMainStep(new ComputeColumnValuesStep());
    	algorithm.addMainStep(new GroupingLevelDetectorStep());
    	
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
