/**
 * 
 */
package net.sf.reportengine.core.calc;


/**
 * The intermediate result for group calculators. 
 * This is usually an immutable class holding the values needed for the group calculators. 
 * 
 * @author dragos balan
 *
 */
public interface CalcIntermResult<E>{
	
	/**
	 * returns the result stored inside.
	 * @return
	 */
	public E getResult(); 
}
