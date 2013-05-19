/**
 * 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.core.steps.crosstab.IntermediateDataInfo;
import net.sf.reportengine.core.steps.crosstab.IntermediateReportRow;
import net.sf.reportengine.core.steps.crosstab.IntermediateTotalInfo;

import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestIntermediateCrosstabOutput {
	
	
	@Test
	public void testOutput() {
		IntermediateReportRow mockRow = new IntermediateReportRow(); 
		mockRow.addIntermComputedData(new IntermediateDataInfo("test", 0,0,0)); 
		mockRow.addIntermTotalsInfo(new IntermediateTotalInfo(100, new int[]{0,0,0}, new String[]{"dist1", "dist2", "dist3"}));
		
		IntermediateCrosstabOutput classUnderTest = new IntermediateCrosstabOutput(); 
		classUnderTest.open(); 
		classUnderTest.startReport(new ReportProps()); 
		classUnderTest.startDataRow(new RowProps(0)); 
		classUnderTest.outputDataCell(new CellProps.Builder(mockRow).build()); 
		classUnderTest.endDataRow(); 
		
		classUnderTest.endReport(); 
		classUnderTest.close(); 
	}
}
