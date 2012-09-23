/**
 * 
 */
package net.sf.reportengine.scenarios;

import net.sf.reportengine.config.DefaultColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.IColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.algorithm.IAlgorithmInput;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.in.StreamReportInput;

/**
 * @author dragos
 *
 */
public class OhlcComputationScenario {
	
	public static final IAlgorithmInput INPUT = new StreamReportInput(OhlcComputationScenario.class.getClassLoader().getResourceAsStream(""),"\t");
	
	public static final IColumn[] CONFIG_COLUMNS = new IColumn[]{
		new DefaultColumn("Date", 0, 0),
		new DefaultColumn("Time",1, DefaultColumn.NO_GROUP_COLUMN, Calculator.FIRST),
		new DefaultColumn("Open",2, DefaultColumn.NO_GROUP_COLUMN, Calculator.FIRST),
		new DefaultColumn("High",3, DefaultColumn.NO_GROUP_COLUMN, Calculator.MAX),
		new DefaultColumn("Low",4, 	DefaultColumn.NO_GROUP_COLUMN, Calculator.MIN),
		new DefaultColumn("Close",5, DefaultColumn.NO_GROUP_COLUMN, Calculator.LAST),
		new DefaultColumn("Volume",6, DefaultColumn.NO_GROUP_COLUMN, Calculator.SUM)
	};
	
	public static final IDataColumn[] DATA_COLUMNS = new IDataColumn[]{
		new DefaultDataColumn("Time",1, Calculator.FIRST),
		new DefaultDataColumn("Open",2, Calculator.FIRST),
		new DefaultDataColumn("High",3, Calculator.MAX),
		new DefaultDataColumn("Low",4, 	Calculator.MIN),
		new DefaultDataColumn("Close",5, Calculator.LAST),
		new DefaultDataColumn("Volume",6, Calculator.SUM)
	};
	
	public static final IGroupColumn[] GROUP_COLUMNS = new IGroupColumn[]{
		new DefaultGroupColumn("Date", 0,0)
	};
}
