/**
 * 
 */
package net.sf.reportengine.core.steps.neo;

import java.io.StringWriter;
import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.out.neo.DefaultReportOutput;
import net.sf.reportengine.out.neo.TestImplForReportOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestColumnHeaderOutputInitStep  {
	
	@Test
	public void testInitScenario1() {
		AlgoContext mockContext = new DefaultAlgorithmContext(); 
		
		TestImplForReportOutput mockOutput = new TestImplForReportOutput();  
		mockOutput.open(); 
		
		mockContext.set(ContextKeys.NEW_LOCAL_REPORT_OUTPUT, mockOutput); 
		
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);  
		mockAlgoInput.put(IOKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockAlgoInput.put(IOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS) ;
		
		NewColumnHeaderOutputInitStep classUnderTest = new NewColumnHeaderOutputInitStep(); 
		classUnderTest.init(new StepInput(mockAlgoInput, mockContext));
		
		mockOutput.close();
		System.out.println(mockOutput.getBuffer());
		
//		Assert.assertTrue(MatrixUtils.compareMatrices(Scenario1.EXPECTED_REPORT_COLUMNS_HEADER, 
//												mockOutput.getHeaderCellMatrix()));
	}
	
	
}
