/*
 * Created on 08.02.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

/**
 * utility collection of calculators 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class Calculators {
    
    /**
     * 
     */
    public static final Calculator SUM = new SumCalculator();
    
    /**
     * 
     */
    public static final Calculator COUNT = new CountCalculator();
    
    /**
     * 
     */
    public static final Calculator AVG = new AvgCalculator();
    
    /**
     * 
     */
    public static final Calculator MIN = new MinCalculator();
    
    /**
     * 
     */
    public static final Calculator MAX = new MaxCalculator();
    
    /**
     * 
     */
    public static final Calculator FIRST = new FirstCalculator();
    
    /**
     * 
     */
    public static final Calculator LAST = new LastCalculator();
    
    
    private Calculators(){
        //dummy impl just to prevent inheritance
    }
}
