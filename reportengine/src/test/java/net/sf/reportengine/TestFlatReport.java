package net.sf.reportengine;

import java.io.InputStream;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.in.TextInput;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.out.ExcelOutput;
import net.sf.reportengine.out.Html5Output;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.out.PdfOutput;
import net.sf.reportengine.out.PngOutput;
import net.sf.reportengine.out.StaxReportOutput;
import net.sf.reportengine.out.XsltOutput;
import net.sf.reportengine.scenarios.NoGroupsScenario;
import net.sf.reportengine.scenarios.OhlcComputationScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2x3x1;
import net.sf.reportengine.scenarios.ScenarioFormatedValues;
import net.sf.reportengine.scenarios.ScenarioSort;
import net.sf.reportengine.scenarios.SortScenarioOnlyDataColsCount;
import net.sf.reportengine.util.MatrixUtils;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class TestFlatReport {
	
	@Test
	public void testExecuteReportWithoutGroupColumns(){
		//this report has no grouping columns but it has a grand total at the end
		//because the data columns have group calculators added
		FlatReport report = new FlatReport.Builder()
					.title("This report has no grouping columns but it has a grand total at the end")
					.input(Scenario1.INPUT)
					.output(new Html5Output("./target/ReportWithoutGroupColumns.html"))
					.dataColumns(Scenario1.DATA_COLUMNS)
					//.showDataRows(true) - this is by default
				.build();
		
		//test the computed values
		Assert.assertTrue(report.getShowDataRows());
		Assert.assertTrue(report.getShowTotals());
		Assert.assertTrue(report.getShowGrandTotal());
		Assert.assertNotNull(report.getTitle());
		
		//execute the report
		report.execute();
		
		//TODO: test the output here
	}
	
	
	@Test
	public void testExecuteScenario1(){
		//test of a simple report with 3 grouping columns and 3 data columns 
		//the grouping columns don't require any sorting
		
		OutputDispatcher outputDispatcher = new OutputDispatcher(); 
		CellPropsArrayOutput testOut = new CellPropsArrayOutput();
		outputDispatcher.registerOutput(testOut); 
		outputDispatcher.registerOutput(new Html5Output("./target/Scenario1.html")); 
		
		FlatReport flatReport = new FlatReport.Builder()
									.input(Scenario1.INPUT)
									.output(outputDispatcher)
									.dataColumns(Scenario1.DATA_COLUMNS)
									.groupColumns(Scenario1.GROUPING_COLUMNS)
									//.showTotals(true)
									//.showDataRows(true)
									//.showGrandTotal(true)
									.build();
		
		//check the default values
		Assert.assertTrue(flatReport.getShowTotals());
		Assert.assertTrue(flatReport.getShowGrandTotal());
		Assert.assertTrue(flatReport.getShowDataRows());
		Assert.assertNull(flatReport.getTitle());
		
		//execute the report
		flatReport.execute();
		
		//test the output	
		Assert.assertTrue(MatrixUtils.compareMatrices(
										testOut.getDataCellMatrix(), 
										Scenario1.EXPECTED_OUTPUT_UNSORTED));
	}
	
	@Test
	public void testExecuteScenario1WithNonFluentBuilder(){
		//same test as above but instead of using the fluent builder we use the FlatReport constructor
		//plus instead of adding all columns at once (as a list) we add the columns one by one
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
		
		//one of the drawbacks when using the FlatReport constructor 
		//instead of the fluent builder is that you need to set the 
		//values manually 
		flatReport.setShowTotals(true);
		flatReport.setShowDataRows(true);
		flatReport.setShowGrandTotal(true); 
		
		Assert.assertTrue(flatReport.getShowDataRows());
		Assert.assertTrue(flatReport.getShowTotals());
		Assert.assertTrue(flatReport.getShowGrandTotal());
		
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
		output.registerOutput(new Html5Output("target/testFlatReportOHLC.html"));
		output.registerOutput(new ExcelOutput("target/testFlatReportOHLC.xls"));
		
		flatReport.setIn(in);
		flatReport.setOut(output);
		
		flatReport.setTitle("OHLC TEST");
		flatReport.setDataColumns(OhlcComputationScenario.DATA_COLUMNS);
		flatReport.setGroupColumns(OhlcComputationScenario.GROUPING_COLUMNS); 
		flatReport.setShowTotals(true);
		flatReport.setShowDataRows(false);
		flatReport.execute();
		
		//TODO: test the final output here
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
        output.registerOutput(new Html5Output("target/testExecute2x3x1.html"));
        output.registerOutput(new StaxReportOutput("target/testExecute2x3x1.xml"));
        output.registerOutput(new XsltOutput(	"target/testXsltOutput2x3x1.html", 
        										false, 
        										ReportIoUtils.createReaderFromClassPath("net/sf/reportengine/xslt/defaultTemplate.xslt")));
        output.registerOutput(new PdfOutput("target/testExecute2x3x1.pdf"));
        output.registerOutput(new PngOutput("target/testExecute2x3x1.png"));
        flatReport.setOut(output);
        
        flatReport.setShowTotals(true);
        flatReport.execute();
        
        //TODO: assert here
    }
	
	@Test
	public void testExecuteNoGroupingColumnsReport() {
		FlatReport flatReport = new FlatReport();	
    	flatReport.setGroupColumns(NoGroupsScenario.GROUPING_COLUMNS);
        flatReport.setDataColumns(NoGroupsScenario.DATA_COLUMNS);
        
        flatReport.setTitle("Test report having no grops but still showing the grand total");    
        flatReport.setIn(NoGroupsScenario.INPUT); 
        
        OutputDispatcher output = new OutputDispatcher();
        output.registerOutput(new Html5Output("target/testFlatReportNoGroupings.html"));
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
		
		flatReport.setTitle("Sîne klâwen durh die wolken sint geslagen");
		
		ReportInput reportInput = new TextInput(inputStream, ",", "UTF-8"); 
		flatReport.setIn(reportInput); 
		
		flatReport.addDataColumn(new DefaultDataColumn(0)); 
		flatReport.addDataColumn(new DefaultDataColumn(1));
		flatReport.addDataColumn(new DefaultDataColumn(2));
		flatReport.addDataColumn(new DefaultDataColumn(3));
		
		flatReport.setOut(new Html5Output(ReportIoUtils.createWriterFromPath("./target/testFlatReportUtf8Output.html")));
		
		flatReport.execute(); 
	}
	
	@Test
	public void testFlatReportUtf8PdfOutput(){
		FlatReport flatReport = new FlatReport();	
		InputStream inputStream = ReportIoUtils.createInputStreamFromClassPath("Utf8Input.txt");
		Assert.assertNotNull(inputStream);
		
		flatReport.setTitle("Sîne klâwen durh die wolken sint geslagen");
		
		ReportInput reportInput = new TextInput(inputStream, ",", "UTF-8"); 
		flatReport.setIn(reportInput); 
		
		flatReport.addDataColumn(new DefaultDataColumn(0)); 
		flatReport.addDataColumn(new DefaultDataColumn(1));
		flatReport.addDataColumn(new DefaultDataColumn(2));
		flatReport.addDataColumn(new DefaultDataColumn(3));
		
		flatReport.setOut(new PdfOutput("./target/testFlatUtf8PdfOutput.pdf"));
		
		flatReport.execute(); 
		
		//TODO: assert here
	}
	
	@Test
	public void testHugeReportHtmlOut(){
		new FlatReport.Builder()
			.title("OHLC TEST")
			.input(OhlcComputationScenario.INPUT)
			.output(new Html5Output("./target/testHugeReport.html"))
			.groupColumns(OhlcComputationScenario.GROUPING_COLUMNS)
			.dataColumns(OhlcComputationScenario.DATA_COLUMNS)
			.build() 
		.execute(); 
		
		//TODO: assert here
	}
	
	@Test
	public void testFlatReportWithFormattedValues(){
		FlatReport flatReport = new FlatReport();	
		flatReport.setIn(ScenarioFormatedValues.INPUT);
		flatReport.setOut(new Html5Output("./target/testFormattedValues.html"));
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
		flatReport.setOut(new Html5Output("./target/testMemoryLeaks.html"));
		flatReport.setTitle("Test Memory leaks");
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
		FlatReport flatReport = new FlatReport(); //TODO: apply builder here	
		flatReport.setValuesSorted(false); // all group values will be sorted 
		
		CellPropsArrayOutput mockOut = new CellPropsArrayOutput();
		
		flatReport.setIn(ScenarioSort.INPUT);
		flatReport.setOut(mockOut);
		flatReport.setDataColumns(ScenarioSort.DATA_COLUMNS);
		flatReport.setGroupColumns(ScenarioSort.GROUPING_COLUMNS);
		flatReport.setShowTotals(false);
		flatReport.setShowDataRows(true);
		flatReport.setShowGrandTotal(true);
		flatReport.execute();
		
		Assert.assertTrue(MatrixUtils.compareMatrices(	mockOut.getDataCellMatrix(), 
														ScenarioSort.EXPECTED_OUTPUT_SORTED));
	}
	
	@Test
	public void testExecuteWithSortingOnDataCols(){
		FlatReport flatReport = new FlatReport.Builder()
									//.showDataRows(true)
									//.showGrandTotal(true)
									.showTotals(false)
									//.sortValues() - no need to declare that values need sorting because the dataColumns below have sorting
									.input(SortScenarioOnlyDataColsCount.INPUT)
									.output(new PdfOutput("./target/testSortingOnDataCols.pdf"))
									.dataColumns(SortScenarioOnlyDataColsCount.DATA_COLUMNS)
									.groupColumns(SortScenarioOnlyDataColsCount.GROUPING_COLUMNS)
									.build();
		
		//check some of the computed values + default ones
		Assert.assertTrue(flatReport.getShowDataRows());
		Assert.assertFalse(flatReport.getShowTotals()); 
		Assert.assertTrue(flatReport.getShowGrandTotal());
		Assert.assertTrue(flatReport.hasValuesSorted());
				
		flatReport.execute();
	}
	
	@Test
	public void testTwoTablesInSameFile(){
		new FlatReport.Builder()
			.title("This is the first report in the document. Below this table you should see another one.")
			.input(Scenario1.INPUT)
			.output(new Html5Output("./target/ReportMultipleTables.html", false))
			.dataColumns(Scenario1.DATA_COLUMNS)
			.build().execute();;
		
		new FlatReport.Builder()
			.title("This is the second table. If it is displayed correctly then the test has passed")
			.input(Scenario1.INPUT)
			.output(new Html5Output("./target/ReportMultipleTables.html", true))
			.dataColumns(Scenario1.DATA_COLUMNS)
			.build().execute();
	}
}
