/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import static org.junit.Assert.*;

import java.util.List;

import net.sf.reportengine.CrossTabReport;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.util.CtMetadata;

import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestConfigCrosstabColumnsInitStep {

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.ConfigCrosstabColumnsInitStep#constructGroupColumnsForSecondProcess(java.util.List)}.
	 */
	@Test
	public void testConstructGroupColumnsForSecondProcess() {
		//TODO: CONTINUE THE TEST
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.ConfigCrosstabColumnsInitStep#constructDataColumnsForSecondProcess(net.sf.reportengine.util.CtMetadata, java.util.List, boolean, boolean)}.
	 */
	@Test
	public void testConstructDataColumnsForSecondProcess() {
		ConfigCrosstabColumnsInitStep classUnderTest = new ConfigCrosstabColumnsInitStep(); 
		
		CtMetadata testMetadata = new CtMetadata(CtScenario2x2x1With1G1D.MOCK_DISTINCT_VALUES_HOLDER); 
		
		List<DataColumn> result = classUnderTest.constructDataColumnsForSecondProcess(testMetadata, 
															CtScenario2x2x1With1G1D.DATA_COLUMNS, 
															false, 
															false); 
		assertNotNull(result);
		//TODO: continue the test
	}

}
