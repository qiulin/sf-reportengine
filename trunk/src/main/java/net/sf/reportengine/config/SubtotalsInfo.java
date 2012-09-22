/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.calc.ICalculator;

/**
 * 
 * @author dragos
 * @since 0.3
 */
public class SubtotalsInfo {
	
	public static final int GRAND_TOTAL_GROUPING_LEVEL = -1;
	
	private int groupingLevel;
	
	private IAlgorithmContext context;
	
	private ICalculator[] groupingCalculators;
	
	private int[] totalColumnIndexes;
	
	private Object[] previousRowValues;
	
	private int[] groupColumnsIndex;
	
	public SubtotalsInfo(){
		
	}

	/**
	 * @return the groupingLevel
	 */
	public int getGroupingLevel() {
		return groupingLevel;
	}

	/**
	 * @param groupingLevel the groupingLevel to set
	 */
	public void setGroupingLevel(int groupingLevel) {
		this.groupingLevel = groupingLevel;
	}

	/**
	 * @return the context
	 */
	public IAlgorithmContext getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(IAlgorithmContext context) {
		this.context = context;
	}

	/**
	 * @return the groupingCalculators
	 */
	public ICalculator[] getGroupingCalculators() {
		return groupingCalculators;
	}

	/**
	 * @param groupingCalculators the groupingCalculators to set
	 */
	public void setGroupingCalculators(ICalculator[] groupingCalculators) {
		this.groupingCalculators = groupingCalculators;
	}

	/**
	 * @return the totalColumnIndexes
	 */
	public int[] getTotalColumnIndexes() {
		return totalColumnIndexes;
	}

	/**
	 * @param totalColumnIndexes the totalColumnIndexes to set
	 */
	public void setTotalColumnIndexes(int[] totalColumnIndexes) {
		this.totalColumnIndexes = totalColumnIndexes;
	}

	/**
	 * @return the previousRowValues
	 */
	public Object[] getPreviousRowValues() {
		return previousRowValues;
	}

	/**
	 * @param previousRowValues the previousRowValues to set
	 */
	public void setPreviousRowValues(Object[] previousRowValues) {
		this.previousRowValues = previousRowValues;
	}

	/**
	 * @return the groupColumnsIndex
	 */
	public int[] getGroupColumnsIndex() {
		return groupColumnsIndex;
	}

	/**
	 * @param groupColumnsIndex the groupColumnsIndex to set
	 */
	public void setGroupColumnsIndex(int[] groupColumnsIndex) {
		this.groupColumnsIndex = groupColumnsIndex;
	}
	
	
}
