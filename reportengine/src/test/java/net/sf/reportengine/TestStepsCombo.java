/**
 * 
 */
package net.sf.reportengine;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.LoopThroughReportInputAlgo;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.CloseReportIOExitStep;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.ConfigReportIOInitStep;
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
import net.sf.reportengine.core.steps.crosstab.ConfigCrosstabColumnsInitStep;
import net.sf.reportengine.core.steps.crosstab.ConfigCrosstabIOInitStep;
import net.sf.reportengine.core.steps.crosstab.CrosstabHeaderOutputInitStep;
import net.sf.reportengine.core.steps.crosstab.DistinctValuesDetectorStep;
import net.sf.reportengine.core.steps.crosstab.GenerateCrosstabMetadataInitStep;
import net.sf.reportengine.core.steps.crosstab.IntermedRowMangerStep;
import net.sf.reportengine.core.steps.intermed.ConfigIntermedColsInitStep;
import net.sf.reportengine.core.steps.intermed.ConfigIntermedIOInitStep;
import net.sf.reportengine.core.steps.intermed.IntermedDataRowsOutputStep;
import net.sf.reportengine.core.steps.intermed.IntermedGroupLevelDetectorStep;
import net.sf.reportengine.core.steps.intermed.IntermedPreviousRowManagerStep;
import net.sf.reportengine.core.steps.intermed.IntermedReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.intermed.IntermedSetResultsExitStep;
import net.sf.reportengine.core.steps.intermed.IntermedTotalsCalculatorStep;
import net.sf.reportengine.core.steps.intermed.IntermedTotalsOutputStep;
import net.sf.reportengine.in.IntermediateCrosstabReportInput;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.LoggerOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.ct.CtScenario1x1x1;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With0G2D;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.scenarios.ct.CtScenario4x3x1;
import net.sf.reportengine.util.IDistinctValuesHolder;
import net.sf.reportengine.util.IOKeys;
import net.sf.reportengine.util.MatrixUtils;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestStepsCombo  {
	
	@Test
	public void testCombo(){
		OutputDispatcher outputDispatcher = new OutputDispatcher(); 
		CellPropsArrayOutput cumulativeReportOutput = new CellPropsArrayOutput();
		outputDispatcher.registerOutput(cumulativeReportOutput);
		outputDispatcher.registerOutput(new LoggerOutput());
		
		AlgoContext mockContext = new DefaultAlgorithmContext(); 
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class); 
		
		mockAlgoInput.put(IOKeys.REPORT_INPUT, Scenario1.INPUT); 
		mockAlgoInput.put(IOKeys.REPORT_OUTPUT, outputDispatcher); 
		mockAlgoInput.put(IOKeys.SHOW_GRAND_TOTAL, Scenario1.SHOW_GRAND_TOTAL); 
		mockAlgoInput.put(IOKeys.SHOW_TOTALS, true); 
		mockAlgoInput.put(IOKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockAlgoInput.put(IOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS);
		
		//construct the steps
		InitReportDataInitStep initReportDataInitStep = new InitReportDataInitStep(); 
		FlatReportExtractTotalsDataInitStep extractDataInitStep = new FlatReportExtractTotalsDataInitStep(); 
		ConfigReportIOInitStep configIOStep = new ConfigReportIOInitStep(); 
		OpenReportIOInitStep openIOStep = new OpenReportIOInitStep(); 
		
		StartReportInitStep startReportInitStep = new StartReportInitStep(); 
		
		ColumnHeaderOutputInitStep columnHeaderInitStep = new ColumnHeaderOutputInitStep();
		//ComputeColumnValuesStep computeColumnStep = new ComputeColumnValuesStep();
		GroupLevelDetectorStep levelDetectorStep = new GroupLevelDetectorStep();
		FlatReportTotalsOutputStep yTotalsOutput = new FlatReportTotalsOutputStep();
		DataRowsOutputStep dataRowOutput = new DataRowsOutputStep();
		TotalsCalculatorStep totalsCalculator = new TotalsCalculatorStep();
		PreviousRowManagerStep previosGroupValuesManager = new PreviousRowManagerStep();
		
		EndReportExitStep endReportExitStep = new EndReportExitStep(); 
		
		//init steps
		initReportDataInitStep.init(mockAlgoInput, mockContext); 
		configIOStep.init(mockAlgoInput, mockContext); 
		openIOStep.init(mockAlgoInput, mockContext); 
		extractDataInitStep.init(mockAlgoInput, mockContext);
		startReportInitStep.init(mockAlgoInput, mockContext); 
		
		columnHeaderInitStep.init(mockAlgoInput, mockContext);
		//computeColumnStep.init(mockAlgoInput, TEST_REPORT_CONTEXT);
		levelDetectorStep.init(mockAlgoInput, mockContext);
		yTotalsOutput.init(mockAlgoInput, mockContext);
		dataRowOutput.init(mockAlgoInput, mockContext); 
		totalsCalculator.init(mockAlgoInput, mockContext);
		previosGroupValuesManager.init(mockAlgoInput, mockContext);
		
		//execute steps
		NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_1);
		//computeColumnStep.execute(dataRowEvent);
		levelDetectorStep.execute(dataRowEvent);
		yTotalsOutput.execute(dataRowEvent);
		dataRowOutput.execute(dataRowEvent);
		totalsCalculator.execute(dataRowEvent);
		previosGroupValuesManager.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_2);
		//computeColumnStep.execute(dataRowEvent);
		levelDetectorStep.execute(dataRowEvent);
		yTotalsOutput.execute(dataRowEvent);
		dataRowOutput.execute(dataRowEvent);
		totalsCalculator.execute(dataRowEvent);
		previosGroupValuesManager.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_3);
		//computeColumnStep.execute(dataRowEvent);
		levelDetectorStep.execute(dataRowEvent);
		yTotalsOutput.execute(dataRowEvent);
		dataRowOutput.execute(dataRowEvent);
		totalsCalculator.execute(dataRowEvent);
		previosGroupValuesManager.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_4);
		//computeColumnStep.execute(dataRowEvent);
		levelDetectorStep.execute(dataRowEvent);
		yTotalsOutput.execute(dataRowEvent);
		dataRowOutput.execute(dataRowEvent);
		totalsCalculator.execute(dataRowEvent);
		previosGroupValuesManager.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_5);
		//computeColumnStep.execute(dataRowEvent);
		levelDetectorStep.execute(dataRowEvent);
		yTotalsOutput.execute(dataRowEvent);
		dataRowOutput.execute(dataRowEvent);
		totalsCalculator.execute(dataRowEvent);
		previosGroupValuesManager.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_6);
		//computeColumnStep.execute(dataRowEvent);
		levelDetectorStep.execute(dataRowEvent);
		yTotalsOutput.execute(dataRowEvent);
		dataRowOutput.execute(dataRowEvent);
		totalsCalculator.execute(dataRowEvent);
		previosGroupValuesManager.execute(dataRowEvent);
		
		//exit
		//computeColumnStep.exit(mockAlgoInput, TEST_REPORT_CONTEXT);
		levelDetectorStep.exit();
		yTotalsOutput.exit();
		dataRowOutput.exit();
		totalsCalculator.exit();
		previosGroupValuesManager.exit();
		
		endReportExitStep.exit(mockAlgoInput, mockContext);
		
		MatrixUtils.compareMatrices(Scenario1.EXPECTED_REPORT_COLUMNS_HEADER, 
									cumulativeReportOutput.getHeaderCellMatrix()); 
		
		CellProps[][] result = cumulativeReportOutput.getDataCellMatrix();
		MatrixUtils.compareMatrices(result, Scenario1.EXPECTED_OUTPUT_UNSORTED);
	}
	
	@Test
	public void testExecuteScenario2x2x1xTForIntermediateReport() throws Exception{
			ReportOutput mockOutput = new HtmlOutput("target/intermediateReport2x2x1xT.html");  
			
			MultiStepAlgo algo = new LoopThroughReportInputAlgo(); 
			algo.addIn(IOKeys.REPORT_INPUT, CtScenario2x2x1With1G1D.INPUT);
			//this output doesn't count because an internal output is built inside
			algo.addIn(IOKeys.REPORT_OUTPUT, mockOutput);
			algo.addIn(IOKeys.GROUP_COLS, CtScenario2x2x1With1G1D.GROUPING_COLUMNS);
			algo.addIn(IOKeys.DATA_COLS, CtScenario2x2x1With1G1D.DATA_COLUMNS); 
			algo.addIn(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario2x2x1With1G1D.HEADER_ROWS); 
			algo.addIn(IOKeys.CROSSTAB_DATA, CtScenario2x2x1With1G1D.CROSSTAB_DATA); 
			algo.addIn(IOKeys.SHOW_GRAND_TOTAL, true); 
			algo.addIn(IOKeys.SHOW_TOTALS, true); 
			//classUnderTest.setShowDataRows(true); 
			
			algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConfigIntermedIOInitStep()); 
			algo.addInitStep(new OpenReportIOInitStep()); 
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
			algo.addInitStep(new StartReportInitStep()); 
	    	//only for debug
	    	//algorithm.addInitStep(new ColumnHeaderOutputInitStep("Intermediate report"));
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
	    	//algorithm.addMainStep(new ComputeColumnValuesStep());
	    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
	    	
	    	//only for debug 
	    	//if( getShowTotals() || getShowGrandTotal()){
	    	//algorithm.addMainStep(new FlatReportTotalsOutputStep());
			//}
	    	algo.addMainStep(new IntermedRowMangerStep());
	    	algo.addMainStep(new IntermedTotalsCalculatorStep());
	    	
	    	//only for debug 
	    	//algorithm.addMainStep(new DataRowsOutputStep());
	    	algo.addMainStep(new IntermedPreviousRowManagerStep());
	    	algo.addExitStep(new EndReportExitStep()); 
	    	algo.addExitStep(new CloseReportIOExitStep()); 
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
			algo.execute();
			
			IDistinctValuesHolder metadata = 
					(IDistinctValuesHolder)algo.getResult(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)algo.getResult(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportInput actualOutputReader = new IntermediateCrosstabReportInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportInput expectedOutputReader = 
					new IntermediateCrosstabReportInput(
							ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput2x2x1xT.rep"));
			
			actualOutputReader.open(); 
			expectedOutputReader.open(); 
			
			while(expectedOutputReader.hasMoreRows()){
				Assert.assertTrue(actualOutputReader.hasMoreRows());
				Assert.assertEquals(expectedOutputReader.nextRow(), actualOutputReader.nextRow()); 
				
			}
			actualOutputReader.close();
			expectedOutputReader.close(); 
	}
	
	@Test
	public void testExecuteScenario1x3x1xTForIntermediateReport() throws Exception{
			ReportOutput mockOutput = new HtmlOutput("target/intermediateReport1x3x1xT.html");  
			
			MultiStepAlgo algo = new LoopThroughReportInputAlgo(); 
			algo.addIn(IOKeys.REPORT_INPUT, CtScenario1x3x1.INPUT);
			//this output doesn't count because an internal output is built inside
			algo.addIn(IOKeys.REPORT_OUTPUT, mockOutput);
			algo.addIn(IOKeys.GROUP_COLS, CtScenario1x3x1.GROUP_COLUMNS);
			algo.addIn(IOKeys.DATA_COLS, CtScenario1x3x1.DATA_COLUMNS); 
			algo.addIn(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x3x1.HEADER_ROWS); 
			algo.addIn(IOKeys.CROSSTAB_DATA, CtScenario1x3x1.CROSSTAB_DATA); 
			algo.addIn(IOKeys.SHOW_GRAND_TOTAL, true); 
			algo.addIn(IOKeys.SHOW_TOTALS, true); 
			
			//init steps
			algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConfigIntermedIOInitStep()); 
			algo.addInitStep(new OpenReportIOInitStep()); 
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
			algo.addInitStep(new StartReportInitStep()); 
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
	    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
	    	algo.addMainStep(new IntermedRowMangerStep());
	    	algo.addMainStep(new IntermedTotalsCalculatorStep());
	    	algo.addMainStep(new IntermedPreviousRowManagerStep());
	    	
	    	//exit steps
	    	algo.addExitStep(new EndReportExitStep()); 
	    	algo.addExitStep(new CloseReportIOExitStep()); 
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
			algo.execute();
			
			IDistinctValuesHolder metadata = 
					(IDistinctValuesHolder)algo.getResult(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)algo.getResult(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportInput actualOutputReader = new IntermediateCrosstabReportInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportInput expectedOutputReader = 
					new IntermediateCrosstabReportInput(
							ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput1x3x1xT.rep"));
			
			actualOutputReader.open(); 
			expectedOutputReader.open(); 
			
			while(expectedOutputReader.hasMoreRows()){
				Assert.assertTrue(actualOutputReader.hasMoreRows());
				Assert.assertEquals(expectedOutputReader.nextRow(), actualOutputReader.nextRow()); 
				
			}
			actualOutputReader.close();
			expectedOutputReader.close(); 
	}
	
	@Test
	public void testExecuteScenario1x3x1xNoTotalsForIntermediateReport() throws Exception{
			ReportOutput mockOutput = new HtmlOutput("target/intermediateReport1x3x1xNoTotals.html");  
			
			MultiStepAlgo algo = new LoopThroughReportInputAlgo(); 
			algo.addIn(IOKeys.REPORT_INPUT, CtScenario1x3x1.INPUT);
			//this output doesn't count because an internal output is built inside
			algo.addIn(IOKeys.REPORT_OUTPUT, mockOutput);
			algo.addIn(IOKeys.GROUP_COLS, CtScenario1x3x1.GROUP_COLUMNS);
			algo.addIn(IOKeys.DATA_COLS, CtScenario1x3x1.DATA_COLUMNS); 
			algo.addIn(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x3x1.HEADER_ROWS); 
			algo.addIn(IOKeys.CROSSTAB_DATA, CtScenario1x3x1.CROSSTAB_DATA); 
			algo.addIn(IOKeys.SHOW_GRAND_TOTAL, false); 
			algo.addIn(IOKeys.SHOW_TOTALS, false); 
			
			//init steps
			algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConfigIntermedIOInitStep()); 
			algo.addInitStep(new OpenReportIOInitStep());
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
			algo.addInitStep(new StartReportInitStep()); 
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
	    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
	    	algo.addMainStep(new IntermedRowMangerStep());
	    	algo.addMainStep(new IntermedTotalsCalculatorStep());
	    	algo.addMainStep(new IntermedPreviousRowManagerStep());
	    	
	    	//exit steps
	    	algo.addExitStep(new EndReportExitStep()); 
	    	algo.addExitStep(new CloseReportIOExitStep()); 
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
			algo.execute();
			
			IDistinctValuesHolder metadata = 
					(IDistinctValuesHolder)algo.getResult(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)algo.getResult(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportInput actualOutputReader = new IntermediateCrosstabReportInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportInput expectedOutputReader = 
					new IntermediateCrosstabReportInput(
							ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput1x3x1.rep"));
			
			actualOutputReader.open(); 
			expectedOutputReader.open(); 
			
			while(expectedOutputReader.hasMoreRows()){
				Assert.assertTrue(actualOutputReader.hasMoreRows());
				Assert.assertEquals(expectedOutputReader.nextRow(), actualOutputReader.nextRow()); 
				
			}
			actualOutputReader.close();
			expectedOutputReader.close(); 
	}
	
	@Test
	public void testExecuteScenario1x1x1xTForIntermediateReport() throws Exception{
			ReportOutput mockOutput = new HtmlOutput("target/intermediateReport1x1x1xT.html");  
			
			MultiStepAlgo algo = new LoopThroughReportInputAlgo(); 
			algo.addIn(IOKeys.REPORT_INPUT, CtScenario1x1x1.INPUT);
			//this output doesn't count because an internal output is built inside
			algo.addIn(IOKeys.REPORT_OUTPUT, mockOutput);
			algo.addIn(IOKeys.GROUP_COLS, CtScenario1x1x1.GROUP_COLUMNS);
			algo.addIn(IOKeys.DATA_COLS, CtScenario1x1x1.DATA_COLUMNS); 
			algo.addIn(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x1x1.ROW_HEADERS); 
			algo.addIn(IOKeys.CROSSTAB_DATA, CtScenario1x1x1.CROSSTAB_DATA_WITH_TOTALS); 
			algo.addIn(IOKeys.SHOW_GRAND_TOTAL, true); 
			algo.addIn(IOKeys.SHOW_TOTALS, true); 
			
			//init steps
			algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConfigIntermedIOInitStep()); 
			algo.addInitStep(new OpenReportIOInitStep()); 
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
			algo.addInitStep(new StartReportInitStep()); 
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
	    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
	    	algo.addMainStep(new IntermedRowMangerStep());
	    	algo.addMainStep(new IntermedTotalsCalculatorStep());
	    	algo.addMainStep(new IntermedPreviousRowManagerStep());
	    	
	    	//exit steps
	    	algo.addExitStep(new EndReportExitStep()); 
	    	algo.addExitStep(new CloseReportIOExitStep()); 
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
			algo.execute();
			
			IDistinctValuesHolder metadata = 
					(IDistinctValuesHolder)algo.getResult(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)algo.getResult(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportInput actualOutputReader = new IntermediateCrosstabReportInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportInput expectedOutputReader = 
					new IntermediateCrosstabReportInput(
							ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput1x1x1xT.rep"));
			
			actualOutputReader.open(); 
			expectedOutputReader.open(); 
			
			while(expectedOutputReader.hasMoreRows()){
				Assert.assertTrue(actualOutputReader.hasMoreRows());
				Assert.assertEquals(expectedOutputReader.nextRow(), actualOutputReader.nextRow()); 
			}
			actualOutputReader.close();
			expectedOutputReader.close(); 
	}
	
	@Test
	public void testExecuteScenario4x3x1xTForIntermediateReport() throws Exception{
			ReportOutput mockOutput = new HtmlOutput("target/intermediateReport4x3x1xT.html");  
			
			MultiStepAlgo algo = new LoopThroughReportInputAlgo(); 
			algo.addIn(IOKeys.REPORT_INPUT, CtScenario4x3x1.INPUT);
			//this output doesn't count because an internal output is built inside
			algo.addIn(IOKeys.REPORT_OUTPUT, mockOutput);
			algo.addIn(IOKeys.GROUP_COLS, CtScenario4x3x1.GROUP_COLUMNS);
			algo.addIn(IOKeys.DATA_COLS, CtScenario4x3x1.DATA_COLUMNS); 
			algo.addIn(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario4x3x1.HEADER_ROWS); 
			algo.addIn(IOKeys.CROSSTAB_DATA, CtScenario4x3x1.CROSSTAB_DATA); 
			algo.addIn(IOKeys.SHOW_GRAND_TOTAL, true); 
			algo.addIn(IOKeys.SHOW_TOTALS, true); 
			
			//init steps
			algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConfigIntermedIOInitStep()); 
			algo.addInitStep(new OpenReportIOInitStep()); 
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
			algo.addInitStep(new StartReportInitStep()); 
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
	    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
	    	algo.addMainStep(new IntermedRowMangerStep());
	    	algo.addMainStep(new IntermedTotalsCalculatorStep());
	    	algo.addMainStep(new IntermedPreviousRowManagerStep());
	    	
	    	//exit steps
	    	algo.addExitStep(new EndReportExitStep()); 
	    	algo.addExitStep(new CloseReportIOExitStep()); 
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
			algo.execute();
			
			IDistinctValuesHolder metadata = 
					(IDistinctValuesHolder)algo.getResult(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)algo.getResult(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportInput actualOutputReader = new IntermediateCrosstabReportInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportInput expectedOutputReader = 
					new IntermediateCrosstabReportInput(
							ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput4x3x1xT.rep"));
			
			actualOutputReader.open(); 
			expectedOutputReader.open(); 
			
			while(expectedOutputReader.hasMoreRows()){
				Assert.assertTrue(actualOutputReader.hasMoreRows());
				Assert.assertEquals(expectedOutputReader.nextRow(), actualOutputReader.nextRow()); 
			}
			actualOutputReader.close();
			expectedOutputReader.close(); 
	}
	
	@Test
	public void testExecuteScenario4x3x1xNoTotalsForIntermediateReport() throws Exception{
			MultiStepAlgo algo = new LoopThroughReportInputAlgo(); 
			algo.addIn(IOKeys.REPORT_INPUT, CtScenario4x3x1.INPUT);
			//this output doesn't count because an internal output is built inside
			algo.addIn(IOKeys.REPORT_OUTPUT, new HtmlOutput("target/intermediateReport4x3x1xNoTotals.html"));
			algo.addIn(IOKeys.GROUP_COLS, CtScenario4x3x1.GROUP_COLUMNS);
			algo.addIn(IOKeys.DATA_COLS, CtScenario4x3x1.DATA_COLUMNS); 
			algo.addIn(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario4x3x1.HEADER_ROWS); 
			algo.addIn(IOKeys.CROSSTAB_DATA, CtScenario4x3x1.CROSSTAB_DATA); 
			algo.addIn(IOKeys.SHOW_GRAND_TOTAL, false); 
			algo.addIn(IOKeys.SHOW_TOTALS, false); 
			
			//init steps
			algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConfigIntermedIOInitStep()); 
			algo.addInitStep(new OpenReportIOInitStep()); 
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
			algo.addInitStep(new StartReportInitStep()); 
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
	    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
	    	algo.addMainStep(new IntermedRowMangerStep());
	    	algo.addMainStep(new IntermedTotalsCalculatorStep());
	    	algo.addMainStep(new IntermedPreviousRowManagerStep());
	    	
	    	//exit steps
	    	algo.addExitStep(new EndReportExitStep()); 
	    	algo.addExitStep(new CloseReportIOExitStep());
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
			algo.execute();
			
			IDistinctValuesHolder metadata = 
					(IDistinctValuesHolder)algo.getResult(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)algo.getResult(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportInput actualOutputReader = new IntermediateCrosstabReportInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportInput expectedOutputReader = 
					new IntermediateCrosstabReportInput(
							ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput4x3x1.rep"));
			
			actualOutputReader.open(); 
			expectedOutputReader.open(); 
			
			while(expectedOutputReader.hasMoreRows()){
				Assert.assertTrue(actualOutputReader.hasMoreRows());
				Assert.assertEquals(expectedOutputReader.nextRow(), actualOutputReader.nextRow()); 
			}
			actualOutputReader.close();
			expectedOutputReader.close(); 
	}
	
	@Test
	public void testExecuteScenario2x2x1With0G2DxNoTotalsForIntermediateReport() throws Exception{
			MultiStepAlgo algo = new LoopThroughReportInputAlgo(); 
			algo.addIn(IOKeys.REPORT_INPUT, CtScenario2x2x1With0G2D.INPUT);
			//this output doesn't count because an internal output is built inside
			algo.addIn(IOKeys.REPORT_OUTPUT, new CellPropsArrayOutput());
			algo.addIn(IOKeys.GROUP_COLS, CtScenario2x2x1With0G2D.GROUPING_COLUMNS);
			algo.addIn(IOKeys.DATA_COLS, CtScenario2x2x1With0G2D.DATA_COLUMNS); 
			algo.addIn(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario2x2x1With0G2D.HEADER_ROWS); 
			algo.addIn(IOKeys.CROSSTAB_DATA, CtScenario2x2x1With0G2D.CROSSTAB_DATA); 
			algo.addIn(IOKeys.SHOW_GRAND_TOTAL, false); 
			algo.addIn(IOKeys.SHOW_TOTALS, false); 
			
			//init steps
			algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConfigIntermedIOInitStep()); 
			algo.addInitStep(new OpenReportIOInitStep()); 
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
			algo.addInitStep(new StartReportInitStep()); 
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
	    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
	    	algo.addMainStep(new IntermedRowMangerStep());
	    	algo.addMainStep(new IntermedTotalsCalculatorStep());
	    	algo.addMainStep(new IntermedPreviousRowManagerStep());
	    	
	    	//exit steps
	    	algo.addExitStep(new EndReportExitStep()); 
	    	algo.addExitStep(new CloseReportIOExitStep()); 
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
			algo.execute();
			
			IDistinctValuesHolder metadata = 
					(IDistinctValuesHolder)algo.getResult(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)algo.getResult(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportInput actualOutputReader = new IntermediateCrosstabReportInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportInput expectedOutputReader = 
					new IntermediateCrosstabReportInput(
							ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput2x2x1With0G2D.rep"));
			
			actualOutputReader.open(); 
			expectedOutputReader.open(); 
			
			while(expectedOutputReader.hasMoreRows()){
				Assert.assertTrue(actualOutputReader.hasMoreRows());
				Assert.assertEquals(expectedOutputReader.nextRow(), actualOutputReader.nextRow()); 
			}
			actualOutputReader.close();
			expectedOutputReader.close(); 
	}
	
	@Test
	public void testExecuteScenario2x2x1xTWith1G1DForSecondReport(){
		ReportOutput mockOutput = new HtmlOutput("target/SecondProcessorOut2x2x1xT.html");  
		
		MultiStepAlgo algo = new LoopThroughReportInputAlgo(); 
		algo.addIn(IOKeys.REPORT_INPUT, null);//it doesn't matter, will be replaced
		algo.addIn(IOKeys.REPORT_OUTPUT, mockOutput);
		algo.addIn(IOKeys.GROUP_COLS, CtScenario2x2x1With1G1D.GROUPING_COLUMNS);
		algo.addIn(IOKeys.DATA_COLS, CtScenario2x2x1With1G1D.DATA_COLUMNS); 
		algo.addIn(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario2x2x1With1G1D.HEADER_ROWS); 
		algo.addIn(IOKeys.CROSSTAB_DATA, CtScenario2x2x1With1G1D.CROSSTAB_DATA); 
		algo.addIn(IOKeys.SHOW_GRAND_TOTAL, true); 
		algo.addIn(IOKeys.SHOW_TOTALS, true); 
		
		//mock values which usually come from the previous algorithm
		File fileFromPrevAlgo = new File("./src/test/resources/TestIntermediateInput2x2x1xT.rep"); 
		assertNotNull(fileFromPrevAlgo); 
		algo.addIn(IOKeys.INTERMEDIATE_OUTPUT_FILE, fileFromPrevAlgo); 
		algo.addIn(IOKeys.DISTINCT_VALUES_HOLDER, CtScenario2x2x1With1G1D.MOCK_DISTINCT_VALUES_HOLDER); 
		
		
		//adding steps to the algorithm
		algo.addInitStep(new GenerateCrosstabMetadataInitStep()); 
		algo.addInitStep(new ConfigCrosstabColumnsInitStep()); 
		algo.addInitStep(new ConfigCrosstabIOInitStep()); 
		algo.addInitStep(new OpenReportIOInitStep()); 
    	algo.addInitStep(new InitReportDataInitStep()); 
    	
    	algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());
    	
    	algo.addInitStep(new StartReportInitStep()); 
    	algo.addInitStep(new CrosstabHeaderOutputInitStep());
    	
    	//algorithm.addMainStep(new ComputeColumnValuesStep());
    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
    	algo.addMainStep(new IntermedTotalsOutputStep());
    	algo.addMainStep(new IntermedTotalsCalculatorStep());//this uses the internal data
    	algo.addMainStep(new IntermedDataRowsOutputStep());
        algo.addMainStep(new IntermedPreviousRowManagerStep());//uses internal data
        
        algo.addExitStep(new EndReportExitStep()); //uses original output
        algo.addExitStep(new CloseReportIOExitStep()); 
        
        algo.execute();
        
	}
	
	@Test
	public void testExecuteScenario2x2x1xNoTotalsWith0G2DForSecondReport(){
		ReportOutput mockOutput = new HtmlOutput("target/SecondProcessorOut2x2x1With0G2D.html");  
		
		MultiStepAlgo algo = new LoopThroughReportInputAlgo(); 
		algo.addIn(IOKeys.REPORT_INPUT, null);//it doesn't matter, will be replaced
		algo.addIn(IOKeys.REPORT_OUTPUT, mockOutput);
		algo.addIn(IOKeys.GROUP_COLS, CtScenario2x2x1With0G2D.GROUPING_COLUMNS);
		algo.addIn(IOKeys.DATA_COLS, CtScenario2x2x1With0G2D.DATA_COLUMNS); 
		algo.addIn(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario2x2x1With0G2D.HEADER_ROWS); 
		algo.addIn(IOKeys.CROSSTAB_DATA, CtScenario2x2x1With0G2D.CROSSTAB_DATA); 
		algo.addIn(IOKeys.SHOW_GRAND_TOTAL, false); 
		algo.addIn(IOKeys.SHOW_TOTALS, false); 
		
		//mock values which usually come from the previous algorithm
		File fileFromPrevAlgo = new File("./src/test/resources/TestIntermediateInput2x2x1With0G2D.rep"); 
		assertNotNull(fileFromPrevAlgo); 
		algo.addIn(IOKeys.INTERMEDIATE_OUTPUT_FILE, fileFromPrevAlgo); 
		algo.addIn(IOKeys.DISTINCT_VALUES_HOLDER, CtScenario2x2x1With0G2D.MOCK_DISTINCT_VALUES_HOLDER); 
		
		//adding steps to the algorithm
		algo.addInitStep(new GenerateCrosstabMetadataInitStep()); 
		algo.addInitStep(new ConfigCrosstabColumnsInitStep()); 
		algo.addInitStep(new ConfigCrosstabIOInitStep()); 
		algo.addInitStep(new OpenReportIOInitStep());
    	algo.addInitStep(new InitReportDataInitStep()); 
    	
    	algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());
    	
    	algo.addInitStep(new StartReportInitStep()); 
    	algo.addInitStep(new CrosstabHeaderOutputInitStep());
    	
    	//algorithm.addMainStep(new ComputeColumnValuesStep());
    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
    	algo.addMainStep(new IntermedTotalsOutputStep());
    	algo.addMainStep(new IntermedTotalsCalculatorStep());//this uses the internal data
    	algo.addMainStep(new IntermedDataRowsOutputStep());
        algo.addMainStep(new IntermedPreviousRowManagerStep());//uses internal data
        
        algo.addExitStep(new EndReportExitStep()); //uses original output
        algo.addExitStep(new CloseReportIOExitStep()); 
        
        algo.execute();
        
	}
	
	@Test
	public void testExecuteScenario1x3x1xTForSecondReport(){
		ReportOutput mockOutput = new HtmlOutput("target/SecondProcessorOut1x3x1xT.html");  
		
		MultiStepAlgo algo = new LoopThroughReportInputAlgo(); 
		algo.addIn(IOKeys.REPORT_INPUT, null);//it doesn't matter, will be replaced
		algo.addIn(IOKeys.REPORT_OUTPUT, mockOutput);
		algo.addIn(IOKeys.GROUP_COLS, CtScenario1x3x1.GROUP_COLUMNS);
		algo.addIn(IOKeys.DATA_COLS, CtScenario1x3x1.DATA_COLUMNS); 
		algo.addIn(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x3x1.HEADER_ROWS); 
		algo.addIn(IOKeys.CROSSTAB_DATA, CtScenario1x3x1.CROSSTAB_DATA); 
		algo.addIn(IOKeys.SHOW_GRAND_TOTAL, true); 
		algo.addIn(IOKeys.SHOW_TOTALS, true); 
		
		//mock values which usually come from the previous algorithm
		File fileFromPrevAlgo = new File("./src/test/resources/TestIntermediateInput1x3x1xT.rep"); 
		assertNotNull(fileFromPrevAlgo); 
		algo.addIn(IOKeys.INTERMEDIATE_OUTPUT_FILE, fileFromPrevAlgo); 
		algo.addIn(IOKeys.DISTINCT_VALUES_HOLDER, CtScenario1x3x1.MOCK_DISTINCT_VALUES_HOLDER); 
		
		//adding steps to the algorithm
		algo.addInitStep(new GenerateCrosstabMetadataInitStep()); 
		algo.addInitStep(new ConfigCrosstabColumnsInitStep()); 
		algo.addInitStep(new ConfigCrosstabIOInitStep()); 
		algo.addInitStep(new OpenReportIOInitStep()); 
    	algo.addInitStep(new InitReportDataInitStep()); 
    	
    	algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());
    	
    	algo.addInitStep(new StartReportInitStep()); 
    	algo.addInitStep(new CrosstabHeaderOutputInitStep());
    	
    	//algorithm.addMainStep(new ComputeColumnValuesStep());
    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
    	algo.addMainStep(new IntermedTotalsOutputStep());
    	algo.addMainStep(new IntermedTotalsCalculatorStep());//this uses the internal data
    	algo.addMainStep(new IntermedDataRowsOutputStep());
        algo.addMainStep(new IntermedPreviousRowManagerStep());//uses internal data
        
        algo.addExitStep(new EndReportExitStep()); //uses original output
        algo.addExitStep(new CloseReportIOExitStep()); 
        
        algo.execute();
        
	}
	
	@Test
	public void testExecuteScenario1x3x1xNoTotalsForSecondReport(){
		ReportOutput mockOutput = new HtmlOutput("target/SecondProcessorOut1x3x1.html");  
		
		MultiStepAlgo algo = new LoopThroughReportInputAlgo(); 
		algo.addIn(IOKeys.REPORT_INPUT, null);//it doesn't matter, will be replaced
		algo.addIn(IOKeys.REPORT_OUTPUT, mockOutput);
		algo.addIn(IOKeys.GROUP_COLS, CtScenario1x3x1.GROUP_COLUMNS);
		algo.addIn(IOKeys.DATA_COLS, CtScenario1x3x1.DATA_COLUMNS); 
		algo.addIn(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x3x1.HEADER_ROWS); 
		algo.addIn(IOKeys.CROSSTAB_DATA, CtScenario1x3x1.CROSSTAB_DATA); 
		algo.addIn(IOKeys.SHOW_GRAND_TOTAL, false); 
		algo.addIn(IOKeys.SHOW_TOTALS, false); 
		
		//mock values which usually come from the previous algorithm
		File fileFromPrevAlgo = new File("./src/test/resources/TestIntermediateInput1x3x1.rep"); 
		assertNotNull(fileFromPrevAlgo); 
		algo.addIn(IOKeys.INTERMEDIATE_OUTPUT_FILE, fileFromPrevAlgo); 
		algo.addIn(IOKeys.DISTINCT_VALUES_HOLDER, CtScenario1x3x1.MOCK_DISTINCT_VALUES_HOLDER); 
		
		//adding steps to the algorithm
		algo.addInitStep(new GenerateCrosstabMetadataInitStep()); 
		algo.addInitStep(new ConfigCrosstabColumnsInitStep()); 
		algo.addInitStep(new ConfigCrosstabIOInitStep()); 
		algo.addInitStep(new OpenReportIOInitStep()); 
    	algo.addInitStep(new InitReportDataInitStep()); 
    	
    	algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());
    	
    	algo.addInitStep(new StartReportInitStep()); 
    	algo.addInitStep(new CrosstabHeaderOutputInitStep());
    	
    	//algorithm.addMainStep(new ComputeColumnValuesStep());
    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
    	algo.addMainStep(new IntermedTotalsOutputStep());
    	algo.addMainStep(new IntermedTotalsCalculatorStep());
    	algo.addMainStep(new IntermedDataRowsOutputStep());
        algo.addMainStep(new IntermedPreviousRowManagerStep());
        
        algo.addExitStep(new EndReportExitStep()); //uses original output
        algo.addExitStep(new CloseReportIOExitStep()); 
        
        algo.execute();
	}
}
