package net.sf.reportengine.scenarios.ct;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.CrosstabHeaderRow;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.ArrayReportInput;
import net.sf.reportengine.in.ReportInput;

public class CtUnsortedScenario2x2x1With1G1D {
	
	public static final String[][] RAW_INPUT = new String[][]{
		new String[]{"North",	"M",	"20",	"Sweden", 	"1000"},
		new String[]{"North",	"M",	"50",	"Sweden", 	"10"},
		new String[]{"North",	"M",	"80",	"Sweden", 	"4"},
		new String[]{"North",	"F",	"20",	"Sweden", 	"1"},
		
		//wrong place (south between north )
		new String[]{"South",	"M",	"20",	"Italy", 	"2000"},
		
		new String[]{"North",	"M",	"50",	"Norway", 	"100"}, 
		
		new String[]{"West",	"M",	"80",	"France", 	"3000"}, 
		
		//wrong place (east between west)
		new String[]{"East",	"F",	"50",	"Romania", 	"200"}, 
		new String[]{"West",	"M",	"20",	"France", 	"300"}, 
		new String[]{"West",	"F",	"20",	"France", 	"30"}
    }; 
	
	public final static ReportInput INPUT = new ArrayReportInput(RAW_INPUT);
	
	public static final List<GroupColumn> GROUPING_COLUMNS = Arrays.asList(
			new GroupColumn[]{
					new DefaultGroupColumn("Programatically sorted Region", 0, 0)					
	});
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(
			new DataColumn[]{
					new DefaultDataColumn("Country", 3)
	});
	
	public static final List<CrosstabHeaderRow> HEADER_ROWS = Arrays.asList(new CrosstabHeaderRow[]{
		new DefaultCrosstabHeaderRow(1, null), //Sex 
		new DefaultCrosstabHeaderRow(2, null)   //Age
	}); 
	
	public static final DefaultCrosstabData CROSSTAB_DATA = new DefaultCrosstabData(4, GroupCalculators.SUM); //the count column
}
