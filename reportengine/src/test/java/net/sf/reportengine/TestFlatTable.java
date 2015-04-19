package net.sf.reportengine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.calc.SumGroupCalculator;
import net.sf.reportengine.in.SqlTableInput;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.neo.DefaultReportOutput;
import net.sf.reportengine.out.neo.NewReportOutput;
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
		
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("./target/FlatTableWithoutGroupColumns.html")); 
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
		
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("./target/FlatTableScenario1.html")); 
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
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("./target/TestFlatReportOHLC.html")); 
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
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("./target/TestExecute2x3x1.html")); 
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
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("./target/TestFlatReportNoGroupings.html")); 
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
	public void testFlatReportUtf8PdfOutput() throws IOException {
		NewReportOutput reportOutput = new DefaultReportOutput(ReportIoUtils.createWriterFromPath("./target/testUtf8Output.html")); 
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
		//TODO: assert here
	}
	
	@Test
	public void testHugeReportHtmlOut() throws IOException {
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("./target/testHugeReport.html")); 
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
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("./target/testFormattedValues.html")); 
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
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("./target/FlatTableSortedProgramatically.html")); 
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
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("./target/testSortingOnDataCols.html")); 
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
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("./target/TestFlatTableWithSqlInput.html")); 
		reportOutput.open(); 
		
		SqlTableInput input = new SqlTableInput(); 
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:./src/test/resources/databases/testdb");
		input.setSqlStatement("select country, region, city, sex, religion, value from testreport t order by country, region, city");
		
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
}
