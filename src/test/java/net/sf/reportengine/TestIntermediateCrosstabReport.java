package net.sf.reportengine;

import java.io.FileNotFoundException;

import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.steps.crosstab.CrosstabDistinctValuesDetectorStep;
import net.sf.reportengine.core.steps.crosstab.IntermComputedDataList;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalGroupValuesList;
import net.sf.reportengine.core.steps.crosstab.IntermComputedTotalsList;
import net.sf.reportengine.core.steps.crosstab.IntermediateReportRow;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.out.MemoryOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.scenarios.ct.CtScenario1x1x1;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1;
import net.sf.reportengine.scenarios.ct.CtScenario4x3x1;
import net.sf.reportengine.test.ReportengineTC;
import net.sf.reportengine.util.DistinctValuesHolder;

public class TestIntermediateCrosstabReport extends ReportengineTC {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testTransfomCrosstabColsInIntermediateColsScenario2x2x1(){
		IGroupColumn[] result = new IntermediateCrosstabReport(1,1).transformGroupingCrosstabConfigInFlatReportConfig(CtScenario2x2x1.GROUPING_COLUMNS, 
																							CtScenario2x2x1.DATA_COLUMNS, 
																							CtScenario2x2x1.HEADER_ROWS);
		assertNotNull(result); 
		assertEquals(3, result.length); 
		assertTrue(result[0] instanceof DefaultGroupColumn); 
		assertEquals(0, result[0].getGroupingLevel()); 
		assertTrue(result[1] instanceof IntermediateCrosstabReport.IntermGroupColFromCtDataCol); 
		assertEquals(1, result[1].getGroupingLevel()); 
		assertTrue(result[2] instanceof IntermediateCrosstabReport.IntermGroupColFromHeaderRow);
		assertEquals(2, result[2].getGroupingLevel()); 
	}
	
	public void testTransfomCrosstabColsInIntermediateColsScenario1x3x1(){
		IGroupColumn[] result = new IntermediateCrosstabReport(0,1).transformGroupingCrosstabConfigInFlatReportConfig(
				CtScenario1x3x1.GROUP_COLUMNS, 
				CtScenario1x3x1.DATA_COLUMNS, 
				CtScenario1x3x1.HEADER_ROWS);
		assertNotNull(result); 
		assertEquals(3, result.length); 
		assertTrue(result[0] instanceof IntermediateCrosstabReport.IntermGroupColFromCtDataCol); 
		assertEquals(0, result[0].getGroupingLevel()); 
		assertTrue(result[1] instanceof IntermediateCrosstabReport.IntermGroupColFromHeaderRow); 
		assertEquals(1, result[1].getGroupingLevel()); 
		assertTrue(result[2] instanceof IntermediateCrosstabReport.IntermGroupColFromHeaderRow);
		assertEquals(2, result[2].getGroupingLevel()); 
	}
	
	public void testTransfomCrosstabColsInIntermediateColsScenario4x3x1(){
		IGroupColumn[] result = new IntermediateCrosstabReport(3,1).transformGroupingCrosstabConfigInFlatReportConfig(
				CtScenario4x3x1.GROUP_COLUMNS, 
				CtScenario4x3x1.DATA_COLUMNS, 
				CtScenario4x3x1.HEADER_ROWS);
		
		assertNotNull(result); 
		assertEquals(6, result.length); 
		
		assertTrue(result[0] instanceof DefaultGroupColumn); 
		assertEquals(0, result[0].getGroupingLevel());
		
		assertTrue(result[1] instanceof DefaultGroupColumn); 
		assertEquals(1, result[1].getGroupingLevel());
		
		assertTrue(result[2] instanceof DefaultGroupColumn); 
		assertEquals(2, result[2].getGroupingLevel());
		
		assertTrue(result[3] instanceof IntermediateCrosstabReport.IntermGroupColFromCtDataCol); 
		assertEquals(3, result[3].getGroupingLevel());
		
		assertTrue(result[4] instanceof IntermediateCrosstabReport.IntermGroupColFromHeaderRow); 
		assertEquals(4, result[4].getGroupingLevel()); 
		
		assertTrue(result[5] instanceof IntermediateCrosstabReport.IntermGroupColFromHeaderRow);
		assertEquals(5, result[5].getGroupingLevel()); 
	}
	
	
	public void testTransfomCrosstabColsInIntermediateColsScenario1x1x1(){
		IGroupColumn[] result = new IntermediateCrosstabReport(0,1).transformGroupingCrosstabConfigInFlatReportConfig(
				CtScenario1x1x1.GROUP_COLUMNS, 
				CtScenario1x1x1.DATA_COLUMNS, 
				CtScenario1x1x1.ROW_HEADERS);
		
		assertNotNull(result); 
		assertEquals(1, result.length); 
		
		assertTrue(result[0] instanceof IntermediateCrosstabReport.IntermGroupColFromCtDataCol); 
		assertEquals(0, result[0].getGroupingLevel());
	}
	
	public void testExecuteScenario2x2x1xT() {
			IReportOutput visualOutput = new HtmlOutput(createTestOutputFile("intermediateReport2x2x1xT.html"));  
			MemoryOutput memoryOutput = new MemoryOutput(); 
			IntermediateCrosstabOutput realLifeOutput = new IntermediateCrosstabOutput(); 
			
			OutputDispatcher testOutput = new OutputDispatcher();
			testOutput.registerOutput(visualOutput);
			testOutput.registerOutput(memoryOutput);
			testOutput.registerOutput(realLifeOutput); 
			
			IntermediateCrosstabReport classUnderTest = new IntermediateCrosstabReport(1,1);
			classUnderTest.setIn(CtScenario2x2x1.INPUT);
			classUnderTest.setOut(testOutput);
			classUnderTest.setGroupColumns(CtScenario2x2x1.GROUPING_COLUMNS);
			classUnderTest.setDataColumns(CtScenario2x2x1.DATA_COLUMNS); 
			classUnderTest.setCrosstabHeaderRows(CtScenario2x2x1.HEADER_ROWS); 
			classUnderTest.setCrosstabData(CtScenario2x2x1.CROSSTAB_DATA); 
			classUnderTest.setShowGrandTotal(true); 
			classUnderTest.setShowTotals(true); 
			classUnderTest.setShowDataRows(true); 
			
			classUnderTest.execute();
			
			DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(CrosstabDistinctValuesDetectorStep.CONTEXT_KEY_INTERMEDIATE_DISTINCT_VALUES_HOLDER);
			assertNotNull(metadata);
			
			assertNotNull(memoryOutput.getCellMatrix());
			//assertEquals(5, memoryOutput.getCellMatrix().length);
			
			int rowToTest = 0; 
			assertNotNull(memoryOutput.getCellMatrix()[rowToTest]);
			assertEquals(1, memoryOutput.getCellMatrix()[rowToTest].length);
			CellProps cell = memoryOutput.getCellMatrix()[rowToTest][0];
			assertNotNull(cell);
			assertTrue(cell.getValue() instanceof IntermediateReportRow); 
			IntermediateReportRow intermRow = (IntermediateReportRow)cell.getValue();
			//assertTrue(!intermRow.isLast());
			IntermOriginalGroupValuesList intermGroupList = intermRow.getIntermGroupValuesList();
			assertNotNull(intermGroupList);
			assertNotNull(intermGroupList.getGroupValues());
			assertEquals(1, intermGroupList.getGroupValues().size());
			//assertEquals("North", intermGroupList.getGroupValues().get(0));
			
			IntermComputedDataList intermDataList = intermRow.getIntermComputedDataList(); 
			assertNotNull(intermDataList); 
			assertNotNull(intermDataList.getDataList());
			//assertEquals(4, intermDataList.getDataList().size());
			
			IntermComputedTotalsList intermTotalsList = intermRow.getIntermComputedTotalsList(); 
			assertNotNull(intermTotalsList); 
			assertNotNull(intermTotalsList.getTotalsDataList()); 
			//assertEquals(4, intermTotalsList.getTotalsDataList().size()); 
			
		
		
		//TODO: check the output here
	}
	
	
	public void testExecuteScenario1x3x1xT() {
		
			HtmlOutput testOutput = new HtmlOutput(createTestOutputFile("intermediateReport1x3x1xT.html")); 
			IntermediateCrosstabOutput realLifeOutput = new IntermediateCrosstabOutput(); 
			
			IntermediateCrosstabReport classUnderTest = new IntermediateCrosstabReport(0,1);
			classUnderTest.setIn(CtScenario1x3x1.INPUT);
			classUnderTest.setOut(realLifeOutput);
			classUnderTest.setGroupColumns(CtScenario1x3x1.GROUP_COLUMNS);
			classUnderTest.setDataColumns(CtScenario1x3x1.DATA_COLUMNS); 
			classUnderTest.setCrosstabHeaderRows(CtScenario1x3x1.HEADER_ROWS); 
			classUnderTest.setCrosstabData(CtScenario1x3x1.CROSSTAB_DATA); 
			classUnderTest.setShowDataRows(true); 
			classUnderTest.setShowTotals(true); 
			classUnderTest.setShowGrandTotal(true); 
			
			classUnderTest.execute();
			
			DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(CrosstabDistinctValuesDetectorStep.CONTEXT_KEY_INTERMEDIATE_DISTINCT_VALUES_HOLDER);
		
		
		//TODO: check the output here
	}
	
	public void testExecuteScenario1x1x1xT() {
		
			HtmlOutput testOutput = new HtmlOutput(createTestOutputFile("intermediateReport1x1x1xT.html"));
			IntermediateCrosstabOutput realLifeOutput = new IntermediateCrosstabOutput(); 
			
			IntermediateCrosstabReport classUnderTest = new IntermediateCrosstabReport(0,1);
			classUnderTest.setIn(CtScenario1x1x1.INPUT);
			classUnderTest.setOut(realLifeOutput);
			classUnderTest.setGroupColumns(CtScenario1x1x1.GROUP_COLUMNS);
			classUnderTest.setDataColumns(CtScenario1x1x1.DATA_COLUMNS); 
			classUnderTest.setCrosstabHeaderRows(CtScenario1x1x1.ROW_HEADERS); 
			classUnderTest.setCrosstabData(CtScenario1x1x1.CROSSTAB_DATA_WITH_TOTALS); 
			classUnderTest.setShowDataRows(true); 
			classUnderTest.setShowTotals(true); 
			classUnderTest.setShowGrandTotal(true);
			
			classUnderTest.execute();
			
			DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(CrosstabDistinctValuesDetectorStep.CONTEXT_KEY_INTERMEDIATE_DISTINCT_VALUES_HOLDER);
			assertNotNull(metadata);
		
		
		//TODO: check the output here
	}
	
	public void testExecuteScenario1x3x1xNoTotals() {
		
			HtmlOutput testOutput = new HtmlOutput(createTestOutputFile("intermediateReport1x3x1.html"));
			IntermediateCrosstabOutput realLifeOutput = new IntermediateCrosstabOutput();
			
			IntermediateCrosstabReport classUnderTest = new IntermediateCrosstabReport(0,1);
			classUnderTest.setIn(CtScenario1x3x1.INPUT);
			classUnderTest.setOut(realLifeOutput);
			classUnderTest.setGroupColumns(CtScenario1x3x1.GROUP_COLUMNS);
			classUnderTest.setDataColumns(CtScenario1x3x1.DATA_COLUMNS); 
			classUnderTest.setCrosstabHeaderRows(CtScenario1x3x1.HEADER_ROWS); 
			classUnderTest.setCrosstabData(CtScenario1x3x1.CROSSTAB_DATA); 
			classUnderTest.setShowGrandTotal(false); 
			classUnderTest.setShowTotals(false);
			
			classUnderTest.execute();
			
			DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(CrosstabDistinctValuesDetectorStep.CONTEXT_KEY_INTERMEDIATE_DISTINCT_VALUES_HOLDER);
			assertNotNull(metadata);
		 
		
		//TODO: check the output here
	}
	
	public void testExecuteScenario4x3x1xT() {
		
			HtmlOutput testOutput = new HtmlOutput(createTestOutputFile("intermediateReport4x3x1xT.html"));
			IntermediateCrosstabOutput realLifeOutput = new IntermediateCrosstabOutput();
			
			IntermediateCrosstabReport classUnderTest = new IntermediateCrosstabReport(3,1);
			classUnderTest.setIn(CtScenario4x3x1.INPUT);
			classUnderTest.setOut(realLifeOutput);
			classUnderTest.setGroupColumns(CtScenario4x3x1.GROUP_COLUMNS);
			classUnderTest.setDataColumns(CtScenario4x3x1.DATA_COLUMNS); 
			classUnderTest.setCrosstabHeaderRows(CtScenario4x3x1.HEADER_ROWS); 
			classUnderTest.setCrosstabData(CtScenario4x3x1.CROSSTAB_DATA); 
			classUnderTest.setShowDataRows(true); 
			classUnderTest.setShowTotals(true); 
			classUnderTest.setShowGrandTotal(true);
			
			classUnderTest.execute();
			
			DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(CrosstabDistinctValuesDetectorStep.CONTEXT_KEY_INTERMEDIATE_DISTINCT_VALUES_HOLDER);
			assertNotNull(metadata);
		 
		//TODO: check the output here
	}
	
	public void testExecuteScenario4x3x1() {
		
			IntermediateCrosstabOutput realLifeOutput = new IntermediateCrosstabOutput();
			HtmlOutput testOutput = new HtmlOutput(createTestOutputFile("intermediateReport4x3x1.html"));
			
			IntermediateCrosstabReport classUnderTest = new IntermediateCrosstabReport(3,1);
			classUnderTest.setIn(CtScenario4x3x1.INPUT);
			classUnderTest.setOut(realLifeOutput);
			classUnderTest.setGroupColumns(CtScenario4x3x1.GROUP_COLUMNS);
			classUnderTest.setDataColumns(CtScenario4x3x1.DATA_COLUMNS); 
			classUnderTest.setCrosstabHeaderRows(CtScenario4x3x1.HEADER_ROWS); 
			classUnderTest.setCrosstabData(CtScenario4x3x1.CROSSTAB_DATA); 
			classUnderTest.setShowDataRows(true); 
			classUnderTest.setShowTotals(false); 
			classUnderTest.setShowGrandTotal(false);
			
			classUnderTest.execute();
			
			DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(CrosstabDistinctValuesDetectorStep.CONTEXT_KEY_INTERMEDIATE_DISTINCT_VALUES_HOLDER);
			assertNotNull(metadata);
		 
		//TODO: check the output here
	}
}
