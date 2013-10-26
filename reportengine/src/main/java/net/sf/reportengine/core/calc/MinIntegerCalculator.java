/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * @author dragos
 *
 */
public class MinIntegerCalculator extends AbstractMinCalculator<Integer> {

	/**
	 * the serial version id
	 */
	private static final long serialVersionUID = -1500686524913700683L;

	@Override
	protected Integer getInitValue() {
		return Integer.MAX_VALUE; 
	}

	

}
