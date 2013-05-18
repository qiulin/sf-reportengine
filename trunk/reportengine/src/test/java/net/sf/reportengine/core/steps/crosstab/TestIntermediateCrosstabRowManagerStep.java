/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.DefaultReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.InputKeys;

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
		ReportContext context = new DefaultReportContext();
		Map<InputKeys, Object> mockInput = new EnumMap<InputKeys, Object>(InputKeys.class);
		
		mockInput.put(InputKeys.REPORT_OUTPUT, new CellPropsArrayOutput()); 
		mockInput.put(InputKeys.SHOW_TOTALS, false); 
		mockInput.put(InputKeys.SHOW_GRAND_TOTAL, false); 
		mockInput.put(InputKeys.ORIGINAL_CT_DATA_COLS_COUNT, Integer.valueOf(2)); 
		mockInput.put(InputKeys.ORIGINAL_CT_GROUP_COLS_COUNT, Integer.valueOf(1)); 
		
		context.set(ContextKeys.NEW_GROUPING_LEVEL, Integer.valueOf(1)); 
		context.set(ContextKeys.LAST_GROUPING_VALUES, new String[]{"1", "2", "3"}); 
		
		IntermediateCrosstabRowMangerStep classUnderTest = new IntermediateCrosstabRowMangerStep(); 
		classUnderTest.init(mockInput, context); 
		
		classUnderTest.execute(new NewRowEvent(Arrays.asList(new Object[]{"2", "3", "4"}))); 
		
		classUnderTest.exit(mockInput, context); 
		
	}

}
