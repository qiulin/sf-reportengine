/**
 * 
 */
package net.sf.reportengine.scenarios.ct;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
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
 * @author dragos balan
 *
 */
public class CtScenario1x3x1 {
	
	public static final String[][] DISTINCT_VALUES = new String[][]{
		new String[]{"North","South","East","West"},
		new String[]{"M","F"},
		new String[]{"20", "50", "80"}
	};
	
	public static final Boolean HAS_HEADER_TOTALS = new Boolean(true);
	
	
	public static final String[][] RAW_INPUT = new String[][]{
		
		new String[]{"Norway",	"North",	"M",	"50",	"100"}, 
		
		new String[]{"Sweden", 	"North",	"M",	"20",	"1000"},
		new String[]{"Sweden", 	"North",	"M",	"50",	"10"},
		new String[]{"Sweden", 	"North",	"M",	"80",	"4"},
		new String[]{"Sweden", 	"North",	"F",	"20",	"1"},
		
		new String[]{"Italy",	"South",	"M",	"20",	"2000"},
		
		new String[]{"Romania",	"East",		"F",	"50",	"200"}, 
		new String[]{"Romania",	"West",		"F",	"50",	"2"},
		
		new String[]{"France",	"East",		"M",	"80",	"3000"}, 
		new String[]{"France",	"West",		"M",	"20",	"300"}, 
		new String[]{"France",	"West",		"F",	"20",	"30"}
    }; 
	
	public final static List<IGroupColumn> GROUP_COLUMNS = null; 
		//new IGroupColumn[]{
		//new DefaultGroupColumn("Country",  0, 0) 
	//};
	
	public final static List<IDataColumn> DATA_COLUMNS = Arrays.asList(
		new IDataColumn[]{
				new DefaultDataColumn("Country", 0)
	});
	
	public final static List<ICrosstabHeaderRow> HEADER_ROWS = Arrays.asList( 
			new ICrosstabHeaderRow[]{
		new DefaultCrosstabHeaderRow(1), 	//Region
		new DefaultCrosstabHeaderRow(2),	//Sex
		new DefaultCrosstabHeaderRow(3)		//Age
	}); 
	
	public final static ICrosstabData CROSSTAB_DATA = new DefaultCrosstabData(4, Calculators.SUM);
	
	public final static IReportInput INPUT = new MemoryReportInput(RAW_INPUT);
	
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
