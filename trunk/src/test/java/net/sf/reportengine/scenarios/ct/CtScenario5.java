/**
 * 
 */
package net.sf.reportengine.scenarios.ct;

import net.sf.reportengine.util.Locator;

/**
 * @author dragos
 *
 */
public class CtScenario5 {
	
	public static final String[][] DISTINCT_VALUES = CtScenario2.DISTINCT_VALUES;
	
	public static final Boolean HAS_HEADER_TOTALS = new Boolean(true);
	
	public static final String[][] HEADER_TEMPLATE = new String[][]{
		new String[]{"North","North",	"North",		"South",	"South",	"South",	"East",	"East",	"East",		"West",	"West", "West", 		Locator.TOTAL},
		new String[]{"M",		"F",	Locator.TOTAL, 	"M",			"F",	Locator.TOTAL, "M",	"F",	Locator.TOTAL, "M",	 "F", 	Locator.TOTAL, 	Locator.SPACE},
	};
}
