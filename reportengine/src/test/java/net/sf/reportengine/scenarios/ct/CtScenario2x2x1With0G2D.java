/**
 * 
 */
package net.sf.reportengine.scenarios.ct;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.CrosstabHeaderRow;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.ArrayReportInput;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.util.DistinctValuesHolder;
import net.sf.reportengine.util.MockDistinctValuesHolder;

/**
 * 
 * @author dragos balan
 *
 */
public class CtScenario2x2x1With0G2D {
	
	public static final Object[][] RAW_INPUT = new Object[][]{
		new Object[]{"North",	"Sweden", 	"M",	"20",	1000},
		new Object[]{"North",	"Sweden", 	"M",	"50",	10},
		new Object[]{"North",	"Sweden", 	"M",	"80",	4},
		new Object[]{"North",	"Sweden", 	"F",	"20",	1},
		new Object[]{"North",	"Norway", 	"M",	"50",	100}, 
		new Object[]{"South",	"Italy", 	"M",	"20",	2000},
		new Object[]{"East",	"Romania", 	"F",	"50",	200}, 
		new Object[]{"West",	"France", 	"M",	"80",	3000}, 
		new Object[]{"West",	"France", 	"M",	"20",	300}, 
		new Object[]{"West",	"France", 	"F",	"20",	30}
    }; 
	
	public final static ReportInput INPUT = new ArrayReportInput(RAW_INPUT);
	
	public static final List<GroupColumn> GROUPING_COLUMNS = null; 
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(
			new DataColumn[]{
					new DefaultDataColumn("Region", 0), 
					new DefaultDataColumn("Country", 1)
			});
	
	public static final List<CrosstabHeaderRow> HEADER_ROWS = Arrays.asList(new CrosstabHeaderRow[]{
			new DefaultCrosstabHeaderRow(2, null), //Sex 
			new DefaultCrosstabHeaderRow(3, null)   //Age
		}); 
		
	public static final DefaultCrosstabData CROSSTAB_DATA = new DefaultCrosstabData(4, GroupCalculators.SUM); //the count column
	
	public static DistinctValuesHolder MOCK_DISTINCT_VALUES_HOLDER = new MockDistinctValuesHolder(new String[][]{
			new String[]{"M", "F"}, 
			new String[]{"20", "50", "80"}
		});
}
