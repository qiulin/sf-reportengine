/**
 * 
 */
package net.sf.reportengine;

import java.io.FileNotFoundException;

import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.out.ExcelOutput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.out.StaxReportOutput;
import net.sf.reportengine.out.XslFoOutput;
import net.sf.reportengine.scenarios.ct.CtScenario1x1x1;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1;
import net.sf.reportengine.test.ReportengineTC;

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
		//TODO: complete test
	}
	
	public void testConstructSecondReportGroupColumns(){
		//TODO: complete report
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
	
	public void testExecute1x1x1() throws FileNotFoundException{
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
		classUnderTest.setIn(CtScenario2x2x1.INPUT); 
		classUnderTest.setOut(new HtmlOutput("target/CrosstabReport2x2x1xT.html"));
		classUnderTest.setGroupColumns(CtScenario2x2x1.GROUPING_COLUMNS);
		classUnderTest.setDataColumns(CtScenario2x2x1.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario2x2x1.CROSSTAB_DATA); 
		classUnderTest.setHeaderRows(CtScenario2x2x1.HEADER_ROWS); 
		classUnderTest.setShowTotals(true); 
		classUnderTest.setShowGrandTotal(true); 
		
		classUnderTest.execute(); 
	}
	
	public void testExecute2x2x1() throws FileNotFoundException{
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario2x2x1.INPUT); 
		
		OutputDispatcher outDispatcher = new OutputDispatcher(); 
		outDispatcher.registerOutput(new HtmlOutput("./target/CrosstabReport2x2x1.html"));
		outDispatcher.registerOutput(new StaxReportOutput("./target/CrosstabReport2x2x1.xml"));
		outDispatcher.registerOutput(new XslFoOutput("./target/CrosstabReport2x2x1.pdf"));
		outDispatcher.registerOutput(new ExcelOutput("./target/CrossrabReport2x2x1.xls"));
		classUnderTest.setOut(outDispatcher);
		
		classUnderTest.setGroupColumns(CtScenario2x2x1.GROUPING_COLUMNS);
		classUnderTest.setDataColumns(CtScenario2x2x1.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario2x2x1.CROSSTAB_DATA); 
		classUnderTest.setHeaderRows(CtScenario2x2x1.HEADER_ROWS); 
		
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
	
	public void testWrongConfiguration1(){
		try{
			CrossTabReport classUnderTest = new CrossTabReport(); 
			//don't set any input nor output
			classUnderTest.execute();
			fail("this test should have thrown an exception in the method above");
		}catch(ConfigValidationException configExc){
			assertEquals(configExc.getMessage(), "The report has no input");
		}catch(Throwable other){
			fail("Expected config validation exception but found "+other.getClass());
		}
	}
	
	public void testWrongConfiguration2(){
		try{
			CrossTabReport classUnderTest = new CrossTabReport();
			classUnderTest.setIn(CtScenario1x3x1.INPUT);
			//don't set any output
			
			classUnderTest.execute();	
			fail("this test should have thrown an exception in the method above");
		}catch(ConfigValidationException configExc){
			assertEquals(configExc.getMessage(), "The report has no output");
		}catch(Throwable other){
			fail("Expected config validation exception but found "+other.getClass());
		}
	}
}
