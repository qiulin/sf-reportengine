/**
 * 
 */
package net.sf.reportengine.scenarios;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.in.ArrayReportInput;

/**
 * @author Administrator
 *
 */
public final class NoGroupsScenario {
	
	public static final Object[][] RAW_INPUT = new Object[][]{
		new String[]{"1","a","2","b","3","c"},
		new String[]{"1","a","2","b","3","c"},
		new String[]{"0","a","2","b","3","c"},
		new String[]{"0","a","2","b","3","c"},
		new String[]{"0","a","4","b","3","c"},
		new String[]{"0","a","4","b","3","c"},
		new String[]{"0","a","4","b","3","c"},
		new String[]{"0","a","4","b","6","c"},
		new String[]{"0","a","4","b","6","c"},
		new String[]{"0","a","4","b","6","c"}
	};
	
	public final static ReportInput INPUT = new ArrayReportInput(RAW_INPUT);
	
	public static final List<GroupColumn> GROUPING_COLUMNS = null; 
			
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList( 
			new DataColumn[]{
		new DefaultDataColumn("Zero or one", 0, null), 
		new DefaultDataColumn("2 multiples", 2, GroupCalculators.SUM), 
		new DefaultDataColumn("3 multiples", 4, GroupCalculators.AVG), 
		new DefaultDataColumn("Column A", 1), 
		new DefaultDataColumn("Column A", 3), 
		new DefaultDataColumn("Column A", 5, GroupCalculators.COUNT), 
	});
	
	public final static int[] AGG_LEVEL = new int[]{
		-1, 
		-1,
		-1,
		-1,
		-1,
		-1,
		-1,
		-1,
		-1,
		-1
	};
	
	
}
