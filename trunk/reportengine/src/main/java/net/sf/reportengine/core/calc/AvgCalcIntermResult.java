/**
 * 
 */
package net.sf.reportengine.core.calc;



/**
 * @author dragos balan
 *
 */
public class AvgCalcIntermResult<T> extends DefaultCalcIntermResult<T>{
	
	private final int elementsCount; 
	
	public AvgCalcIntermResult(T initValue, int elementsCount){
		super(initValue); 
		this.elementsCount = elementsCount; 
	}
	
	public int getCount(){
		return elementsCount; 
	}
}
