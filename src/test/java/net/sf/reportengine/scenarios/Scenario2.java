/**
 * 
 */
package net.sf.reportengine.scenarios;

import java.math.BigDecimal;

import net.sf.reportengine.config.DefaultColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.IColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.MockCalculator;

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
	
	/**
	 * @deprecated
	 */
	public static final IColumn[] CONFIG_COLUMNS = new IColumn[]{
		new DefaultColumn("Zero or One", 0, 0),
		new DefaultColumn("Column A", 1, DefaultColumn.NO_GROUP_COLUMN),
		new DefaultColumn("2 multiples", 2, 1),
		new DefaultColumn("Column B", 3, DefaultColumn.NO_GROUP_COLUMN),
		new DefaultColumn("3 Multiples", 4, 2),
		new DefaultColumn("Column C", 5, DefaultColumn.NO_GROUP_COLUMN, Calculator.COUNT)
	};
	
	public static final IGroupColumn[] GROUPING_COLUMNS = new IGroupColumn[]{
		new DefaultGroupColumn("Zero or One", 0, 0), 
		new DefaultGroupColumn("2 multiples", 2, 1), 
		new DefaultGroupColumn("3 Multiples", 4, 2), 
	};
	
	public static final IDataColumn[] DATA_COLUMNS = new IDataColumn[]{
		new DefaultDataColumn("Column A", 1), 
		new DefaultDataColumn("Column A", 3), 
		new DefaultDataColumn("Column A", 5, Calculator.COUNT), 
	};
	
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
	
	public final static ICalculator[][] CALCULATORS_RESULTS = new ICalculator[][]{
    	new ICalculator[]{new MockCalculator(new BigDecimal(3))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(6))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(8))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(10))}
	};
	
}
