/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.util.CtMetadata;

import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestConstrDataColsForSecondProcessInitStep {

	@Test
	public void testConstructDataColumnsForSecondProcess() {
		ConstrDataColsForSecondProcessInitStep classUnderTest = new ConstrDataColsForSecondProcessInitStep(); 
		
		CtMetadata testMetadata = new CtMetadata(CtScenario2x2x1With1G1D.MOCK_DISTINCT_VALUES_HOLDER); 
		
		List<DataColumn> result = classUnderTest.constructDataColumnsForSecondProcess(testMetadata, 
															CtScenario2x2x1With1G1D.DATA_COLUMNS, 
															CtScenario2x2x1With1G1D.CROSSTAB_DATA, 
															false, 
															false); 
		assertNotNull(result);
		//TODO: continue the test
	}

}
