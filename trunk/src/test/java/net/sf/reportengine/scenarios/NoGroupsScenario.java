/**
 * 
 */
package net.sf.reportengine.scenarios;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.in.MemoryReportInput;

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
	
	public final static IReportInput INPUT = new MemoryReportInput(RAW_INPUT);
	
	public static final List<IGroupColumn> GROUPING_COLUMNS = null; 
			
	public static final List<IDataColumn> DATA_COLUMNS = Arrays.asList( 
			new IDataColumn[]{
		new DefaultDataColumn("Zero or one", 0, null), 
		new DefaultDataColumn("2 multiples", 2, Calculators.SUM), 
		new DefaultDataColumn("3 multiples", 4, Calculators.AVG), 
		new DefaultDataColumn("Column A", 1), 
		new DefaultDataColumn("Column A", 3), 
		new DefaultDataColumn("Column A", 5, Calculators.COUNT), 
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
