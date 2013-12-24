package net.sf.reportengine.core.calc;


/**
 * this is just a holder of the last value passed to {@link #compute(Object)} therefore 
 * it will always return the last value passed 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class LastGroupCalculator<T> extends AbstractGroupCalculator<T, T> {
	
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
	 * empty implementation of #GroupCalculator.init()
	 */
	public void init() {}
}
