/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * This group calculator always stores the last value passed to the {@link #compute(DefaultCalcIntermResult, Object)} method
 * 
 * @author dragos balan
 */
public class LastGroupCalculator<T> extends AbstractGroupCalculator<T, DefaultCalcIntermResult<T>, T> {

	/**
	 * the default label
	 */
	public static final String LABEL = "Last"; 
	
	/**
	 * builds this group calculator with the default label
	 */
	public LastGroupCalculator(){
		this(LABEL); 
	}
	
	/**
	 * builds this group calculator with the custom label 
	 * @param label	the label
	 */
	public LastGroupCalculator(String label){
		super(label); 
	}
	
	/**
	 * returns an empty intermediate calculator result ( empty means that it contains a null value)
	 */
	public DefaultCalcIntermResult<T> init() {
		//TODO come back here when java 8 will be the target platform
		//and add an optional inside DefaultCalcIntermResult
		return new DefaultCalcIntermResult<T>(null); 
	}
	
	/**
	 * stores the last value passes to this method as @param newValue
	 * 
	 * @param intermResult	the previous result
	 * @param newValue		the new value	
	 */
	public DefaultCalcIntermResult<T> compute(
			DefaultCalcIntermResult<T> intermResult, T newValue) {
		return new DefaultCalcIntermResult<T>(newValue);
	}


}
