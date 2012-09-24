package net.sf.reportengine;

import java.io.InputStream;
import java.io.OutputStream;

import net.sf.reportengine.core.ReportEngineException;
import net.sf.reportengine.in.StreamReportInput;
import net.sf.reportengine.out.MemoryOutput;
import net.sf.reportengine.out.ExcelOutput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.out.XmlDOMReportOutput;
import net.sf.reportengine.out.XslFoOutput;
import net.sf.reportengine.out.XsltReportOutput;
import net.sf.reportengine.scenarios.NoGroupsScenario;
import net.sf.reportengine.scenarios.OhlcComputationScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2x3x1;
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
			flatReport.setGroupingColumns(Scenario1.GROUPING_COLUMNS);
			flatReport.setShowTotals(true);
			flatReport.setShowDataRows(true);
		
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
				flatReport.setGroupingColumns(OhlcComputationScenario.GROUP_COLUMNS); 
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
			flatReport.setGroupingColumns(ShowOnlySpecificTotalsScenario.GROUP_COLUMNS);
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
        boolean flawless = true;
        try{
        	InputStream inputStream = getTestFileFromClasspath("2x3x1.txt");
        	StreamReportInput input = new StreamReportInput(inputStream);
        	
        	flatReport.setGroupingColumns(Scenario2x3x1.GROUP_COLUMNS);
            flatReport.setDataColumns(Scenario2x3x1.DATA_COLUMNS);
            
            flatReport.setReportTitle("Test flat report 2x3x1d");    
            flatReport.setIn(input); 
            
            OutputStream testOutput = createTestOutputFile("test_FlatReportTool_file_in.html");
            assertNotNull(testOutput);
            
            InputStream xstlStream = getTestFileFromClasspath("net/sf/reportengine/defaultTemplate.xslt");
            assertNotNull(xstlStream);
            
            OutputDispatcher output = new OutputDispatcher();
            output.registerOutput(new HtmlOutput(createTestOutputFile("testExecute2x3x1.html")));
            output.registerOutput(new XmlDOMReportOutput(createTestOutputFile("testExecute2x3x1.xml")));
            output.registerOutput(new XsltReportOutput(testOutput, xstlStream));
            output.registerOutput(new XslFoOutput(createTestOutputFile("testExecute2x3x1.pdf")));
            flatReport.setOut(output);
            
            flatReport.setShowTotals(true);
            flatReport.execute();
            
        }catch(Throwable rex){
            flawless = false;
            rex.printStackTrace();
        }
        
        assertTrue(flawless);
    }
	
	public void testExecuteNoGroupingColumnsReport() throws Exception{
    	flatReport.setGroupingColumns(NoGroupsScenario.GROUPING_COLUMNS);
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

	/*
	public void testHugeReport(){
		try {
			InputStream testStream = getTestFileFromClasspath("EURUSD_4YEARS_ONE_MINUTE_DATA.csv");
			assertNotNull(testStream);
			
			StreamReportInput in = new StreamReportInput(testStream,",");
			
			flatReport.setIn(in);
			flatReport.setOut(new HtmlReportOutput(createTestOutputFile("testHugeReport.html")));
			
			flatReport.setReportTitle("OHLC TEST");
			flatReport.setGroupingColumns(OhlcComputationScenario.GROUP_COLUMNS);
			flatReport.setDataColumns(OhlcComputationScenario.DATA_COLUMNS);
			flatReport.setShowTotals(true);
			flatReport.setShowDataRows(true);
			flatReport.execute();
			
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Error occured when executing test : "+e.getMessage());			
		}
	}
	*/
}
