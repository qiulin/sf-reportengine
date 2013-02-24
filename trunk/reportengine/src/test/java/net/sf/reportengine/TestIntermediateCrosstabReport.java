package net.sf.reportengine;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.core.steps.crosstab.IntermComputedDataList;
import net.sf.reportengine.core.steps.crosstab.IntermComputedTotalsList;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalGroupValuesList;
import net.sf.reportengine.core.steps.crosstab.IntermediateReportRow;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.scenarios.ct.CtScenario1x1x1;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With0G2D;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.scenarios.ct.CtScenario4x3x1;
import net.sf.reportengine.test.ReportengineTC;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.DistinctValuesHolder;
import net.sf.reportengine.util.ReportIoUtils;

public class TestIntermediateCrosstabReport extends ReportengineTC {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testTransfomCrosstabColsInIntermediateColsScenario2x2x1(){
		List<IGroupColumn> result = new IntermediateCrosstabReport(1,1)
			.transformGroupingCrosstabConfigInFlatReportConfig(	CtScenario2x2x1With1G1D.GROUPING_COLUMNS, 
																CtScenario2x2x1With1G1D.DATA_COLUMNS, 
																CtScenario2x2x1With1G1D.HEADER_ROWS);
		assertNotNull(result); 
		assertEquals(3, result.size()); 
		assertTrue(result.get(0) instanceof DefaultGroupColumn); 
		assertEquals(0, result.get(0).getGroupingLevel()); 
		assertTrue(result.get(1) instanceof IntermediateCrosstabReport.IntermGroupColFromCtDataCol); 
		assertEquals(1, result.get(1).getGroupingLevel()); 
		assertTrue(result.get(2) instanceof IntermediateCrosstabReport.IntermGroupColFromHeaderRow);
		assertEquals(2, result.get(2).getGroupingLevel()); 
	}
	
	public void testTransfomCrosstabColsInIntermediateColsScenario1x3x1(){
		List<IGroupColumn> result = new IntermediateCrosstabReport(0,1).transformGroupingCrosstabConfigInFlatReportConfig(
				CtScenario1x3x1.GROUP_COLUMNS, 
				CtScenario1x3x1.DATA_COLUMNS, 
				CtScenario1x3x1.HEADER_ROWS);
		
		assertNotNull(result); 
		assertEquals(3, result.size()); 
		assertTrue(result.get(0) instanceof IntermediateCrosstabReport.IntermGroupColFromCtDataCol); 
		assertEquals(0, result.get(0).getGroupingLevel()); 
		assertTrue(result.get(1) instanceof IntermediateCrosstabReport.IntermGroupColFromHeaderRow); 
		assertEquals(1, result.get(1).getGroupingLevel()); 
		assertTrue(result.get(2) instanceof IntermediateCrosstabReport.IntermGroupColFromHeaderRow);
		assertEquals(2, result.get(2).getGroupingLevel()); 
	}
	
	public void testTransfomCrosstabColsInIntermediateColsScenario4x3x1(){
		List<IGroupColumn> result = new IntermediateCrosstabReport(3,1).transformGroupingCrosstabConfigInFlatReportConfig(
				CtScenario4x3x1.GROUP_COLUMNS, 
				CtScenario4x3x1.DATA_COLUMNS, 
				CtScenario4x3x1.HEADER_ROWS);
		
		assertNotNull(result); 
		assertEquals(6, result.size()); 
		
		assertTrue(result.get(0) instanceof DefaultGroupColumn); 
		assertEquals(0, result.get(0).getGroupingLevel());
		
		assertTrue(result.get(1) instanceof DefaultGroupColumn); 
		assertEquals(1, result.get(1).getGroupingLevel());
		
		assertTrue(result.get(2) instanceof DefaultGroupColumn); 
		assertEquals(2, result.get(2).getGroupingLevel());
		
		assertTrue(result.get(3) instanceof IntermediateCrosstabReport.IntermGroupColFromCtDataCol); 
		assertEquals(3, result.get(3).getGroupingLevel());
		
		assertTrue(result.get(4) instanceof IntermediateCrosstabReport.IntermGroupColFromHeaderRow); 
		assertEquals(4, result.get(4).getGroupingLevel()); 
		
		assertTrue(result.get(5) instanceof IntermediateCrosstabReport.IntermGroupColFromHeaderRow);
		assertEquals(5, result.get(5).getGroupingLevel()); 
	}
	
	
	public void testTransfomCrosstabColsInIntermediateColsScenario1x1x1(){
		List<IGroupColumn> result = new IntermediateCrosstabReport(0,1).transformGroupingCrosstabConfigInFlatReportConfig(
				CtScenario1x1x1.GROUP_COLUMNS, 
				CtScenario1x1x1.DATA_COLUMNS, 
				CtScenario1x1x1.ROW_HEADERS);
		
		assertNotNull(result); 
		assertEquals(1, result.size()); 
		
		assertTrue(result.get(0) instanceof IntermediateCrosstabReport.IntermGroupColFromCtDataCol); 
		assertEquals(0, result.get(0).getGroupingLevel());
	}
	
	public void testExecuteScenario2x2x1xT() {
			IReportOutput visualOutput = new HtmlOutput("target/intermediateReport2x2x1xT.html");  
			CellPropsArrayOutput memoryOutput = new CellPropsArrayOutput(); 
			IntermediateCrosstabOutput realLifeOutput = new IntermediateCrosstabOutput(); 
			
			OutputDispatcher testOutput = new OutputDispatcher();
			testOutput.registerOutput(visualOutput);
			testOutput.registerOutput(memoryOutput);
			testOutput.registerOutput(realLifeOutput); 
			
			IntermediateCrosstabReport classUnderTest = new IntermediateCrosstabReport(1,1);
			classUnderTest.setIn(CtScenario2x2x1With1G1D.INPUT);
			classUnderTest.setOut(testOutput);
			classUnderTest.setGroupColumns(CtScenario2x2x1With1G1D.GROUPING_COLUMNS);
			classUnderTest.setDataColumns(CtScenario2x2x1With1G1D.DATA_COLUMNS); 
			classUnderTest.setCrosstabHeaderRows(CtScenario2x2x1With1G1D.HEADER_ROWS); 
			classUnderTest.setCrosstabData(CtScenario2x2x1With1G1D.CROSSTAB_DATA); 
			classUnderTest.setShowGrandTotal(true); 
			classUnderTest.setShowTotals(true); 
			classUnderTest.setShowDataRows(true); 
			
			classUnderTest.execute();
			
			DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER);
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
		
			//HtmlOutput testOutput = new HtmlOutput("target/intermediateReport1x3x1xT.html"); 
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
			
			DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER);
		
		
		//TODO: check the output here
	}
	
	public void testExecuteScenario1x1x1xT() {
		
			//HtmlOutput testOutput = new HtmlOutput("target/intermediateReport1x1x1xT.html");
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
			
			DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER);
			assertNotNull(metadata);
		
		
		//TODO: check the output here
	}
	
	public void testExecuteScenario1x3x1xNoTotals() {
		
			//HtmlOutput testOutput = new HtmlOutput("target/intermediateReport1x3x1.html");
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
			
			DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER);
			assertNotNull(metadata);
		 
		
		//TODO: check the output here
	}
	
	public void testExecuteScenario4x3x1xT() {
		
			//HtmlOutput testOutput = new HtmlOutput("target/intermediateReport4x3x1xT.html");
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
			
			DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER);
			assertNotNull(metadata);
		 
		//TODO: check the output here
	}
	
	public void testExecuteScenario4x3x1() {
		
			IntermediateCrosstabOutput realLifeOutput = new IntermediateCrosstabOutput();
			//HtmlOutput testOutput = new HtmlOutput("target/intermediateReport4x3x1.html");
			
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
			
			DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER);
			assertNotNull(metadata);
		 
		//TODO: check the output here
	}
	
	public void testExecuteScenario2x2x1With0G2D() {
		
		OutputDispatcher output = new OutputDispatcher(); 
		output.registerOutput(new IntermediateCrosstabOutput());
		output.registerOutput(new HtmlOutput("target/intermediateReport2x2x1With0G2D.html"));
		
		IntermediateCrosstabReport classUnderTest = new IntermediateCrosstabReport(0,2);
		classUnderTest.setIn(CtScenario2x2x1With0G2D.INPUT);
		classUnderTest.setOut(output);
		
		classUnderTest.setGroupColumns(null); 
		classUnderTest.setDataColumns(CtScenario2x2x1With0G2D.DATA_COLUMNS); 
		classUnderTest.setCrosstabHeaderRows(CtScenario2x2x1With0G2D.HEADER_ROWS);
		classUnderTest.setCrosstabData(CtScenario2x2x1With0G2D.CROSSTAB_DATA); 
		
		classUnderTest.setShowDataRows(true); 
		classUnderTest.setShowTotals(false); 
		classUnderTest.setShowGrandTotal(false);
		
		classUnderTest.execute();
		
		DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER);
		assertNotNull(metadata);
	 
	//TODO: check the output here
	}
	
	public void testExecuteScenario2x2x1xTWith0G2D() {
		
		OutputDispatcher output = new OutputDispatcher(); 
		output.registerOutput(new IntermediateCrosstabOutput());
		output.registerOutput(new HtmlOutput("target/intermediateReport2x2x1xTWith0G2D.html"));
		
		IntermediateCrosstabReport classUnderTest = new IntermediateCrosstabReport(0,2);
		classUnderTest.setIn(CtScenario2x2x1With0G2D.INPUT);
		classUnderTest.setOut(output);
		
		classUnderTest.setGroupColumns(null); 
		classUnderTest.setDataColumns(CtScenario2x2x1With0G2D.DATA_COLUMNS); 
		classUnderTest.setCrosstabHeaderRows(CtScenario2x2x1With0G2D.HEADER_ROWS);
		classUnderTest.setCrosstabData(CtScenario2x2x1With0G2D.CROSSTAB_DATA); 
		
		classUnderTest.setShowDataRows(true); 
		classUnderTest.setShowTotals(true); 
		classUnderTest.setShowGrandTotal(true);
		
		classUnderTest.execute();
		
		DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER);
		assertNotNull(metadata);
	 
		//TODO: check the output here
	}
	
	
	public void testExecuteScenario3x2x1() {
		
		IntermediateCrosstabOutput realLifeOutput = new IntermediateCrosstabOutput();
		//HtmlOutput testOutput = new HtmlOutput("target/intermediateReport3x2x1.html");
		
		IntermediateCrosstabReport classUnderTest = new IntermediateCrosstabReport(2,1);
		classUnderTest.setIn(new TextInput(ReportIoUtils.createInputStreamFromClassPath("3x2x1.txt")));
		classUnderTest.setOut(realLifeOutput);
		
		classUnderTest.addGroupColumn(new DefaultGroupColumn("Country", 0, 0)); 
		classUnderTest.addGroupColumn(new DefaultGroupColumn("Region", 1, 1)); 
		
		classUnderTest.addDataColumn(new DefaultDataColumn("City", 2)); 
		
		classUnderTest.setCrosstabHeaderRows(Arrays.asList(new DefaultCrosstabHeaderRow(3), new DefaultCrosstabHeaderRow(4)));
		classUnderTest.setCrosstabData(new DefaultCrosstabData(5, Calculators.SUM)); 
		
		classUnderTest.setShowDataRows(true); 
		classUnderTest.setShowTotals(true); 
		classUnderTest.setShowGrandTotal(true);
		
		classUnderTest.execute();
		
		DistinctValuesHolder metadata = (DistinctValuesHolder)classUnderTest.getAlgorithm().getContext().get(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER);
		assertNotNull(metadata);
	 
	//TODO: check the output here
	}
}
