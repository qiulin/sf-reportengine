/**
 * 
 */
package net.sf.reportengine.scenarios;

import java.math.BigDecimal;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupingColumn;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.MockCalculator;
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
	
	public static final IGroupingColumn[] GROUPING_COLUMNS = null; 
			
	public static final IDataColumn[] DATA_COLUMNS = new IDataColumn[]{
		new DefaultDataColumn("Zero or one", 0, null), 
		new DefaultDataColumn("2 multiples", 2, Calculator.SUM), 
		new DefaultDataColumn("3 multiples", 4, Calculator.AVG), 
		new DefaultDataColumn("Column A", 1), 
		new DefaultDataColumn("Column A", 3), 
		new DefaultDataColumn("Column A", 5, Calculator.COUNT), 
	};
	
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
