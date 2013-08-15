package net.sf.reportengine.scenarios.ct;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.CrosstabHeaderRow;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.ArrayReportInput;
import net.sf.reportengine.in.ReportInput;

/**
 * in this scenario the data columns have sortLevel set. The report doesn't need any other configuration
 * 
 * @author dragos
 *
 */
public class CtUnsortedScenario2x2x1With0G2D {
	
	public static final String[][] RAW_INPUT = new String[][]{
		new String[]{"North",	"Sweden", 	"M",	"20",	"1000"},
		new String[]{"North",	"Sweden", 	"M",	"50",	"10"},
		new String[]{"North",	"Sweden", 	"M",	"80",	"4"},
		new String[]{"North",	"Sweden", 	"F",	"20",	"1"},
		//wrong place
		new String[]{"South",	"Italy", 	"M",	"20",	"2000"},
				
		new String[]{"North",	"Norway", 	"M",	"50",	"100"}, 
				
		new String[]{"West",	"France", 	"M",	"80",	"3000"}, 
		//wrong place
		new String[]{"East",	"Romania", 	"F",	"50",	"200"}, 
		new String[]{"West",	"France", 	"M",	"20",	"300"}, 
		new String[]{"West",	"France", 	"F",	"20",	"30"}

    }; 
	
	public final static ReportInput INPUT = new ArrayReportInput(RAW_INPUT);
	
	public static final List<GroupColumn> GROUPING_COLUMNS = null; 
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(
			new DataColumn[]{
					new DefaultDataColumn("Region", 0, null, null, HorizAlign.CENTER, 0/*first sort level*/), 
					new DefaultDataColumn("Country", 1, null, null, HorizAlign.CENTER, 1/*second sort level*/)
			});
	
	public static final List<CrosstabHeaderRow> HEADER_ROWS = Arrays.asList(new CrosstabHeaderRow[]{
			new DefaultCrosstabHeaderRow(2, null), //Sex 
			new DefaultCrosstabHeaderRow(3, null)   //Age
		}); 
		
	public static final DefaultCrosstabData CROSSTAB_DATA = new DefaultCrosstabData(4, Calculators.SUM); //the count column
	
}
