/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.reportengine;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.components.EmptyLine;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.out.neo.ExcelXmlOutputFormat;
import net.sf.reportengine.out.neo.ExcelXmlReportOutput;
import net.sf.reportengine.out.neo.FoOutputFormat;
import net.sf.reportengine.out.neo.FoReportOutput;
import net.sf.reportengine.out.neo.HtmlReportOutput;
import net.sf.reportengine.out.neo.MockReportOutput;
import net.sf.reportengine.out.neo.PdfReportOutput;
import net.sf.reportengine.out.neo.PngReportOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.ScenarioFormatedValues;

import org.apache.commons.lang.SystemUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

public class TestReport {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTwoComponentsAndHtmlOutput() throws IOException {
		MockReportOutput mockOutput = new MockReportOutput();//new FileWriter("./target/TestReport.html") 
		new Report.Builder(mockOutput)
			.add(new ReportTitle("this is the report title "))
			.add(new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
					.build())
			.build()
		.execute(); 
		
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
		new Report.Builder(new FoReportOutput(new FileWriter("./target/TestTwoComponents.fo"), new FoOutputFormat("A3")))
			.add(new ReportTitle("this is the report title "))
			.add(new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
					.build())
			.build()
		.execute(); 
	}
	
	@Test
	public void testTwoComponentsAndExcelXmlOutput() throws IOException {
		new Report.Builder(new ExcelXmlReportOutput(new FileWriter("./target/TestTwoComponents.xml"), new ExcelXmlOutputFormat()))
			.add(new ReportTitle("this is the report title"))
			.add(new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
					.build())
			.add(new EmptyLine())
			.build()
		.execute(); 
	}
	
	@Test
	public void testTwoComponentsAndPdfOutput() throws IOException {
		new Report.Builder(new PdfReportOutput(new FileOutputStream("./target/TestTwoComponents.pdf"))) 
			.add(new ReportTitle("this is the report title "))
			.add(new EmptyLine())
			.add(new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
					.build())
			.build()
		.execute(); 
	}
	
	@Test
	public void testTwoComponentsAndPngOutput() throws IOException {
		new Report.Builder(new PngReportOutput(new FileOutputStream("./target/TestTwoComponents.png"))) 
			.add(new ReportTitle("this is the report title "))
			.add(new EmptyLine())
			.add(new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
					.build())
			.build()
		.execute(); 
	}
	
	@Test
	public void testTwoTablesInSameReport() throws IOException{
		new Report.Builder(new HtmlReportOutput(new FileWriter("./target/ReportWithMultipleTables.html"))) 
			.add(new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
					.build())
			.add(new EmptyLine())
			.add(
				new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
					.build())
			.build()
		.execute(); 
	}
	
	@Ignore
	public void testMemoryLeaksOutputHtml() throws IOException {
		Report.Builder reportBuilder = new Report.Builder(new HtmlReportOutput(new FileWriter("./target/TestMemoryLeaks.html"))); 
		
		reportBuilder.add(new ReportTitle("Testing the html output for memory leaks")); 
		//add 1000 flat tables
		for(int i=0;i<1000;i++){
			
			reportBuilder.add(new FlatTable.Builder()
				.input(ScenarioFormatedValues.INPUT)
				.showTotals(true)
				.showGrandTotal(true)
				.groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
				.dataColumns(ScenarioFormatedValues.DATA_COLUMNS)
				.build()); 
			reportBuilder.add(new EmptyLine());
		}
		
		reportBuilder.build().execute(); 
	}
	
	
	@Ignore
	public void testMemoryLeaksOutputFo() throws IOException {
		Report.Builder reportBuilder = new Report.Builder(new FoReportOutput(new FileWriter("./target/TestMemoryLeaks.fo"), new FoOutputFormat())); 
		
		reportBuilder.add(new ReportTitle("Testing the fo output for memory leaks")); 
		//add 1000 flat tables
		for(int i=0;i<1000;i++){
			
			reportBuilder.add(new FlatTable.Builder()
				.input(ScenarioFormatedValues.INPUT)
				.showTotals(true)
				.showGrandTotal(true)
				.groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
				.dataColumns(ScenarioFormatedValues.DATA_COLUMNS)
				.build()); 
			reportBuilder.add(new EmptyLine());
		}
		
		reportBuilder.build().execute(); 
	}
	
	
	@Ignore
	public void testMemoryLeaksOutputPdf() throws IOException {
		Report.Builder reportBuilder = new Report.Builder(new PdfReportOutput(new FileOutputStream("./target/TestMemoryLeaks.pdf"))); 
		
		reportBuilder.add(new ReportTitle("Testing the Pdf output for memory leaks")); 
		//add 1000 flat tables
		for(int i=0;i<1000;i++){
			
			reportBuilder.add(new FlatTable.Builder()
				.input(ScenarioFormatedValues.INPUT)
				.showTotals(true)
				.showGrandTotal(true)
				.groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
				.dataColumns(ScenarioFormatedValues.DATA_COLUMNS)
				.build()); 
			reportBuilder.add(new EmptyLine());
		}
		
		reportBuilder.build().execute(); 
	}
	
	
	@Ignore
	public void testMemoryLeaksOutputPng() throws IOException {
		Report.Builder reportBuilder = new Report.Builder(new PngReportOutput("./target/TestMemoryLeaks.png")); 
		
		reportBuilder.add(new ReportTitle("Testing the png output for memory leaks")); 
		//add 1000 flat tables
		for(int i=0;i<1000;i++){
			
			reportBuilder.add(new FlatTable.Builder()
				.input(ScenarioFormatedValues.INPUT)
				.showTotals(true)
				.showGrandTotal(true)
				.groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
				.dataColumns(ScenarioFormatedValues.DATA_COLUMNS)
				.build()); 
			reportBuilder.add(new EmptyLine());
		}
		
		reportBuilder.build().execute(); 
	}
}
