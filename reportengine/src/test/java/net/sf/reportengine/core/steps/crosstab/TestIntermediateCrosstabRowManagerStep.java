/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.AlgorithmContext;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestIntermediateCrosstabRowManagerStep {

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.IntermediateCrosstabRowMangerStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	@Test
	public void testExecute() {
		AlgorithmContext context = new DefaultAlgorithmContext();
		Map<IOKeys, Object> mockInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		
		mockInput.put(IOKeys.REPORT_OUTPUT, new CellPropsArrayOutput()); 
		mockInput.put(IOKeys.SHOW_TOTALS, false); 
		mockInput.put(IOKeys.SHOW_GRAND_TOTAL, false); 
		mockInput.put(IOKeys.ORIGINAL_CT_DATA_COLS_COUNT, Integer.valueOf(2)); 
		mockInput.put(IOKeys.ORIGINAL_CT_GROUP_COLS_COUNT, Integer.valueOf(1)); 
		
		context.set(ContextKeys.NEW_GROUPING_LEVEL, Integer.valueOf(1)); 
		context.set(ContextKeys.LAST_GROUPING_VALUES, new String[]{"1", "2", "3"}); 
		
		IntermediateCrosstabRowMangerStep classUnderTest = new IntermediateCrosstabRowMangerStep(); 
		classUnderTest.init(mockInput, context); 
		
		classUnderTest.execute(new NewRowEvent(Arrays.asList(new Object[]{"2", "3", "4"}))); 
		
		classUnderTest.exit(mockInput, context); 
		
	}

}
