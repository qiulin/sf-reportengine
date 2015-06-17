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
package net.sf.reportengine.util;

import java.io.InputStream;
import java.util.List;

import net.sf.reportengine.in.IntermediateCrosstabReportTableInput;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.Html5Output;
import net.sf.reportengine.out.ReportProps;
import net.sf.reportengine.out.RowProps;

/**
 * @author dragos balan
 *
 */
public class IntermedCrosstabViewer {

	/**
	 * we take advantage of the existing input to get access 
	 * to each line of the intermediate report
	 */
	private final IntermediateCrosstabReportTableInput intermCtInput; 
	
	/**
	 * we take advantage of the extisting outputs
	 */
	private final Html5Output output ; 
	
	/**
	 * 
	 * @param inputFilePath
	 * @param outputFilePath
	 */
	public IntermedCrosstabViewer(InputStream input, String outputFilePath){
		intermCtInput = new IntermediateCrosstabReportTableInput(input);
		output = new Html5Output(outputFilePath); 
	}
	
	/**
	 * 
	 */
	public void exportToHtml(){
		intermCtInput.open(); 
		output.open();
		output.startReport(new ReportProps()); 
		int rowNbr = 0;
		while(intermCtInput.hasMoreRows()){
			// we get a list with 4 objects
			List<Object> intermLine = intermCtInput.nextRow(); 
			output.startDataRow(new RowProps(rowNbr));
			
			// result[0] is an instance of IntermGroupValuesList
			// result[1] is an instance of OriginalDataValueList
			// result[2] is an instance of IntermComputedDataList
			// result[3] an instance of IntermComputedTotalsList
			output.outputDataCell(new CellProps.Builder(intermLine.get(0).toString()).build()); 
			output.outputDataCell(new CellProps.Builder(intermLine.get(1).toString()).build()); 
			output.outputDataCell(new CellProps.Builder(intermLine.get(2).toString()).build());
			output.outputDataCell(new CellProps.Builder(intermLine.get(3).toString()).build());
			
			output.endDataRow(); 
			rowNbr++;
		}
		output.endReport(); 
		output.close(); 
		intermCtInput.close(); 
	}
	
}
