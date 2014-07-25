/*
 * Created on 08.02.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;


/**
 * utility collection of calculators 
 * 
 * @author dragos balan 
 */
public final class GroupCalculators {
    
    /**
     * 
     */
    public static final SumGroupCalculator SUM = new SumGroupCalculator(); 
	
    /**
     * 
     */
    public static final CountGroupCalculator COUNT = new CountGroupCalculator(); 
    
    
    /**
     * 
     */
    public static final AvgGroupCalculator AVG = new AvgGroupCalculator(); 
    
    /**
     * 
     */
    public static final MinGroupCalculator MIN = new MinGroupCalculator(); 
    
    /**
     * 
     */
    public static final MaxGroupCalculator MAX = new MaxGroupCalculator(); 
    
    /**
     * 
     */
    public static final FirstGroupCalculator<Object> FIRST = new FirstGroupCalculator<Object>(); 
    
    /**
     * 
     */
    public static final LastGroupCalculator<Object> LAST = new LastGroupCalculator<Object>();
    
    /**
     * just to prevent inheritance
     */
    private GroupCalculators(){
    }
}
