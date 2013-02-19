package net.sf.reportengine;

import java.io.InputStream;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.out.ExcelOutput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.out.StaxReportOutput;
import net.sf.reportengine.out.XslFoOutput;
import net.sf.reportengine.out.XsltOutput;
import net.sf.reportengine.scenarios.NoGroupsScenario;
import net.sf.reportengine.scenarios.OhlcComputationScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2x3x1;
import net.sf.reportengine.scenarios.ScenarioFormatedValues;
import net.sf.reportengine.test.ReportengineTC;
import net.sf.reportengine.util.MatrixUtils;
import net.sf.reportengine.util.ReportIoUtils;

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
			
			CellPropsArrayOutput testOut = new CellPropsArrayOutput();
			
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
		CellPropsArrayOutput testOut = new CellPropsArrayOutput();
		
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
		InputStream testStream = ReportIoUtils.createInputStreamFromClassPath("EURUSD_2007-2009_FirstHours.txt");
		assertNotNull(testStream);
		
		TextInput in = new TextInput(testStream,"\t");
		
		OutputDispatcher output = new OutputDispatcher();
		output.registerOutput(new HtmlOutput("target/testFlatReportOHLC.html"));
		output.registerOutput(new ExcelOutput("target/testFlatReportOHLC.xls"));
		
		flatReport.setIn(in);
		flatReport.setOut(output);
		
		flatReport.setReportTitle("OHLC TEST");
		flatReport.setDataColumns(OhlcComputationScenario.DATA_COLUMNS);
		flatReport.setGroupColumns(OhlcComputationScenario.GROUPING_COLUMNS); 
		flatReport.setShowTotals(true);
		flatReport.setShowDataRows(false);
		flatReport.execute();
		
		//TODO: create assert
	}
	
	public void testExecute2x3x1(){
    	InputStream inputStream = ReportIoUtils.createInputStreamFromClassPath("2x3x1.txt");
    	TextInput input = new TextInput(inputStream);
    	
    	flatReport.setGroupColumns(Scenario2x3x1.GROUP_COLUMNS);
        flatReport.setDataColumns(Scenario2x3x1.DATA_COLUMNS);
        
        flatReport.setReportTitle("Test flat report 2x3x1d");    
        flatReport.setIn(input); 
        
        OutputDispatcher output = new OutputDispatcher();
        output.registerOutput(new HtmlOutput("target/testExecute2x3x1.html"));
        output.registerOutput(new StaxReportOutput("target/testExecute2x3x1.xml"));
        output.registerOutput(new XsltOutput(	"target/testXsltOutput2x3x1.html", 
        										ReportIoUtils.createReaderFromClassPath("net/sf/reportengine/xslt/defaultTemplate.xslt")));
        output.registerOutput(new XslFoOutput("target/testExecute2x3x1.pdf"));
        flatReport.setOut(output);
        
        flatReport.setShowTotals(true);
        flatReport.execute();
        
        //TODO: assert here
    }
	
	public void testExecuteNoGroupingColumnsReport() throws Exception{
    	flatReport.setGroupColumns(NoGroupsScenario.GROUPING_COLUMNS);
        flatReport.setDataColumns(NoGroupsScenario.DATA_COLUMNS);
        
        flatReport.setReportTitle("Test report having no grops but still showing the grand total");    
        flatReport.setIn(NoGroupsScenario.INPUT); 
        
        OutputDispatcher output = new OutputDispatcher();
        output.registerOutput(new HtmlOutput("target/testFlatReportNoGroupings.html"));
        flatReport.setOut(output);
        flatReport.setShowTotals(true); 
        flatReport.setShowGrandTotal(true); 
        
        flatReport.execute();
        
        //TODO: assert here
	}
	
	public void testFlatReportUtf8Output(){
		InputStream inputStream = ReportIoUtils.createInputStreamFromClassPath("Utf8Input.txt");
		assertNotNull(inputStream);
		
		flatReport.setReportTitle("Τη γλώσσα μου έδωσαν ελληνική");
		
		IReportInput reportInput = new TextInput(inputStream, ",", "UTF-8"); 
		flatReport.setIn(reportInput); 
		
		flatReport.addDataColumn(new DefaultDataColumn(0)); 
		flatReport.addDataColumn(new DefaultDataColumn(1));
		flatReport.addDataColumn(new DefaultDataColumn(2));
		flatReport.addDataColumn(new DefaultDataColumn(3));
		
		flatReport.setOut(new HtmlOutput(createTestWriter("testFlatReportUtf8Output.html")));
		
		flatReport.execute(); 
	}
	
	public void testFlatReportUtf8PdfOutput(){
		InputStream inputStream = ReportIoUtils.createInputStreamFromClassPath("Utf8Input.txt");
		assertNotNull(inputStream);
		
		flatReport.setReportTitle("Τη γλώσσα μου έδωσαν ελληνική");
		
		IReportInput reportInput = new TextInput(inputStream, ",", "UTF-8"); 
		flatReport.setIn(reportInput); 
		
		flatReport.addDataColumn(new DefaultDataColumn(0)); 
		flatReport.addDataColumn(new DefaultDataColumn(1));
		flatReport.addDataColumn(new DefaultDataColumn(2));
		flatReport.addDataColumn(new DefaultDataColumn(3));
		
		flatReport.setOut(new XslFoOutput("./target/testFlatUtf8PdfOutput.pdf"));
		
		flatReport.execute(); 
		
		//TODO: assert here
	}
	
	public void testHugeReportHtmlOut(){
		InputStream testStream = ReportIoUtils.createInputStreamFromClassPath("2010-1MIN-DATA.tsv");
		assertNotNull(testStream);
		
		TextInput in = new TextInput(testStream, "\t");
		
		flatReport.setIn(in);
		flatReport.setOut(new HtmlOutput("./target/testHugeReport.html"));
		
		flatReport.setReportTitle("OHLC TEST");
		flatReport.setGroupColumns(OhlcComputationScenario.GROUPING_COLUMNS);
		flatReport.setDataColumns(OhlcComputationScenario.DATA_COLUMNS);
		flatReport.setShowTotals(true);
		flatReport.setShowDataRows(true);
		flatReport.execute();
		
		//TODO: assert here
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
		
		//TODO: assert here
	}
	
//	public void testMemoryLeaks(){
//		
//		flatReport.setIn(ScenarioFormatedValues.INPUT);
//		flatReport.setOut(new HtmlOutput("./target/testMemoryLeaks.html"));
//		flatReport.setReportTitle("Formatted Values");
//		flatReport.setShowTotals(true); 
//		flatReport.setShowGrandTotal(true); 
//		
//		flatReport.setGroupColumns(ScenarioFormatedValues.GROUP_COLUMNS);
//		flatReport.setDataColumns(ScenarioFormatedValues.DATA_COLUMNS);
//		
//		for(int i=0;i<1000;i++){
//			flatReport.execute();
//		}
//	}
	
}
