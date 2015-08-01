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

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.sql.SQLException;

import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.calc.SumGroupCalculator;
import net.sf.reportengine.in.SqlTableInput;
import net.sf.reportengine.in.TableInputException;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.AbstractReportOutput;
import net.sf.reportengine.out.HtmlReportOutput;
import net.sf.reportengine.scenarios.NoGroupsScenario;
import net.sf.reportengine.scenarios.OhlcComputationScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2x3x1;
import net.sf.reportengine.scenarios.ScenarioFormatedValues;
import net.sf.reportengine.scenarios.ScenarioSort;
import net.sf.reportengine.scenarios.SortScenarioOnlyDataColsCount;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Assert;
import org.junit.Test;

public class TestFlatTable {
	
	@Test
	public void testExecuteReportWithoutGroupColumns() throws IOException{
		//this report has no grouping columns but it has a grand total at the end
		//because the data columns have group calculators added
		
		AbstractReportOutput reportOutput = new HtmlReportOutput(new FileWriter("./target/FlatTableWithoutGroupColumns.html")); 
		reportOutput.open(); 
		FlatTable flatTable = new FlatTable.Builder()
					.input(Scenario1.INPUT)
					.dataColumns(Scenario1.DATA_COLUMNS)
				.build();
		
		//test the computed values
		Assert.assertTrue(flatTable.getShowDataRows());
		Assert.assertTrue(flatTable.getShowTotals());
		Assert.assertTrue(flatTable.getShowGrandTotal());
		
		//execute the report
		flatTable.output(reportOutput);
		reportOutput.close(); 
		
		//TODO: test the output here
	}
	
	
	@Test
	public void testExecuteScenario1() throws IOException{
		//test of a simple report with 3 grouping columns and 3 data columns 
		//the grouping columns don't require any sorting
		
		AbstractReportOutput reportOutput = new HtmlReportOutput(new FileWriter("./target/FlatTableScenario1.html")); 
		reportOutput.open();
		FlatTable flatTable = new FlatTable.Builder()
									.input(Scenario1.INPUT)
									.dataColumns(Scenario1.DATA_COLUMNS)
									.groupColumns(Scenario1.GROUPING_COLUMNS)
									.build();
		
		//check the default values
		Assert.assertTrue(flatTable.getShowTotals());
		Assert.assertTrue(flatTable.getShowGrandTotal());
		Assert.assertTrue(flatTable.getShowDataRows());
		
		//execute the report
		flatTable.output(reportOutput);
		reportOutput.close(); 
		
		//@TODO: test the output
//		Assert.assertTrue(MatrixUtils.compareMatrices(
//										testOut.getDataCellMatrix(), 
//										Scenario1.EXPECTED_OUTPUT_UNSORTED));
		
	}
	
	
	@Test
	public void testExecuteScenarioOhlc() throws IOException{
		AbstractReportOutput reportOutput = new HtmlReportOutput(new FileWriter("./target/TestFlatReportOHLC.html")); 
		reportOutput.open(); 
		
		InputStream testStream = ReportIoUtils.createInputStreamFromClassPath("EURUSD_2007-2009_FirstHours.txt");
		Assert.assertNotNull(testStream);
		
		new FlatTable.Builder()
			.input(new TextTableInput(testStream,"\t"))
			.dataColumns(OhlcComputationScenario.DATA_COLUMNS)
			.groupColumns(OhlcComputationScenario.GROUPING_COLUMNS)
			.showTotals(true)
			.showDataRows(false)
			.build()
		.output(reportOutput); 
		
		reportOutput.close();
		//TODO: test the final output here
	}
	
	@Test
	public void testExecute2x3x1() throws IOException {
		AbstractReportOutput reportOutput = new HtmlReportOutput(new FileWriter("./target/TestExecute2x3x1.html")); 
		reportOutput.open(); 
		
		InputStream inputStream = ReportIoUtils.createInputStreamFromClassPath("2x3x1.txt");
		Assert.assertNotNull(inputStream); 
		
		new FlatTable.Builder()	
    		.input(new TextTableInput(inputStream))
    		.groupColumns(Scenario2x3x1.GROUP_COLUMNS)
    		.dataColumns(Scenario2x3x1.DATA_COLUMNS)
    		.showTotals(true)
    		.build()
    	.output(reportOutput);
        
        reportOutput.close(); 
        
        //TODO: assert here
    }
	
	@Test
	public void testExecuteNoGroupingColumnsReport() throws IOException {
		AbstractReportOutput reportOutput = new HtmlReportOutput(new FileWriter("./target/TestFlatReportNoGroupings.html")); 
		reportOutput.open(); 
		
		new FlatTable.Builder()	
    		.groupColumns(NoGroupsScenario.GROUPING_COLUMNS)
    		.dataColumns(NoGroupsScenario.DATA_COLUMNS)
    		.input(NoGroupsScenario.INPUT)
    		.showTotals(true)
    		.showGrandTotal(true)
    		.build()
    	.output(reportOutput);
        
        reportOutput.close(); 
        //TODO: assert here
	}
	
	@Test
	public void testFlatTableUtf8PdfOutput() throws IOException {
		Writer testWriter = ReportIoUtils.createWriterFromPath("./target/testUtf8Output.html"); 
		testWriter.write("<head><meta charset=\"UTF-8\"></head><body>"); 
		
		AbstractReportOutput reportOutput = new HtmlReportOutput(testWriter, false); 
		reportOutput.open(); 
		
		InputStream inputStream = ReportIoUtils.createInputStreamFromClassPath("Utf8Input.txt");
		Assert.assertNotNull(inputStream);
		
		new FlatTable.Builder()	
			.input(new TextTableInput(inputStream, ",", "UTF-8")) 
		
			.addDataColumn(new DefaultDataColumn(0))
			.addDataColumn(new DefaultDataColumn(1))
			.addDataColumn(new DefaultDataColumn(2))
			.addDataColumn(new DefaultDataColumn(3))
			.build()
		.output(reportOutput);
		
		reportOutput.close();
		
		testWriter.write("</body>");
		//TODO: assert here
	}
	
	@Test
	public void testHugeInputHtmlOut() throws IOException {
		AbstractReportOutput reportOutput = new HtmlReportOutput(new FileWriter("./target/testHugeFlatTable.html")); 
		reportOutput.open(); 
		
		new FlatTable.Builder()
			.input(OhlcComputationScenario.INPUT)
			.groupColumns(OhlcComputationScenario.GROUPING_COLUMNS)
			.dataColumns(OhlcComputationScenario.DATA_COLUMNS)
			.build() 
		.output(reportOutput); 
		
		reportOutput.close(); 
		//TODO: assert here
	}
	
	@Test
	public void testFlatReportWithFormattedValues() throws IOException {
		AbstractReportOutput reportOutput = new HtmlReportOutput(new FileWriter("./target/testFormattedValues.html")); 
		reportOutput.open(); 
		
		
		new FlatTable.Builder()	
			.input(ScenarioFormatedValues.INPUT)
			.showTotals(true)
			.showGrandTotal(true)
			.groupColumns(ScenarioFormatedValues.GROUP_COLUMNS)
			.dataColumns(ScenarioFormatedValues.DATA_COLUMNS)
			.build()
			.output(reportOutput); 
		
		reportOutput.close(); 
		//TODO: assert here
	}
	
	
	@Test
	public void testExecuteWithSorting() throws IOException {
		AbstractReportOutput reportOutput = new HtmlReportOutput(new FileWriter("./target/FlatTableSortedProgramatically.html")); 
		reportOutput.open(); 
		
		new FlatTable.Builder()
			.sortValues()
			.input(ScenarioSort.INPUT)
			.dataColumns(ScenarioSort.DATA_COLUMNS)
			.groupColumns(ScenarioSort.GROUPING_COLUMNS)
			.showTotals(false)
			.showDataRows(true)
			.showGrandTotal(true)
			.build()
			.output(reportOutput);
		
//		Assert.assertTrue(MatrixUtils.compareMatrices(	mockOut.getDataCellMatrix(), 
//														ScenarioSort.EXPECTED_OUTPUT_SORTED));
		
		reportOutput.close(); 
	}
	
	@Test
	public void testExecuteWithSortingOnDataCols() throws IOException {
		AbstractReportOutput reportOutput = new HtmlReportOutput(new FileWriter("./target/testSortingOnDataCols.html")); 
		reportOutput.open(); 
		
		FlatTable flatReport = new FlatTable.Builder()
									.showTotals(false)
									.input(SortScenarioOnlyDataColsCount.INPUT)
									.dataColumns(SortScenarioOnlyDataColsCount.DATA_COLUMNS)
									.groupColumns(SortScenarioOnlyDataColsCount.GROUPING_COLUMNS)
									.build();
		
		//check some of the computed values + default ones
		Assert.assertTrue(flatReport.getShowDataRows());
		Assert.assertFalse(flatReport.getShowTotals()); 
		Assert.assertTrue(flatReport.getShowGrandTotal());
		Assert.assertTrue(flatReport.hasValuesSorted());
				
		flatReport.output(reportOutput);
		
		reportOutput.close(); 
	}
	
	@Test
	public void testSqlInput() throws IOException {
		AbstractReportOutput reportOutput = new HtmlReportOutput(new FileWriter("./target/TestFlatTableWithSqlInput.html")); 
		reportOutput.open(); 
		
		SqlTableInput input = new SqlTableInput(
				"jdbc:hsqldb:file:./src/test/resources/databases/testdb",
				"org.hsqldb.jdbcDriver",
				"SA",
				"",
				"select country, region, city, sex, religion, value from testreport t order by country, region, city");
		
		new FlatTable.Builder()
			.input(input)
			.addGroupColumn(new DefaultGroupColumn.Builder(0).header("COUNTRY").horizAlign(HorizAlign.RIGHT).vertAlign(VertAlign.TOP).build())
			.addGroupColumn(new DefaultGroupColumn.Builder(1).header("REGION").build())
			.addGroupColumn(new DefaultGroupColumn.Builder(2).header("CITY").build())
			.addDataColumn(new DefaultDataColumn.Builder(5).header("Value").useCalculator(new SumGroupCalculator(), "%f $").valuesFormatter("%d $").build())
			.build()
		.output(reportOutput);
		
		reportOutput.close();
		
	}
	
	@Test
	public void testSqlException() throws Exception {
		AbstractReportOutput reportOutput = new HtmlReportOutput(new FileWriter("./target/TestSqlException.html")); 
		reportOutput.open(); 
		
		SqlTableInput input = new SqlTableInput(
				"jdbc:hsqldb:file:./src/test/resources/databases/testdb",
				"org.hsqldb.jdbcDriver",
				"SA",
				"",
				"select WRONG_COLUMN, region, city, sex, religion, value from testreport t order by country, region, city");
		try{
		new FlatTable.Builder()
			.input(input)
			.addGroupColumn(new DefaultGroupColumn.Builder(0).header("COUNTRY").horizAlign(HorizAlign.RIGHT).vertAlign(VertAlign.TOP).build())
			.addGroupColumn(new DefaultGroupColumn.Builder(1).header("REGION").build())
			.addGroupColumn(new DefaultGroupColumn.Builder(2).header("CITY").build())
			.addDataColumn(new DefaultDataColumn.Builder(5).header("Value").useCalculator(new SumGroupCalculator(), "%f $").valuesFormatter("%d $").build())
			.build()
		.output(reportOutput);
		}catch(TableInputException tie){
			Assert.assertTrue(tie.getCause() instanceof SQLException);
		}finally{
			reportOutput.close();
		}
		Assert.assertTrue(input.hasAllResourcesClosed());
	}
}
