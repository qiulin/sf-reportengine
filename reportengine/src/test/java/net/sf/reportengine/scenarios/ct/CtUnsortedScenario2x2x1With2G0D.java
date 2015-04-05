/**
 * 
 */
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
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.ArrayTableInput;
import net.sf.reportengine.in.TableInput;

/**
 * @author dragos balan
 *
 */
public class CtUnsortedScenario2x2x1With2G0D {
	
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
		new String[]{"West",	"France", 	"F",	"20",	"30"},
		
		//wrong place
		new String[]{"South",	"Greece", 	"M",	"20",	"500"},
		
		//wrong place
		new String[]{"North",	"Norway", 	"F",	"20",	"400"},
		
		//wrong place
		new String[]{"East",	"Hungary", 	"F",	"50",	"300"}, 
    }; 
	
	public final static TableInput INPUT = new ArrayTableInput(RAW_INPUT);
	
	public static final List<GroupColumn> GROUPING_COLUMNS = Arrays.asList(
			new GroupColumn[]{
					new DefaultGroupColumn("Region", 0, 0/*first group/sort level*/), 
					new DefaultGroupColumn("Country", 1, 1/*second group/sort level*/)
			}); 
	
	public static final List<DataColumn> DATA_COLUMNS = null; 
	
	public static final List<CrosstabHeaderRow> HEADER_ROWS = Arrays.asList(new CrosstabHeaderRow[]{
			new DefaultCrosstabHeaderRow(2, null), //Sex 
			new DefaultCrosstabHeaderRow(3, null)   //Age
		}); 
		
	public static final DefaultCrosstabData CROSSTAB_DATA = new DefaultCrosstabData(4, GroupCalculators.SUM); //the count column

}
