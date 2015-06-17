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
/**
 * 
 */
package net.sf.reportengine.out;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestExcelOutput {
	
	
	@Test
	public void testOutputEmptyConstructor() {
		ExcelOutput classUnderTest = new ExcelOutput();
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps()); 
		classUnderTest.startDataRow(new RowProps(0));
		classUnderTest.outputDataCell(new CellProps.Builder("value here").rowNumber(0).build());
		classUnderTest.endDataRow();
		classUnderTest.endReport(); 
		classUnderTest.close();
		
		OutputStream os = classUnderTest.getOutputStream();
		assertNotNull(os); 
		
		assertTrue(os instanceof ByteArrayOutputStream); 
		ByteArrayOutputStream baos = (ByteArrayOutputStream)os; 
		String result = baos.toString();
		assertNotNull(result); 
		assertTrue(result.indexOf("value here") > 0);
	}
	
	@Test
	public void testOutputOneCell() {
		ExcelOutput classUnderTest = new ExcelOutput("./target/OneCell.xls");
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps()); 
		classUnderTest.startDataRow(new RowProps(0));
		classUnderTest.outputDataCell(new CellProps.Builder("value here").rowNumber(0).build());
		classUnderTest.endDataRow();
		classUnderTest.endReport(); 
		classUnderTest.close();
	}
	
	
	@Test
	public void testOutputTitleAndOneCell() {
		ExcelOutput classUnderTest = new ExcelOutput("./target/TitleAndOneCell.xls");
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps());
		classUnderTest.outputTitle(new TitleProps("this is the title", 1)); 
		classUnderTest.startDataRow(new RowProps(0));
		classUnderTest.outputDataCell(new CellProps.Builder("value here").rowNumber(0).build());
		classUnderTest.endDataRow();
		classUnderTest.endReport(); 
		classUnderTest.close();
	}
	
	@Test
	public void testOutputTitleAndMultipleColHeadRows() {
		ExcelOutput classUnderTest = new ExcelOutput("./target/TitleAndMultipleColHeadRows.xls");
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps());
		classUnderTest.outputTitle(new TitleProps("this is the title", 1)); 
		
		classUnderTest.startHeaderRow(new RowProps(0));
		classUnderTest.outputHeaderCell(new CellProps.Builder("colum header row 1").rowNumber(0).build());
		classUnderTest.endHeaderRow();
		
		classUnderTest.startHeaderRow(new RowProps(1));
		classUnderTest.outputHeaderCell(new CellProps.Builder("colum header row 2").rowNumber(1).build());
		classUnderTest.endHeaderRow();
		
		classUnderTest.startHeaderRow(new RowProps(2));
		classUnderTest.outputHeaderCell(new CellProps.Builder("colum header row 3").rowNumber(1).build());
		classUnderTest.endHeaderRow();
		
		classUnderTest.startDataRow(new RowProps(0));
		classUnderTest.outputDataCell(new CellProps.Builder("value here").rowNumber(0).build());
		classUnderTest.endDataRow();
		
		classUnderTest.endReport();
		classUnderTest.close();
	}
	
	@Test
	public void testOutputManyRowsNoTitle() {
		ExcelOutput classUnderTest = new ExcelOutput("./target/ManyRowsWithColHeadAndNoTitle.xls");
		classUnderTest.open();
		
		classUnderTest.startReport(new ReportProps());
		classUnderTest.startHeaderRow(new RowProps(0));
		classUnderTest.outputHeaderCell(new CellProps.Builder("column header")
										.rowNumber(0)
										.build());
		classUnderTest.endHeaderRow();
		
		classUnderTest.startDataRow(new RowProps(0));
		classUnderTest.outputDataCell(new CellProps.Builder("1st row with data")
											.rowNumber(0)
											.build());
		classUnderTest.endDataRow();
		
		classUnderTest.startDataRow(new RowProps(1));
		classUnderTest.outputDataCell(new CellProps.Builder("2nd row with data")
											.rowNumber(1)
											.build());
		classUnderTest.endDataRow();		
		classUnderTest.endReport();
		classUnderTest.close();
	}
	
	
	@Test
	public void testOutputManyRowsWithTitle() {
		ExcelOutput classUnderTest = new ExcelOutput("./target/ManyRowsWithColHeadAndTitle.xls");
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps());
		
		classUnderTest.outputTitle(new TitleProps("this is a long title", 1)); 
		
		
		classUnderTest.startHeaderRow(new RowProps(0));
		classUnderTest.outputHeaderCell(new CellProps.Builder("column header")
										.rowNumber(0)
										.build());
		classUnderTest.endHeaderRow();
		
		classUnderTest.startDataRow(new RowProps(0));
		classUnderTest.outputDataCell(new CellProps.Builder("1st row with data")
											.rowNumber(0)
											.build());
		classUnderTest.endDataRow();
		
		classUnderTest.startDataRow(new RowProps(1));
		classUnderTest.outputDataCell(new CellProps.Builder("2nd row with data")
											.rowNumber(1)
											.build());
		classUnderTest.endDataRow();
		
		classUnderTest.endReport();
		classUnderTest.close();
	}
	
	@Test
	public void testOutputUtf8(){
		ExcelOutput classUnderTest = new ExcelOutput(ReportIoUtils.createOutputStreamFromPath("./target/Utf8.xls"));
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps());
		
		classUnderTest.outputTitle(new TitleProps("постига", 1)); 
		
		classUnderTest.startHeaderRow(new RowProps(0));
		classUnderTest.outputHeaderCell(new CellProps.Builder("А Б В Г Д Е Ё Ж З И Й К Л М Н О П Р С Т У Ф Х Ц Ч Ш Щ Ъ Ы Ь Э Ю Я").build());
		classUnderTest.endHeaderRow();
		
		classUnderTest.startDataRow(new RowProps(0)); 
		classUnderTest.outputDataCell(new CellProps.Builder("ά έ ή ί ό ύ ώ").build()); 
		classUnderTest.endDataRow(); 
		
		classUnderTest.endReport();
		classUnderTest.close();
	}
	
	
	@Test
	public void testTitleAndMultipleColHeadersAndMultipleRows() {
		ExcelOutput classUnderTest = new ExcelOutput("./target/TitleAndMultipleColHeadersAndMultipleRows.xls");
		
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps());
		classUnderTest.outputTitle(new TitleProps("title span over 3 columns", 3)); 
		
		classUnderTest.startHeaderRow(new RowProps(0));
		
		classUnderTest.outputHeaderCell(new CellProps.Builder("header 1")
														.rowNumber(0)
														.build());
		classUnderTest.outputHeaderCell(new CellProps.Builder("header 2")
														.rowNumber(0)
														.build());
		classUnderTest.outputHeaderCell(new CellProps.Builder("header 3")
														.rowNumber(0)
														.build());
		classUnderTest.endHeaderRow();
		
		classUnderTest.startHeaderRow(new RowProps(1));
		classUnderTest.outputHeaderCell(new CellProps.Builder("col header span 3")
														.rowNumber(1)
														.colspan(3)
														.build());
		classUnderTest.endHeaderRow();
		
		classUnderTest.startHeaderRow(new RowProps(2));
		classUnderTest.outputHeaderCell(new CellProps.Builder("col header span 2")
														.colspan(2)
														.rowNumber(2)
														.build());
		classUnderTest.outputHeaderCell(new CellProps.Builder("span 1")
														.colspan(1)
														.rowNumber(2)
														.build());
		classUnderTest.endHeaderRow();
		
		classUnderTest.startDataRow(new RowProps(0));
		classUnderTest.outputDataCell(new CellProps.Builder("value 1").rowNumber(0).build());
		classUnderTest.outputDataCell(new CellProps.Builder("value 2").rowNumber(0).build());
		classUnderTest.outputDataCell(new CellProps.Builder("value 3").rowNumber(0).build());
		classUnderTest.endDataRow();
		
		classUnderTest.startDataRow(new RowProps(1));
		classUnderTest.outputDataCell(new CellProps.Builder("value 4").rowNumber(1).build());
		classUnderTest.outputDataCell(new CellProps.Builder("value 5").rowNumber(1).build());
		classUnderTest.outputDataCell(new CellProps.Builder("value 6").rowNumber(1).build());
		classUnderTest.endDataRow();
		
		classUnderTest.startDataRow(new RowProps(2));
		classUnderTest.outputDataCell(new CellProps.Builder("value 7").rowNumber(2).colspan(3).build());
		classUnderTest.endDataRow();
		
		classUnderTest.startDataRow(new RowProps(3));
		classUnderTest.outputDataCell(new CellProps.Builder("value 8 span 2").rowNumber(3).colspan(2).build());
		classUnderTest.outputDataCell(new CellProps.Builder("span 1").rowNumber(3).build());
		classUnderTest.endDataRow();
		
		classUnderTest.endReport();
		classUnderTest.close();
	}

}
