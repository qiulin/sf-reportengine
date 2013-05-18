/**
 * 
 */
package net.sf.reportengine;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.DefaultReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.ComputeColumnValuesStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.EndReportExitStep;
import net.sf.reportengine.core.steps.FlatReportExtractDataInitStep;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.GroupingLevelDetectorStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.StartReportInitStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.out.LoggerOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.InputKeys;
import net.sf.reportengine.util.MatrixUtils;

import org.junit.Before;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestStepsCombo  {
	
	private ReportContext TEST_REPORT_CONTEXT; 
	private OutputDispatcher TEST_OUTPUT_DISPATCHER; 
	private CellPropsArrayOutput cumulativeReportOutput = null;
	private Map<InputKeys, Object> mockAlgoInput = null; 
	
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		cumulativeReportOutput = new CellPropsArrayOutput();
		
		TEST_OUTPUT_DISPATCHER = new OutputDispatcher();
		TEST_OUTPUT_DISPATCHER.registerOutput(cumulativeReportOutput);
		TEST_OUTPUT_DISPATCHER.registerOutput(new LoggerOutput());
		
		TEST_REPORT_CONTEXT = new DefaultReportContext();
		//TEST_REPORT_CONTEXT.setOutput(TEST_OUTPUT_DISPATCHER);
		
		//simulate the context
		//TEST_REPORT_CONTEXT.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS); 
		//TEST_REPORT_CONTEXT.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS);
		//TEST_REPORT_CONTEXT.set(ContextKeys.SHOW_GRAND_TOTAL, Scenario1.SHOW_GRAND_TOTAL);
		
		
		//TEST_REPORT_CONTEXT.setInput(Scenario1.INPUT);
		
		mockAlgoInput = new EnumMap<InputKeys, Object>(InputKeys.class);
		mockAlgoInput.put(InputKeys.REPORT_INPUT, Scenario1.INPUT); 
		mockAlgoInput.put(InputKeys.REPORT_OUTPUT, TEST_OUTPUT_DISPATCHER); 
		mockAlgoInput.put(InputKeys.SHOW_GRAND_TOTAL, Scenario1.SHOW_GRAND_TOTAL); 
		mockAlgoInput.put(InputKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockAlgoInput.put(InputKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS); 
	}
	
	@Test
	public void testCombo(){
		
		//construct the steps
		InitReportDataInitStep initReportDataInitStep = new InitReportDataInitStep(); 
		FlatReportExtractDataInitStep extractDataInitStep = new FlatReportExtractDataInitStep(); 
		
		StartReportInitStep startReportInitStep = new StartReportInitStep(); 
		
		ColumnHeaderOutputInitStep columnHeaderInitStep = new ColumnHeaderOutputInitStep();
		ComputeColumnValuesStep computeColumnStep = new ComputeColumnValuesStep();
		GroupingLevelDetectorStep levelDetectorStep = new GroupingLevelDetectorStep();
		FlatReportTotalsOutputStep yTotalsOutput = new FlatReportTotalsOutputStep();
		DataRowsOutputStep dataRowOutput = new DataRowsOutputStep();
		TotalsCalculatorStep totalsCalculator = new TotalsCalculatorStep();
		PreviousRowManagerStep previosGroupValuesManager = new PreviousRowManagerStep();
		
		EndReportExitStep endReportExitStep = new EndReportExitStep(); 
		
		//init steps
		initReportDataInitStep.init(mockAlgoInput, TEST_REPORT_CONTEXT); 
		extractDataInitStep.init(mockAlgoInput, TEST_REPORT_CONTEXT);
		startReportInitStep.init(mockAlgoInput, TEST_REPORT_CONTEXT); 
		
		columnHeaderInitStep.init(mockAlgoInput, TEST_REPORT_CONTEXT);
		computeColumnStep.init(mockAlgoInput, TEST_REPORT_CONTEXT);
		levelDetectorStep.init(mockAlgoInput, TEST_REPORT_CONTEXT);
		yTotalsOutput.init(mockAlgoInput, TEST_REPORT_CONTEXT);
		dataRowOutput.init(mockAlgoInput, TEST_REPORT_CONTEXT); 
		totalsCalculator.init(mockAlgoInput, TEST_REPORT_CONTEXT);
		previosGroupValuesManager.init(mockAlgoInput, TEST_REPORT_CONTEXT);
		
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
		computeColumnStep.exit(mockAlgoInput, TEST_REPORT_CONTEXT);
		levelDetectorStep.exit(mockAlgoInput, TEST_REPORT_CONTEXT);
		yTotalsOutput.exit(mockAlgoInput, TEST_REPORT_CONTEXT);
		dataRowOutput.exit(mockAlgoInput, TEST_REPORT_CONTEXT);
		totalsCalculator.exit(mockAlgoInput, TEST_REPORT_CONTEXT);
		previosGroupValuesManager.exit(mockAlgoInput, TEST_REPORT_CONTEXT);
		
		endReportExitStep.exit(mockAlgoInput, TEST_REPORT_CONTEXT);
		
		MatrixUtils.compareMatrices(Scenario1.EXPECTED_REPORT_COLUMNS_HEADER, 
									cumulativeReportOutput.getHeaderCellMatrix()); 
		
		CellProps[][] result = cumulativeReportOutput.getDataCellMatrix();
		MatrixUtils.compareMatrices(result, Scenario1.EXPECTED_OUTPUT);
	}
}
