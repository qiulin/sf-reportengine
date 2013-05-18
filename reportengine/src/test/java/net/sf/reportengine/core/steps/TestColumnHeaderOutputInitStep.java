/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.DefaultReportContext;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.InputKeys;
import net.sf.reportengine.util.MatrixUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestColumnHeaderOutputInitStep  {
	
	/**
	 * tested
	 */
	@Test
	public void testInitScenario1() {
		ReportContext testReportContext = new DefaultReportContext(); 
		Map<InputKeys, Object> mockAlgoInput = new EnumMap<InputKeys, Object>(InputKeys.class);  
		CellPropsArrayOutput mockOutput = new CellPropsArrayOutput(); 
		
		mockAlgoInput.put(InputKeys.REPORT_OUTPUT, mockOutput); 
		mockAlgoInput.put(InputKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockAlgoInput.put(InputKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS) ;
		
		ColumnHeaderOutputInitStep classUnderTest = new ColumnHeaderOutputInitStep(); 
		classUnderTest.init(mockAlgoInput, testReportContext);
		
		Assert.assertTrue(MatrixUtils.compareMatrices(Scenario1.EXPECTED_REPORT_COLUMNS_HEADER, 
												mockOutput.getHeaderCellMatrix()));
	}

}
