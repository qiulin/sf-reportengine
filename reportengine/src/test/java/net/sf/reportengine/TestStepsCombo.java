/**
 * 
 */
package net.sf.reportengine;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.DefaultLoopThroughReportInputAlgo;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;
import net.sf.reportengine.core.algorithm.OpenLoopCloseInputAlgo;
import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.steps.ConfigReportOutputInitStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.core.steps.crosstab.ConstrDataColsForSecondProcessInitStep;
import net.sf.reportengine.core.steps.crosstab.ConstrGrpColsForSecondProcessInitStep;
import net.sf.reportengine.core.steps.crosstab.CrosstabHeaderOutputInitStep;
import net.sf.reportengine.core.steps.crosstab.DistinctValuesDetectorStep;
import net.sf.reportengine.core.steps.crosstab.GenerateCrosstabMetadataInitStep;
import net.sf.reportengine.core.steps.crosstab.IntermedRowMangerStep;
import net.sf.reportengine.core.steps.intermed.ConfigIntermedReportOutputInitStep;
import net.sf.reportengine.core.steps.intermed.ConstrIntermedDataColsInitStep;
import net.sf.reportengine.core.steps.intermed.ConstrIntermedGrpColsInitStep;
import net.sf.reportengine.core.steps.intermed.IntermedDataRowsOutputStep;
import net.sf.reportengine.core.steps.intermed.IntermedGroupLevelDetectorStep;
import net.sf.reportengine.core.steps.intermed.IntermedPreviousRowManagerStep;
import net.sf.reportengine.core.steps.intermed.IntermedReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.intermed.IntermedSetResultsExitStep;
import net.sf.reportengine.core.steps.intermed.IntermedTotalsCalculatorStep;
import net.sf.reportengine.core.steps.intermed.IntermedTotalsOutputStep;
import net.sf.reportengine.core.steps.neo.EndTableExitStep;
import net.sf.reportengine.core.steps.neo.StartTableInitStep;
import net.sf.reportengine.in.IntermediateCrosstabReportTableInput;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.neo.FreemarkerReportOutput;
import net.sf.reportengine.out.neo.HtmlReportOutput;
import net.sf.reportengine.out.neo.NewReportOutput;
import net.sf.reportengine.scenarios.ct.CtScenario1x1x1;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With0G2D;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.scenarios.ct.CtScenario4x3x1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.DistinctValuesHolder;
import net.sf.reportengine.util.IOKeys;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestStepsCombo  {
	
	
	
	@Test
	public void testExecuteScenario2x2x1xTForIntermediateReport() throws Exception{
			ReportOutput mockOutput = new HtmlOutput("target/intermediateReport2x2x1xT.html");  
			
			MultiStepAlgo algo = new DefaultLoopThroughReportInputAlgo(); 
			
			Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class); 
			mockAlgoInput.put(IOKeys.REPORT_INPUT, CtScenario2x2x1With1G1D.INPUT);
			//this output doesn't count because an internal output is built inside
			mockAlgoInput.put(IOKeys.REPORT_OUTPUT, mockOutput);
			mockAlgoInput.put(IOKeys.GROUP_COLS, CtScenario2x2x1With1G1D.GROUPING_COLUMNS);
			mockAlgoInput.put(IOKeys.DATA_COLS, CtScenario2x2x1With1G1D.DATA_COLUMNS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario2x2x1With1G1D.HEADER_ROWS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_DATA, CtScenario2x2x1With1G1D.CROSSTAB_DATA); 
			mockAlgoInput.put(IOKeys.SHOW_GRAND_TOTAL, true); 
			mockAlgoInput.put(IOKeys.SHOW_TOTALS, true); 
			//classUnderTest.setShowDataRows(true); 
			
			//algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConstrIntermedGrpColsInitStep());
			algo.addInitStep(new ConstrIntermedDataColsInitStep());
			
			//algo.addInitStep(new ConfigIntermedIOInitStep()); 
			algo.addInitStep(new ConfigIntermedReportOutputInitStep());
			
			//algo.addInitStep(new OpenReportIOInitStep());
			algo.addInitStep(new AlgorithmInitStep<String>() {
	    		public StepResult<String> init(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).open();
	    			return StepResult.NO_RESULT; 
	    		}
			});
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
			
	    	//only for debug
	    	//algorithm.addInitStep(new ColumnHeaderOutputInitStep("Intermediate report"));
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
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
	    	algo.addExitStep(new AlgorithmExitStep<String>() {
	    		public StepResult<String> exit(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).close();
	    			return StepResult.NO_RESULT; 
	    		}
			});
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
	    	
			Map<IOKeys, Object> result = algo.execute(mockAlgoInput);
			
			DistinctValuesHolder metadata = 
					(DistinctValuesHolder)result.get(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)result.get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportTableInput actualOutputReader = new IntermediateCrosstabReportTableInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportTableInput expectedOutputReader = 
					new IntermediateCrosstabReportTableInput(
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
			
			MultiStepAlgo algo = new DefaultLoopThroughReportInputAlgo(); 
			
			Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class); 
			mockAlgoInput.put(IOKeys.REPORT_INPUT, CtScenario1x3x1.INPUT);
			//this output doesn't count because an internal output is built inside
			mockAlgoInput.put(IOKeys.REPORT_OUTPUT, mockOutput);
			mockAlgoInput.put(IOKeys.GROUP_COLS, CtScenario1x3x1.GROUP_COLUMNS);
			mockAlgoInput.put(IOKeys.DATA_COLS, CtScenario1x3x1.DATA_COLUMNS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x3x1.HEADER_ROWS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_DATA, CtScenario1x3x1.CROSSTAB_DATA); 
			mockAlgoInput.put(IOKeys.SHOW_GRAND_TOTAL, true); 
			mockAlgoInput.put(IOKeys.SHOW_TOTALS, true); 
			
			//init steps
			//algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConstrIntermedDataColsInitStep());
			algo.addInitStep(new ConstrIntermedGrpColsInitStep());
			algo.addInitStep(new ConfigIntermedReportOutputInitStep()); 
			algo.addInitStep(new AlgorithmInitStep<String>() {
	    		public StepResult<String> init(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).open();
	    			return StepResult.NO_RESULT; 
	    		}
			});
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
	    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
	    	algo.addMainStep(new IntermedRowMangerStep());
	    	algo.addMainStep(new IntermedTotalsCalculatorStep());
	    	algo.addMainStep(new IntermedPreviousRowManagerStep());
	    	
	    	//exit steps
	    	algo.addExitStep(new AlgorithmExitStep<String>() {
	    		public StepResult<String> exit(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).close();
	    			return StepResult.NO_RESULT; 
	    		}
			}); 
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
	    	
			Map<IOKeys, Object> result = algo.execute(mockAlgoInput);
			
			DistinctValuesHolder metadata = 
					(DistinctValuesHolder)result.get(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)result.get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportTableInput actualOutputReader = new IntermediateCrosstabReportTableInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportTableInput expectedOutputReader = 
					new IntermediateCrosstabReportTableInput(
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
			
			MultiStepAlgo algo = new DefaultLoopThroughReportInputAlgo(); 
			Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
			
			mockAlgoInput.put(IOKeys.REPORT_INPUT, CtScenario1x3x1.INPUT);
			//this output doesn't count because an internal output is built inside
			mockAlgoInput.put(IOKeys.REPORT_OUTPUT, mockOutput);
			mockAlgoInput.put(IOKeys.GROUP_COLS, CtScenario1x3x1.GROUP_COLUMNS);
			mockAlgoInput.put(IOKeys.DATA_COLS, CtScenario1x3x1.DATA_COLUMNS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x3x1.HEADER_ROWS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_DATA, CtScenario1x3x1.CROSSTAB_DATA); 
			mockAlgoInput.put(IOKeys.SHOW_GRAND_TOTAL, false); 
			mockAlgoInput.put(IOKeys.SHOW_TOTALS, false); 
			
			//init steps
			//algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConstrIntermedDataColsInitStep());
			algo.addInitStep(new ConstrIntermedGrpColsInitStep());
			algo.addInitStep(new ConfigIntermedReportOutputInitStep());
			algo.addInitStep(new AlgorithmInitStep<String>() {
	    		public StepResult<String> init(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).open();
	    			return StepResult.NO_RESULT; 
	    		}
			});
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
	    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
	    	algo.addMainStep(new IntermedRowMangerStep());
	    	algo.addMainStep(new IntermedTotalsCalculatorStep());
	    	algo.addMainStep(new IntermedPreviousRowManagerStep());
	    	
	    	//exit steps
	    	algo.addExitStep(new AlgorithmExitStep<String>() {
	    		public StepResult<String> exit(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).close();
	    			return StepResult.NO_RESULT; 
	    		}
			});
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
			Map<IOKeys, Object> result = algo.execute(mockAlgoInput);
			
			DistinctValuesHolder metadata = 
					(DistinctValuesHolder)result.get(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)result.get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportTableInput actualOutputReader = new IntermediateCrosstabReportTableInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportTableInput expectedOutputReader = 
					new IntermediateCrosstabReportTableInput(
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
			
			MultiStepAlgo algo = new DefaultLoopThroughReportInputAlgo(); 
			Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
			mockAlgoInput.put(IOKeys.REPORT_INPUT, CtScenario1x1x1.INPUT);
			//this output doesn't count because an internal output is built inside
			mockAlgoInput.put(IOKeys.REPORT_OUTPUT, mockOutput);
			mockAlgoInput.put(IOKeys.GROUP_COLS, CtScenario1x1x1.GROUP_COLUMNS);
			mockAlgoInput.put(IOKeys.DATA_COLS, CtScenario1x1x1.DATA_COLUMNS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x1x1.ROW_HEADERS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_DATA, CtScenario1x1x1.CROSSTAB_DATA_WITH_TOTALS); 
			mockAlgoInput.put(IOKeys.SHOW_GRAND_TOTAL, true); 
			mockAlgoInput.put(IOKeys.SHOW_TOTALS, true); 
			
			//init steps
			//algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConstrIntermedDataColsInitStep());
			algo.addInitStep(new ConstrIntermedGrpColsInitStep());
			//algo.addInitStep(new ConfigIntermedIOInitStep()); 
			algo.addInitStep(new ConfigIntermedReportOutputInitStep()); 
			
			algo.addInitStep(new AlgorithmInitStep<String>() {
	    		public StepResult<String> init(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).open();
	    			return StepResult.NO_RESULT; 
	    		}
			});
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
	    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
	    	algo.addMainStep(new IntermedRowMangerStep());
	    	algo.addMainStep(new IntermedTotalsCalculatorStep());
	    	algo.addMainStep(new IntermedPreviousRowManagerStep());
	    	
	    	//exit steps
	    	algo.addExitStep(new AlgorithmExitStep<String>() {
	    		public StepResult<String> exit(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).close();
	    			return StepResult.NO_RESULT; 
	    		}
			});
	    	
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
			Map<IOKeys,Object> result = algo.execute(mockAlgoInput);
			
			DistinctValuesHolder metadata = 
					(DistinctValuesHolder)result.get(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)result.get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportTableInput actualOutputReader = new IntermediateCrosstabReportTableInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportTableInput expectedOutputReader = 
					new IntermediateCrosstabReportTableInput(
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
			
			MultiStepAlgo algo = new DefaultLoopThroughReportInputAlgo(); 
			Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
			mockAlgoInput.put(IOKeys.REPORT_INPUT, CtScenario4x3x1.INPUT);
			//this output doesn't count because an internal output is built inside
			mockAlgoInput.put(IOKeys.REPORT_OUTPUT, mockOutput);
			mockAlgoInput.put(IOKeys.GROUP_COLS, CtScenario4x3x1.GROUP_COLUMNS);
			mockAlgoInput.put(IOKeys.DATA_COLS, CtScenario4x3x1.DATA_COLUMNS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario4x3x1.HEADER_ROWS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_DATA, CtScenario4x3x1.CROSSTAB_DATA); 
			mockAlgoInput.put(IOKeys.SHOW_GRAND_TOTAL, true); 
			mockAlgoInput.put(IOKeys.SHOW_TOTALS, true); 
			
			//init steps
			//algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConstrIntermedDataColsInitStep());
			algo.addInitStep(new ConstrIntermedGrpColsInitStep());
			algo.addInitStep(new ConfigIntermedReportOutputInitStep());
			algo.addInitStep(new AlgorithmInitStep<String>() {
	    		public StepResult<String> init(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).open();
	    			return StepResult.NO_RESULT; 
	    		}
			});
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
	    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
	    	algo.addMainStep(new IntermedRowMangerStep());
	    	algo.addMainStep(new IntermedTotalsCalculatorStep());
	    	algo.addMainStep(new IntermedPreviousRowManagerStep());
	    	
	    	//exit steps
	    	algo.addExitStep(new AlgorithmExitStep<String>() {
	    		public StepResult<String> exit(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).close();
	    			return StepResult.NO_RESULT; 
	    		}
			}); 
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
			Map<IOKeys, Object> result = algo.execute(mockAlgoInput);
			
			DistinctValuesHolder metadata = 
					(DistinctValuesHolder)result.get(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)result.get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportTableInput actualOutputReader = new IntermediateCrosstabReportTableInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportTableInput expectedOutputReader = 
					new IntermediateCrosstabReportTableInput(
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
			MultiStepAlgo algo = new DefaultLoopThroughReportInputAlgo(); 
			
			Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
			mockAlgoInput.put(IOKeys.REPORT_INPUT, CtScenario4x3x1.INPUT);
			//this output doesn't count because an internal output is built inside
			mockAlgoInput.put(IOKeys.REPORT_OUTPUT, new HtmlOutput("target/intermediateReport4x3x1xNoTotals.html"));
			mockAlgoInput.put(IOKeys.GROUP_COLS, CtScenario4x3x1.GROUP_COLUMNS);
			mockAlgoInput.put(IOKeys.DATA_COLS, CtScenario4x3x1.DATA_COLUMNS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario4x3x1.HEADER_ROWS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_DATA, CtScenario4x3x1.CROSSTAB_DATA); 
			mockAlgoInput.put(IOKeys.SHOW_GRAND_TOTAL, false); 
			mockAlgoInput.put(IOKeys.SHOW_TOTALS, false); 
			
			//init steps
			//algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConstrIntermedDataColsInitStep());
			algo.addInitStep(new ConstrIntermedGrpColsInitStep());
			algo.addInitStep(new ConfigIntermedReportOutputInitStep());
			algo.addInitStep(new AlgorithmInitStep<String>() {
	    		public StepResult<String> init(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).open();
	    			return StepResult.NO_RESULT; 
	    		}
			});
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
	    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
	    	algo.addMainStep(new IntermedRowMangerStep());
	    	algo.addMainStep(new IntermedTotalsCalculatorStep());
	    	algo.addMainStep(new IntermedPreviousRowManagerStep());
	    	
	    	//exit steps
	    	algo.addExitStep(new AlgorithmExitStep<String>() {
	    		public StepResult<String> exit(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).close();
	    			return StepResult.NO_RESULT; 
	    		}
			});
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
			Map<IOKeys, Object> result = algo.execute(mockAlgoInput);
			
			DistinctValuesHolder metadata = 
					(DistinctValuesHolder)result.get(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)result.get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportTableInput actualOutputReader = new IntermediateCrosstabReportTableInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportTableInput expectedOutputReader = 
					new IntermediateCrosstabReportTableInput(
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
			MultiStepAlgo algo = new DefaultLoopThroughReportInputAlgo(); 
			
			Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
			mockAlgoInput.put(IOKeys.REPORT_INPUT, CtScenario2x2x1With0G2D.INPUT);
			//this output doesn't count because an internal output is built inside
			mockAlgoInput.put(IOKeys.REPORT_OUTPUT, new CellPropsArrayOutput());
			mockAlgoInput.put(IOKeys.GROUP_COLS, CtScenario2x2x1With0G2D.GROUPING_COLUMNS);
			mockAlgoInput.put(IOKeys.DATA_COLS, CtScenario2x2x1With0G2D.DATA_COLUMNS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario2x2x1With0G2D.HEADER_ROWS); 
			mockAlgoInput.put(IOKeys.CROSSTAB_DATA, CtScenario2x2x1With0G2D.CROSSTAB_DATA); 
			mockAlgoInput.put(IOKeys.SHOW_GRAND_TOTAL, false); 
			mockAlgoInput.put(IOKeys.SHOW_TOTALS, false); 
			
			//init steps
			//algo.addInitStep(new ConfigIntermedColsInitStep()); 
			algo.addInitStep(new ConstrIntermedDataColsInitStep());
			algo.addInitStep(new ConstrIntermedGrpColsInitStep());
			algo.addInitStep(new ConfigIntermedReportOutputInitStep());
			algo.addInitStep(new AlgorithmInitStep<String>() {
	    		public StepResult<String> init(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).open();
	    			return StepResult.NO_RESULT; 
	    		}
			}); 
			algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());//TODO: only when totals
	        
	    	//main steps
			algo.addMainStep(new DistinctValuesDetectorStep());
	    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
	    	algo.addMainStep(new IntermedRowMangerStep());
	    	algo.addMainStep(new IntermedTotalsCalculatorStep());
	    	algo.addMainStep(new IntermedPreviousRowManagerStep());
	    	
	    	//exit steps
	    	algo.addExitStep(new AlgorithmExitStep<String>() {
	    		public StepResult<String> exit(StepInput stepInput){
	    			((IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT)).close();
	    			return StepResult.NO_RESULT; 
	    		}
			}); 
	    	algo.addExitStep(new IntermedSetResultsExitStep()); 
	    	Map<IOKeys, Object> result = algo.execute(mockAlgoInput);
			
			DistinctValuesHolder metadata = 
					(DistinctValuesHolder)result.get(IOKeys.DISTINCT_VALUES_HOLDER); 
			assertNotNull(metadata);
			
			File intermedOutput = (File)result.get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
			assertNotNull(intermedOutput); 
			
			//checking the output
			IntermediateCrosstabReportTableInput actualOutputReader = new IntermediateCrosstabReportTableInput(new FileInputStream(intermedOutput));
			IntermediateCrosstabReportTableInput expectedOutputReader = 
					new IntermediateCrosstabReportTableInput(
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
	public void testExecuteScenario2x2x1xTWith1G1DForSecondReport()  throws IOException{
		//ReportOutput mockOutput = new HtmlOutput("target/SecondProcessorOut2x2x1xT.html"); 
		NewReportOutput mockOutput = new HtmlReportOutput(new FileWriter("target/SecondProcessorOut2x2x1xT.html")); 
		mockOutput.open(); 
		
		MultiStepAlgo algo = new OpenLoopCloseInputAlgo(){
			@Override protected TableInput buildReportInput(Map<IOKeys, Object> inputParams){
				File previousAlgoSerializedOutput = (File)inputParams.get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
				return new IntermediateCrosstabReportTableInput(previousAlgoSerializedOutput);
			}
		}; 
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		mockAlgoInput.put(IOKeys.REPORT_INPUT, null);//it doesn't matter, will be replaced
		mockAlgoInput.put(IOKeys.NEW_REPORT_OUTPUT, mockOutput);
		mockAlgoInput.put(IOKeys.GROUP_COLS, CtScenario2x2x1With1G1D.GROUPING_COLUMNS);
		mockAlgoInput.put(IOKeys.DATA_COLS, CtScenario2x2x1With1G1D.DATA_COLUMNS); 
		mockAlgoInput.put(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario2x2x1With1G1D.HEADER_ROWS); 
		mockAlgoInput.put(IOKeys.CROSSTAB_DATA, CtScenario2x2x1With1G1D.CROSSTAB_DATA); 
		mockAlgoInput.put(IOKeys.SHOW_GRAND_TOTAL, true); 
		mockAlgoInput.put(IOKeys.SHOW_TOTALS, true); 
		
		//mock values which usually come from the previous algorithm
		File fileFromPrevAlgo = new File("./src/test/resources/TestIntermediateInput2x2x1xT.rep"); 
		assertNotNull(fileFromPrevAlgo); 
		mockAlgoInput.put(IOKeys.INTERMEDIATE_OUTPUT_FILE, fileFromPrevAlgo); 
		mockAlgoInput.put(IOKeys.DISTINCT_VALUES_HOLDER, CtScenario2x2x1With1G1D.MOCK_DISTINCT_VALUES_HOLDER); 
		
		
		//adding steps to the algorithm
		algo.addInitStep(new GenerateCrosstabMetadataInitStep()); 
		algo.addInitStep(new ConstrDataColsForSecondProcessInitStep());
		algo.addInitStep(new ConstrGrpColsForSecondProcessInitStep());; 
		//algo.addInitStep(new ConfigReportOutputInitStep());
		
    	algo.addInitStep(new InitReportDataInitStep()); 
    	algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());
    	algo.addInitStep(new StartTableInitStep()); 
    	algo.addInitStep(new CrosstabHeaderOutputInitStep());
    	
    	//main steps
    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
    	algo.addMainStep(new IntermedTotalsOutputStep());
    	algo.addMainStep(new IntermedTotalsCalculatorStep());//this uses the internal data
    	algo.addMainStep(new IntermedDataRowsOutputStep());
        algo.addMainStep(new IntermedPreviousRowManagerStep());//uses internal data
        
        //exit steps
        algo.addExitStep(new EndTableExitStep()); 
        //algo.addExitStep(new CloseReportOutputExitStep()); 
        
        Map<IOKeys, Object> result = algo.execute(mockAlgoInput);
        
        mockOutput.close(); 
	}
	
	@Test
	public void testExecuteScenario2x2x1xNoTotalsWith0G2DForSecondReport() throws IOException{
		//ReportOutput mockOutput = new HtmlOutput("target/SecondProcessorOut2x2x1With0G2D.html");  
		NewReportOutput mockOutput = new HtmlReportOutput(new FileWriter("target/SecondProcessorOut2x2x1With0G2D.html")); 
		mockOutput.open();
		
		MultiStepAlgo algo = new OpenLoopCloseInputAlgo(){
			@Override protected TableInput buildReportInput(Map<IOKeys, Object> inputParams){
				File previousAlgoSerializedOutput = (File)inputParams.get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
				return new IntermediateCrosstabReportTableInput(previousAlgoSerializedOutput);
			}
		};  
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		mockAlgoInput.put(IOKeys.REPORT_INPUT, null);//it doesn't matter, will be replaced
		mockAlgoInput.put(IOKeys.NEW_REPORT_OUTPUT, mockOutput);
		mockAlgoInput.put(IOKeys.GROUP_COLS, CtScenario2x2x1With0G2D.GROUPING_COLUMNS);
		mockAlgoInput.put(IOKeys.DATA_COLS, CtScenario2x2x1With0G2D.DATA_COLUMNS); 
		mockAlgoInput.put(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario2x2x1With0G2D.HEADER_ROWS); 
		mockAlgoInput.put(IOKeys.CROSSTAB_DATA, CtScenario2x2x1With0G2D.CROSSTAB_DATA); 
		mockAlgoInput.put(IOKeys.SHOW_GRAND_TOTAL, false); 
		mockAlgoInput.put(IOKeys.SHOW_TOTALS, false); 
		
		//mock values which usually come from the previous algorithm
		File fileFromPrevAlgo = new File("./src/test/resources/TestIntermediateInput2x2x1With0G2D.rep"); 
		assertNotNull(fileFromPrevAlgo); 
		mockAlgoInput.put(IOKeys.INTERMEDIATE_OUTPUT_FILE, fileFromPrevAlgo); 
		mockAlgoInput.put(IOKeys.DISTINCT_VALUES_HOLDER, CtScenario2x2x1With0G2D.MOCK_DISTINCT_VALUES_HOLDER); 
		
		//adding steps to the algorithm
		algo.addInitStep(new GenerateCrosstabMetadataInitStep()); 
		algo.addInitStep(new ConstrDataColsForSecondProcessInitStep());
		algo.addInitStep(new ConstrGrpColsForSecondProcessInitStep()); 
		algo.addInitStep(new ConfigReportOutputInitStep()); 
		//algo.addInitStep(new OpenReportOutputInitStep());
    	algo.addInitStep(new InitReportDataInitStep()); 
    	algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());
    	algo.addInitStep(new StartTableInitStep()); 
    	algo.addInitStep(new CrosstabHeaderOutputInitStep());
    	
    	//main steps
    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
    	algo.addMainStep(new IntermedTotalsOutputStep());
    	algo.addMainStep(new IntermedTotalsCalculatorStep());//this uses the internal data
    	algo.addMainStep(new IntermedDataRowsOutputStep());
        algo.addMainStep(new IntermedPreviousRowManagerStep());//uses internal data
        
        //exit steps
        algo.addExitStep(new EndTableExitStep()); //uses original output
        //algo.addExitStep(new CloseReportOutputExitStep()); 
        
        Map<IOKeys, Object> result = algo.execute(mockAlgoInput);
        mockOutput.close();
	}
	
	@Test
	public void testExecuteScenario1x3x1xTForSecondReport() throws IOException{
		//ReportOutput mockOutput = new HtmlOutput("target/SecondProcessorOut1x3x1xT.html");  
		NewReportOutput mockOutput = new HtmlReportOutput(new FileWriter("target/SecondProcessorOut1x3x1xT.html")); 
		mockOutput.open();
		
		MultiStepAlgo algo = new OpenLoopCloseInputAlgo(){
			@Override protected TableInput buildReportInput(Map<IOKeys, Object> inputParams){
				File previousAlgoSerializedOutput = (File)inputParams.get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
				return new IntermediateCrosstabReportTableInput(previousAlgoSerializedOutput);
			}
		}; 
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		mockAlgoInput.put(IOKeys.REPORT_INPUT, null);//it doesn't matter, will be replaced
		mockAlgoInput.put(IOKeys.NEW_REPORT_OUTPUT, mockOutput);
		mockAlgoInput.put(IOKeys.GROUP_COLS, CtScenario1x3x1.GROUP_COLUMNS);
		mockAlgoInput.put(IOKeys.DATA_COLS, CtScenario1x3x1.DATA_COLUMNS); 
		mockAlgoInput.put(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x3x1.HEADER_ROWS); 
		mockAlgoInput.put(IOKeys.CROSSTAB_DATA, CtScenario1x3x1.CROSSTAB_DATA); 
		mockAlgoInput.put(IOKeys.SHOW_GRAND_TOTAL, true); 
		mockAlgoInput.put(IOKeys.SHOW_TOTALS, true); 
		
		//mock values which usually come from the previous algorithm
		File fileFromPrevAlgo = new File("./src/test/resources/TestIntermediateInput1x3x1xT.rep"); 
		assertNotNull(fileFromPrevAlgo); 
		mockAlgoInput.put(IOKeys.INTERMEDIATE_OUTPUT_FILE, fileFromPrevAlgo); 
		mockAlgoInput.put(IOKeys.DISTINCT_VALUES_HOLDER, CtScenario1x3x1.MOCK_DISTINCT_VALUES_HOLDER); 
		
		//adding steps to the algorithm
		algo.addInitStep(new GenerateCrosstabMetadataInitStep()); 
		algo.addInitStep(new ConstrDataColsForSecondProcessInitStep());
		algo.addInitStep(new ConstrGrpColsForSecondProcessInitStep());
		algo.addInitStep(new ConfigReportOutputInitStep()); 
		//algo.addInitStep(new OpenReportOutputInitStep()); 
    	algo.addInitStep(new InitReportDataInitStep()); 
    	algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());
    	algo.addInitStep(new StartTableInitStep()); 
    	algo.addInitStep(new CrosstabHeaderOutputInitStep());
    	
    	//main steps
    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
    	algo.addMainStep(new IntermedTotalsOutputStep());
    	algo.addMainStep(new IntermedTotalsCalculatorStep());//this uses the internal data
    	algo.addMainStep(new IntermedDataRowsOutputStep());
        algo.addMainStep(new IntermedPreviousRowManagerStep());//uses internal data
        
        algo.addExitStep(new EndTableExitStep()); //uses original output
        //algo.addExitStep(new CloseReportOutputExitStep()); 
        
        Map<IOKeys, Object> result = algo.execute(mockAlgoInput);
        mockOutput.close(); 
	}
	
	@Test
	public void testExecuteScenario1x3x1xNoTotalsForSecondReport() throws IOException{
		//ReportOutput mockOutput = new HtmlOutput("target/SecondProcessorOut1x3x1.html");  
		NewReportOutput mockOutput = new HtmlReportOutput(new FileWriter("target/SecondProcessorOut1x3x1.html")); 
		mockOutput.open();
		
		
		MultiStepAlgo algo = new OpenLoopCloseInputAlgo(){
			@Override protected TableInput buildReportInput(Map<IOKeys, Object> inputParams){
				File previousAlgoSerializedOutput = (File)inputParams.get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
				return new IntermediateCrosstabReportTableInput(previousAlgoSerializedOutput);
			}
		}; 
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		mockAlgoInput.put(IOKeys.REPORT_INPUT, null);//it doesn't matter, will be replaced
		mockAlgoInput.put(IOKeys.NEW_REPORT_OUTPUT, mockOutput);
		mockAlgoInput.put(IOKeys.GROUP_COLS, CtScenario1x3x1.GROUP_COLUMNS);
		mockAlgoInput.put(IOKeys.DATA_COLS, CtScenario1x3x1.DATA_COLUMNS); 
		mockAlgoInput.put(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x3x1.HEADER_ROWS); 
		mockAlgoInput.put(IOKeys.CROSSTAB_DATA, CtScenario1x3x1.CROSSTAB_DATA); 
		mockAlgoInput.put(IOKeys.SHOW_GRAND_TOTAL, false); 
		mockAlgoInput.put(IOKeys.SHOW_TOTALS, false); 
		
		//mock values which usually come from the previous algorithm
		File fileFromPrevAlgo = new File("./src/test/resources/TestIntermediateInput1x3x1.rep"); 
		assertNotNull(fileFromPrevAlgo); 
		mockAlgoInput.put(IOKeys.INTERMEDIATE_OUTPUT_FILE, fileFromPrevAlgo); 
		mockAlgoInput.put(IOKeys.DISTINCT_VALUES_HOLDER, CtScenario1x3x1.MOCK_DISTINCT_VALUES_HOLDER); 
		
		//adding steps to the algorithm
		algo.addInitStep(new GenerateCrosstabMetadataInitStep()); 
		algo.addInitStep(new ConstrDataColsForSecondProcessInitStep());
		algo.addInitStep(new ConstrGrpColsForSecondProcessInitStep());
		algo.addInitStep(new ConfigReportOutputInitStep()); 
		//algo.addInitStep(new OpenReportOutputInitStep()); 
    	algo.addInitStep(new InitReportDataInitStep()); 
    	
    	algo.addInitStep(new IntermedReportExtractTotalsDataInitStep());
    	
    	algo.addInitStep(new StartTableInitStep()); 
    	algo.addInitStep(new CrosstabHeaderOutputInitStep());
    	
    	//main steps
    	algo.addMainStep(new IntermedGroupLevelDetectorStep());
    	algo.addMainStep(new IntermedTotalsOutputStep());
    	algo.addMainStep(new IntermedTotalsCalculatorStep());
    	algo.addMainStep(new IntermedDataRowsOutputStep());
        algo.addMainStep(new IntermedPreviousRowManagerStep());
        
        //exit steps
        algo.addExitStep(new EndTableExitStep());
        //algo.addExitStep(new CloseReportOutputExitStep()); 
        
        Map<IOKeys, Object> result = algo.execute(mockAlgoInput);
        mockOutput.close();
	}
}
