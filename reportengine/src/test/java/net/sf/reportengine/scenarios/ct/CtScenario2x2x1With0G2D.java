/**
 * 
 */
package net.sf.reportengine.scenarios.ct;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.ICrosstabHeaderRow;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.ArrayReportInput;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.util.IDistinctValuesHolder;
import net.sf.reportengine.util.MockDistinctValuesHolder;

/**
 * 
 * @author dragos balan
 *
 */
public class CtScenario2x2x1With0G2D {
	
	public static final String[][] RAW_INPUT = new String[][]{
		new String[]{"North",	"Sweden", 	"M",	"20",	"1000"},
		new String[]{"North",	"Sweden", 	"M",	"50",	"10"},
		new String[]{"North",	"Sweden", 	"M",	"80",	"4"},
		new String[]{"North",	"Sweden", 	"F",	"20",	"1"},
				
		new String[]{"North",	"Norway", 	"M",	"50",	"100"}, 
				
		new String[]{"South",	"Italy", 	"M",	"20",	"2000"},
				
		new String[]{"East",	"Romania", 	"F",	"50",	"200"}, 
				
		new String[]{"West",	"France", 	"M",	"80",	"3000"}, 
		new String[]{"West",	"France", 	"M",	"20",	"300"}, 
		new String[]{"West",	"France", 	"F",	"20",	"30"}

    }; 
	
	public final static IReportInput INPUT = new ArrayReportInput(RAW_INPUT);
	
	public static final List<IGroupColumn> GROUPING_COLUMNS = null; 
	
	public static final List<IDataColumn> DATA_COLUMNS = Arrays.asList(
			new IDataColumn[]{
					new DefaultDataColumn("Region", 0), 
					new DefaultDataColumn("Country", 1)
			});
	
	public static final List<ICrosstabHeaderRow> HEADER_ROWS = Arrays.asList(new ICrosstabHeaderRow[]{
			new DefaultCrosstabHeaderRow(2, null), //Sex 
			new DefaultCrosstabHeaderRow(3, null)   //Age
		}); 
		
	public static final DefaultCrosstabData CROSSTAB_DATA = new DefaultCrosstabData(4, Calculators.SUM); //the count column
	
	public static IDistinctValuesHolder MOCK_DISTINCT_VALUES_HOLDER = new MockDistinctValuesHolder(new String[][]{
			new String[]{"M", "F"}, 
			new String[]{"20", "50", "80"}
		});
}
