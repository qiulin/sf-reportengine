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

import java.io.Writer;

import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestStaxOutput{
	
	@Test
	public void testStaxOutput() throws Exception{
        StaxReportOutput xmlOutTest = new StaxReportOutput("target/staxWithTitleAndManyRows.xml");
        xmlOutTest.open();
        xmlOutTest.startReport(new ReportProps());
        xmlOutTest.outputTitle(new TitleProps("this is the title", 1));
        
        xmlOutTest.startHeaderRow(new RowProps(0));
        xmlOutTest.outputHeaderCell(new CellProps.Builder("column 1").build());
        xmlOutTest.endHeaderRow(); 
        
        xmlOutTest.startDataRow(new RowProps(0));
        xmlOutTest.outputDataCell(new CellProps.Builder("value row 1 col 1").rowNumber(0).build());
        xmlOutTest.endDataRow();
        
        xmlOutTest.startDataRow(new RowProps(1));
        xmlOutTest.outputDataCell(new CellProps.Builder("value row 2 col 1").rowNumber(1).build());
        xmlOutTest.endDataRow();
        
        xmlOutTest.startDataRow(new RowProps(2));
        xmlOutTest.outputDataCell(new CellProps.Builder("value row 3 col 1").rowNumber(2).build());
        xmlOutTest.endDataRow();
        
        xmlOutTest.endReport(); 
        xmlOutTest.close();            
    }
	
	@Test
	public void testStaxUTF8Output() throws Exception{
    	Writer writer = ReportIoUtils.createWriterFromPath("target/staxUtf8.xml");
        StaxReportOutput xmlOutTest = new StaxReportOutput(writer);
        
        xmlOutTest.open();
        xmlOutTest.startReport(new ReportProps()); 
        
        xmlOutTest.startDataRow(new RowProps(0));
        xmlOutTest.outputDataCell(new CellProps.Builder("Πρωτόκολλο Αζήτητων").rowNumber(0).build());
        xmlOutTest.endDataRow();
        
        xmlOutTest.startDataRow(new RowProps(1));
        xmlOutTest.outputDataCell(new CellProps.Builder("Βιβλία").rowNumber(1).build());
        xmlOutTest.endDataRow();
        
        xmlOutTest.startDataRow(new RowProps(2));
        xmlOutTest.outputDataCell(new CellProps.Builder("и канализация са от").rowNumber(2).build());
        xmlOutTest.endDataRow();
        
        xmlOutTest.endReport(); 
        xmlOutTest.close();  
    }
}
