/**
 * 
 */
package net.sf.reportengine.util;

import java.io.InputStream;
import java.util.List;

import net.sf.reportengine.in.IntermediateCrosstabReportInput;
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
	private final IntermediateCrosstabReportInput intermCtInput; 
	
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
		intermCtInput = new IntermediateCrosstabReportInput(input);
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
