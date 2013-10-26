/**
 * 
 */
package net.sf.reportengine.scenarios;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.AbstractDataColumn;
import net.sf.reportengine.config.AbstractGroupColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.core.steps.MockCalculator;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.in.ArrayReportInput;

/**
 * @author dragos balan
 *
 */
public class CalculatedColumnsScenario {
	
	public static final Object[][] RAW_DATA = new Object[][]{
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
		new Object[]{"1",3,"2","3"},
		new Object[]{"1",3,"2","3"},
		new Object[]{"0",2,"2","3"},
		new Object[]{"0",2,"2","3"},
		new Object[]{"0",4,"4","3"},
		new Object[]{"0",4,"4","3"},
		new Object[]{"0",4,"4","3"},
		new Object[]{"0",4,"4","6"},
		new Object[]{"0",4,"4","6"},
		new Object[]{"0",4,"4","6"}
	};
	
	
	
	 public final static ReportInput INPUT = new ArrayReportInput(RAW_DATA);
	    
	public static final List<GroupColumn> GROUP_COLUMNS = Arrays.asList(new GroupColumn[]{
		new DefaultGroupColumn("Zero or One", 0, 0), 
		new AbstractGroupColumn("Computed 0+2", 1, null, HorizAlign.CENTER, false) {
			public Integer getValue(NewRowEvent newRowEvent) {
				List<Object> data = newRowEvent.getInputDataRow();
				return Integer.valueOf((String)data.get(0))+Integer.valueOf((String)data.get(2));
			}	
		}, 
		new DefaultGroupColumn("2 multiples", 2, 2), 
		new DefaultGroupColumn("3 Multiples", 4, 3),
		
	});
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(new DataColumn[] {
		new DefaultDataColumn("Column A", 1),
		new DefaultDataColumn("Column B", 3), 
		new DefaultDataColumn("Column C", 5, Calculators.COUNT),
		new AbstractDataColumn("0+3", null, null, HorizAlign.CENTER) {
			public String getValue(NewRowEvent newRowEvent) {
				List<Object> data = newRowEvent.getInputDataRow();
				return ""+data.get(0)+data.get(3);
			}
		}
	});
	
	
	public final static Object[][] COMPUTED_VALUES = new Object[][]{
		 new Object[]{"1",3, "2", "3", 		"a","b","c", "1b"},
		 new Object[]{"1",3, "2", "3", 		"a","b","c", "1b"},
		 new Object[]{"0",2, "2", "3", 		"a","b","c", "0b"},
		 new Object[]{"0",2, "2", "3", 		"a","b","c", "0b"},
		 new Object[]{"0",4, "4", "3", 		"a","b","c", "0b"},
		 new Object[]{"0",4, "4", "3", 		"a","b","c", "0b"},
		 new Object[]{"0",4, "4", "3", 		"a","b","c", "0b"},
		 new Object[]{"0",4, "4", "6", 		"a","b","c", "0b"},
		 new Object[]{"0",4, "4", "6", 		"a","b","c", "0b"},
		 new Object[]{"0",4, "4", "6", 		"a","b","c", "0b"}
	};
	
	public final static int[] AGG_LEVEL = new int[]{
			-1,
			-1, 
			0,
			-1, 
			1,
			-1,
			-1,
			3,
			-1,
			-1
	};
	
	public final static int[] AGG_COLUMNS_INDEX = new int[]{0,1,3,5};
	
	public final static Calculator[][] CALCULATORS_RESULTS = new Calculator[][]{
	    	new Calculator[]{new MockCalculator(Integer.valueOf(3))},
	    	new Calculator[]{new MockCalculator(Integer.valueOf(6))},
	    	new Calculator[]{new MockCalculator(Integer.valueOf(6))},
	    	new Calculator[]{new MockCalculator(Integer.valueOf(8))},
	    	new Calculator[]{new MockCalculator(Integer.valueOf(10))}
		};
}

