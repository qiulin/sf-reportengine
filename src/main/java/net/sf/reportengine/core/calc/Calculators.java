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
    public static final ICalculatorsFactory SUM = new SumCalculatorsFactory();
    
    /**
     * 
     */
    public static final ICalculatorsFactory COUNT = new CountCalculatorsFactory();
    
    /**
     * 
     */
    public static final ICalculatorsFactory AVG = new AvgCalculatorsFactory();
    
    /**
     * 
     */
    public static final ICalculatorsFactory MIN = new MinCalculatorsFactory();
    
    /**
     * 
     */
    public static final ICalculatorsFactory MAX = new MaxCalculatorsFactory();
    
    /**
     * 
     */
    public static final ICalculatorsFactory FIRST = new FirstCalculatorsFactory();
    
    /**
     * 
     */
    public static final ICalculatorsFactory LAST = new LastCalculatorsFactory();
    
    private Calculators(){
        //dummy impl just to prevent inheritance
    }
    
    
}
