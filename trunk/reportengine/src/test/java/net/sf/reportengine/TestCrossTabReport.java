/**
 * 
 */
package net.sf.reportengine;

import java.io.FileNotFoundException;
import java.util.List;

import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.ExcelOutput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.out.StaxReportOutput;
import net.sf.reportengine.out.XslFoOutput;
import net.sf.reportengine.scenarios.ct.CtScenario1x1x1;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With0G2D;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.test.ReportengineTC;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.ReportIoUtils;

/**
 * @author dragos balan
 *
 */
public class TestCrossTabReport extends ReportengineTC {
	
	private CrossTabReport classUnderTest; 
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		classUnderTest = new CrossTabReport(); 
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
	public void testConstructSecondReportDataColumns() {
		CrossTabReport classUnderTest = new CrossTabReport(); 
		
		
		CtMetadata testMetadata = new CtMetadata(CtScenario2x2x1With1G1D.MOCK_DISTINCT_VALUES_HOLDER); 
		
		List<DataColumn> result = classUnderTest.constructDataColumnsForSecondProcess(testMetadata, 
															CtScenario2x2x1With1G1D.DATA_COLUMNS, 
															false, 
															false); 
		assertNotNull(result);
		//TODO: continue the test
	}
	
	
	public void testExecute1x1x1xT(){
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario1x1x1.INPUT); 
		classUnderTest.setOut(new HtmlOutput("target/CrosstabReport1x1x1xT.html"));
		classUnderTest.setGroupColumns(CtScenario1x1x1.GROUP_COLUMNS);
		classUnderTest.setDataColumns(CtScenario1x1x1.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario1x1x1.CROSSTAB_DATA_WITH_TOTALS); 
		classUnderTest.setHeaderRows(CtScenario1x1x1.ROW_HEADERS); 
		classUnderTest.setShowTotals(true); 
		classUnderTest.setShowGrandTotal(true); 
		
		classUnderTest.execute(); 
	}
	
	public void testExecute1x1x1() {
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario1x1x1.INPUT); 
		classUnderTest.setOut(new HtmlOutput("target/CrosstabReport1x1x1.html"));
		classUnderTest.setGroupColumns(CtScenario1x1x1.GROUP_COLUMNS);
		classUnderTest.setDataColumns(CtScenario1x1x1.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario1x1x1.CROSSTAB_DATA_NO_TOTALS); 
		classUnderTest.setHeaderRows(CtScenario1x1x1.ROW_HEADERS); 
		classUnderTest.setShowTotals(false); 
		classUnderTest.setShowGrandTotal(false); 
		
		classUnderTest.execute(); 
	}
	
	public void testExecute2x2x1xT() throws FileNotFoundException{
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario2x2x1With1G1D.INPUT); 
		classUnderTest.setOut(new HtmlOutput("target/CrosstabReport2x2x1xT.html"));
		classUnderTest.setGroupColumns(CtScenario2x2x1With1G1D.GROUPING_COLUMNS);
		classUnderTest.setDataColumns(CtScenario2x2x1With1G1D.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario2x2x1With1G1D.CROSSTAB_DATA); 
		classUnderTest.setHeaderRows(CtScenario2x2x1With1G1D.HEADER_ROWS); 
		classUnderTest.setShowTotals(true); 
		classUnderTest.setShowGrandTotal(true); 
		
		classUnderTest.execute(); 
	}
	
	public void testExecute2x2x1() throws FileNotFoundException{
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario2x2x1With1G1D.INPUT); 
		
		OutputDispatcher outDispatcher = new OutputDispatcher(); 
		outDispatcher.registerOutput(new HtmlOutput("./target/CrosstabReport2x2x1.html"));
		outDispatcher.registerOutput(new StaxReportOutput("./target/CrosstabReport2x2x1.xml"));
		outDispatcher.registerOutput(new XslFoOutput("./target/CrosstabReport2x2x1.pdf"));
		outDispatcher.registerOutput(new ExcelOutput("./target/CrossrabReport2x2x1.xls"));
		classUnderTest.setOut(outDispatcher);
		
		classUnderTest.setGroupColumns(CtScenario2x2x1With1G1D.GROUPING_COLUMNS);
		classUnderTest.setDataColumns(CtScenario2x2x1With1G1D.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario2x2x1With1G1D.CROSSTAB_DATA); 
		classUnderTest.setHeaderRows(CtScenario2x2x1With1G1D.HEADER_ROWS); 
		
		classUnderTest.setShowTotals(false); 
		classUnderTest.setShowGrandTotal(false); 
		
		classUnderTest.execute(); 
	}
	
	public void testExecute1x3x1xT() throws FileNotFoundException{
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario1x3x1.INPUT); 
		classUnderTest.setOut(new HtmlOutput("target/CrosstabReport1x3x1xT.html"));
		classUnderTest.setGroupColumns(CtScenario1x3x1.GROUP_COLUMNS);
		classUnderTest.setDataColumns(CtScenario1x3x1.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario1x3x1.CROSSTAB_DATA); 
		classUnderTest.setHeaderRows(CtScenario1x3x1.HEADER_ROWS); 
		classUnderTest.setShowTotals(true); 
		classUnderTest.setShowGrandTotal(true); 
		
		classUnderTest.execute(); 
	}
	
	public void testExecute1x3x1() throws FileNotFoundException{
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario1x3x1.INPUT); 
		classUnderTest.setOut(new HtmlOutput("target/CrosstabReport1x3x1.html"));
		classUnderTest.setGroupColumns(CtScenario1x3x1.GROUP_COLUMNS);
		classUnderTest.setDataColumns(CtScenario1x3x1.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario1x3x1.CROSSTAB_DATA); 
		classUnderTest.setHeaderRows(CtScenario1x3x1.HEADER_ROWS); 
		classUnderTest.setShowTotals(false); 
		classUnderTest.setShowGrandTotal(false); 
		
		classUnderTest.execute(); 
	}
	
	public void testExecute3x2x1xT(){
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(new TextInput(ReportIoUtils.createInputStreamFromClassPath("3x2x1.txt"), ",")); 
		classUnderTest.setOut(new HtmlOutput("target/CrosstabReport3x2x1xT.html"));
		
		classUnderTest.addGroupColumn(new DefaultGroupColumn("Country", 0, 0));
		classUnderTest.addGroupColumn(new DefaultGroupColumn("Region", 1, 1));
		
		classUnderTest.addDataColumn(new DefaultDataColumn("City", 2, Calculators.COUNT));
		
		classUnderTest.addHeaderRow(new DefaultCrosstabHeaderRow(3)); 
		classUnderTest.addHeaderRow(new DefaultCrosstabHeaderRow(4)); 
		
		classUnderTest.setCrosstabData(new DefaultCrosstabData(5, Calculators.SUM)); 
		
		classUnderTest.setShowTotals(true); 
		classUnderTest.setShowGrandTotal(true); 
		
		classUnderTest.execute(); 
	}
	
	//TODO
	public void testExecute3x2x1xTHavingNoGroupColumns(){
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(new TextInput(ReportIoUtils.createInputStreamFromClassPath("3x2x1.txt"), ",")); 
		classUnderTest.setOut(new HtmlOutput("target/CrosstabReport3x2x1xTxNoGroupColumns.html"));
		
		classUnderTest.addDataColumn(new DefaultDataColumn("Country", 0)); 
		classUnderTest.addDataColumn(new DefaultDataColumn("Region", 1));
		classUnderTest.addDataColumn(new DefaultDataColumn("City", 2));
		
		classUnderTest.addHeaderRow(new DefaultCrosstabHeaderRow(3)); 
		classUnderTest.addHeaderRow(new DefaultCrosstabHeaderRow(4)); 
		
		classUnderTest.setCrosstabData(new DefaultCrosstabData(5, Calculators.SUM)); 
		
		classUnderTest.setShowTotals(false); 
		classUnderTest.setShowGrandTotal(false); 
		
		classUnderTest.execute(); 
	}
	
	public void testExecute2x2x1xTHavingNoGroupColumns(){
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario2x2x1With0G2D.INPUT); 
		classUnderTest.setOut(new HtmlOutput("target/CrosstabReport2x2x1With0G2D.html"));
		
		classUnderTest.setDataColumns(CtScenario2x2x1With0G2D.DATA_COLUMNS); 
		classUnderTest.setHeaderRows(CtScenario2x2x1With0G2D.HEADER_ROWS);
		classUnderTest.setCrosstabData(CtScenario2x2x1With0G2D.CROSSTAB_DATA); 
		
		classUnderTest.setShowTotals(false); 
		classUnderTest.setShowGrandTotal(false); 
		
		classUnderTest.execute(); 
	}
	
	
//	public void testWrongConfiguration1(){
//		try{
//			CrossTabReport classUnderTest = new CrossTabReport(); 
//			//don't set any input nor output
//			classUnderTest.execute();
//			fail("this test should have thrown an exception in the method above");
//		}catch(ConfigValidationException configExc){
//			assertEquals(configExc.getMessage(), "The report has no input");
//		}catch(Throwable other){
//			fail("Expected config validation exception but found "+other.getClass());
//		}
//	}
//	
//	public void testWrongConfiguration2(){
//		try{
//			CrossTabReport classUnderTest = new CrossTabReport();
//			classUnderTest.setIn(CtScenario1x3x1.INPUT);
//			//don't set any output
//			
//			classUnderTest.execute();	
//			fail("this test should have thrown an exception in the method above");
//		}catch(ConfigValidationException configExc){
//			assertEquals(configExc.getMessage(), "The report has no output");
//		}catch(Throwable other){
//			fail("Expected config validation exception but found "+other.getClass());
//		}
//	}
}
