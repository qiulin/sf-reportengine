/**
 * 
 */
package net.sf.reportengine;

import junit.framework.TestCase;
import net.sf.reportengine.core.algorithm.DefaultReportContext;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.ComputeColumnValuesStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.FlatReportExtractDataInitStep;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.GroupingLevelDetectorStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.LoggerOutput;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.MatrixUtils;

/**
 * @author dragos balan
 *
 */
public class TestStepsCombo extends TestCase {
	
	private ReportContext TEST_REPORT_CONTEXT; 
	private OutputDispatcher TEST_OUTPUT_DISPATCHER; 
	private CellPropsArrayOutput cumulativeReportOutput = null;
	
	
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		cumulativeReportOutput = new CellPropsArrayOutput();
		
		TEST_OUTPUT_DISPATCHER = new OutputDispatcher();
		TEST_OUTPUT_DISPATCHER.registerOutput(cumulativeReportOutput);
		TEST_OUTPUT_DISPATCHER.registerOutput(new LoggerOutput());
		
		TEST_REPORT_CONTEXT = new DefaultReportContext();
		TEST_REPORT_CONTEXT.setOutput(TEST_OUTPUT_DISPATCHER);
		
		//simulate the context
		TEST_REPORT_CONTEXT.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS); 
		TEST_REPORT_CONTEXT.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS);
		TEST_REPORT_CONTEXT.set(ContextKeys.SHOW_GRAND_TOTAL, Scenario1.SHOW_GRAND_TOTAL);
		
		
		TEST_REPORT_CONTEXT.setInput(Scenario1.INPUT);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCombo(){
		
		//construct the steps
		InitReportDataInitStep initReportDataInitStep = new InitReportDataInitStep(); 
		FlatReportExtractDataInitStep extractDataInitStep = new FlatReportExtractDataInitStep(); 
		ColumnHeaderOutputInitStep columnHeaderInitStep = new ColumnHeaderOutputInitStep();
		ComputeColumnValuesStep computeColumnStep = new ComputeColumnValuesStep();
		GroupingLevelDetectorStep levelDetectorStep = new GroupingLevelDetectorStep();
		FlatReportTotalsOutputStep yTotalsOutput = new FlatReportTotalsOutputStep();
		DataRowsOutputStep dataRowOutput = new DataRowsOutputStep();
		TotalsCalculatorStep totalsCalculator = new TotalsCalculatorStep();
		PreviousRowManagerStep previosGroupValuesManager = new PreviousRowManagerStep();
		
		
		//init steps
		initReportDataInitStep.init(TEST_REPORT_CONTEXT); 
		extractDataInitStep.init(TEST_REPORT_CONTEXT);
		columnHeaderInitStep.init(TEST_REPORT_CONTEXT);
		computeColumnStep.init(TEST_REPORT_CONTEXT);
		levelDetectorStep.init(TEST_REPORT_CONTEXT);
		yTotalsOutput.init(TEST_REPORT_CONTEXT);
		dataRowOutput.init(TEST_REPORT_CONTEXT); 
		totalsCalculator.init(TEST_REPORT_CONTEXT);
		previosGroupValuesManager.init(TEST_REPORT_CONTEXT);
		
		//execute steps
		NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_1);
		computeColumnStep.execute(dataRowEvent);
		levelDetectorStep.execute(dataRowEvent);
		yTotalsOutput.execute(dataRowEvent);
		dataRowOutput.execute(dataRowEvent);
		totalsCalculator.execute(dataRowEvent);
		previosGroupValuesManager.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_2);
		computeColumnStep.execute(dataRowEvent);
		levelDetectorStep.execute(dataRowEvent);
		yTotalsOutput.execute(dataRowEvent);
		dataRowOutput.execute(dataRowEvent);
		totalsCalculator.execute(dataRowEvent);
		previosGroupValuesManager.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_3);
		computeColumnStep.execute(dataRowEvent);
		levelDetectorStep.execute(dataRowEvent);
		yTotalsOutput.execute(dataRowEvent);
		dataRowOutput.execute(dataRowEvent);
		totalsCalculator.execute(dataRowEvent);
		previosGroupValuesManager.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_4);
		computeColumnStep.execute(dataRowEvent);
		levelDetectorStep.execute(dataRowEvent);
		yTotalsOutput.execute(dataRowEvent);
		dataRowOutput.execute(dataRowEvent);
		totalsCalculator.execute(dataRowEvent);
		previosGroupValuesManager.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_5);
		computeColumnStep.execute(dataRowEvent);
		levelDetectorStep.execute(dataRowEvent);
		yTotalsOutput.execute(dataRowEvent);
		dataRowOutput.execute(dataRowEvent);
		totalsCalculator.execute(dataRowEvent);
		previosGroupValuesManager.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_6);
		computeColumnStep.execute(dataRowEvent);
		levelDetectorStep.execute(dataRowEvent);
		yTotalsOutput.execute(dataRowEvent);
		dataRowOutput.execute(dataRowEvent);
		totalsCalculator.execute(dataRowEvent);
		previosGroupValuesManager.execute(dataRowEvent);
		
		//exit
		computeColumnStep.exit();
		levelDetectorStep.exit();
		yTotalsOutput.exit();
		dataRowOutput.exit();
		totalsCalculator.exit();
		previosGroupValuesManager.exit();
		
		CellProps[][] result = cumulativeReportOutput.getCellMatrix();
		MatrixUtils.compareMatrices(result, Scenario1.EXPECTED_OUTPUT);
	}
}
