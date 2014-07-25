/**
 * 
 */
package net.sf.reportengine.scenarios;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.core.calc.DefaultCalcIntermResult;
import net.sf.reportengine.core.calc.CountGroupCalculator;

/**
 * @author dragos
 *
 */
public class Scenario2 {
	
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
	
	public static final Object[][] PREVIOUS_GROUP_VALUES = new Object[][]{
		new String[]{"1","2","3"},
		new String[]{"1","2","3"},
		new String[]{"0","2","3"},
		new String[]{"0","2","3"},
		new String[]{"0","4","3"},
		new String[]{"0","4","3"},
		new String[]{"0","4","3"},
		new String[]{"0","4","6"},
		new String[]{"0","4","6"},
		new String[]{"0","4","6"}
	};
	
	public static final Object[][] COMPUTED_INPUT = RAW_INPUT;
	
	public static final List<GroupColumn> GROUPING_COLUMNS = Arrays.asList(new GroupColumn[]{
		new DefaultGroupColumn("Zero or One", 0, 0), 
		new DefaultGroupColumn("2 multiples", 2, 1), 
		new DefaultGroupColumn("3 Multiples", 4, 2), 
	});
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList( 
			new DataColumn[]{
		new DefaultDataColumn("Column A", 1), 
		new DefaultDataColumn("Column A", 3), 
		new DefaultDataColumn("Column A", 5, new CountGroupCalculator()), 
	});
	
	public static final int[] AGG_COLUMNS_INDEX = new int[]{0,2,4};
	
	public final static int[] AGG_LEVEL = new int[]{
		-1, 
		-1,
		 0,
		-1,
		 1,
		-1,
		-1,
		 2,
		-1,
		-1
	};
	
	public final static CalcIntermResult[][] CALCULATORS_RESULTS = new CalcIntermResult[][]{
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(3))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(6))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(8))},
    	new CalcIntermResult[]{new DefaultCalcIntermResult<Integer>(Integer.valueOf(10))}
	};
	
}
