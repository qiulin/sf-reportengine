/*
 * Created on 05.04.2005
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;


/**
 * sum calculator implementation 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public class SumNumberCalculator extends AbstractInitializedCalculator<BigDecimal, Number> {
    
    /**
	 * serial version
	 */
	private static final long serialVersionUID = -1880382251596724984L;

    /**
     * if the new value is not null then we add the new value to the old one 
     * otherwise nothing 
     * this method doesn't thow NullPointerException if the newValue is null 
     */
    public void compute(Number newValue) {
        if(newValue != null){
            setValue(getResult().add(new BigDecimal(newValue.doubleValue())));
        }else{
            System.err.println("skipping null value ");
        }
    }

	@Override
	protected BigDecimal getInitValue() {
		return BigDecimal.ZERO;
	}
}
