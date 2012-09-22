/*
 * Created on 11.09.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps.ct;

import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.util.CrossTabCoefficients;
import net.sf.reportengine.util.Locator;
import net.sf.reportengine.util.MatrixUtils;

/**
 * this step is responsible for ...
 * 
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @deprecated
 */
public class CTLocatorStep extends AbstractCTAlgorithmStep {
    
    public final static String KEY_LOCATOR_CACHE = "locator";
    
    private Locator locator;
    
    public final static String DATA = "Data";
    
    private boolean dataOnXAxis;
    private String[] dataElementNames;
    
    private int resultMatrixRowCount;
    
    public CTLocatorStep(){
        this(false);
    }
    
    public CTLocatorStep(boolean dataOnX){
        this(dataOnX, null);
    }
    
    public CTLocatorStep(boolean dataOnX, String[] dataColNames){
        this.dataOnXAxis = dataOnX;
        this.dataElementNames = dataColNames;
    }
    
    public void init(IAlgorithmContext reportContext) {
        super.init(reportContext);
        //get the template and pass it to locator
        String[][] headerTemplate = (String[][])reportContext.get(CTReportHeaderTemplateInitStep.CONTEXT_KEY_CT_HEADER_TEMPLATE);
        int[] arrJump;
        CrossTabCoefficients coeficients = null; //(CrossTabCoefficients)reportContext.get(CTReportHeaderTemplateInitStep.CONTEXT_KEY_CT_COEFICIENTS);
        Boolean totalsOnXFlag = (Boolean)reportContext.get(CTConfigInitStep.KEY_Y_TOTALS_FLAG);
        if(!totalsOnXFlag.booleanValue()){
            arrJump = coeficients.getColspanPerRow();
        }else{
            arrJump = coeficients.getColspanWhenTotals();
        }
        
        //construction of locator
        locator = new Locator(headerTemplate, arrJump);
        //init the report context
        reportContext.set(KEY_LOCATOR_CACHE, locator);
        
    }
    
    public void postInit(){
        //super.postInit();
        
        //when dataColumns should be displayed on x axis we must fool the Locator to
        //put all the values on a single row
        resultMatrixRowCount = dataOnXAxis ? 1: getDataElementsCount(); 
        locator.newResult(resultMatrixRowCount);
        
        //if data element names have not been set into the constructor then..
        if(dataElementNames == null){
            this.dataElementNames = makeDefaultNames();
        }
    }
    
    public void execute(NewRowEvent rowEvent) {
        Object[] currentRow = rowEvent.getInputDataRow();
        if(getGroupingLevel() <= getXElementsCount()){            
            if(dataOnXAxis){
                locateDataWhenDataOnX(currentRow);
            }else{
                locator.search(currentRow, getYElementsCount());
            }
        }else{
            locator.newResult(resultMatrixRowCount);
            if(dataOnXAxis){
                locateDataWhenDataOnX(currentRow);
            }else{
                locator.search(currentRow, getYElementsCount());
            }
        }
    }    

    private String[] makeDefaultNames(){
        String[] toReturn = new String[getDataElementsCount()];
        for (int i = 0; i < toReturn.length; i++) {
            toReturn[i] = DATA+i;
        }
        return toReturn;
    }
    
    private void locateDataWhenDataOnX(Object[] currentRow ){
        Object[][] linesMultiplied = MatrixUtils.multiplyLines(currentRow, dataElementNames);
        for (int i = 0; i < linesMultiplied.length; i++) {
            //ArrayUtils.logArray(linesMultiplied[i]);
            locator.search(linesMultiplied[i], getYElementsCount());
        }      
    }

}
