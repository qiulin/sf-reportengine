/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * @author dragos balan
 *
 */
public class MinIntegerGroupCalculator extends AbstractMinGroupCalculator<Integer> {

	/**
	 * the serial version id
	 */
	private static final long serialVersionUID = -1500686524913700683L;

	@Override
	protected Integer getInitValue() {
		return Integer.MAX_VALUE; 
	}

	

}
