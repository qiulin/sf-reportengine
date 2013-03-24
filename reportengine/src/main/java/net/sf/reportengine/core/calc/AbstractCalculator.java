/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * abstract implementation for Calculator interface
 * 
 * @author dragos dragos (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public abstract class AbstractCalculator implements Calculator {

	

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 955527292301539906L;
	
	/**
	 * deep copy of this object
	 */
	public Calculator clone(){
		Calculator result = null; 
		try {
			result = (Calculator)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CalculatorException("Cloning is not supported", e);
		}
		return result; 
	}
}
