package net.sf.reportengine;

import java.io.InputStream;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.in.StreamReportInput;
import net.sf.reportengine.out.ExcelOutput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.MemoryOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.out.StaxReportOutput;
import net.sf.reportengine.out.XslFoOutput;
import net.sf.reportengine.out.XsltReportOutput;
import net.sf.reportengine.scenarios.NoGroupsScenario;
import net.sf.reportengine.scenarios.OhlcComputationScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2x3x1;
import net.sf.reportengine.scenarios.ScenarioFormatedValues;
import net.sf.reportengine.scenarios.ShowOnlySpecificTotalsScenario;
import net.sf.reportengine.test.ReportengineTC;
import net.sf.reportengine.util.MatrixUtils;

public class TestFlatReport extends ReportengineTC {
	
	private FlatReport flatReport = null ;
	
	
	protected void setUp() throws Exception {
		super.setUp();
		
		flatReport = new FlatReport();	
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testExecuteScenario1(){
			
			MemoryOutput testOut = new MemoryOutput();
			
			flatReport.setIn(Scenario1.INPUT);
			flatReport.setOut(testOut);
			//flatReport.setOut(new HtmlReportOutput(new FileOutputStream("scenario1.html")));
			flatReport.setDataColumns(Scenario1.DATA_COLUMNS);
			flatReport.setGroupColumns(Scenario1.GROUPING_COLUMNS);
			flatReport.setShowTotals(true);
			flatReport.setShowDataRows(true);
			flatReport.setShowGrandTotal(true);
			
		
			flatReport.execute();
			
			assertTrue(MatrixUtils.compareMatrices(testOut.getCellMatrix(), Scenario1.EXPECTED_OUTPUT));
	}
	
	public void testExecuteScenario1WithAddColumn(){
		
		MemoryOutput testOut = new MemoryOutput();
		
		flatReport.setIn(Scenario1.INPUT);
		flatReport.setOut(testOut);
		
		flatReport.addDataColumn(Scenario1.DATA_COLUMNS.get(0));
		flatReport.addDataColumn(Scenario1.DATA_COLUMNS.get(1));
		flatReport.addDataColumn(Scenario1.DATA_COLUMNS.get(2));
		
		flatReport.addGroupColumn(Scenario1.GROUPING_COLUMNS.get(0));
		flatReport.addGroupColumn(Scenario1.GROUPING_COLUMNS.get(1));
		flatReport.addGroupColumn(Scenario1.GROUPING_COLUMNS.get(2));
		
		flatReport.setShowTotals(true);
		flatReport.setShowDataRows(true);
		flatReport.setShowGrandTotal(true); 
	
		flatReport.execute();
		
		//MatrixUtils.logMatrix(testOut.getCellMatrix()); 
		
		assertTrue(MatrixUtils.compareMatrices(testOut.getCellMatrix(), Scenario1.EXPECTED_OUTPUT));
		
	}
	
	
	
	public void testExecuteScenarioOhlc(){
		boolean flawless = true;
			try {
				InputStream testStream = getTestFileFromClasspath("EURUSD_2007-2009_FirstHours.txt");
				assertNotNull(testStream);
				
				StreamReportInput in = new StreamReportInput(testStream,"\t");
				
				OutputDispatcher output = new OutputDispatcher();
				output.registerOutput(new HtmlOutput(createTestOutputFile("testFlatReportOHLC.html")));
				output.registerOutput(new ExcelOutput(createTestOutputFile("testFlatReportOHLC.xls")));
				
				flatReport.setIn(in);
				flatReport.setOut(output);
				
				flatReport.setReportTitle("OHLC TEST");
				flatReport.setDataColumns(OhlcComputationScenario.DATA_COLUMNS);
				flatReport.setGroupColumns(OhlcComputationScenario.GROUPING_COLUMNS); 
				flatReport.setShowTotals(true);
				flatReport.setShowDataRows(false);
				flatReport.execute();
			} catch (Throwable e) {
				e.printStackTrace();
				flawless = false;			
			}
			assertTrue(flawless);
	}
	

	public void testExecuteScenarioShowOnlySpecificTotals(){
		try {
			MemoryOutput testOut = new MemoryOutput();
			
			flatReport.setIn(ShowOnlySpecificTotalsScenario.INPUT);
			flatReport.setOut(testOut);
			flatReport.setOut(new HtmlOutput(createTestOutputFile("onlySpecificTotals.html")));
			flatReport.setGroupColumns(ShowOnlySpecificTotalsScenario.GROUP_COLUMNS);
			flatReport.setDataColumns(ShowOnlySpecificTotalsScenario.DATA_COLUMNS);
			flatReport.setShowTotals(true);
			flatReport.setShowGrandTotal(false);
			flatReport.setShowDataRows(true);
		
			flatReport.execute();
			
			//TODO this is a new feature
			//assertTrue(MatrixUtils.compareMatrices(testOut.getCellMatrix(), .EXPECTED_OUTPUT));
			
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} 
	}
	
	
	public void testExecute2x3x1(){
        try{
        	InputStream inputStream = getTestFileFromClasspath("2x3x1.txt");
        	StreamReportInput input = new StreamReportInput(inputStream);
        	
        	flatReport.setGroupColumns(Scenario2x3x1.GROUP_COLUMNS);
            flatReport.setDataColumns(Scenario2x3x1.DATA_COLUMNS);
            
            flatReport.setReportTitle("Test flat report 2x3x1d");    
            flatReport.setIn(input); 
            
            
            InputStream xstlStream = getTestFileFromClasspath("net/sf/reportengine/defaultTemplate.xslt");
            assertNotNull(xstlStream);
            
            OutputDispatcher output = new OutputDispatcher();
            output.registerOutput(new HtmlOutput(createTestOutputFile("testExecute2x3x1.html")));
            output.registerOutput(new StaxReportOutput(createTestOutputFile("testExecute2x3x1.xml")));
            output.registerOutput(new XsltReportOutput(createTestOutputFile("testXsltOutput2x3x1.html"), xstlStream));
            output.registerOutput(new XslFoOutput(createTestOutputFile("testExecute2x3x1.pdf")));
            flatReport.setOut(output);
            
            flatReport.setShowTotals(true);
            flatReport.execute();
            
        }catch(Throwable rex){
            rex.printStackTrace();
            fail(rex.getMessage()); 
        }
    }
	
	public void testExecuteNoGroupingColumnsReport() throws Exception{
    	flatReport.setGroupColumns(NoGroupsScenario.GROUPING_COLUMNS);
        flatReport.setDataColumns(NoGroupsScenario.DATA_COLUMNS);
        
        flatReport.setReportTitle("Test report having no grops but still showing the grand total");    
        flatReport.setIn(NoGroupsScenario.INPUT); 
        
        OutputDispatcher output = new OutputDispatcher();
        output.registerOutput(new HtmlOutput(createTestOutputFile("testFlatReportNoGroupings.html")));
        flatReport.setOut(output);
        flatReport.setShowTotals(true); 
        flatReport.setShowGrandTotal(true); 
        
        flatReport.execute();
	}
	
	public void testFlatReportUtf8Output(){
		InputStream inputStream = getTestFileFromClasspath("Utf8Input.txt");
		assertNotNull(inputStream);
		
		flatReport.setReportTitle("ÎºÎ±Î¹ ÎµÎ´Î±Ï†Î¹ÎºÎ­Ï‚ Ï€Ï�Î¿ÎºÎ»Î®ÏƒÎµÎ¹Ï‚ Ï€Î¿Ï…");
		
		IReportInput reportInput = new StreamReportInput(inputStream, ",", "UTF-8"); 
		flatReport.setIn(reportInput); 
		
		flatReport.addDataColumn(new DefaultDataColumn(0)); 
		flatReport.addDataColumn(new DefaultDataColumn(1));
		flatReport.addDataColumn(new DefaultDataColumn(2));
		flatReport.addDataColumn(new DefaultDataColumn(3));
		
		flatReport.setOut(new HtmlOutput(createTestWriter("testFlatReportUtf8Output.html")));
		
		flatReport.execute(); 
	}
	
	public void testFlatReportUtf8PdfOutput(){
		InputStream inputStream = getTestFileFromClasspath("Utf8Input.txt");
		assertNotNull(inputStream);
		
		flatReport.setReportTitle("Τη γλώσσα μου έδωσαν ελληνική");
		
		IReportInput reportInput = new StreamReportInput(inputStream, ",", "UTF-8"); 
		flatReport.setIn(reportInput); 
		
		flatReport.addDataColumn(new DefaultDataColumn(0)); 
		flatReport.addDataColumn(new DefaultDataColumn(1));
		flatReport.addDataColumn(new DefaultDataColumn(2));
		flatReport.addDataColumn(new DefaultDataColumn(3));
		
		flatReport.setOut(new XslFoOutput("./target/testFlatUtf8PdfOutput.pdf"));
		
		flatReport.execute(); 
	}
	
	public void testHugeReportHtmlOut(){
		InputStream testStream = getTestFileFromClasspath("2010-1MIN-DATA.tsv");
		assertNotNull(testStream);
		
		StreamReportInput in = new StreamReportInput(testStream, "\t");
		
		flatReport.setIn(in);
		flatReport.setOut(new HtmlOutput("./target/testHugeReport.html"));
		
		flatReport.setReportTitle("OHLC TEST");
		flatReport.setGroupColumns(OhlcComputationScenario.GROUPING_COLUMNS);
		flatReport.setDataColumns(OhlcComputationScenario.DATA_COLUMNS);
		flatReport.setShowTotals(true);
		flatReport.setShowDataRows(true);
		flatReport.execute();
	}
	
	public void testFlatReportWithFormattedValues(){
		flatReport.setIn(ScenarioFormatedValues.INPUT);
		flatReport.setOut(new HtmlOutput("./target/testFormattedValues.html"));
		flatReport.setReportTitle("Formatted Values");
		flatReport.setShowTotals(true); 
		flatReport.setShowGrandTotal(true); 
		
		flatReport.setGroupColumns(ScenarioFormatedValues.GROUP_COLUMNS);
		flatReport.setDataColumns(ScenarioFormatedValues.DATA_COLUMNS);
		flatReport.execute();
	}
	
}
