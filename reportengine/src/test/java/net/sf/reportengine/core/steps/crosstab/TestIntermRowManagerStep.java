/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import static net.sf.reportengine.util.IOKeys.DATA_COLS;
import static net.sf.reportengine.util.IOKeys.GROUP_COLS;
import static net.sf.reportengine.util.IOKeys.SHOW_GRAND_TOTAL;
import static net.sf.reportengine.util.IOKeys.SHOW_TOTALS;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestIntermRowManagerStep {

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.IntermedRowMangerStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	@Test
	public void testExecute() {
		AlgoContext context = new DefaultAlgorithmContext();
		Map<IOKeys, Object> mockInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		
		mockInput.put(DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockInput.put(GROUP_COLS, Scenario1.GROUPING_COLUMNS); 
		//mockInput.put(REPORT_OUTPUT, new CellPropsArrayOutput()); 
		mockInput.put(SHOW_TOTALS, false); 
		mockInput.put(SHOW_GRAND_TOTAL, false); 
		
		context.set(ContextKeys.NEW_GROUPING_LEVEL, Integer.valueOf(1)); 
		context.set(ContextKeys.LAST_GROUPING_VALUES, new String[]{"1", "2", "3", "4", "5", "6"}); 
		
		ReportOutput mockReportOutput = new IntermediateCrosstabOutput(); 
		context.set(ContextKeys.LOCAL_REPORT_OUTPUT, mockReportOutput);
		
		mockReportOutput.open(); 
		
		IntermedRowMangerStep classUnderTest = new IntermedRowMangerStep(); 
		classUnderTest.init(mockInput, context); 
		
		classUnderTest.execute(new NewRowEvent(Arrays.asList(new Object[]{"2", "3", "4", "5", "6", "7"}))); 
		
		classUnderTest.exit(); 
		
		mockReportOutput.close(); 
		
	}

}
