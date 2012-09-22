/**
 * 
 */
package net.sf.reportengine.scenarios.ct;

import net.sf.reportengine.util.Locator;

/**
 * @author dragos
 *
 */
public class CtScenario4 {
	
	public static final String[][] DISTINCT_VALUES = CtScenario1x1x1.DISTINCT_VALUES;
	
	public static final Boolean HAS_HEADER_TOTALS = new Boolean(true);
	
	public static final String[][] HEADER_TEMPLATE = new String[][]{
		new String[]{"East","North","South","West",Locator.TOTAL}
	};
}
