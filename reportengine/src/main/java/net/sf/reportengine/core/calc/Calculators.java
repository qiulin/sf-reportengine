/*
 * Created on 08.02.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

/**
 * utility collection of calculators 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public final class Calculators {
    
    /**
     * 
     */
    public static final Calculator<BigDecimal, Object> SUM = new UniversalSumCalculator();
    
    /**
     * 
     */
    public static final Calculator<Integer, Object> COUNT = new CountCalculator();
    
    /**
     * 
     */
    public static final Calculator<BigDecimal, Object> AVG = new UniversalAvgCalculator();
    
    /**
     * 
     */
    public static final Calculator<BigDecimal, Object> MIN = new UniversalMinCalculator();
    
    /**
     * 
     */
    public static final Calculator<BigDecimal, Object> MAX = new UniversalMaxCalculator();
    
    /**
     * 
     */
    public static final Calculator<Object, Object> FIRST = new FirstCalculator<Object>();
    
    /**
     * 
     */
    public static final Calculator<Object, Object> LAST = new LastCalculator<Object>();
    
    /**
     * just to prevent inheritance
     */
    private Calculators(){
    }
}
