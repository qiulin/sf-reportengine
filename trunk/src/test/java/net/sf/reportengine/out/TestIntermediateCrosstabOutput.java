/**
 * 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.steps.crosstab.IntermediateDataInfo;
import net.sf.reportengine.core.steps.crosstab.IntermediateReportRow;
import net.sf.reportengine.core.steps.crosstab.IntermediateTotalInfo;
import junit.framework.TestCase;

/**
 * @author dragos
 *
 */
public class TestIntermediateCrosstabOutput extends TestCase {
	
	
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link net.sf.reportengine.out.IntermediateCrosstabOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testOutput() {
		IntermediateReportRow mockRow = new IntermediateReportRow(); 
		mockRow.addIntermComputedData(new IntermediateDataInfo("test", 0,0,0)); 
		mockRow.addIntermTotalsInfo(new IntermediateTotalInfo(100, new int[]{0,0,0}, new String[]{"dist1", "dist2", "dist3"}));
		
		IntermediateCrosstabOutput classUnderTest = new IntermediateCrosstabOutput(); 
		classUnderTest.open(); 
		classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA)); 
		classUnderTest.output(new CellProps.Builder(mockRow).build()); 
		classUnderTest.endRow(); 
		classUnderTest.close(); 
	}
}
