/**
 * 
 */
package net.sf.reportengine.scenarios;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.in.StreamReportInput;

/**
 * @author dragos
 *
 */
public class Scenario2x3x1 {
	
	public static final IReportInput INPUT = new StreamReportInput(Scenario2x3x1.class.getClassLoader().getResourceAsStream("2x3x1.txt"),",");
	
	public static final IGroupColumn[] GROUP_COLUMNS = new IGroupColumn[]{
		new DefaultGroupColumn("Continent", 0, 0),
		new DefaultGroupColumn("Direction", 1, 1),
		new DefaultGroupColumn("Country", 2, 2),

	};
	
	public static final IDataColumn[] DATA_COLUMNS = new IDataColumn[]{
		new DefaultDataColumn("Sex", 3),
		new DefaultDataColumn("Age", 4),
		new DefaultDataColumn("Count", 5, Calculators.SUM)
	};
	
}
