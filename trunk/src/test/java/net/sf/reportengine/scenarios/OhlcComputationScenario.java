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
 * @author dragos balan
 *
 */
public class OhlcComputationScenario {
	
	public static final IReportInput INPUT = new StreamReportInput(OhlcComputationScenario.class.getClassLoader().getResourceAsStream(""),"\t");
	
	public static final IDataColumn[] DATA_COLUMNS = new IDataColumn[]{
		new DefaultDataColumn("Time",1, Calculators.FIRST),
		new DefaultDataColumn("Open",2, Calculators.FIRST),
		new DefaultDataColumn("High",3, Calculators.MAX),
		new DefaultDataColumn("Low",4, 	Calculators.MIN),
		new DefaultDataColumn("Close",5, Calculators.LAST),
		new DefaultDataColumn("Volume",6, Calculators.SUM)
	};
	
	public static final IGroupColumn[] GROUP_COLUMNS = new IGroupColumn[]{
		new DefaultGroupColumn("Date", 0,0)
	};
}
