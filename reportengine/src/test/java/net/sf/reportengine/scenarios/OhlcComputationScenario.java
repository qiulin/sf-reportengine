/**
 * 
 */
package net.sf.reportengine.scenarios;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.calc.Calculators;
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
		new DefaultDataColumn("Time",1, Calculators.FIRST),
		
		new DefaultDataColumn("Volume",2, Calculators.SUM),
		
		new DefaultDataColumn("Open",3, Calculators.FIRST),
		new DefaultDataColumn("High",4, Calculators.MAX),
		new DefaultDataColumn("Low",5, 	Calculators.MIN),
		new DefaultDataColumn("Close",6, Calculators.LAST)
	});
	
	public static final List<GroupColumn> GROUPING_COLUMNS = Arrays.asList(
	new GroupColumn[] {
		new DefaultGroupColumn("Date", 0,0)
	});
}
