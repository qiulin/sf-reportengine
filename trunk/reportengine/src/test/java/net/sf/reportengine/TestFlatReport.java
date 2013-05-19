package net.sf.reportengine;

import java.io.InputStream;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.out.ExcelOutput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.out.PdfOutput;
import net.sf.reportengine.out.PngOutput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.StaxReportOutput;
import net.sf.reportengine.out.XslFoOutput;
import net.sf.reportengine.out.XsltOutput;
import net.sf.reportengine.scenarios.NoGroupsScenario;
import net.sf.reportengine.scenarios.OhlcComputationScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2x3x1;
import net.sf.reportengine.scenarios.ScenarioFormatedValues;
import net.sf.reportengine.util.MatrixUtils;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class TestFlatReport {
	
	@Test
	public void testExecuteScenario1(){
		FlatReport flatReport = new FlatReport();	
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
			
			Assert.assertTrue(MatrixUtils.compareMatrices(testOut.getDataCellMatrix(), Scenario1.EXPECTED_OUTPUT_UNSORTED));
	}
	
	@Test
	public void testExecuteScenario1WithAddColumn(){
		FlatReport flatReport = new FlatReport();	
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
		Assert.assertTrue(MatrixUtils.compareMatrices(testOut.getDataCellMatrix(), Scenario1.EXPECTED_OUTPUT_UNSORTED));
	}
	
	@Test
	public void testExecuteScenarioOhlc(){
		FlatReport flatReport = new FlatReport();	
		InputStream testStream = ReportIoUtils.createInputStreamFromClassPath("EURUSD_2007-2009_FirstHours.txt");
		Assert.assertNotNull(testStream);
		
		TextInput in = new TextInput(testStream,"\t");
		
		OutputDispatcher output = new OutputDispatcher();
		output.registerOutput(new HtmlOutput("target/testFlatReportOHLC.html"));
		output.registerOutput(new ExcelOutput("target/testFlatReportOHLC.xls"));
		
		flatReport.setIn(in);
		flatReport.setOut(output);
		
		flatReport.setTitle("OHLC TEST");
		flatReport.setDataColumns(OhlcComputationScenario.DATA_COLUMNS);
		flatReport.setGroupColumns(OhlcComputationScenario.GROUPING_COLUMNS); 
		flatReport.setShowTotals(true);
		flatReport.setShowDataRows(false);
		flatReport.execute();
		
		//TODO: create assert
	}
	
	@Test
	public void testExecute2x3x1(){
		FlatReport flatReport = new FlatReport();	
    	InputStream inputStream = ReportIoUtils.createInputStreamFromClassPath("2x3x1.txt");
    	TextInput input = new TextInput(inputStream);
    	
    	flatReport.setGroupColumns(Scenario2x3x1.GROUP_COLUMNS);
        flatReport.setDataColumns(Scenario2x3x1.DATA_COLUMNS);
        
        flatReport.setTitle("Test flat report 2x3x1d");    
        flatReport.setIn(input); 
        
        OutputDispatcher output = new OutputDispatcher();
        output.registerOutput(new HtmlOutput("target/testExecute2x3x1.html"));
        output.registerOutput(new StaxReportOutput("target/testExecute2x3x1.xml"));
        output.registerOutput(new XsltOutput(	"target/testXsltOutput2x3x1.html", 
        										ReportIoUtils.createReaderFromClassPath("net/sf/reportengine/xslt/defaultTemplate.xslt")));
        output.registerOutput(new PdfOutput("target/testExecute2x3x1.pdf"));
        output.registerOutput(new PngOutput("target/testExecute2x3x1.png"));
        flatReport.setOut(output);
        
        flatReport.setShowTotals(true);
        flatReport.execute();
        
        //TODO: assert here
    }
	
	@Test
	public void testExecuteNoGroupingColumnsReport() throws Exception{
		FlatReport flatReport = new FlatReport();	
    	flatReport.setGroupColumns(NoGroupsScenario.GROUPING_COLUMNS);
        flatReport.setDataColumns(NoGroupsScenario.DATA_COLUMNS);
        
        flatReport.setTitle("Test report having no grops but still showing the grand total");    
        flatReport.setIn(NoGroupsScenario.INPUT); 
        
        OutputDispatcher output = new OutputDispatcher();
        output.registerOutput(new HtmlOutput("target/testFlatReportNoGroupings.html"));
        output.registerOutput(new PdfOutput("target/testFlatReportNoGroupings.pdf")); 
        output.registerOutput(new PngOutput("target/testFlatReportNoGroupings.png")); 
        
        flatReport.setOut(output);
        flatReport.setShowTotals(true); 
        flatReport.setShowGrandTotal(true); 
        
        flatReport.execute();
        
        //TODO: assert here
	}
	
	@Test
	public void testFlatReportUtf8Output(){
		FlatReport flatReport = new FlatReport();	
		InputStream inputStream = ReportIoUtils.createInputStreamFromClassPath("Utf8Input.txt");
		Assert.assertNotNull(inputStream);
		
		flatReport.setTitle("Τη γλώσσα μου έδωσαν ελληνική");
		
		ReportInput reportInput = new TextInput(inputStream, ",", "UTF-8"); 
		flatReport.setIn(reportInput); 
		
		flatReport.addDataColumn(new DefaultDataColumn(0)); 
		flatReport.addDataColumn(new DefaultDataColumn(1));
		flatReport.addDataColumn(new DefaultDataColumn(2));
		flatReport.addDataColumn(new DefaultDataColumn(3));
		
		flatReport.setOut(new HtmlOutput(ReportIoUtils.createWriterFromPath("./target/testFlatReportUtf8Output.html")));
		
		flatReport.execute(); 
	}
	
	@Test
	public void testFlatReportUtf8PdfOutput(){
		FlatReport flatReport = new FlatReport();	
		InputStream inputStream = ReportIoUtils.createInputStreamFromClassPath("Utf8Input.txt");
		Assert.assertNotNull(inputStream);
		
		flatReport.setTitle("Τη γλώσσα μου έδωσαν ελληνική");
		
		ReportInput reportInput = new TextInput(inputStream, ",", "UTF-8"); 
		flatReport.setIn(reportInput); 
		
		flatReport.addDataColumn(new DefaultDataColumn(0)); 
		flatReport.addDataColumn(new DefaultDataColumn(1));
		flatReport.addDataColumn(new DefaultDataColumn(2));
		flatReport.addDataColumn(new DefaultDataColumn(3));
		
		flatReport.setOut(new XslFoOutput("./target/testFlatUtf8PdfOutput.pdf"));
		
		flatReport.execute(); 
		
		//TODO: assert here
	}
	
	@Test
	public void testHugeReportHtmlOut(){
		FlatReport flatReport = new FlatReport();	
		InputStream testStream = ReportIoUtils.createInputStreamFromClassPath("2010-1MIN-DATA.tsv");
		Assert.assertNotNull(testStream);
		
		TextInput in = new TextInput(testStream, "\t");
		
		flatReport.setIn(in);
		flatReport.setOut(new HtmlOutput("./target/testHugeReport.html"));
		
		flatReport.setTitle("OHLC TEST");
		flatReport.setGroupColumns(OhlcComputationScenario.GROUPING_COLUMNS);
		flatReport.setDataColumns(OhlcComputationScenario.DATA_COLUMNS);
		flatReport.setShowTotals(true);
		flatReport.setShowDataRows(true);
		flatReport.execute();
		
		//TODO: assert here
	}
	
	@Test
	public void testFlatReportWithFormattedValues(){
		FlatReport flatReport = new FlatReport();	
		flatReport.setIn(ScenarioFormatedValues.INPUT);
		flatReport.setOut(new HtmlOutput("./target/testFormattedValues.html"));
		flatReport.setTitle("Formatted Values");
		flatReport.setShowTotals(true); 
		flatReport.setShowGrandTotal(true); 
		
		flatReport.setGroupColumns(ScenarioFormatedValues.GROUP_COLUMNS);
		flatReport.setDataColumns(ScenarioFormatedValues.DATA_COLUMNS);
		flatReport.execute();
		
		//TODO: assert here
	}
	
	
	@Ignore
	public void testMemoryLeaks(){
		FlatReport flatReport = new FlatReport();	
		flatReport.setIn(ScenarioFormatedValues.INPUT);
		flatReport.setOut(new HtmlOutput("./target/testMemoryLeaks.html"));
		flatReport.setTitle("Formatted Values");
		flatReport.setShowTotals(true); 
		flatReport.setShowGrandTotal(true); 
		
		flatReport.setGroupColumns(ScenarioFormatedValues.GROUP_COLUMNS);
		flatReport.setDataColumns(ScenarioFormatedValues.DATA_COLUMNS);
		
		for(int i=0;i<1000;i++){
			flatReport.execute();
		}
	}
	
	
	@Test
	public void testExecuteWithSorting(){
		FlatReport flatReport = new FlatReport();	
		flatReport.setGroupValuesSorted(false); 
		
		CellPropsArrayOutput mockOut = new CellPropsArrayOutput();
		//ReportOutput htmlOut = new HtmlOutput("./target/ExternalySorted.html"); 
		
		flatReport.setIn(Scenario1.INPUT);
		flatReport.setOut(mockOut);
		flatReport.setDataColumns(Scenario1.DATA_COLUMNS);
		flatReport.setGroupColumns(Scenario1.GROUPING_COLUMNS);
		flatReport.setShowTotals(false);
		flatReport.setShowDataRows(true);
		flatReport.setShowGrandTotal(true);
		flatReport.execute();
			
		Assert.assertTrue(MatrixUtils.compareMatrices(	mockOut.getDataCellMatrix(), 
														Scenario1.EXPECTED_OUTPUT_SORTED));
	}
}
