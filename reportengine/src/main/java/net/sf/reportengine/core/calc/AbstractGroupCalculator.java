/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * This is just a holder for the label of group calculators. 
 * More properties could be added in the future. 
 * 
 * @author dragos balan
 *
 */
public abstract class AbstractGroupCalculator<R, I extends CalcIntermResult<R>, V> 
				implements GroupCalculator <R, I, V>{

	/**
	 *  serial version id
	 */
	private static final long serialVersionUID = 307245703496260662L;
	
	/**
	 * the label of the calculator
	 */
	private final String label; 
	
	/**
	 * 
	 */
	public AbstractGroupCalculator(String label) {
		this.label = label; 
	}


	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.calc.GroupCalculator#getLabel()
	 */
	public String getLabel() {
		return label; 
	}

}
