/**
 * 
 */
package net.sf.reportengine.scenarios;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.calc.Calculators;

/**
 * @author dragos
 *
 */
public class SortScenario {
	
	public static final List<Object> ROW_OF_DATA_1 = Arrays.asList(new Object[]{"1","2","3",    "4",  "5","6"});
	public static final List<Object> ROW_OF_DATA_2 = Arrays.asList(new Object[]{"1","2","3",    "3",  "3","3"});
	public static final List<Object> ROW_OF_DATA_3 = Arrays.asList(new Object[]{"1","2","2",    "2",  "2","2"});
	public static final List<Object> ROW_OF_DATA_4 = Arrays.asList(new Object[]{"1","1","1",    "1",  "1","1"});
	public static final List<Object> ROW_OF_DATA_5 = Arrays.asList(new Object[]{"1","1","1",    "1",  "1","1"});
	public static final List<Object> ROW_OF_DATA_6 = Arrays.asList(new Object[]{"7","1","1",    "1",  "7","1"});
	
	/**
	 * 
	 */
	public static final List<GroupColumn> GROUPING_COLUMNS = Arrays.asList(
		new GroupColumn[]{
				new DefaultGroupColumn("col 0", 0, 0, null, HorizAlign.CENTER, true), 
				new DefaultGroupColumn("col 1", 1, 1, null, HorizAlign.CENTER, true), 
				new DefaultGroupColumn("col 2", 2, 2, null, HorizAlign.CENTER, true)
	});
		
	/**
	 * 
	 */
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(
		new DataColumn[]{
			new DefaultDataColumn("col 3", 3), //no ordering
			new DefaultDataColumn("col 4", 4, Calculators.COUNT, null, HorizAlign.CENTER, 1), 
			new DefaultDataColumn("col 5", 5, Calculators.SUM, null, HorizAlign.CENTER, 0) //higher order priority
	});
}
