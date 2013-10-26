package net.sf.reportengine.core.calc;


/**
 * this is just a holder of the last value passed to {@link #compute(Object)} therefore 
 * it will always return the last value passed 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class LastCalculator<T> extends AbstractCalculator<T, T> {
	
	/**
	 * the serial version id
	 */
	private static final long serialVersionUID = -8775227212750608867L;
	
	/**
	 * keeps the value passed as parameter for later usage
	 */
	public void compute(T value) {
		setValue(value);
	}
	
	/**
	 * empty implementation of #Calculator.init()
	 */
	public void init() {}
}
