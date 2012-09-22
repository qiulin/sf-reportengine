/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * @author dragos dragos(dragos.balan@gmail.com)
 *
 */
public abstract class AbstractCalculator implements ICalculator {

	

	/**
     * just a simple call to Object.clone()
     */
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
