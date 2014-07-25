/**
 * 
 */
package net.sf.reportengine.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.core.calc.GroupCalculator;

/**
 * @author dragos balan
 *
 */
public class CalculatorIntermResultsMatrix {
	
    /**
     * matrix of calculators 
     * Each row represents a level 
     */
    private CalcIntermResult[][] intermResultsMatrix;
	
    
    /**
     * the calculator prototypes
     */
    private List<GroupCalculator> calculatorPrototypes;  
    
    
    /**
     * 
     */
    private List<DataColumn> dataColumnsHavingCalculators; 
    
    /**
     * 
     */
    private List<Integer> indexOfColumnsHavingCalculators; 
    
    /**
     * constructor
     * @param levelCount    defines how many levels (rows) we will have 
     * @param calculatorFactories   a prototype row of calculators
     */
    public CalculatorIntermResultsMatrix(int rowCount, List<DataColumn> dataColumns){
    	extractCalculatorsData(dataColumns);
    	this.intermResultsMatrix = new CalcIntermResult[rowCount][calculatorPrototypes.size()];
    }
    
    /**
     * extracts from dataColumns the following info: 
     * 	1.calculators, 
     * 	2.dataColumnsHavingCalculators 
     *  3.index of dataColumns having calculators 
     *  
     * @param columns	data columns
     */
    private void extractCalculatorsData(List<DataColumn> columns){
    	
    	calculatorPrototypes = new ArrayList<GroupCalculator>();
    	dataColumnsHavingCalculators = new ArrayList<DataColumn>();
    	indexOfColumnsHavingCalculators = new ArrayList<Integer>();
    	
    	//prepare also final value for calculatorsDistributionInDataColumnsArray
    	for(int i=0; i<columns.size(); i++){
    		GroupCalculator calcPrototype = columns.get(i).getCalculator();
    		if(calcPrototype != null){
    			calculatorPrototypes.add(calcPrototype);
    			dataColumnsHavingCalculators.add(columns.get(i));
    			indexOfColumnsHavingCalculators.add(Integer.valueOf(i)); 
    		}
    	}
    }
    
    
    /**
     * calls the init method for each calculator on the specified level
     * 
     * @param level
     */
    public void initRow(int row){
        for (int i = 0; i < calculatorPrototypes.size(); i++) {
            intermResultsMatrix[row][i] = calculatorPrototypes.get(i).init();
        }
    }
    
    /**
     * calls the init method for all calculators in the first specified rows
     * @param rowCount
     */
    public void initFirstXRows(int rowCount){
        for(int i= 0; i < rowCount; i++){
           initRow(i);
        }
    }
    
    /**
     * calls the init method of all calculators in this matrix
     */
    public void initAll(){
        for(int i=0; i< intermResultsMatrix.length; i++){
            initRow(i);
        }
    }
    
    public CalcIntermResult[][] getIntermResultsMatrix(){
    	return intermResultsMatrix; 
    }
    
    List<GroupCalculator> getCalculatorPrototypes(){
    	return this.calculatorPrototypes; 
    }
    
    List<DataColumn> getDataColumnsHavingCalculators(){
    	return this.dataColumnsHavingCalculators; 
    }
    
    List<Integer> getIndexOfColumnsHavingCalculators(){
    	return this.indexOfColumnsHavingCalculators; 
    }
    
    public void addValuesToEachRow(NewRowEvent newRowEvent){
    	for(int i= 0; i < intermResultsMatrix.length; i++){
            for(int j = 0; j < intermResultsMatrix[i].length; j++){
            	intermResultsMatrix[i][j] = calculatorPrototypes.get(j).compute(intermResultsMatrix[i][j], 
            			dataColumnsHavingCalculators.get(j).getValue(newRowEvent));
            }
        }
    }
}
