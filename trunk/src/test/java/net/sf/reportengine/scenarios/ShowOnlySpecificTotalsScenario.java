/**
 * 
 */
package net.sf.reportengine.scenarios;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.in.MemoryReportInput;

/**
 * @author dragos balan
 *
 */
public class ShowOnlySpecificTotalsScenario extends Scenario2 {
	
	public static final IReportInput INPUT = new MemoryReportInput(RAW_INPUT);
	
	public static final boolean[] SHOW_TOTALS_ON_GROUPING_LEVEL = new boolean[]{true, false, true};
	
	public static final IGroupColumn[] GROUP_COLUMNS = new IGroupColumn[]{
		new DefaultGroupColumn("Zero or One", 0, 0 ),
		new DefaultGroupColumn("2 multiples", 2, 1), 
		new DefaultGroupColumn("3 multiples",4, 2)
	};
	
	public static final List<IDataColumn> DATA_COLUMNS = Arrays.asList( 
			new IDataColumn[]{
					new DefaultDataColumn("Column A", 1),
					new DefaultDataColumn("Column B", 3),
					new DefaultDataColumn("Column C", 5, Calculators.COUNT)
	});
	
}
