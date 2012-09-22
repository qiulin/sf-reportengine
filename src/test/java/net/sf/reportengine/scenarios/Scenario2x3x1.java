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
import net.sf.reportengine.core.algorithm.IAlgorithmInput;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.in.StreamReportInput;

/**
 * @author dragos
 *
 */
public class Scenario2x3x1 {
	
	public static final IAlgorithmInput INPUT = new StreamReportInput(Scenario2x3x1.class.getClassLoader().getResourceAsStream("2x3x1.txt"),",");
	
	public static final IColumn[] CONFIG_COLUMNS = new IColumn[]{
			new DefaultColumn("Continent", 0, 0),
			new DefaultColumn("Direction", 1, 1),
			new DefaultColumn("Country", 2, 2),
			new DefaultColumn("Sex", 3),
			new DefaultColumn("Age", 4),
			new DefaultColumn("Count", 5, -1, Calculator.SUM)
	};
	
	public static final IGroupingColumn[] GROUP_COLUMNS = new IGroupingColumn[]{
		new DefaultGroupingColumn("Continent", 0, 0),
		new DefaultGroupingColumn("Direction", 1, 1),
		new DefaultGroupingColumn("Country", 2, 2),

	};
	
	public static final IDataColumn[] DATA_COLUMNS = new IDataColumn[]{
		new DefaultDataColumn("Sex", 3),
		new DefaultDataColumn("Age", 4),
		new DefaultDataColumn("Count", 5, Calculator.SUM)
	};
	
}
