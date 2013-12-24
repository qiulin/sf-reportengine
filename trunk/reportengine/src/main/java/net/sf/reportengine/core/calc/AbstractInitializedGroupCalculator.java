/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractInitializedGroupCalculator<T, V> extends AbstractGroupCalculator<T, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8438556186270480835L;
	
	/**
     * initializer of the totals 
     * Example:<br>
     *  for a sum calculator the default initial value should be 0 <br>
     *  for a product calculator the default value should be 1
     */
	public void init(){
		setValue(getInitValue()); 
	}
	
	/**
	 * getter for the initial value. 
	 * @return
	 */
	protected abstract T getInitValue(); 

}
