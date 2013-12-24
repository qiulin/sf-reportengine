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
public final class GroupCalculators {
    
    /**
     * 
     */
    public static final GroupCalculator<BigDecimal, Object> SUM = new UniversalSumGroupCalculator();
    
    /**
     * 
     */
    public static final GroupCalculator<Integer, Object> COUNT = new CountGroupCalculator();
    
    /**
     * 
     */
    public static final GroupCalculator<BigDecimal, Object> AVG = new UniversalAvgGroupCalculator();
    
    /**
     * 
     */
    public static final GroupCalculator<BigDecimal, Object> MIN = new UniversalMinGroupCalculator();
    
    /**
     * 
     */
    public static final GroupCalculator<BigDecimal, Object> MAX = new UniversalMaxGroupCalculator();
    
    /**
     * 
     */
    public static final GroupCalculator<Object, Object> FIRST = new FirstGroupCalculator<Object>();
    
    /**
     * 
     */
    public static final GroupCalculator<Object, Object> LAST = new LastGroupCalculator<Object>();
    
    /**
     * just to prevent inheritance
     */
    private GroupCalculators(){
    }
}
