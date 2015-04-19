/**
 * 
 */
package net.sf.reportengine;

import java.io.FileWriter;
import java.io.IOException;

import junit.framework.TestCase;
import net.sf.reportengine.components.PivotTable;
import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.calc.SumGroupCalculator;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.ExcelOutput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.out.PdfOutput;
import net.sf.reportengine.out.StaxReportOutput;
import net.sf.reportengine.out.neo.DefaultReportOutput;
import net.sf.reportengine.out.neo.NewReportOutput;
import net.sf.reportengine.scenarios.ct.CtScenario1x1x1;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With0G2D;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.scenarios.ct.CtScenarioFormatting4x3x1;
import net.sf.reportengine.scenarios.ct.CtUnsortedScenario2x2x1With0G2D;
import net.sf.reportengine.scenarios.ct.CtUnsortedScenario2x2x1With1G1D;
import net.sf.reportengine.scenarios.ct.CtUnsortedScenario2x2x1With2G0D;
import net.sf.reportengine.util.ReportIoUtils;

/**
 * @author dragos balan
 *
 */
public class TestPivotTable extends TestCase {
	
	
	public void testExecute1x1x1xT() throws IOException{
		
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("target/CrosstabReport1x1x1xT.html")); 
		reportOutput.open();
		
		PivotTable classUnderTest = new PivotTable.Builder()
											//.title("Crosstab report 1 group, 1x1x1xT")
											.input(CtScenario1x1x1.INPUT)
											//.output(new HtmlOutput("target/CrosstabReport1x1x1xT.html"))
											.groupColumns(CtScenario1x1x1.GROUP_COLUMNS)
											.dataColumns(CtScenario1x1x1.DATA_COLUMNS)
											.crosstabData(CtScenario1x1x1.CROSSTAB_DATA_WITH_TOTALS)
											.headerRows(CtScenario1x1x1.ROW_HEADERS)
											.showTotals(true)
											.showGrandTotal(true)
											.build(); 
		classUnderTest.output(reportOutput); 
		reportOutput.close(); 
		
	}
	
	public void testExecute1x1x1() throws IOException{
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("target/CrosstabReport1x1x1.html")); 
		reportOutput.open();
		
		
		new PivotTable.Builder() 
			//.title("Crosstab report 1x1x1 no totals")
			.input(CtScenario1x1x1.INPUT) 
			//.output(new HtmlOutput("target/CrosstabReport1x1x1.html"))
			.groupColumns(CtScenario1x1x1.GROUP_COLUMNS)
			.dataColumns(CtScenario1x1x1.DATA_COLUMNS)
			.crosstabData(CtScenario1x1x1.CROSSTAB_DATA_NO_TOTALS)
			.headerRows(CtScenario1x1x1.ROW_HEADERS)
			.showTotals(false) 
			.showGrandTotal(false)
			.build()
		.output(reportOutput);
		
		reportOutput.close(); 
		
	}
	
	public void testExecute2x2x1xT() throws IOException{
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("target/CrosstabReport2x2x1xT.html")); 
		reportOutput.open();
		
		new PivotTable.Builder() 
			//.title("crosstab report 2x2x1xT")
			.input(CtScenario2x2x1With1G1D.INPUT)
			//.output(new HtmlOutput("target/CrosstabReport2x2x1xT.html"))
			.groupColumns(CtScenario2x2x1With1G1D.GROUPING_COLUMNS)
			.dataColumns(CtScenario2x2x1With1G1D.DATA_COLUMNS) 
			.crosstabData(CtScenario2x2x1With1G1D.CROSSTAB_DATA)
			.headerRows(CtScenario2x2x1With1G1D.HEADER_ROWS)
			.showTotals()
			.showGrandTotal()
			.build()
		.output(reportOutput); 
		
		reportOutput.close(); 
	}
	
	public void testExecute2x2x1() throws IOException{
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("./target/CrosstabReport2x2x1.html")); 
		reportOutput.open();
		
		new PivotTable.Builder() 
		//classUnderTest.setTitle("crosstab 2x2x1");		
		.input(CtScenario2x2x1With1G1D.INPUT)
		
//		OutputDispatcher outDispatcher = new OutputDispatcher(); 
//		outDispatcher.registerOutput(new HtmlOutput("./target/CrosstabReport2x2x1.html"));
//		outDispatcher.registerOutput(new StaxReportOutput("./target/CrosstabReport2x2x1.xml"));
//		outDispatcher.registerOutput(new PdfOutput("./target/CrosstabReport2x2x1.pdf"));
//		outDispatcher.registerOutput(new ExcelOutput("./target/CrossrabReport2x2x1.xls"));
//		classUnderTest.setOut(outDispatcher);
		
		.groupColumns(CtScenario2x2x1With1G1D.GROUPING_COLUMNS)
		.dataColumns(CtScenario2x2x1With1G1D.DATA_COLUMNS)
		.crosstabData(CtScenario2x2x1With1G1D.CROSSTAB_DATA) 
		.headerRows(CtScenario2x2x1With1G1D.HEADER_ROWS) 
		
		.showTotals(false)
		.showGrandTotal(false)
		.build()		
		.output(reportOutput); 
		
		reportOutput.close(); 
	}
	
	
	public void testProgramaticSorting2x2x1() throws IOException{
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("./target/CtProgramaticallySorted2x2x1.html")); 
		reportOutput.open();
		
		
		new PivotTable.Builder() 
		//classUnderTest.setTitle("This report has group columns sorted programatically");
		.input(CtUnsortedScenario2x2x1With1G1D.INPUT)
		//classUnderTest.setOut(new HtmlOutput("./target/CtProgramaticallySorted2x2x1.html"));
		.sortValues()
		
		.groupColumns(CtUnsortedScenario2x2x1With1G1D.GROUPING_COLUMNS)
		.dataColumns(CtUnsortedScenario2x2x1With1G1D.DATA_COLUMNS)
		.crosstabData(CtUnsortedScenario2x2x1With1G1D.CROSSTAB_DATA)
		.headerRows(CtUnsortedScenario2x2x1With1G1D.HEADER_ROWS)
		
		.showTotals(false)
		.showGrandTotal(false)
		.build()
		.output(reportOutput); 
		
		reportOutput.close(); 
	}
	
	public void testExecute1x3x1xT() throws IOException{
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("target/CrosstabReport1x3x1xT.html")); 
		reportOutput.open();
		
		
		new PivotTable.Builder() 
		.input(CtScenario1x3x1.INPUT)
		//classUnderTest.setOut(new HtmlOutput("target/CrosstabReport1x3x1xT.html"));
		.groupColumns(CtScenario1x3x1.GROUP_COLUMNS)
		.dataColumns(CtScenario1x3x1.DATA_COLUMNS)
		.crosstabData(CtScenario1x3x1.CROSSTAB_DATA) 
		.headerRows(CtScenario1x3x1.HEADER_ROWS)
		.showTotals(true)
		.showGrandTotal(true)
		.build()
		.output(reportOutput); 
		
		reportOutput.close(); 
	}
	
	public void testExecute1x3x1() throws IOException{
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("target/CrosstabReport1x3x1.html")); 
		reportOutput.open();
		
		
		new PivotTable.Builder() 
		.input(CtScenario1x3x1.INPUT)
		//classUnderTest.setOut(new HtmlOutput("target/CrosstabReport1x3x1.html"));
		.groupColumns(CtScenario1x3x1.GROUP_COLUMNS)
		.dataColumns(CtScenario1x3x1.DATA_COLUMNS)
		.crosstabData(CtScenario1x3x1.CROSSTAB_DATA)
		.headerRows(CtScenario1x3x1.HEADER_ROWS)
		.showTotals(false)
		.showGrandTotal(false)
		.build()
		.output(reportOutput); 
		
		reportOutput.close(); 
	}
	
	public void testExecute3x2x1xT() throws IOException{
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("target/CrosstabReport3x2x1xT.html")); 
		reportOutput.open();
		
		new PivotTable.Builder() 
			//.title("Pivot table 3x2x1xT. Please observe the strange behaviour on Count Rows. <br/>It's due to using two different group calculators (one for crosstabData and a different one for dataColumn") 
			.input(new TextTableInput(ReportIoUtils.createInputStreamFromClassPath("3x2x1.txt"), ","))
			//.output(new HtmlOutput("target/CrosstabReport3x2x1xT.html"))
			.addGroupColumn(new DefaultGroupColumn.Builder(0).header("Country").build())
			.addGroupColumn(new DefaultGroupColumn.Builder(1).header("Region").build())
		
			.addDataColumn(new DefaultDataColumn("City", 2, GroupCalculators.COUNT))
		
			.addHeaderRow(new DefaultCrosstabHeaderRow(3))
			.addHeaderRow(new DefaultCrosstabHeaderRow(4)) 
		
			.crosstabData(new DefaultCrosstabData(5, GroupCalculators.SUM)) 
		
			.showTotals(true) 
			.showGrandTotal(true)
			.build()
		.output(reportOutput); 
		
		reportOutput.close(); 
	}
	
	//TODO
	public void testExecute3x2x1xTHavingNoGroupColumns() throws IOException{
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("target/CrosstabReport3x2x1xTxNoGroupColumns.html")); 
		reportOutput.open();
		
		
		new PivotTable.Builder()
		.input(new TextTableInput(ReportIoUtils.createInputStreamFromClassPath("3x2x1.txt"), ",")) 
		//classUnderTest.setOut(new HtmlOutput("target/CrosstabReport3x2x1xTxNoGroupColumns.html"));
		
		.addDataColumn(new DefaultDataColumn("Country", 0)) 
		.addDataColumn(new DefaultDataColumn("Region", 1))
		.addDataColumn(new DefaultDataColumn("City", 2))
		
		.addHeaderRow(new DefaultCrosstabHeaderRow(3))
		.addHeaderRow(new DefaultCrosstabHeaderRow(4))
		
		.crosstabData(new DefaultCrosstabData(5, new SumGroupCalculator()))
		
		.showTotals(false)
		.showGrandTotal(false)
		.build()
		.output(reportOutput); 
		
		reportOutput.close(); 
	}
	
	public void testExecute2x2x1xTHavingNoGroupColumns() throws IOException{
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("target/CrosstabReport2x2x1With0G2D.html")); 
		reportOutput.open();
		
		new PivotTable.Builder() 
		.input(CtScenario2x2x1With0G2D.INPUT)
		//classUnderTest.setOut(new HtmlOutput("target/CrosstabReport2x2x1With0G2D.html"));
		
		.dataColumns(CtScenario2x2x1With0G2D.DATA_COLUMNS)
		.headerRows(CtScenario2x2x1With0G2D.HEADER_ROWS)
		.crosstabData(CtScenario2x2x1With0G2D.CROSSTAB_DATA)
		
		.showTotals(false)
		.showGrandTotal(false)
		.build()
		.output(reportOutput); 
		
		reportOutput.close(); 
	}
	
	
	public void testProgramaticSortingFor2x2x1xTHavingNoGroups() throws IOException{
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("target/CrosstabReportProgramaticallySorted2x2x1With0G2D.html")); 
		reportOutput.open();
		
		new PivotTable.Builder() 
			//.title("This report has 2 data columns programatically sorted.")
			.input(CtUnsortedScenario2x2x1With0G2D.INPUT)
			//.output(new HtmlOutput("target/CrosstabReportProgramaticallySorted2x2x1With0G2D.html"))
			.dataColumns(CtUnsortedScenario2x2x1With0G2D.DATA_COLUMNS)
			.headerRows(CtUnsortedScenario2x2x1With0G2D.HEADER_ROWS)
			.crosstabData(CtUnsortedScenario2x2x1With0G2D.CROSSTAB_DATA)
			.showTotals(false)
			.showGrandTotal(false)
			.build()
		.output(reportOutput); 
		
		reportOutput.close(); 
	}
	
	
	public void testProgramaticSortingFor2x2x1xTHavingNoDataCols() throws IOException{
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("target/CrosstabReportProgramaticallySorted2x2x1With2G0D.html")); 
		reportOutput.open();
		
		new PivotTable.Builder() 
			//.title("This report has 2 group columns programatically sorted.")
			.input(CtUnsortedScenario2x2x1With2G0D.INPUT)
			//.output(new HtmlOutput("target/CrosstabReportProgramaticallySorted2x2x1With2G0D.html"))
			.groupColumns(CtUnsortedScenario2x2x1With2G0D.GROUPING_COLUMNS)
			//.dataColumns(CtUnsortedScenario2x2x1With2G0D.DATA_COLUMNS) /*dataCols is null*/
			.headerRows(CtUnsortedScenario2x2x1With2G0D.HEADER_ROWS)
			.crosstabData(CtUnsortedScenario2x2x1With2G0D.CROSSTAB_DATA)
			//.showTotals(true)
			//.showGrandTotal(true)
			.sortValues()
			.build()
		.output(reportOutput); 
		
		reportOutput.close(); 
	}
	
	
	public void testFormatting() throws IOException{
		NewReportOutput reportOutput = new DefaultReportOutput(new FileWriter("./target/testCrosstabFormatting.html")); 
		reportOutput.open();
		
		new PivotTable.Builder()
			//.title("Test Formatting")
			.input(CtScenarioFormatting4x3x1.INPUT)
			//.output(new HtmlOutput("./target/testCrosstabFormatting.html"))
			.dataColumns(CtScenarioFormatting4x3x1.DATA_COLUMNS)
			.groupColumns(CtScenarioFormatting4x3x1.GROUP_COLUMNS)
			.headerRows(CtScenarioFormatting4x3x1.HEADER_ROWS)
			.crosstabData(CtScenarioFormatting4x3x1.CROSSTAB_DATA)
			.showTotals()
			.showGrandTotal()
			.build()
		.output(reportOutput); 
		
		reportOutput.close(); 
	}
	
//	public void testWrongConfiguration1(){
//		try{
//			CrossTabReport classUnderTest = new CrossTabReport(); 
//			//don't set any input nor output
//			classUnderTest.execute();
//			fail("this test should have thrown an exception in the method above");
//		}catch(ConfigValidationException configExc){
//			assertEquals(configExc.getMessage(), "The report has no input");
//		}catch(Throwable other){
//			fail("Expected config validation exception but found "+other.getClass());
//		}
//	}
//	
//	public void testWrongConfiguration2(){
//		try{
//			CrossTabReport classUnderTest = new CrossTabReport();
//			classUnderTest.setIn(CtScenario1x3x1.INPUT);
//			//don't set any output
//			
//			classUnderTest.execute();	
//			fail("this test should have thrown an exception in the method above");
//		}catch(ConfigValidationException configExc){
//			assertEquals(configExc.getMessage(), "The report has no output");
//		}catch(Throwable other){
//			fail("Expected config validation exception but found "+other.getClass());
//		}
//	}
}
