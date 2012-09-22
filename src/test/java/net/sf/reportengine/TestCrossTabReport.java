/**
 * 
 */
package net.sf.reportengine;

import java.io.FileNotFoundException;

import net.sf.reportengine.out.HtmlReportOutput;
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
	
	public void testExecute1x1x1xT() throws FileNotFoundException{
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario1x1x1.INPUT); 
		classUnderTest.setOut(new HtmlReportOutput(createTestOutputFile("CrosstabReport1x1x1xT.html")));
		classUnderTest.setGroupingColumns(CtScenario1x1x1.GROUP_COLUMNS);
		classUnderTest.setDataColumns(CtScenario1x1x1.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario1x1x1.CROSSTAB_DATA); 
		classUnderTest.setCrosstabHeaderRows(CtScenario1x1x1.ROW_HEADERS); 
		classUnderTest.setShowTotals(true); 
		classUnderTest.setShowGrandTotal(true); 
		
		classUnderTest.execute(); 
	}
	
	public void testExecute1x1x1() throws FileNotFoundException{
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario1x1x1.INPUT); 
		classUnderTest.setOut(new HtmlReportOutput(createTestOutputFile("CrosstabReport1x1x1.html")));
		classUnderTest.setGroupingColumns(CtScenario1x1x1.GROUP_COLUMNS);
		classUnderTest.setDataColumns(CtScenario1x1x1.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario1x1x1.CROSSTAB_DATA); 
		classUnderTest.setCrosstabHeaderRows(CtScenario1x1x1.ROW_HEADERS); 
		classUnderTest.setShowTotals(false); 
		classUnderTest.setShowGrandTotal(false); 
		
		classUnderTest.execute(); 
	}
	
	public void testExecute2x2x1xT() throws FileNotFoundException{
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario2x2x1.INPUT); 
		classUnderTest.setOut(new HtmlReportOutput(createTestOutputFile("CrosstabReport2x2x1xT.html")));
		classUnderTest.setGroupingColumns(CtScenario2x2x1.GROUPING_COLUMNS);
		classUnderTest.setDataColumns(CtScenario2x2x1.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario2x2x1.CROSSTAB_DATA); 
		classUnderTest.setCrosstabHeaderRows(CtScenario2x2x1.HEADER_ROWS); 
		classUnderTest.setShowTotals(true); 
		classUnderTest.setShowGrandTotal(true); 
		
		classUnderTest.execute(); 
	}
	
	public void testExecute2x2x1() throws FileNotFoundException{
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario2x2x1.INPUT); 
		classUnderTest.setOut(new HtmlReportOutput(createTestOutputFile("CrosstabReport2x2x1.html")));
		classUnderTest.setGroupingColumns(CtScenario2x2x1.GROUPING_COLUMNS);
		classUnderTest.setDataColumns(CtScenario2x2x1.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario2x2x1.CROSSTAB_DATA); 
		classUnderTest.setCrosstabHeaderRows(CtScenario2x2x1.HEADER_ROWS); 
		classUnderTest.setShowTotals(false); 
		classUnderTest.setShowGrandTotal(false); 
		
		classUnderTest.execute(); 
	}
	
	public void testExecute1x3x1xT() throws FileNotFoundException{
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario1x3x1.INPUT); 
		classUnderTest.setOut(new HtmlReportOutput(createTestOutputFile("CrosstabReport1x3x1xT.html")));
		classUnderTest.setGroupingColumns(CtScenario1x3x1.GROUP_COLUMNS);
		classUnderTest.setDataColumns(CtScenario1x3x1.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario1x3x1.CROSSTAB_DATA); 
		classUnderTest.setCrosstabHeaderRows(CtScenario1x3x1.HEADER_ROWS); 
		classUnderTest.setShowTotals(true); 
		classUnderTest.setShowGrandTotal(true); 
		
		classUnderTest.execute(); 
	}
	
	public void testExecute1x3x1() throws FileNotFoundException{
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setIn(CtScenario1x3x1.INPUT); 
		classUnderTest.setOut(new HtmlReportOutput(createTestOutputFile("CrosstabReport1x3x1.html")));
		classUnderTest.setGroupingColumns(CtScenario1x3x1.GROUP_COLUMNS);
		classUnderTest.setDataColumns(CtScenario1x3x1.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtScenario1x3x1.CROSSTAB_DATA); 
		classUnderTest.setCrosstabHeaderRows(CtScenario1x3x1.HEADER_ROWS); 
		classUnderTest.setShowTotals(false); 
		classUnderTest.setShowGrandTotal(false); 
		
		classUnderTest.execute(); 
	}
}
