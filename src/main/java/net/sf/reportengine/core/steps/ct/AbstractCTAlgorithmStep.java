/*
 * Created on 16.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps.ct;

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;

public abstract class AbstractCTAlgorithmStep extends AbstractReportStep {
    
    private final static String ILLEGAL_STATE_MESSAGE = "Requesting values before set!. You should call init() before !";
    
    private int xElementsCount;
    private int yElementsCount;
    private int dataElementsCount;
    
    private int[] X_ELEMENTS = null;
    
    private int[] Y_ELEMENTS = null;
    
    /**
     * helper for xElementsCount, yElementsCount, dataElementsCount
     */
    private boolean valuesSet = false;
    
    public void init(IAlgorithmContext reportContext){
        super.init(reportContext);
        
        try{
            /** @todo the following statement is not right 
             * you should use this method only for registering parameters
             */
            xElementsCount = ((Integer)reportContext.get(CTConfigInitStep.KEY_X_ELEMENTS_COUNT)).intValue();
            yElementsCount = ((Integer)reportContext.get(CTConfigInitStep.KEY_Y_ELEMENTS_COUNT)).intValue();
            dataElementsCount = ((Integer)reportContext.get(CTConfigInitStep.KEY_DATA_ELEMENTS_COUNT)).intValue();
            
            valuesSet = true;
        }catch(NullPointerException ex){
            //thrown when the values are not present in the context
            throw ex;
        }
    }
    
    
    
    public int getXElementsCount(){
        if(!valuesSet)
            throw new IllegalStateException(ILLEGAL_STATE_MESSAGE);
        return xElementsCount;
    }
    
    public int getYElementsCount(){
        if(!valuesSet)
            throw new IllegalStateException(ILLEGAL_STATE_MESSAGE);
        return yElementsCount;
    }
    
    public int getDataElementsCount(){
        if(!valuesSet)
            throw new IllegalStateException(ILLEGAL_STATE_MESSAGE);
        return dataElementsCount;
    }
    
    public int[] getXElements(){
        if(!valuesSet)
            throw new IllegalStateException(ILLEGAL_STATE_MESSAGE);
        return X_ELEMENTS;
        }
    
    public int[] getYElements(){
        if(!valuesSet)
            throw new IllegalStateException(ILLEGAL_STATE_MESSAGE);
        return Y_ELEMENTS;
    }
    
}