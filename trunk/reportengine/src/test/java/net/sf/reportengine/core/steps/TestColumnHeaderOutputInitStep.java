/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;
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
		AlgoContext mockContext = new DefaultAlgorithmContext(); 
		
		CellPropsArrayOutput mockOutput = new CellPropsArrayOutput(); 
		mockContext.set(ContextKeys.LOCAL_REPORT_OUTPUT, mockOutput); 
		
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);  
		//mockAlgoInput.put(IOKeys.REPORT_OUTPUT, mockOutput); 
		mockAlgoInput.put(IOKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockAlgoInput.put(IOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS) ;
		
		ColumnHeaderOutputInitStep classUnderTest = new ColumnHeaderOutputInitStep(); 
		classUnderTest.init(new StepInput(mockAlgoInput, mockContext));
		
		Assert.assertTrue(MatrixUtils.compareMatrices(Scenario1.EXPECTED_REPORT_COLUMNS_HEADER, 
												mockOutput.getHeaderCellMatrix()));
	}

}
