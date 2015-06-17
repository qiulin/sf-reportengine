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

import java.io.FileWriter;

import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.VertAlign;

import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestFoOutput {


	/**
	 * Test method for {@link net.sf.reportengine.out.FoOutput#outputDataCell(net.sf.reportengine.out.CellProps)}.
	 */
	@Test
	public void testOutput() throws Exception{
		FoOutput classUnderTest;
		classUnderTest = new FoOutput(new FileWriter("./target/foOutput.fo"));
	
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps()); 
		
		classUnderTest.outputTitle(new TitleProps("report title", 3));
		
		classUnderTest.startHeaderRow(new RowProps(0)); 
		classUnderTest.outputHeaderCell(new CellProps.Builder("header1").rowNumber(0).build()); 
		classUnderTest.outputHeaderCell(new CellProps.Builder("header2").rowNumber(0).build());
		classUnderTest.outputHeaderCell(new CellProps.Builder("header3").rowNumber(0).build());
		classUnderTest.endHeaderRow(); 
		
		classUnderTest.startDataRow(new RowProps(0)); 
		classUnderTest.outputDataCell(new CellProps.Builder("first cell").rowNumber(0).build()); 
		classUnderTest.outputDataCell(new CellProps.Builder("second cell").rowNumber(0).build());
		classUnderTest.outputDataCell(new CellProps.Builder("third cell").rowNumber(0).build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.startDataRow(new RowProps(1)); 
		classUnderTest.outputDataCell(new CellProps.Builder("this cell has a colspan of 3").rowNumber(1).colspan(3).build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.startDataRow(new RowProps(2)); 
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 1 aligned to right").rowNumber(2).horizAlign(HorizAlign.RIGHT).build()); 
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 2 vertically aligned to top").rowNumber(2).vertAlign(VertAlign.TOP).build());
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 3").rowNumber(2).build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.endReport(); 
		classUnderTest.close(); 
	}

}
