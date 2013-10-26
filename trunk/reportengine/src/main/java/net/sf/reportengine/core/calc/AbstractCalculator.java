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
public abstract class AbstractCalculator<T, V> implements Calculator<T, V> {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 955527292301539906L;
	
	/**
	 * the return value of this calculator
	 */
	private T value; 
	
	/**
	 * 
	 */
	public AbstractCalculator(){}
	
	/**
	 * 
	 */
	public T getResult(){
        return value;
    }
	
	
	/**
	 * returns a clone of this object
	 */
	public Calculator<T, V> clone(){
		Calculator<T, V> result = null; 
		try {
			result = (Calculator<T, V>)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CalculatorException("Cloning is not supported", e);
		}
		return result; 
	}
	
	/**
	 * 
	 * @param value
	 */
	protected void setValue(T value){
		this.value = value; 
	}
	
	
	/**
     * returns the name of the class and the value
     * @return a string
     */
    public String toString(){
        String className = this.getClass().getSimpleName(); 
        return className+" [val="+value+"]";
    }    
}
