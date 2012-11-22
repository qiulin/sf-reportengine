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
import net.sf.reportengine.config.HorizontalAlign;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.MockCalculator;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.in.MemoryReportInput;

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
	
	
	
	 public final static IReportInput INPUT = new MemoryReportInput(RAW_DATA);
	    
	public static final IGroupColumn[] GROUP_COLUMNS = new IGroupColumn[]{
		new DefaultGroupColumn("Zero or One", 0, 0), 
		new AbstractGroupColumn("Computed 0+2", 1, null, HorizontalAlign.CENTER, false) {
			public Integer getValue(NewRowEvent newRowEvent) {
				Object[] data = newRowEvent.getInputDataRow();
				return Integer.valueOf((String)data[0])+Integer.valueOf((String)data[2]);
			}	
		}, 
		new DefaultGroupColumn("2 multiples", 2, 2), 
		new DefaultGroupColumn("3 Multiples", 4, 3),
		
	};
	
	public static final List<IDataColumn> DATA_COLUMNS = Arrays.asList(new IDataColumn[] {
		new DefaultDataColumn("Column A", 1),
		new DefaultDataColumn("Column B", 3), 
		new DefaultDataColumn("Column C", 5, Calculators.COUNT),
		new AbstractDataColumn("0+3", null, null, HorizontalAlign.CENTER) {
			public String getValue(NewRowEvent newRowEvent) {
				Object[] data = newRowEvent.getInputDataRow();
				return ""+data[0]+data[3];
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
	
	public final static ICalculator[][] CALCULATORS_RESULTS = new ICalculator[][]{
	    	new ICalculator[]{new MockCalculator(new BigDecimal(3))},
	    	new ICalculator[]{new MockCalculator(new BigDecimal(6))},
	    	new ICalculator[]{new MockCalculator(new BigDecimal(6))},
	    	new ICalculator[]{new MockCalculator(new BigDecimal(8))},
	    	new ICalculator[]{new MockCalculator(new BigDecimal(10))}
		};
}

