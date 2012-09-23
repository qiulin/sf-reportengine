/**
 * 
 */
package net.sf.reportengine;

import java.io.InputStream;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.steps.ReportAlgorithmStepTC;
import net.sf.reportengine.in.IntermediateCrosstabReportInput;
import net.sf.reportengine.out.HtmlReportOutput;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.IDistinctValuesHolder;

/**
 * @author Administrator
 *
 */
public class TestSecondCrosstabProcessorReport extends ReportAlgorithmStepTC {
	
	//TODO: get rid of this mock
	private CrossTabReport mockCrosstabReport;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		mockCrosstabReport = new CrossTabReport(); 
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link net.sf.reportengine.AbstractReport#execute()}.
	 */
	public void testExecute2x2x1xT() throws Exception{
		
		IDistinctValuesHolder distinctValuesHolder = CtScenario2x2x1.MOCK_DISTINCT_VALUES_HOLDER; 
		CtMetadata crosstabMetadata = new CtMetadata(distinctValuesHolder);
		crosstabMetadata.computeCoefficients(); 
		
		InputStream intermObjectStream = getTestFileFromClasspath("TestIntermediateInput2x2x1xT.rep");
		assertNotNull(intermObjectStream);
		IntermediateCrosstabReportInput intermediateInput = new IntermediateCrosstabReportInput(intermObjectStream); 
		
		SecondCrosstabProcessorReport report = new SecondCrosstabProcessorReport(crosstabMetadata); 
		report.setIn(intermediateInput);
		report.setOut(new HtmlReportOutput(createTestOutputFile("SecondProcessorOut2x2x1xT.html"))); 
		
		IDataColumn[] dataColumns = mockCrosstabReport.constructDataColumnsForSecondProcess( 	crosstabMetadata, 
																								CtScenario2x2x1.DATA_COLUMNS, 
																								true, 
																								true);
		assertNotNull(dataColumns); 
		assertEquals(10, dataColumns.length); 
		report.setDataColumns(dataColumns);
		
		IGroupColumn[] groupColumns = mockCrosstabReport.constructGroupColumnsForSecondProcess(CtScenario2x2x1.GROUPING_COLUMNS);
		assertNotNull(groupColumns); 
		assertEquals(1, groupColumns.length);
		report.setGroupingColumns(groupColumns); 
		
		report.execute(); 
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.AbstractReport#execute()}.
	 */
	public void testExecute1x3x1xT() throws Exception{
		
		IDistinctValuesHolder distinctValuesHolder = CtScenario1x3x1.MOCK_DISTINCT_VALUES_HOLDER; 
		CtMetadata crosstabMetadata = new CtMetadata(distinctValuesHolder);
		crosstabMetadata.computeCoefficients(); 
		
		InputStream intermObjectStream = getTestFileFromClasspath("TestIntermediateInput1x3x1xT.rep");
		assertNotNull(intermObjectStream);
		IntermediateCrosstabReportInput intermediateInput = new IntermediateCrosstabReportInput(intermObjectStream); 
		
		SecondCrosstabProcessorReport report = new SecondCrosstabProcessorReport(crosstabMetadata); 
		report.setIn(intermediateInput);
		report.setOut(new HtmlReportOutput(createTestOutputFile("SecondProcessorOut1x3x1xT.html"))); 
		
		IDataColumn[] dataColumns = mockCrosstabReport.constructDataColumnsForSecondProcess( crosstabMetadata, 
																							CtScenario1x3x1.DATA_COLUMNS, 
																							true, 
																							true);
		assertNotNull(dataColumns); 
		//assertEquals(10, dataColumns.length); 
		report.setDataColumns(dataColumns);
		
//		IGroupColumn[] groupColumns = CrossTabReport.constructGroupColumnsForSecondProcess(CtScenario1x3x1.GROUP_COLUMNS[0]);
//		assertNotNull(groupColumns); 
//		assertEquals(1, groupColumns.length);
//		report.setGroupingColumns(groupColumns); 
		
		report.execute(); 
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.AbstractReport#execute()}.
	 */
	public void testExecute1x3x1() throws Exception{
		
		IDistinctValuesHolder distinctValuesHolder = CtScenario1x3x1.MOCK_DISTINCT_VALUES_HOLDER; 
		CtMetadata crosstabMetadata = new CtMetadata(distinctValuesHolder);
		crosstabMetadata.computeCoefficients(); 
		
		InputStream intermObjectStream = getTestFileFromClasspath("TestIntermediateInput1x3x1.rep");
		assertNotNull(intermObjectStream);
		IntermediateCrosstabReportInput intermediateInput = new IntermediateCrosstabReportInput(intermObjectStream); 
		
		SecondCrosstabProcessorReport report = new SecondCrosstabProcessorReport(crosstabMetadata); 
		report.setIn(intermediateInput);
		report.setOut(new HtmlReportOutput(createTestOutputFile("SecondProcessorOut1x3x1.html"))); 
		report.setShowTotals(false); 
		report.setShowGrandTotal(false); 
		
		IDataColumn[] dataColumns = mockCrosstabReport.constructDataColumnsForSecondProcess( 	crosstabMetadata, 
																								CtScenario1x3x1.DATA_COLUMNS, 
																								false, 
																								false);
		assertNotNull(dataColumns); 
		//assertEquals(10, dataColumns.length); 
		report.setDataColumns(dataColumns);
		
		report.execute(); 
	}

}
