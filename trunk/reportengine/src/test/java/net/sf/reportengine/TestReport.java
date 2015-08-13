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
import java.io.StringWriter;

import net.sf.reportengine.components.EmptyLine;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.out.ExcelXmlReportOutput;
import net.sf.reportengine.out.FoOutputFormat;
import net.sf.reportengine.out.FoReportOutput;
import net.sf.reportengine.out.HtmlReportOutput;
import net.sf.reportengine.out.MockReportOutput;
import net.sf.reportengine.out.PdfReportOutput;
import net.sf.reportengine.out.PngReportOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.ScenarioFormatedValues;

import org.apache.commons.lang.SystemUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestReport {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTwoComponents() throws IOException {
		StringWriter testWriter = new StringWriter();
		MockReportOutput mockOutput = new MockReportOutput(testWriter);
		new ReportBuilder(mockOutput)
				.add(new ReportTitle("unit test"))
				.add(new FlatTable.Builder().input(Scenario1.INPUT)
						.dataColumns(Scenario1.DATA_COLUMNS).build()).build()
				.execute();

		Assert.assertEquals(
				"startReport"
						+ SystemUtils.LINE_SEPARATOR
						+ "title unit test"
						+ SystemUtils.LINE_SEPARATOR
						+ "start table"
						+ SystemUtils.LINE_SEPARATOR
						+ "start header row"
						+ SystemUtils.LINE_SEPARATOR
						+ "[HeaderCell cspan=1 value=col 3 hAlign=CENTER vAlign=MIDDLE][HeaderCell cspan=1 value=col 4 hAlign=CENTER vAlign=MIDDLE][HeaderCell cspan=1 value=col 5 hAlign=CENTER vAlign=MIDDLE]"
						+ SystemUtils.LINE_SEPARATOR
						+ "end header row"
						+ SystemUtils.LINE_SEPARATOR
						+ "startRow 0"
						+ SystemUtils.LINE_SEPARATOR
						+ "[Cell cspan=1 value=4][Cell cspan=1 value=5][Cell cspan=1 value=6]"
						+ SystemUtils.LINE_SEPARATOR
						+ "endRow"
						+ SystemUtils.LINE_SEPARATOR
						+ "startRow 1"
						+ SystemUtils.LINE_SEPARATOR
						+ "[Cell cspan=1 value=3][Cell cspan=1 value=3][Cell cspan=1 value=3]"
						+ SystemUtils.LINE_SEPARATOR
						+ "endRow"
						+ SystemUtils.LINE_SEPARATOR
						+ "startRow 2"
						+ SystemUtils.LINE_SEPARATOR
						+ "[Cell cspan=1 value=2][Cell cspan=1 value=2][Cell cspan=1 value=2]"
						+ SystemUtils.LINE_SEPARATOR
						+ "endRow"
						+ SystemUtils.LINE_SEPARATOR
						+ "startRow 3"
						+ SystemUtils.LINE_SEPARATOR
						+ "[Cell cspan=1 value=1][Cell cspan=1 value=1][Cell cspan=1 value=1]"
						+ SystemUtils.LINE_SEPARATOR
						+ "endRow"
						+ SystemUtils.LINE_SEPARATOR
						+ "startRow 4"
						+ SystemUtils.LINE_SEPARATOR
						+ "[Cell cspan=1 value=1][Cell cspan=1 value=1][Cell cspan=1 value=1]"
						+ SystemUtils.LINE_SEPARATOR
						+ "endRow"
						+ SystemUtils.LINE_SEPARATOR
						+ "startRow 5"
						+ SystemUtils.LINE_SEPARATOR
						+ "[Cell cspan=1 value=1][Cell cspan=1 value=7][Cell cspan=1 value=1]"
						+ SystemUtils.LINE_SEPARATOR
						+ "endRow"
						+ SystemUtils.LINE_SEPARATOR
						+ "startRow 6"
						+ SystemUtils.LINE_SEPARATOR
						+ "[Cell cspan=1 value= ][Cell cspan=1 value=6][Cell cspan=1 value=14]"
						+ SystemUtils.LINE_SEPARATOR + "endRow"
						+ SystemUtils.LINE_SEPARATOR + "end table"
						+ SystemUtils.LINE_SEPARATOR + "end report", testWriter
						.getBuffer().toString());
	}

	@Test
	public void testTwoComponentsAndFoOutput() throws IOException {
		new ReportBuilder(new FoReportOutput(new FileWriter(
				"./target/TestTwoComponents.fo"), true,
				new FoOutputFormat("A3")))
				.add(new ReportTitle("this is the report title "))
				.add(new FlatTable.Builder().input(Scenario1.INPUT)
						.dataColumns(Scenario1.DATA_COLUMNS).build()).build()
				.execute();
	}

	@Test
	public void testTwoComponentsAndExcelXmlOutput() throws IOException {
		new ReportBuilder(new ExcelXmlReportOutput(new FileWriter(
				"./target/TestTwoComponents.xml"), true))
				.add(new ReportTitle("this is the report title"))
				.add(new FlatTable.Builder().input(Scenario1.INPUT)
						.dataColumns(Scenario1.DATA_COLUMNS).build())
				.add(new EmptyLine()).build().execute();
	}

	@Test
	public void testTwoComponentsAndPdfOutput() throws IOException {
		new ReportBuilder(new PdfReportOutput(new FileOutputStream(
				"./target/TestTwoComponents.pdf")))
				.add(new ReportTitle("this is the report title "))
				.add(new EmptyLine())
				.add(new FlatTable.Builder().input(Scenario1.INPUT)
						.dataColumns(Scenario1.DATA_COLUMNS).build()).build()
				.execute();
	}

	@Test
	public void testTwoComponentsAndPngOutput() throws IOException {
		new ReportBuilder(new PngReportOutput(new FileOutputStream(
				"./target/TestTwoComponents.png")))
				.add(new ReportTitle("this is the report title "))
				.add(new EmptyLine())
				.add(new FlatTable.Builder().input(Scenario1.INPUT)
						.dataColumns(Scenario1.DATA_COLUMNS).build()).build()
				.execute();
	}

	@Test
	public void testTwoTablesInSameReport() throws IOException {
		new ReportBuilder(new HtmlReportOutput(new FileWriter(
				"./target/ReportWithMultipleTables.html")))
				.add(new FlatTable.Builder().input(Scenario1.INPUT)
						.dataColumns(Scenario1.DATA_COLUMNS).build())
				.add(new EmptyLine())
				.add(new FlatTable.Builder().input(Scenario1.INPUT)
						.dataColumns(Scenario1.DATA_COLUMNS).build()).build()
				.execute();
	}

	@Ignore
	public void testMemoryLeaksOutputHtml() throws IOException {
		ReportBuilder reportBuilder = new ReportBuilder(new HtmlReportOutput(
				new FileWriter("./target/TestMemoryLeaks.html")));

		reportBuilder.add(new ReportTitle(
				"Testing the html output for memory leaks"));
		// add 1000 flat tables
		for (int i = 0; i < 1000; i++) {

			reportBuilder.add(new FlatTable.Builder()
					.input(ScenarioFormatedValues.INPUT).showTotals(true)
					.showGrandTotal(true)
					.groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
					.dataColumns(ScenarioFormatedValues.DATA_COLUMNS).build());
			reportBuilder.add(new EmptyLine());
		}

		reportBuilder.build().execute();
	}

	@Ignore
	public void testMemoryLeaksOutputFo() throws IOException {
		ReportBuilder reportBuilder = new ReportBuilder(new FoReportOutput(
				new FileWriter("./target/TestMemoryLeaks.fo"), true,
				new FoOutputFormat()));

		reportBuilder.add(new ReportTitle(
				"Testing the fo output for memory leaks"));
		// add 1000 flat tables
		for (int i = 0; i < 1000; i++) {

			reportBuilder.add(new FlatTable.Builder()
					.input(ScenarioFormatedValues.INPUT).showTotals(true)
					.showGrandTotal(true)
					.groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
					.dataColumns(ScenarioFormatedValues.DATA_COLUMNS).build());
			reportBuilder.add(new EmptyLine());
		}

		reportBuilder.build().execute();
	}

	@Ignore
	public void testMemoryLeaksOutputPdf() throws IOException {
		ReportBuilder reportBuilder = new ReportBuilder(new PdfReportOutput(
				new FileOutputStream("./target/TestMemoryLeaks.pdf")));

		reportBuilder.add(new ReportTitle(
				"Testing the Pdf output for memory leaks"));
		// add 1000 flat tables
		for (int i = 0; i < 1000; i++) {

			reportBuilder.add(new FlatTable.Builder()
					.input(ScenarioFormatedValues.INPUT).showTotals(true)
					.showGrandTotal(true)
					.groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
					.dataColumns(ScenarioFormatedValues.DATA_COLUMNS).build());
			reportBuilder.add(new EmptyLine());
		}

		reportBuilder.build().execute();
	}

	@Ignore
	public void testMemoryLeaksOutputPng() throws IOException {
		ReportBuilder reportBuilder = new ReportBuilder(new PngReportOutput(
				"./target/TestMemoryLeaks.png"));

		reportBuilder.add(new ReportTitle(
				"Testing the png output for memory leaks"));
		// add 1000 flat tables
		for (int i = 0; i < 1000; i++) {

			reportBuilder.add(new FlatTable.Builder()
					.input(ScenarioFormatedValues.INPUT).showTotals(true)
					.showGrandTotal(true)
					.groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
					.dataColumns(ScenarioFormatedValues.DATA_COLUMNS).build());
			reportBuilder.add(new EmptyLine());
		}

		reportBuilder.build().execute();
	}
}
