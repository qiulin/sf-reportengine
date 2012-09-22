/*
 * Created on 28.09.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps.ct;

import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.steps.DefaultConfigInitStep;

/**
 * 
 * @author Administrator
 * @deprecated : to be deleted when all crosstab tests succeed
 */
public class CTConfigInitStep extends DefaultConfigInitStep {    
    
    /**
     * context key (identifier) for y totals flag 
     */
    public static final String KEY_Y_TOTALS_FLAG = "net.sf.reportengine.yTotalsFlag";
    
    /**
     * boolean value holder for x totals flag
     */
    private final Boolean hasYTotals;
    
    /**
     * context key (identifier) for x elements count 
     */
    public static final String KEY_X_ELEMENTS_COUNT = "net.sf.reportengine.xElementsCount";
    public final Integer xElementsCount;
    
    /**
     * key for x elements
     */
    public static final String KEY_X_ELEMENTS = "net.sf.reportengine.xElements";
    public int[] xElements ;
    
    /**
     * context key (identifier) for y elements count
     */
    public static final String KEY_Y_ELEMENTS_COUNT = "net.sf.reportengine.yElementsCount";
    public final Integer yElementsCount;
    
    /**
     * key for y elements
     */
    public static final String KEY_Y_ELEMENTS = "net.sf.reportengine.yElements";
    public int[] yElements ;
    
    /**
     * context key (identifier) for data elements count
     */
    public static final String KEY_DATA_ELEMENTS_COUNT = "net.sf.reportengine.dataElementsCount";
    public final Integer dataElementsCount;
    
    
    /**
     * configuration init step
     * @param hasXTotals
     * @param hasYTotals
     */
    public CTConfigInitStep(int[] xElements, 
                            int[] yElements,
                            int dataElementsCount, 
                            boolean hasXTotals, 
                            boolean hasYTotals){
        super(hasXTotals);
        this.hasYTotals = Boolean.valueOf(hasYTotals);
        this.xElements = xElements;
        this.yElements = yElements;
        this.dataElementsCount = Integer.valueOf(dataElementsCount);
        this.xElementsCount = Integer.valueOf(this.xElements.length);
        this.yElementsCount = Integer.valueOf(this.yElements.length);
    }
    
    /**
     * 
     */
    public void init(IAlgorithmContext reportContext){
        super.init(reportContext);
        
        reportContext.set(KEY_X_ELEMENTS_COUNT, xElementsCount);
        reportContext.set(KEY_Y_ELEMENTS_COUNT, yElementsCount);
        reportContext.set(KEY_DATA_ELEMENTS_COUNT, dataElementsCount);
        reportContext.set(KEY_Y_TOTALS_FLAG, hasYTotals);
        
        reportContext.set(KEY_X_ELEMENTS, xElements);
        reportContext.set(KEY_Y_ELEMENTS,yElements);
        
    }

}
