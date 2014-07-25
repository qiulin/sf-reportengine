/**
 * 
 */
package net.sf.reportengine;

import java.io.FileNotFoundException;

import junit.framework.TestCase;
import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.calc.CountGroupCalculator;
import net.sf.reportengine.core.calc.SumGroupCalculator;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.ExcelOutput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.out.PdfOutput;
import net.sf.reportengine.out.StaxReportOutput;
import net.sf.reportengine.scenarios.ct.CtScenario1x1x1;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With0G2D;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.scenarios.ct.CtScenarioFormatting4x3x1;
import net.sf.reportengine.scenarios.ct.CtUnsortedScenario2x2x1With0G2D;
import net.sf.reportengine.scenarios.ct.CtUnsortedScenario2x2x1With1G1D;
import net.sf.reportengine.scenarios.ct.CtUnsortedScenario2x2x1With2G0D;
import net.sf.reportengine.util.ReportIoUtils;

/**
 * @author dragos balan
 *
 */
public class TestCrossTabReport extends TestCase {
	
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

	
	public void testExecute1x1x1xT(){
		CrossTabReport classUnderTest = new CrossTabReport.Builder()
											.title("Crosstab report 1 group, 1x1x1xT")
											.input(CtScenario1x1x1.INPUT)
											.output(new HtmlOutput("target/CrosstabReport1x1x1xT.html"))
											.groupColumns(CtScenario1x1x1.GROUP_COLUMNS)
											.dataColumns(CtScenario1x1x1.DATA_COLUMNS)
											.crosstabData(CtScenario1x1x1.CROSSTAB_DATA_WITH_TOTALS)
											.headerRows(CtScenario1x1x1.ROW_HEADERS)
											.showTotals(true)
											.showGrandTotal(true)
											.build(); 
		classUnderTest.execute(); 
	}
	
	public void testExecute1x1x1() {
		new CrossTabReport.Builder() 
			.title("Crosstab report 1x1x1 no totals")
			.input(CtScenario1x1x1.INPUT) 
			.output(new HtmlOutput("target/CrosstabReport1x1x1.html"))
			.groupColumns(CtScenario1x1x1.GROUP_COLUMNS)
			.dataColumns(CtScenario1x1x1.DATA_COLUMNS)
			.crosstabData(CtScenario1x1x1.CROSSTAB_DATA_NO_TOTALS)
			.headerRows(CtScenario1x1x1.ROW_HEADERS)
			.showTotals(false) 
			.showGrandTotal(false)
			.build()
		.execute();
		
	}
	
	public void testExecute2x2x1xT() throws FileNotFoundException{
		new CrossTabReport.Builder() 
			.title("crosstab report 2x2x1xT")
			.input(CtScenario2x2x1With1G1D.INPUT)
			.output(new HtmlOutput("target/CrosstabReport2x2x1xT.html"))
			.groupColumns(CtScenario2x2x1With1G1D.GROUPING_COLUMNS)
			.dataColumns(CtScenario2x2x1With1G1D.DATA_COLUMNS) 
			.crosstabData(CtScenario2x2x1With1G1D.CROSSTAB_DATA)
			.headerRows(CtScenario2x2x1With1G1D.HEADER_ROWS)
			.showTotals()
			.showGrandTotal()
			.build()
		.execute(); 
	}
	
	public void testExecute2x2x1() throws FileNotFoundException{
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setTitle("crosstab 2x2x1");		
		classUnderTest.setIn(CtScenario2x2x1With1G1D.INPUT); 
		
		OutputDispatcher outDispatcher = new OutputDispatcher(); 
		outDispatcher.registerOutput(new HtmlOutput("./target/CrosstabReport2x2x1.html"));
		outDispatcher.registerOutput(new StaxReportOutput("./target/CrosstabReport2x2x1.xml"));
		outDispatcher.registerOutput(new PdfOutput("./target/CrosstabReport2x2x1.pdf"));
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
	
	
	public void testProgramaticSorting2x2x1() throws FileNotFoundException{
		CrossTabReport classUnderTest = new CrossTabReport(); 
		classUnderTest.setTitle("This report has group columns sorted programatically");
		classUnderTest.setIn(CtUnsortedScenario2x2x1With1G1D.INPUT); 
		classUnderTest.setOut(new HtmlOutput("./target/CtProgramaticallySorted2x2x1.html"));
		classUnderTest.setValuesSorted(false); 
		
		classUnderTest.setGroupColumns(CtUnsortedScenario2x2x1With1G1D.GROUPING_COLUMNS);
		classUnderTest.setDataColumns(CtUnsortedScenario2x2x1With1G1D.DATA_COLUMNS); 
		classUnderTest.setCrosstabData(CtUnsortedScenario2x2x1With1G1D.CROSSTAB_DATA); 
		classUnderTest.setHeaderRows(CtUnsortedScenario2x2x1With1G1D.HEADER_ROWS); 
		
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
		classUnderTest.setTitle("crosstab report 3x2x1xT"); 
		classUnderTest.setIn(new TextInput(ReportIoUtils.createInputStreamFromClassPath("3x2x1.txt"), ",")); 
		classUnderTest.setOut(new HtmlOutput("target/CrosstabReport3x2x1xT.html"));
		
		classUnderTest.addGroupColumn(new DefaultGroupColumn("Country", 0, 0));
		classUnderTest.addGroupColumn(new DefaultGroupColumn("Region", 1, 1));
		
		classUnderTest.addDataColumn(new DefaultDataColumn("City", 2, new CountGroupCalculator()));
		
		classUnderTest.addHeaderRow(new DefaultCrosstabHeaderRow(3)); 
		classUnderTest.addHeaderRow(new DefaultCrosstabHeaderRow(4)); 
		
		classUnderTest.setCrosstabData(new DefaultCrosstabData(5, new CountGroupCalculator())); 
		
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
		
		classUnderTest.setCrosstabData(new DefaultCrosstabData(5, new SumGroupCalculator())); 
		
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
	
	
	public void testProgramaticSortingFor2x2x1xTHavingNoGroups(){
		new CrossTabReport.Builder() 
			.title("This report has 2 data columns programatically sorted.")
			.input(CtUnsortedScenario2x2x1With0G2D.INPUT)
			.output(new HtmlOutput("target/CrosstabReportProgramaticallySorted2x2x1With0G2D.html"))
			.dataColumns(CtUnsortedScenario2x2x1With0G2D.DATA_COLUMNS)
			.headerRows(CtUnsortedScenario2x2x1With0G2D.HEADER_ROWS)
			.crosstabData(CtUnsortedScenario2x2x1With0G2D.CROSSTAB_DATA)
			.showTotals(false)
			.showGrandTotal(false)
			.build()
		.execute(); 
	}
	
	
	public void testProgramaticSortingFor2x2x1xTHavingNoDataCols(){
		new CrossTabReport.Builder() 
			.title("This report has 2 group columns programatically sorted.")
			.input(CtUnsortedScenario2x2x1With2G0D.INPUT)
			.output(new HtmlOutput("target/CrosstabReportProgramaticallySorted2x2x1With2G0D.html"))
			.groupColumns(CtUnsortedScenario2x2x1With2G0D.GROUPING_COLUMNS)
			//.dataColumns(CtUnsortedScenario2x2x1With2G0D.DATA_COLUMNS) /*dataCols is null*/
			.headerRows(CtUnsortedScenario2x2x1With2G0D.HEADER_ROWS)
			.crosstabData(CtUnsortedScenario2x2x1With2G0D.CROSSTAB_DATA)
			//.showTotals(true)
			//.showGrandTotal(true)
			.sortValues()
			.build()
		.execute(); 
	}
	
	
	public void testFormatting(){
		new CrossTabReport.Builder()
			.title("Test Formatting")
			.input(CtScenarioFormatting4x3x1.INPUT)
			.output(new HtmlOutput("./target/testCrosstabFormatting.html"))
			.dataColumns(CtScenarioFormatting4x3x1.DATA_COLUMNS)
			.groupColumns(CtScenarioFormatting4x3x1.GROUP_COLUMNS)
			.headerRows(CtScenarioFormatting4x3x1.HEADER_ROWS)
			.crosstabData(CtScenarioFormatting4x3x1.CROSSTAB_DATA)
			.showTotals()
			.showGrandTotal()
			.build()
		.execute(); 
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
