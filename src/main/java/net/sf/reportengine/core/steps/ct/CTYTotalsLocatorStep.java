/*
 * Created on 27.09.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps.ct;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.util.ReportEngineArrayUtils;
import net.sf.reportengine.util.CalculatorMatrix;
import net.sf.reportengine.util.Locator;
import net.sf.reportengine.util.MatrixUtils;

/**
 * 
 * @author dragos balan
 *
 */
public class CTYTotalsLocatorStep extends AbstractCTAlgorithmStep {
    
    private Object[] dataToBeOutputted;
    
    private Object[] previousDataRow;
    
    private Locator locator;
    
     
    
    /**
     * 
     */
    public void postInit(){
        //super.postInit();
        locator = (Locator)getContext().get(CTLocatorStep.KEY_LOCATOR_CACHE);
        assert locator != null : "the locator should be not null";
    }
    
    /**
     * 
     */
    public void execute(NewRowEvent rowEvent) {
        ICalculator[][] levelCalculators = getCalculatorMatrix();
        
        /** @todo we assume that data elements are the latest */
        if(getGroupingLevel() > 0 /*getDataElementsCount()*/){
            constructAndlocate( getGroupingLevel(), 
                                previousDataRow.length, 
                                levelCalculators);    
        }
        previousDataRow = rowEvent.getInputDataRow();
    }
    
    
    /**
     * 
     */
    public void exit(){
        ICalculator[][] levelCalculators = getCalculatorMatrix();
        //maybe the first argument is not the best choice
        constructAndlocate( getDataElementsCount() + getXElementsCount(),
                            previousDataRow.length, 
                            levelCalculators);
    }
    
    
    /**
     * contructs a temporary row (containing the row header) 
     * which will be located using the locator step 
     * @param level             the level
     * @param dataRowLength     current data row
     * @param levelCalculators  the calculators
     */
    private void constructAndlocate(int level, 
                                    int dataRowLength, 
                                    ICalculator[][] levelCalculators){
        
        for (int i = 1; i <= level && i <= getXElementsCount() ; i++) {
            //first construct an array of objects having the length of a normal data row
            dataToBeOutputted = new Object[dataRowLength];
            
            //then copy the first values 
            System.arraycopy(previousDataRow, 0, dataToBeOutputted, 0, dataRowLength);
            
            //put the word "total" 
            dataToBeOutputted[dataRowLength - i - getDataElementsCount()] = Locator.TOTAL; // counting from the end
            
            //for each column containing data we add the total 
            for(int dataColumn = 0; dataColumn < getDataElementsCount(); dataColumn++){
                dataToBeOutputted[dataRowLength - getDataElementsCount() + dataColumn] 
                                  = levelCalculators[i+1][dataColumn].getResult();
            }
            
            //ArrayUtils.logArray("dataToBeOutputted",dataToBeOutputted, System.err);            
            //MatrixUtils.logCalculatorsResults(levelCalculators, System.err);
            
            locator.searchLineWithTotals(dataToBeOutputted, getYElementsCount());
        }
    }
    
}
