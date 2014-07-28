/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * This calculator will return the first value passed after calling init. 
 * No matter how many times you call {@link #compute(FirstCalcIntermResult, Object)} it will always 
 * return the first value
 * 
 * @author dragos balan
 */
public class FirstGroupCalculator<T> extends AbstractGroupCalculator<T, FirstCalcIntermResult<T>, T> {
	
	/**
	 * the serial version id
	 */
	private static final long serialVersionUID = -2380332591835262973L;
	
	/**
	 * the default label
	 */
	public static final String LABEL = "First"; 
	
	/**
	 * builds this group calculator with the default label
	 */
	public FirstGroupCalculator(){
		this(LABEL); 
	}
	
	/**
	 * builds this group calculator with a custom label
	 * @param label	the label
	 */
	public FirstGroupCalculator(String label){
		super(label); 
	}
	
	/**
	 * returns an empty (null) element with the isFirst flag = true
	 */
	public FirstCalcIntermResult<T> init() {
		return new FirstCalcIntermResult<T>(null, true);
	}

	/**
	 * if the previous result is first then it stores the new value inside the intermediate result. 
	 * otherwise it returns the previous intermediate result. 
	 */
	public FirstCalcIntermResult<T> compute(FirstCalcIntermResult<T> intermResult, T newValue) {
		FirstCalcIntermResult<T> result = intermResult; 
		if(intermResult.isFirst()){
			result = new FirstCalcIntermResult<T>(newValue, false); 
		}
		return result;
	}
}
