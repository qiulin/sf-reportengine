/**
 * 
 */
package net.sf.reportengine.scenarios.ct;

/**
 * @author dragos
 *
 */
public class CtScenario2 {
	
	public static final String[][] DISTINCT_VALUES = new String[][]{
		new String[]{"North","South","East","West"},
		new String[]{"M","F"},
	};
	
	public static final Boolean HAS_HEADER_TOTALS = new Boolean(false);
	
	public static final String[][] HEADER_TEMPLATE = new String[][]{
		new String[]{"North","North","South","South",	"East",	"East",	"West",	"West"},
		new String[]{"M",		"F",	"M",	"F",	"M",	"F",	"M",	"F"},
	};
	
}
