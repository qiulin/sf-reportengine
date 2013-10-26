/**
 * 
 */
package net.sf.reportengine.scenarios.ct;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.CrosstabData;
import net.sf.reportengine.config.CrosstabHeaderRow;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.core.steps.crosstab.IntermediateDataInfo;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.in.ArrayReportInput;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.util.CrossTabCoefficients;

/**
 * @author dragos
 *
 */
public class CtScenario1x1x1 {
	
	public static final String[][] DISTINCT_VALUES = new String[][]{
		new String[]{"East",	"North",	"South",	"West"}
	};
	
	public static final Boolean HAS_HEADER_TOTALS = new Boolean(false);
	
	public static final String[][] HEADER_TEMPLATE = new String[][]{
		new String[]{"East",	"North",	"South",	"West"}
	};
	
	public static final Object[][] RAW_INPUT = new Object[][]{
		new Object[]{"Asia",			"North", 	1},
		new Object[]{"Asia",			"South",	100},
		new Object[]{"Europe",			"West", 	2},
		new Object[]{"North America",	"East",		200},
		new Object[]{"North America",	"South",	3}
	};
	
	
	public static final List<GroupColumn> GROUP_COLUMNS = null; 
//		new GroupColumn[]{
//		new DefaultGroupColumn("Continent", 0, 0)
//	};
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList( 
			new DataColumn[]{
					new DefaultDataColumn("Continent", 0)
	});
	
	public static final List<CrosstabHeaderRow> ROW_HEADERS = Arrays.asList( 
			new CrosstabHeaderRow[]{
		new DefaultCrosstabHeaderRow(1)//Region - second column
	});
	
	public static final CrosstabData CROSSTAB_DATA_WITH_TOTALS = new DefaultCrosstabData(2, Calculators.SUM);
	public static final CrosstabData CROSSTAB_DATA_NO_TOTALS = new DefaultCrosstabData(2);
	
	
	public final static ReportInput INPUT = new ArrayReportInput(RAW_INPUT);
    
	
	public final static int[] AGG_LEVEL = new int[]{
		-1,
		1,
		0,
		0
	};
	
	public static final IntermediateDataInfo[] INTERMEDIATE_DATA_INFO = new IntermediateDataInfo[]{
		new IntermediateDataInfo(1, 	new int[]{0}),
		new IntermediateDataInfo(100, 	new int[]{1}),
		new IntermediateDataInfo(2, 	new int[]{2}),
		new IntermediateDataInfo(200, 	new int[]{3}),
		new IntermediateDataInfo(3, 	new int[]{1})
	};
	
	
//	public final static CellProps[][] EXPECTED_OUTPUT_DATA = new CellProps[][]{
//	    new CellProps[]{
//	            new CellProps.Builder("East").contentType(ReportContent.COLUMN_HEADER).build(), 
//	            new CellProps.Builder("North").contentType(ReportContent.COLUMN_HEADER).build(), 
//	            new CellProps.Builder("South").contentType(ReportContent.COLUMN_HEADER).build(), 
//	            new CellProps.Builder("West").contentType(ReportContent.COLUMN_HEADER).build()},
//	    
//	};
	
	public static final CrossTabCoefficients COEFFICIENTS = new CrossTabCoefficients(DISTINCT_VALUES, HAS_HEADER_TOTALS);
}
