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
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.calc.UniversalSumGroupCalculator;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.in.TextInput;

/**
 * @author dragos balan
 *
 */
public class OhlcComputationScenario {
	
	public static final ReportInput INPUT = new TextInput(OhlcComputationScenario.class.getClassLoader().getResourceAsStream(""),"\t");
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(
	new DataColumn[]{
		new DefaultDataColumn("Time",1, GroupCalculators.FIRST),
		
		new DefaultDataColumn("Volume",2, new UniversalSumGroupCalculator()),
		
		new DefaultDataColumn("Open",3, GroupCalculators.FIRST),
		new DefaultDataColumn("High",4, GroupCalculators.MAX),
		new DefaultDataColumn("Low",5, 	GroupCalculators.MIN),
		new DefaultDataColumn("Close",6, GroupCalculators.LAST)
	});
	
	public static final List<GroupColumn> GROUPING_COLUMNS = Arrays.asList(
	new GroupColumn[] {
		new DefaultGroupColumn("Date", 0,0)
	});
}
