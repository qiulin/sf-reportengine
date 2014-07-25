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
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.calc.SumGroupCalculator;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.in.TextInput;

/**
 * @author dragos
 *
 */
public class Scenario2x3x1 {
	
	public static final ReportInput INPUT = new TextInput(Scenario2x3x1.class.getClassLoader().getResourceAsStream("2x3x1.txt"),",");
	
	public static final List<GroupColumn> GROUP_COLUMNS = Arrays.asList(
		new GroupColumn[]{
				new DefaultGroupColumn("Continent", 0, 0),
				new DefaultGroupColumn("Direction", 1, 1),
				new DefaultGroupColumn("Country", 2, 2)
	});
	
	public static final List<DataColumn> DATA_COLUMNS = Arrays.asList(
		new DataColumn[]{
				new DefaultDataColumn("Sex", 3),
				new DefaultDataColumn("Age", 4),
				new DefaultDataColumn("Count", 5, new SumGroupCalculator())
	});
	
}
