/*
 * Created on 28.09.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;

/**
 * 
 * @author dragos
 * 
 *@deprecated not useful anymore ( to be deleted when all test passed)
 */
public class DefaultConfigInitStep implements IAlgorithmInitStep {
    
    /**
     * context key (identifier) for x totals flag 
     */
    public static final String KEY_X_TOTALS_FLAG = "net.sf.reportengine.xTotalsFlag";
    
    /**
     * see above 
     */
    private final Boolean hasXTotals;
    
    public DefaultConfigInitStep(boolean hasXTotals){
        this.hasXTotals = Boolean.valueOf(hasXTotals);
    }

    public void init(IAlgorithmContext reportContext) {
        reportContext.set(KEY_X_TOTALS_FLAG, hasXTotals);
    }

}
