/**
 * 
 */
package net.sf.reportengine.scenarios;

import net.sf.reportengine.config.DefaultColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupingColumn;
import net.sf.reportengine.config.IColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupingColumn;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.in.MemoryReportInput;

/**
 * @author dragos balan
 *
 */
public class ShowOnlySpecificTotalsScenario extends Scenario2 {
	
	public static final IColumn[] CONFIG_COLUMNS = new IColumn[]{
		new DefaultColumn("Zero or One", 0, 0, true),
		new DefaultColumn("Column A", 1, DefaultColumn.NO_GROUP_COLUMN),
		new DefaultColumn("2 multiples", 2, 1 , false),
		new DefaultColumn("Column B", 3, DefaultColumn.NO_GROUP_COLUMN),
		new DefaultColumn("3 Multiples", 4, 2, true),
		new DefaultColumn("Column C", 5, DefaultColumn.NO_GROUP_COLUMN, Calculator.COUNT)
	};
	
	public static final IReportInput INPUT = new MemoryReportInput(RAW_INPUT);
	
	public static final boolean[] SHOW_TOTALS_ON_GROUPING_LEVEL = new boolean[]{true, false, true};
	
	public static final IGroupingColumn[] GROUP_COLUMNS = new IGroupingColumn[]{
		new DefaultGroupingColumn("Zero or One", 0, 0 ),
		new DefaultGroupingColumn("2 multiples", 2, 1), 
		new DefaultGroupingColumn("3 multiples",4, 2)
	};
	
	public static final IDataColumn[] DATA_COLUMNS = new IDataColumn[]{
		new DefaultDataColumn("Column A", 1),
		new DefaultDataColumn("Column B", 3),
		new DefaultDataColumn("Column C", 5, Calculator.COUNT)
	};
	
}
