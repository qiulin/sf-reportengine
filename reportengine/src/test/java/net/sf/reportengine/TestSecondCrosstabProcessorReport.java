/**
 * 
 */
package net.sf.reportengine;

import java.io.InputStream;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.in.IntermediateCrosstabReportInput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With0G2D;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.IDistinctValuesHolder;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * This report uses temporary files created by {@link TestIntermediateCrosstabReport}
 * @author dragos balan
 *
 */
public class TestSecondCrosstabProcessorReport /*extends ReportAlgorithmStepTC*/ {
	
	//TODO: get rid of this mock
	private CrossTabReport mockCrosstabReport;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		mockCrosstabReport = new CrossTabReport(); 
	}

	/**
	 * Test method for {@link net.sf.reportengine.AbstractReport#execute()}.
	 */
	@Test
	public void testExecute2x2x1xT() throws Exception{
		
		IDistinctValuesHolder distinctValuesHolder = CtScenario2x2x1With1G1D.MOCK_DISTINCT_VALUES_HOLDER; 
		CtMetadata crosstabMetadata = new CtMetadata(distinctValuesHolder);
		crosstabMetadata.computeCoefficients(); 
		
		InputStream intermObjectStream = ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput2x2x1xT.rep");
		Assert.assertNotNull(intermObjectStream);
		IntermediateCrosstabReportInput intermediateInput = new IntermediateCrosstabReportInput(intermObjectStream); 
		
		SecondCrosstabProcessorReport report = new SecondCrosstabProcessorReport(crosstabMetadata); 
		report.setIn(intermediateInput);
		report.setOut(new HtmlOutput("target/SecondProcessorOut2x2x1xT.html")); 
		
		List<DataColumn> dataColumns = mockCrosstabReport.constructDataColumnsForSecondProcess( 	crosstabMetadata, 
																									CtScenario2x2x1With1G1D.DATA_COLUMNS, 
																									true, 
																									true);
		Assert.assertNotNull(dataColumns); 
		Assert.assertEquals(10, dataColumns.size()); 
		report.setDataColumns(dataColumns);
		
		List<GroupColumn> groupColumns = mockCrosstabReport.constructGroupColumnsForSecondProcess(CtScenario2x2x1With1G1D.GROUPING_COLUMNS);
		Assert.assertNotNull(groupColumns); 
		Assert.assertEquals(1, groupColumns.size());
		report.setGroupColumns(groupColumns); 
		
		report.execute(); 
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.AbstractReport#execute()}.
	 */
	@Test
	public void testExecute2x2x1With0G2D() throws Exception{
		
		CtMetadata crosstabMetadata = new CtMetadata(CtScenario2x2x1With0G2D.MOCK_DISTINCT_VALUES_HOLDER);
		crosstabMetadata.computeCoefficients(); 
		
		InputStream intermObjectStream = ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput2x2x1With0G2D.rep");
		Assert.assertNotNull(intermObjectStream);
		IntermediateCrosstabReportInput intermediateInput = new IntermediateCrosstabReportInput(intermObjectStream); 
		
		SecondCrosstabProcessorReport report = new SecondCrosstabProcessorReport(crosstabMetadata); 
		report.setIn(intermediateInput);
		report.setOut(new HtmlOutput("target/SecondProcessorOut2x2x1With0G2D.html")); 
		
		List<DataColumn> dataColumns = mockCrosstabReport.constructDataColumnsForSecondProcess( 	crosstabMetadata, 
																									CtScenario2x2x1With0G2D.DATA_COLUMNS, 
																									false, 
																									false);
		Assert.assertNotNull(dataColumns); 
		//assertEquals(10, dataColumns.size()); 
		report.setDataColumns(dataColumns);
		
		List<GroupColumn> groupColumns = mockCrosstabReport.constructGroupColumnsForSecondProcess(CtScenario2x2x1With0G2D.GROUPING_COLUMNS);
		Assert.assertNull(groupColumns); 
		
		report.setGroupColumns(groupColumns); 
		
		report.execute(); 
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.AbstractReport#execute()}.
	 */
	@Test
	public void testExecute1x3x1xT() throws Exception{
		
		IDistinctValuesHolder distinctValuesHolder = CtScenario1x3x1.MOCK_DISTINCT_VALUES_HOLDER; 
		CtMetadata crosstabMetadata = new CtMetadata(distinctValuesHolder);
		crosstabMetadata.computeCoefficients(); 
		
		InputStream intermObjectStream = ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput1x3x1xT.rep");
		Assert.assertNotNull(intermObjectStream);
		IntermediateCrosstabReportInput intermediateInput = new IntermediateCrosstabReportInput(intermObjectStream); 
		
		SecondCrosstabProcessorReport report = new SecondCrosstabProcessorReport(crosstabMetadata); 
		report.setIn(intermediateInput);
		report.setOut(new HtmlOutput("target/SecondProcessorOut1x3x1xT.html")); 
		
		List<DataColumn> dataColumns = mockCrosstabReport.constructDataColumnsForSecondProcess( crosstabMetadata, 
																							CtScenario1x3x1.DATA_COLUMNS, 
																							true, 
																							true);
		Assert.assertNotNull(dataColumns); 
		//assertEquals(10, dataColumns.length); 
		report.setDataColumns(dataColumns);
		
//		GroupColumn[] groupColumns = CrossTabReport.constructGroupColumnsForSecondProcess(CtScenario1x3x1.GROUP_COLUMNS[0]);
//		assertNotNull(groupColumns); 
//		assertEquals(1, groupColumns.length);
//		report.setGroupingColumns(groupColumns); 
		
		report.execute(); 
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.AbstractReport#execute()}.
	 */
	@Test
	public void testExecute1x3x1() throws Exception{
		
		IDistinctValuesHolder distinctValuesHolder = CtScenario1x3x1.MOCK_DISTINCT_VALUES_HOLDER; 
		CtMetadata crosstabMetadata = new CtMetadata(distinctValuesHolder);
		crosstabMetadata.computeCoefficients(); 
		
		InputStream intermObjectStream = ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput1x3x1.rep");
		Assert.assertNotNull(intermObjectStream);
		IntermediateCrosstabReportInput intermediateInput = new IntermediateCrosstabReportInput(intermObjectStream); 
		
		SecondCrosstabProcessorReport report = new SecondCrosstabProcessorReport(crosstabMetadata); 
		report.setIn(intermediateInput);
		report.setOut(new HtmlOutput("target/SecondProcessorOut1x3x1.html")); 
		report.setShowTotals(false); 
		report.setShowGrandTotal(false); 
		
		List<DataColumn> dataColumns = mockCrosstabReport.constructDataColumnsForSecondProcess( 	crosstabMetadata, 
																								CtScenario1x3x1.DATA_COLUMNS, 
																								false, 
																								false);
		Assert.assertNotNull(dataColumns); 
		//assertEquals(10, dataColumns.length); 
		report.setDataColumns(dataColumns);
		
		report.execute(); 
	}

}
