package net.sf.reportengine.scenarios;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.SortType;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.ArrayReportInput;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.CellPropsArrayOutput;

public class SortScenarioOnlyDataColsCount {
	
	public static final Object[][] RAW_DATA = new Object[][]{
		new Object[]{"1","1","1",    "4",  5,3}, 
		new Object[]{"1","1","1",    "3",  4,5},
		new Object[]{"1","1","1",    "2",  3,4},
		new Object[]{"1","1","1",    "1",  2,2}, 
		new Object[]{"1","1","1",    "1",  1,1}, 
		new Object[]{"1","1","1",    "1",  0,1}
	};
	
	/**
	 * even if the group columns seem to count they don't because the group values are equal
	 */
	public static final List<GroupColumn> GROUPING_COLUMNS = Arrays.asList(
		new GroupColumn[]{
				new DefaultGroupColumn("col 0", 0, 0, null, HorizAlign.CENTER, VertAlign.MIDDLE, true, SortType.ASC), 
				new DefaultGroupColumn("col 1", 1, 1, null, HorizAlign.CENTER, VertAlign.MIDDLE, true, SortType.ASC), 
				new DefaultGroupColumn("col 2", 2, 2, null, HorizAlign.CENTER, VertAlign.MIDDLE, true, SortType.ASC)
	});
		
	/**
	 * 
	 */
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(
		new DataColumn[]{
			new DefaultDataColumn("no sorting", 3), //no ordering
			new DefaultDataColumn("asc. sorted with sorting level 1", 4, Calculators.COUNT, null, HorizAlign.CENTER, VertAlign.MIDDLE, 1, SortType.ASC), 
			new DefaultDataColumn("desc sorted with sorting level 0", 5, Calculators.SUM, null, HorizAlign.CENTER, VertAlign.MIDDLE, 0, SortType.DESC) //higher order priority
	});
	
	public final static ReportInput INPUT = new ArrayReportInput(RAW_DATA);
	public static CellPropsArrayOutput OUTPUT = new CellPropsArrayOutput();
}