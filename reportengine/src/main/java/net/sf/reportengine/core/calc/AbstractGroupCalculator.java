/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * abstract implementation for GroupCalculator interface
 * 
 * @author dragos dragos (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public abstract class AbstractGroupCalculator<T, V> implements GroupCalculator<T, V> {

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
	public AbstractGroupCalculator(){}
	
	/**
	 * 
	 */
	public T getResult(){
        return value;
    }
	
	
	/**
	 * returns a clone of this object
	 */
	public GroupCalculator<T, V> clone(){
		GroupCalculator<T, V> result = null; 
		try {
			result = (GroupCalculator<T, V>)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new GroupCalculatorException("Cloning is not supported", e);
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
