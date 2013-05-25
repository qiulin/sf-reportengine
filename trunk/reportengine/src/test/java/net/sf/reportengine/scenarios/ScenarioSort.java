/**
 * 
 */
package net.sf.reportengine.scenarios;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.core.steps.MockCalculator;
import net.sf.reportengine.in.ArrayReportInput;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.CellPropsArrayOutput;

/**
 * @author dragos
 *
 */
public class ScenarioSort {
	
	public static final List<Object> ROW_OF_DATA_1 = 
			Arrays.asList(new Object[]{"1","2","4",    "4",  "5","6"});
	public static final List<Object> ROW_OF_DATA_2 = 
			Arrays.asList(new Object[]{"1","2","3",    "3",  "3","3"});
	public static final List<Object> ROW_OF_DATA_3 = 
			Arrays.asList(new Object[]{"1","2","2",    "2",  "2","2"});
	public static final List<Object> ROW_OF_DATA_4 = 
			Arrays.asList(new Object[]{"1","1","2",    "1",  "1","1"});
	public static final List<Object> ROW_OF_DATA_5 = 
			Arrays.asList(new Object[]{"1","1","1",    "1",  "1","1"});
	public static final List<Object> ROW_OF_DATA_6 = 
			Arrays.asList(new Object[]{"7","1","1",    "1",  "7","1"});
	
	public static final Object[][] RAW_DATA = new Object[][]{
		ROW_OF_DATA_1.toArray(new String[]{}),
		ROW_OF_DATA_2.toArray(new String[]{}),
		ROW_OF_DATA_3.toArray(new String[]{}),
		ROW_OF_DATA_4.toArray(new String[]{}),
		ROW_OF_DATA_5.toArray(new String[]{}),
		ROW_OF_DATA_6.toArray(new String[]{})
	};
	
	public static final List<GroupColumn> GROUPING_COLUMNS = Arrays.asList(
			new GroupColumn[]{
					new DefaultGroupColumn("col 0", 0, 0, null, HorizAlign.CENTER, true), 
					new DefaultGroupColumn("col 1", 1, 1, null, HorizAlign.CENTER, true), 
					new DefaultGroupColumn("col 2", 2, 2, null, HorizAlign.CENTER, true)
			});
		
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(
				new DataColumn[]{
			new DefaultDataColumn("col 3", 3), 
			new DefaultDataColumn("col 4", 4, Calculators.COUNT), 
			new DefaultDataColumn("col 5", 5, Calculators.SUM)
	});
	
	 public final static Calculator[][] ROW_6_CALCULATORS_RESULTS = new Calculator[][]{
	    	new Calculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(1))},
	    	new Calculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(1))},
	    	new Calculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(1))},
	    	new Calculator[]{new MockCalculator(new BigDecimal(6)),new MockCalculator(new BigDecimal(14))}
		};
	
	public final static CellProps[][] EXPECTED_OUTPUT_SORTED = new CellProps[][]{
		
		//displayed on row 1
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_5.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(5)).build() 
		},
		//displayed on row 2
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_4.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(5)).build() 
		},
		
		//displayed on row 3
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_3.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_3.get(5)).build() 
		},
		
		//displayed on row 4
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_2.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(5)).build() 
		},
		//displayed on row 5
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_1.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(5)).build() 
		},
		
		//displayed on row 6
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_6.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(5)).build() 
		},
		
		new CellProps[]{
				new CellProps.Builder("Grand Total").build(),
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[3][0].getResult()).build(),
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[3][1].getResult()).build()
		}
	};
	
	public final static ReportInput INPUT = new ArrayReportInput(RAW_DATA);
	public static CellPropsArrayOutput OUTPUT = new CellPropsArrayOutput();
}
