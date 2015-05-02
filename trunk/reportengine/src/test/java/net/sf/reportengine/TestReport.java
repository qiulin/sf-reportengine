package net.sf.reportengine;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.components.EmptyLine;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.out.neo.AbstractReportOutput;
import net.sf.reportengine.out.neo.DefaultReportOutput;
import net.sf.reportengine.out.neo.ExcelXmlOutputFormat;
import net.sf.reportengine.out.neo.FoOutputFormat;
import net.sf.reportengine.out.neo.MockReportOutput;
import net.sf.reportengine.out.neo.PdfOutputFormat;
import net.sf.reportengine.out.neo.PostProcessedByteReportOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.ScenarioFormatedValues;

import org.apache.commons.lang.SystemUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestReport {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTwoComponentsAndHtmlOutput() throws IOException {
		MockReportOutput mockOutput = new MockReportOutput();//new FileWriter("./target/TestReport.html") 
		Report report = new Report(mockOutput); 
		report.add(new ReportTitle("this is the report title "));
		report.add(new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
					.build());
		report.execute(); 
		
//		System.out.println(mockOutput.getBuffer());
		
		Assert.assertEquals(
		"startReport"+SystemUtils.LINE_SEPARATOR+
		"title"+SystemUtils.LINE_SEPARATOR+
		"start table"+SystemUtils.LINE_SEPARATOR+
		"start header row"+SystemUtils.LINE_SEPARATOR+
		"[HeaderCell cspan=1 value=col 3][HeaderCell cspan=1 value=col 4][HeaderCell cspan=1 value=col 5]"+SystemUtils.LINE_SEPARATOR+
		"end header row"+SystemUtils.LINE_SEPARATOR+
		"startRow"+SystemUtils.LINE_SEPARATOR+
		"[Cell cspan=1 value=4][Cell cspan=1 value=5][Cell cspan=1 value=6]"+SystemUtils.LINE_SEPARATOR+
		"endRow"+SystemUtils.LINE_SEPARATOR+
		"startRow"+SystemUtils.LINE_SEPARATOR+
		"[Cell cspan=1 value=3][Cell cspan=1 value=3][Cell cspan=1 value=3]"+SystemUtils.LINE_SEPARATOR+
		"endRow"+SystemUtils.LINE_SEPARATOR+
		"startRow"+SystemUtils.LINE_SEPARATOR+
		"[Cell cspan=1 value=2][Cell cspan=1 value=2][Cell cspan=1 value=2]"+SystemUtils.LINE_SEPARATOR+
		"endRow"+SystemUtils.LINE_SEPARATOR+
		"startRow"+SystemUtils.LINE_SEPARATOR+
		"[Cell cspan=1 value=1][Cell cspan=1 value=1][Cell cspan=1 value=1]"+SystemUtils.LINE_SEPARATOR+
		"endRow"+SystemUtils.LINE_SEPARATOR+
		"startRow"+SystemUtils.LINE_SEPARATOR+
		"[Cell cspan=1 value=1][Cell cspan=1 value=1][Cell cspan=1 value=1]"+SystemUtils.LINE_SEPARATOR+
		"endRow"+SystemUtils.LINE_SEPARATOR+
		"startRow"+SystemUtils.LINE_SEPARATOR+
		"[Cell cspan=1 value=1][Cell cspan=1 value=7][Cell cspan=1 value=1]"+SystemUtils.LINE_SEPARATOR+
		"endRow"+SystemUtils.LINE_SEPARATOR+
		"startRow"+SystemUtils.LINE_SEPARATOR+
		"[Cell cspan=1 value= ][Cell cspan=1 value=6][Cell cspan=1 value=14]"+SystemUtils.LINE_SEPARATOR+
		"endRow"+SystemUtils.LINE_SEPARATOR+
		"end table"+SystemUtils.LINE_SEPARATOR+
		"end report", 
		mockOutput.getBuffer());
	}
	
	@Test
	public void testTwoComponentsAndFoOutput() throws IOException {
		AbstractReportOutput mockOutput = new DefaultReportOutput(	new FileWriter("./target/TestTwoComponents.fo"), 
																new FoOutputFormat("A3"));
		Report report = new Report(mockOutput); 
		report.add(new ReportTitle("this is the report title "));
		report.add(new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
					.build());
		report.execute(); 
		
		//System.out.println(mockOutput.getBuffer());
	}
	
	@Test
	public void testTwoComponentsAndExcelXmlOutput() throws IOException {
		AbstractReportOutput mockOutput = new DefaultReportOutput(	new FileWriter("./target/TestTwoComponents.xml"), 
																new ExcelXmlOutputFormat());
		Report report = new Report(mockOutput); 
		report.add(new ReportTitle("this is the report title "));
		report.add(new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
					.build());
		report.add(new EmptyLine()); 
		report.execute(); 
	}
	
	@Test
	public void testTwoComponentsAndPdfOutput() throws IOException {
		AbstractReportOutput mockOutput = new PostProcessedByteReportOutput(new FileOutputStream("./target/TestTwoComponents.pdf"), 
																			new PdfOutputFormat());
		Report report = new Report(mockOutput); 
		report.add(new ReportTitle("this is the report title "));
		report.add(new EmptyLine()); 
		report.add(new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
					.build());
		report.execute(); 
	}
	
	@Test
	public void testTwoTablesInSameReport() throws IOException{
		Report report = new Report(new DefaultReportOutput(new FileWriter("./target/ReportWithMultipleTables.html"))); 
		report.add(
				new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
					.build());
		
		report.add(new EmptyLine()); 
		
		report.add(
				new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
					.build());
		report.execute(); 
	}
	
	@Test
	public void testMemoryLeaksOutputHtml() throws IOException {
		Report report = new Report(new DefaultReportOutput(new FileWriter("./target/TestMemoryLeaks.html"))); 
		
		//add 1000 flat tables
		for(int i=0;i<1000;i++){
			
			report.add(new FlatTable.Builder()
				.input(ScenarioFormatedValues.INPUT)
				.showTotals(true)
				.showGrandTotal(true)
				.groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
				.dataColumns(ScenarioFormatedValues.DATA_COLUMNS)
				.build()); 
		}
		
		report.execute(); 
	}
	
	
	@Test
	public void testMemoryLeaksOutputFo() throws IOException {
		Report report = new Report(new DefaultReportOutput(new FileWriter("./target/TestMemoryLeaks.fo"), new FoOutputFormat())); 
		
		//add 1000 flat tables
		for(int i=0;i<1000;i++){
			
			report.add(new FlatTable.Builder()
				.input(ScenarioFormatedValues.INPUT)
				.showTotals(true)
				.showGrandTotal(true)
				.groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
				.dataColumns(ScenarioFormatedValues.DATA_COLUMNS)
				.build()); 
			report.add(new EmptyLine());
		}
		
		report.execute(); 
	}
	
	
	@Test
	public void testMemoryLeaksOutputPdf() throws IOException {
		Report report = new Report(
				new PostProcessedByteReportOutput(	new FileOutputStream("./target/TestMemoryLeaks.pdf"), 
													new PdfOutputFormat())); 
		
		//add 1000 flat tables
		for(int i=0;i<1000;i++){
			
			report.add(new FlatTable.Builder()
				.input(ScenarioFormatedValues.INPUT)
				.showTotals(true)
				.showGrandTotal(true)
				.groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
				.dataColumns(ScenarioFormatedValues.DATA_COLUMNS)
				.build()); 
			report.add(new EmptyLine());
		}
		
		report.execute(); 
	}
}
