/**
 * 
 */
package net.sf.reportengine.scenarios.ct;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.ICrosstabData;
import net.sf.reportengine.config.ICrosstabHeaderRow;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.core.steps.crosstab.IntermediateDataInfo;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.in.MemoryReportInput;
import net.sf.reportengine.util.IDistinctValuesHolder;
import net.sf.reportengine.util.MockDistinctValuesHolder;

/**
 * @author dragos
 *
 */
public class CtScenario4x3x1 {
	public static final String[][] DISTINCT_VALUES = new String[][]{
		new String[]{"North","South","East","West"},
		new String[]{"M","F"},
		new String[]{"20", "50", "80"}
	};
	
	public static final Boolean HAS_HEADER_TOTALS = new Boolean(true);
	
	public static final Object[][] RAW_INPUT = new Object[][]{
		
		new Object[]{"Earth", "Europe", "N", "Norway",	"North",	"M",	"50",	100}, 
		
		new Object[]{"Earth", "Europe", "N", "Sweden", 	"North",	"M",	"20",	1000},
		new Object[]{"Earth", "Europe", "N", "Sweden", 	"North",	"M",	"50",	10},
		new Object[]{"Earth", "Europe", "N", "Sweden", 	"North",	"M",	"80",	4},
		new Object[]{"Earth", "Europe", "N", "Sweden", 	"North",	"F",	"20",	1},
		
		new Object[]{"Earth", "Europe", "S", "Italy",	"South",	"M",	"20",	2000},
		
		new Object[]{"Earth", "Europe", "E","Romania",	"East",		"F",	"50",	200}, 
		new Object[]{"Earth", "Europe", "E","Romania",	"West",		"F",	"50",	2},
		
		new Object[]{"Earth", "Europe", "W", "France",	"East",		"M",	"80",	3000}, 
		new Object[]{"Earth", "Europe", "W", "France",	"West",		"M",	"20",	300}, 
		new Object[]{"Earth", "Europe", "W", "France",	"West",		"F",	"20",	30}, 
		
		new Object[]{"Earth", "Asia", "S", "India",	"West",		"F",	"20",	11},
		new Object[]{"Earth", "Asia", "S", "India",	"West",		"F",	"50",	12},
		new Object[]{"Earth", "Asia", "S", "India",	"West",		"F",	"80",	13}, 
		new Object[]{"Earth", "Asia", "S", "India",	"West",		"M",	"20",	13}, 
    }; 
	
	public final static IGroupColumn[] GROUP_COLUMNS = new IGroupColumn[]{
		new DefaultGroupColumn("Planet",  0, 0), 
		new DefaultGroupColumn("Continent", 1, 1), 
		new DefaultGroupColumn("Region", 2, 2),
	};
	
	public final static List<IDataColumn> DATA_COLUMNS = Arrays.asList( 
			new IDataColumn[]{
					new DefaultDataColumn("Country", 3)
	});
	
	public final static ICrosstabHeaderRow[] HEADER_ROWS = new ICrosstabHeaderRow[]{
		new DefaultCrosstabHeaderRow(4), 	//Region inside country
		new DefaultCrosstabHeaderRow(5),	//Sex
		new DefaultCrosstabHeaderRow(6)		//Age
	}; 
	
	public final static ICrosstabData CROSSTAB_DATA = 
		new DefaultCrosstabData(7, Calculators.SUM);
	
	public final static IReportInput INPUT = new MemoryReportInput(RAW_INPUT);
	
	//untested
	public final static int[] AGG_LEVEL = new int[]{
		-1,
		
		0,
		3,
		3,		
		2,
		
		0,
		
		0,
		1,
		
		0, 
		1, 
		2};  

	//untested
	public static final IntermediateDataInfo[] INTERMEDIATE_DATA_INFO = new IntermediateDataInfo[]{
		new IntermediateDataInfo("100", new int[]{0,0,0}),
		
		new IntermediateDataInfo("1000", new int[]{0,0,1}),
		new IntermediateDataInfo("10", new int[]{0,0,0}),
		new IntermediateDataInfo("4", new int[]{0,0,2}),
		new IntermediateDataInfo("1", new int[]{0,1,1}),
		
		new IntermediateDataInfo("2000", new int[]{1,0,1}),
		
		new IntermediateDataInfo("200", new int[]{2,1,0}),
		new IntermediateDataInfo("2", new int[]{3,1,0}),
		
		new IntermediateDataInfo("3000", new int[]{2,0,2}),
		new IntermediateDataInfo("300", new int[]{3,0,1}),
		new IntermediateDataInfo("30", new int[]{3,1,1})
	};
	
	public static IDistinctValuesHolder MOCK_DISTINCT_VALUES_HOLDER = new MockDistinctValuesHolder(new String[][]{
			new String[]{"North", "South", "East", "West"},
			new String[]{"M", "F"},
			new String[]{"20","50","80"}
	});
}
