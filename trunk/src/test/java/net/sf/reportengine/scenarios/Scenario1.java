/**
 * 
 */
package net.sf.reportengine.scenarios;

import java.math.BigDecimal;

import net.sf.reportengine.config.DefaultColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupingColumn;
import net.sf.reportengine.config.IColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupingColumn;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.MockCalculator;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.in.MemoryReportInput;
import net.sf.reportengine.out.CellProps;

/**
 * @author dragos balan
 *
 */
public class Scenario1 {
	
	public static boolean[] SHOW_TOTALS_ON_GROUP_LEVEL = new boolean[]{true, true, true};
	public static boolean SHOW_GRAND_TOTAL = true;
	public static final int[] AGG_COLUMNS_INDEX = new int []{0,1,2};
	
	public static final String[] ROW_OF_DATA_1 = new String[]{"1","2","3",    "4",  "5","6"};
	public static final String[] ROW_OF_DATA_2 = new String[]{"1","2","3",    "3",  "3","3"};
	public static final String[] ROW_OF_DATA_3 = new String[]{"1","2","2",    "2",  "2","2"};
	public static final String[] ROW_OF_DATA_4 = new String[]{"1","1","1",    "1",  "1","1"};
	public static final String[] ROW_OF_DATA_5 = new String[]{"1","1","1",    "1",  "1","1"};
	public static final String[] ROW_OF_DATA_6 = new String[]{"7","1","1",    "1",  "7","1"};
	
	public static final Object[][] RAW_DATA = new Object[][]{
		ROW_OF_DATA_1,
		ROW_OF_DATA_2, 
		ROW_OF_DATA_3,
		ROW_OF_DATA_4, 
		ROW_OF_DATA_5, 
		ROW_OF_DATA_6
	};
	
	public static final Object[][] PREVIOUS_GROUP_VALUES = new Object[][]{
		new String[]{"1","2","3"}, 
		new String[]{"1","2","3"},
		new String[]{"1","2","2"},
		new String[]{"1","1","1"},
		new String[]{"1","1","1"},
		new String[]{"7","1","1"},
	};
	
	/**
	 * @deprecated
	 */
	public static final IColumn[] CONFIG_COLUMNS = new IColumn[]{
		new DefaultColumn("col 0", 0, 0),
		new DefaultColumn("col 1", 1, 1),
		new DefaultColumn("col 2", 2, 2),
		new DefaultColumn("col 3", 3, DefaultColumn.NO_GROUP_COLUMN),
		new DefaultColumn("col 4", 4, DefaultColumn.NO_GROUP_COLUMN, Calculator.COUNT),
		new DefaultColumn("col 5", 5, DefaultColumn.NO_GROUP_COLUMN, Calculator.SUM)
	};
	
	public static final IGroupingColumn[] GROUPING_COLUMNS = new IGroupingColumn[] {
		new DefaultGroupingColumn("col 0", 0, 0), 
		new DefaultGroupingColumn("col 1", 1, 1), 
		new DefaultGroupingColumn("col 2", 2, 2)
	};
	
	public static final IDataColumn[] DATA_COLUMNS = new IDataColumn[]{
		new DefaultDataColumn("col 3", 3), 
		new DefaultDataColumn("col 4", 4, Calculator.COUNT), 
		new DefaultDataColumn("col 5", 5, Calculator.SUM)
	};
	
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
	
	public final static ICalculator[] TEST_CALCULATOR_PROTOTYPES = new ICalculator[]{Calculator.COUNT, Calculator.SUM};
    public final static int[] TEST_COLUMNS_TO_COMPUTE_TOTALS_ON = new int[]{4,5};
    
    public final static ICalculator[][] ROW_1_CALCULATORS_RESULTS = new ICalculator[][]{
    	new ICalculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(6))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(6))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(6))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(6))}
    															};
    
    public final static ICalculator[][] ROW_2_CALCULATORS_RESULTS = new ICalculator[][]{
    	new ICalculator[]{new MockCalculator(new BigDecimal(2)),new MockCalculator(new BigDecimal(9))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(2)),new MockCalculator(new BigDecimal(9))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(2)),new MockCalculator(new BigDecimal(9))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(2)),new MockCalculator(new BigDecimal(9))}
															};
    
    public final static ICalculator[][] ROW_3_CALCULATORS_RESULTS = new ICalculator[][]{
    	new ICalculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(2))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(3)),new MockCalculator(new BigDecimal(11))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(3)),new MockCalculator(new BigDecimal(11))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(3)),new MockCalculator(new BigDecimal(11))}
															};
    public final static ICalculator[][] ROW_4_CALCULATORS_RESULTS = new ICalculator[][]{
    	new ICalculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(1))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(1))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(4)),new MockCalculator(new BigDecimal(12))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(4)),new MockCalculator(new BigDecimal(12))}
	};
    
    public final static ICalculator[][] ROW_5_CALCULATORS_RESULTS = new ICalculator[][]{
    	new ICalculator[]{new MockCalculator(new BigDecimal(2)),new MockCalculator(new BigDecimal(2))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(2)),new MockCalculator(new BigDecimal(2))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(5)),new MockCalculator(new BigDecimal(13))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(5)),new MockCalculator(new BigDecimal(13))}
	};
    
    public final static ICalculator[][] ROW_6_CALCULATORS_RESULTS = new ICalculator[][]{
    	new ICalculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(1))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(1))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(1)),new MockCalculator(new BigDecimal(1))},
    	new ICalculator[]{new MockCalculator(new BigDecimal(6)),new MockCalculator(new BigDecimal(14))}
	};
	
    public final static IReportInput INPUT = new MemoryReportInput(RAW_DATA);
    
	public final static CellProps[][] OUTPUT_TOTALS = new CellProps[][]{
			//displayed on row 3
			new CellProps[]{
					new CellProps("Total 3"), 
					new CellProps(" ", 2), 
					new CellProps(" ", 1), 
					new CellProps(ROW_2_CALCULATORS_RESULTS[0][0].getResult()), 
					new CellProps(ROW_2_CALCULATORS_RESULTS[0][1].getResult())
			},
			
			//displayed on row 4
			new CellProps[]{
					new CellProps("Total 2"), 
					new CellProps(" ", 2), 
					new CellProps(" ", 1),
					new CellProps(ROW_3_CALCULATORS_RESULTS[0][0].getResult()), 
					new CellProps(ROW_3_CALCULATORS_RESULTS[0][1].getResult())
			}, 
			new CellProps[]{
					new CellProps("Total 2"),
					new CellProps(" ",2),
					new CellProps(" ", 1),
					new CellProps(ROW_3_CALCULATORS_RESULTS[1][0].getResult()),
					new CellProps(ROW_3_CALCULATORS_RESULTS[1][1].getResult())
			},
			
			//displayed on row 6
			new CellProps[]{
					new CellProps("Total 1"), 
					new CellProps(" ", 2),
					new CellProps(" ", 1),
					new CellProps(ROW_5_CALCULATORS_RESULTS[0][0].getResult()), 
					new CellProps(ROW_5_CALCULATORS_RESULTS[0][1].getResult())
			},
			new CellProps[]{
					new CellProps("Total 1"), 
					new CellProps(" ", 2),
					new CellProps(" ", 1),
					new CellProps(ROW_5_CALCULATORS_RESULTS[1][0].getResult()), 
					new CellProps(ROW_5_CALCULATORS_RESULTS[1][1].getResult())
			}, 
			new CellProps[]{
					new CellProps("Total 1"),
					new CellProps(" ",2),
					new CellProps(" ", 1),
					new CellProps(ROW_5_CALCULATORS_RESULTS[2][0].getResult()),
					new CellProps(ROW_5_CALCULATORS_RESULTS[2][1].getResult())
			},
			
			//displayed on exit
			new CellProps[]{
					new CellProps("Total 1"), 
					new CellProps(" ", 2),
					new CellProps(" ", 1),
					new CellProps(ROW_6_CALCULATORS_RESULTS[0][0].getResult()), 
					new CellProps(ROW_6_CALCULATORS_RESULTS[0][1].getResult())
			},
			new CellProps[]{
					new CellProps("Total 1"), 
					new CellProps(" ", 2),
					new CellProps(" ", 1),
					new CellProps(ROW_6_CALCULATORS_RESULTS[1][0].getResult()), 
					new CellProps(ROW_6_CALCULATORS_RESULTS[1][1].getResult())
			}, 
			new CellProps[]{
					new CellProps("Total 7"),
					new CellProps(" ",2),
					new CellProps(" ", 1),
					new CellProps(ROW_6_CALCULATORS_RESULTS[2][0].getResult()),
					new CellProps(ROW_6_CALCULATORS_RESULTS[2][1].getResult())
			},
			new CellProps[]{
					new CellProps("Grand Total"),
					new CellProps(" ",2),
					new CellProps(" ", 1),
					new CellProps(ROW_6_CALCULATORS_RESULTS[3][0].getResult()),
					new CellProps(ROW_6_CALCULATORS_RESULTS[3][1].getResult())
			}
	};
	
	public final static CellProps[][] OUTPUT_DATA = new CellProps[][]{
		//displayed on row 1
		new CellProps[]{
				new CellProps(ROW_OF_DATA_1[0]), 
				new CellProps(ROW_OF_DATA_1[1]), 
				new CellProps(ROW_OF_DATA_1[2]), 
				new CellProps(ROW_OF_DATA_1[3]), 
				new CellProps(ROW_OF_DATA_1[4]), 
				new CellProps(ROW_OF_DATA_1[5]) 
		},
		//displayed on row 2
		new CellProps[]{
				new CellProps(ROW_OF_DATA_2[0]), 
				new CellProps(ROW_OF_DATA_2[1]), 
				new CellProps(ROW_OF_DATA_2[2]), 
				new CellProps(ROW_OF_DATA_2[3]), 
				new CellProps(ROW_OF_DATA_2[4]), 
				new CellProps(ROW_OF_DATA_2[5]) 
		},
		//displayed on row 3
		new CellProps[]{
				new CellProps(ROW_OF_DATA_3[0]), 
				new CellProps(ROW_OF_DATA_3[1]), 
				new CellProps(ROW_OF_DATA_3[2]), 
				new CellProps(ROW_OF_DATA_3[3]), 
				new CellProps(ROW_OF_DATA_3[4]), 
				new CellProps(ROW_OF_DATA_3[5]) 
		},
		//displayed on row 4
		new CellProps[]{
				new CellProps(ROW_OF_DATA_4[0]), 
				new CellProps(ROW_OF_DATA_4[1]), 
				new CellProps(ROW_OF_DATA_4[2]), 
				new CellProps(ROW_OF_DATA_4[3]), 
				new CellProps(ROW_OF_DATA_4[4]), 
				new CellProps(ROW_OF_DATA_4[5]) 
		},
		//displayed on row 5
		new CellProps[]{
				new CellProps(ROW_OF_DATA_5[0]), 
				new CellProps(ROW_OF_DATA_5[1]), 
				new CellProps(ROW_OF_DATA_5[2]), 
				new CellProps(ROW_OF_DATA_5[3]), 
				new CellProps(ROW_OF_DATA_5[4]), 
				new CellProps(ROW_OF_DATA_5[5]) 
		},
		//displayed on row 6
		new CellProps[]{
				new CellProps(ROW_OF_DATA_6[0]), 
				new CellProps(ROW_OF_DATA_6[1]), 
				new CellProps(ROW_OF_DATA_6[2]), 
				new CellProps(ROW_OF_DATA_6[3]), 
				new CellProps(ROW_OF_DATA_6[4]), 
				new CellProps(ROW_OF_DATA_6[5]) 
		}
	};
	
	public final static CellProps[] EXPECTED_REPORT_COLUMNS_HEADER = new CellProps[]{
		new CellProps("col 0", 1, 1, ReportContent.CONTENT_COLUMN_HEADERS),
		new CellProps("col 1", 1, 1, ReportContent.CONTENT_COLUMN_HEADERS),	
		new CellProps("col 2", 1, 1, ReportContent.CONTENT_COLUMN_HEADERS),
		new CellProps("col 3", 1, 1, ReportContent.CONTENT_COLUMN_HEADERS),
		new CellProps("col 4", 1, 1, ReportContent.CONTENT_COLUMN_HEADERS),
		new CellProps("col 5", 1, 1, ReportContent.CONTENT_COLUMN_HEADERS)
	}; 
	
	public final static CellProps[][] EXPECTED_OUTPUT = new CellProps[][]{
		// the report header
		EXPECTED_REPORT_COLUMNS_HEADER, 
		//displayed on row 1
		new CellProps[]{
				new CellProps(ROW_OF_DATA_1[0]), 
				new CellProps(ROW_OF_DATA_1[1]), 
				new CellProps(ROW_OF_DATA_1[2]), 
				new CellProps(ROW_OF_DATA_1[3]), 
				new CellProps(ROW_OF_DATA_1[4]), 
				new CellProps(ROW_OF_DATA_1[5]) 
		},
		//displayed on row 2
		new CellProps[]{
				new CellProps(ROW_OF_DATA_2[0]), 
				new CellProps(ROW_OF_DATA_2[1]), 
				new CellProps(ROW_OF_DATA_2[2]), 
				new CellProps(ROW_OF_DATA_2[3]), 
				new CellProps(ROW_OF_DATA_2[4]), 
				new CellProps(ROW_OF_DATA_2[5]) 
		},
		
		//totals displayed on row 3
		new CellProps[]{
				new CellProps("Total 3"), 
				new CellProps(" ", 2),
				new CellProps(" ", 1),
				new CellProps(ROW_2_CALCULATORS_RESULTS[0][0].getResult()), 
				new CellProps(ROW_2_CALCULATORS_RESULTS[0][1].getResult())
		},
		
		//displayed on row 3
		new CellProps[]{
				new CellProps(ROW_OF_DATA_3[0]), 
				new CellProps(ROW_OF_DATA_3[1]), 
				new CellProps(ROW_OF_DATA_3[2]), 
				new CellProps(ROW_OF_DATA_3[3]), 
				new CellProps(ROW_OF_DATA_3[4]), 
				new CellProps(ROW_OF_DATA_3[5]) 
		},
		
		//totals displayed on row 4
		new CellProps[]{
				new CellProps("Total 2"), 
				new CellProps(" ", 2),
				new CellProps(" ", 1),
				new CellProps(ROW_3_CALCULATORS_RESULTS[0][0].getResult()), 
				new CellProps(ROW_3_CALCULATORS_RESULTS[0][1].getResult())
		}, 
		new CellProps[]{
				new CellProps("Total 2"),
				new CellProps(" ", 2),
				new CellProps(" ", 1),
				new CellProps(ROW_3_CALCULATORS_RESULTS[1][0].getResult()),
				new CellProps(ROW_3_CALCULATORS_RESULTS[1][1].getResult())
		},
		
		//displayed on row 4
		new CellProps[]{
				new CellProps(ROW_OF_DATA_4[0]), 
				new CellProps(ROW_OF_DATA_4[1]), 
				new CellProps(ROW_OF_DATA_4[2]), 
				new CellProps(ROW_OF_DATA_4[3]), 
				new CellProps(ROW_OF_DATA_4[4]), 
				new CellProps(ROW_OF_DATA_4[5]) 
		},
		//displayed on row 5
		new CellProps[]{
				new CellProps(ROW_OF_DATA_5[0]), 
				new CellProps(ROW_OF_DATA_5[1]), 
				new CellProps(ROW_OF_DATA_5[2]), 
				new CellProps(ROW_OF_DATA_5[3]), 
				new CellProps(ROW_OF_DATA_5[4]), 
				new CellProps(ROW_OF_DATA_5[5]) 
		},
		
		//totals displayed on row 6
		new CellProps[]{
				new CellProps("Total 1"), 
				new CellProps(" ", 2),
				new CellProps(" ", 1), 
				new CellProps(ROW_5_CALCULATORS_RESULTS[0][0].getResult()), 
				new CellProps(ROW_5_CALCULATORS_RESULTS[0][1].getResult())
		},
		new CellProps[]{
				new CellProps("Total 1"), 
				new CellProps(" ", 2),
				new CellProps(" ", 1),
				new CellProps(ROW_5_CALCULATORS_RESULTS[1][0].getResult()), 
				new CellProps(ROW_5_CALCULATORS_RESULTS[1][1].getResult())
		}, 
		new CellProps[]{
				new CellProps("Total 1"),
				new CellProps(" ", 2),
				new CellProps(" ", 1),
				new CellProps(ROW_5_CALCULATORS_RESULTS[2][0].getResult()),
				new CellProps(ROW_5_CALCULATORS_RESULTS[2][1].getResult())
		},
		
		//displayed on row 6
		new CellProps[]{
				new CellProps(ROW_OF_DATA_6[0]), 
				new CellProps(ROW_OF_DATA_6[1]), 
				new CellProps(ROW_OF_DATA_6[2]), 
				new CellProps(ROW_OF_DATA_6[3]), 
				new CellProps(ROW_OF_DATA_6[4]), 
				new CellProps(ROW_OF_DATA_6[5]) 
		},
		
		//totals displayed on exit
		new CellProps[]{
				new CellProps("Total 1"), 
				new CellProps(" ", 2),
				new CellProps(" ", 1),
				new CellProps(ROW_6_CALCULATORS_RESULTS[0][0].getResult()), 
				new CellProps(ROW_6_CALCULATORS_RESULTS[0][1].getResult())
		},
		new CellProps[]{
				new CellProps("Total 1"), 
				new CellProps(" ", 2),
				new CellProps(" ", 1),
				new CellProps(ROW_6_CALCULATORS_RESULTS[1][0].getResult()), 
				new CellProps(ROW_6_CALCULATORS_RESULTS[1][1].getResult())
		}, 
		new CellProps[]{
				new CellProps("Total 7"),
				new CellProps(" ", 2),
				new CellProps(" ", 1),
				new CellProps(ROW_6_CALCULATORS_RESULTS[2][0].getResult()),
				new CellProps(ROW_6_CALCULATORS_RESULTS[2][1].getResult())
		},
		new CellProps[]{
				new CellProps("Grand Total"),
				new CellProps(" ", 2),
				new CellProps(" ", 1),
				new CellProps(ROW_6_CALCULATORS_RESULTS[3][0].getResult()),
				new CellProps(ROW_6_CALCULATORS_RESULTS[3][1].getResult())
		}
	};
}
