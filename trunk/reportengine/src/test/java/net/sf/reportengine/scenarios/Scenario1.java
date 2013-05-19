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
 * @author dragos balan
 *
 */
public class Scenario1 {
	
	public static boolean[] SHOW_TOTALS_ON_GROUP_LEVEL = new boolean[]{true, true, true};
	public static boolean SHOW_GRAND_TOTAL = true;
	public static final int[] AGG_COLUMNS_INDEX = new int []{0,1,2};
	
	public static final List<Object> ROW_OF_DATA_1 = 
			Arrays.asList(new Object[]{"1","2","3",    "4",  "5","6"});
	public static final List<Object> ROW_OF_DATA_2 = 
			Arrays.asList(new Object[]{"1","2","3",    "3",  "3","3"});
	public static final List<Object> ROW_OF_DATA_3 = 
			Arrays.asList(new Object[]{"1","2","2",    "2",  "2","2"});
	public static final List<Object> ROW_OF_DATA_4 = 
			Arrays.asList(new Object[]{"1","1","1",    "1",  "1","1"});
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
	
	public static final Object[][] PREVIOUS_GROUP_VALUES = new Object[][]{
		new String[]{"1","2","3"}, 
		new String[]{"1","2","3"},
		new String[]{"1","2","2"},
		new String[]{"1","1","1"},
		new String[]{"1","1","1"},
		new String[]{"7","1","1"},
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
	
	public static final int[] DISTRIBUTION_OF_CALCULATOR_IN_DATA_ROW_ARRAY = new int[]{-1,0,1};  
	
	public static final int ROW_1_AGG_LEVEL = -1;
	public static final int ROW_2_AGG_LEVEL = -1;
	public static final int ROW_3_AGG_LEVEL = 2;
	public static final int ROW_4_AGG_LEVEL = 1;
	public static final int ROW_5_AGG_LEVEL = -1;
	public static final int ROW_6_AGG_LEVEL = 0;
	
	public static final int[] AGG_LEVEL = new int[]{
		ROW_1_AGG_LEVEL,
		ROW_2_AGG_LEVEL,
		ROW_3_AGG_LEVEL,
		ROW_4_AGG_LEVEL,
		ROW_5_AGG_LEVEL, 
		ROW_6_AGG_LEVEL
	};
	
	public final static Calculator[] TEST_CALCULATOR_PROTOTYPES = new Calculator[]{Calculators.COUNT, Calculators.SUM};
	
    public final static int[] TEST_COLUMNS_TO_COMPUTE_TOTALS_ON = new int[]{4,5};
    
    public final static Calculator[][] ROW_1_CALCULATORS_RESULTS = new Calculator[][]{
    	new Calculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(6))},
    	new Calculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(6))},
    	new Calculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(6))},
    	new Calculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(6))}
    															};
    
    public final static Calculator[][] ROW_2_CALCULATORS_RESULTS = new Calculator[][]{
    	new Calculator[]{new MockCalculator(new BigDecimal(2)),new MockCalculator(new BigDecimal(9))},
    	new Calculator[]{new MockCalculator(new BigDecimal(2)),new MockCalculator(new BigDecimal(9))},
    	new Calculator[]{new MockCalculator(new BigDecimal(2)),new MockCalculator(new BigDecimal(9))},
    	new Calculator[]{new MockCalculator(new BigDecimal(2)),new MockCalculator(new BigDecimal(9))}
															};
    
    public final static Calculator[][] ROW_3_CALCULATORS_RESULTS = new Calculator[][]{
    	new Calculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(2))},
    	new Calculator[]{new MockCalculator(new BigDecimal(3)),new MockCalculator(new BigDecimal(11))},
    	new Calculator[]{new MockCalculator(new BigDecimal(3)),new MockCalculator(new BigDecimal(11))},
    	new Calculator[]{new MockCalculator(new BigDecimal(3)),new MockCalculator(new BigDecimal(11))}
															};
    public final static Calculator[][] ROW_4_CALCULATORS_RESULTS = new Calculator[][]{
    	new Calculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(1))},
    	new Calculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(1))},
    	new Calculator[]{new MockCalculator(new BigDecimal(4)),new MockCalculator(new BigDecimal(12))},
    	new Calculator[]{new MockCalculator(new BigDecimal(4)),new MockCalculator(new BigDecimal(12))}
	};
    
    public final static Calculator[][] ROW_5_CALCULATORS_RESULTS = new Calculator[][]{
    	new Calculator[]{new MockCalculator(new BigDecimal(2)),new MockCalculator(new BigDecimal(2))},
    	new Calculator[]{new MockCalculator(new BigDecimal(2)),new MockCalculator(new BigDecimal(2))},
    	new Calculator[]{new MockCalculator(new BigDecimal(5)),new MockCalculator(new BigDecimal(13))},
    	new Calculator[]{new MockCalculator(new BigDecimal(5)),new MockCalculator(new BigDecimal(13))}
	};
    
    public final static Calculator[][] ROW_6_CALCULATORS_RESULTS = new Calculator[][]{
    	new Calculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(1))},
    	new Calculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(1))},
    	new Calculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(1))},
    	new Calculator[]{new MockCalculator(new BigDecimal(6)),new MockCalculator(new BigDecimal(14))}
	};
	
    public final static ReportInput INPUT = new ArrayReportInput(RAW_DATA);
    
	public final static CellProps[][] OUTPUT_TOTALS = new CellProps[][]{
			//displayed on row 3
			new CellProps[]{
					new CellProps.Builder("Total 3").build(), 
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL, 
					new CellProps.Builder(""+ROW_2_CALCULATORS_RESULTS[0][0].getResult()).build(), 
					new CellProps.Builder(""+ROW_2_CALCULATORS_RESULTS[0][1].getResult()).build()
			},
			
			//displayed on row 4
			new CellProps[]{
					new CellProps.Builder("Total 2").build(), 
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[0][0].getResult()).build(), 
					new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[0][1].getResult()).build()
			}, 
			new CellProps[]{
					new CellProps.Builder("Total 2").build(),
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[1][0].getResult()).build(),
					new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[1][1].getResult()).build()
			},
			
			//displayed on row 6
			new CellProps[]{
					new CellProps.Builder("Total 1").build(), 
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[0][0].getResult()).build(), 
					new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[0][1].getResult()).build()
			},
			new CellProps[]{
					new CellProps.Builder("Total 1").build(), 
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[1][0].getResult()).build(), 
					new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[1][1].getResult()).build()
			}, 
			new CellProps[]{
					new CellProps.Builder("Total 1").build(),
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[2][0].getResult()).build(),
					new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[2][1].getResult()).build()
			},
			
			//displayed on exit
			new CellProps[]{
					new CellProps.Builder("Total 1").build(), 
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[0][0].getResult()).build(), 
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[0][1].getResult()).build()
			},
			new CellProps[]{
					new CellProps.Builder("Total 1").build(), 
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[1][0].getResult()).build(), 
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[1][1].getResult()).build()
			}, 
			new CellProps[]{
					new CellProps.Builder("Total 7").build(),
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					CellProps.EMPTY_CELL,
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[2][0].getResult()).build(),
					new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[2][1].getResult()).build()
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
	
	public final static CellProps[][] OUTPUT_DATA = new CellProps[][]{
		//displayed on row 1
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_1.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(5)).build() 
		},
		//displayed on row 2
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_2.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(5)).build() 
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
				new CellProps.Builder(ROW_OF_DATA_4.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(5)).build() 
		},
		//displayed on row 5
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_5.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(5)).build() 
		},
		//displayed on row 6
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_6.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_6.get(5)).build() 
		}
	};
	
	public final static CellProps[][] EXPECTED_REPORT_COLUMNS_HEADER = new CellProps[][]{
		new CellProps[]{
			new CellProps.Builder("col 0").build(),
			new CellProps.Builder("col 1").build(),	
			new CellProps.Builder("col 2").build(),
			new CellProps.Builder("col 3").build(),
			new CellProps.Builder("col 4").build(),
			new CellProps.Builder("col 5").build()
		}}; 
	
	public final static CellProps[][] EXPECTED_OUTPUT_UNSORTED = new CellProps[][]{
	
		//displayed on row 1
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_1.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_1.get(5)).build() 
		},
		//displayed on row 2
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_2.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_2.get(5)).build() 
		},
		
		//totals displayed on row 3
		new CellProps[]{
				new CellProps.Builder("Total 3").build(), 
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				new CellProps.Builder(""+ROW_2_CALCULATORS_RESULTS[0][0].getResult()).build(), 
				new CellProps.Builder(""+ROW_2_CALCULATORS_RESULTS[0][1].getResult()).build()
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
		
		//totals displayed on row 4
		new CellProps[]{
				new CellProps.Builder("Total 2").build(), 
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[0][0].getResult()).build(), 
				new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[0][1].getResult()).build()
		}, 
		new CellProps[]{
				new CellProps.Builder("Total 2").build(),
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[1][0].getResult()).build(),
				new CellProps.Builder(""+ROW_3_CALCULATORS_RESULTS[1][1].getResult()).build()
		},
		
		//displayed on row 4
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_4.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_4.get(5)).build() 
		},
		//displayed on row 5
		new CellProps[]{
				new CellProps.Builder(ROW_OF_DATA_5.get(0)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(1)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(2)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(3)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(4)).build(), 
				new CellProps.Builder(ROW_OF_DATA_5.get(5)).build() 
		},
		
		//totals displayed on row 6
		new CellProps[]{
				new CellProps.Builder("Total 1").build(), 
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL, 
				new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[0][0].getResult()).build(), 
				new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[0][1].getResult()).build()
		},
		new CellProps[]{
				new CellProps.Builder("Total 1").build(), 
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[1][0].getResult()).build(), 
				new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[1][1].getResult()).build()
		}, 
		new CellProps[]{
				new CellProps.Builder("Total 1").build(),
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[2][0].getResult()).build(),
				new CellProps.Builder(""+ROW_5_CALCULATORS_RESULTS[2][1].getResult()).build()
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
		
		//totals displayed on exit
		new CellProps[]{
				new CellProps.Builder("Total 1").build(), 
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[0][0].getResult()).build(), 
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[0][1].getResult()).build()
		},
		new CellProps[]{
				new CellProps.Builder("Total 1").build(), 
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[1][0].getResult()).build(), 
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[1][1].getResult()).build()
		}, 
		new CellProps[]{
				new CellProps.Builder("Total 7").build(),
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				CellProps.EMPTY_CELL,
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[2][0].getResult()).build(),
				new CellProps.Builder(""+ROW_6_CALCULATORS_RESULTS[2][1].getResult()).build()
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
	
	public static CellPropsArrayOutput OUTPUT = new CellPropsArrayOutput();
}