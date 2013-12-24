/*
 * Created on 02.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.util;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.GroupCalculatorException;
import net.sf.reportengine.core.calc.GroupCalculator;

/**
 * this is a wrapper around a matrix of GroupCalculator which builds itself based on the 
 * prototype calculators passed as arguments in the constructor. The building process
 * is based entirely on cloning
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public class CalculatorMatrix {
    
	
	
    /**
     * matrix of calculators 
     * Each row represents a level 
     */
    private GroupCalculator[][] calculators;
    
    /**
     * the calculator prototypes
     */
    private GroupCalculator[] calculatorPrototypes;  
    
    /**
     * 
     */
    private DataColumn[] dataColumnsHavingCalculators; 
    
    /**
     * 
     */
    private int[] indexOfColumnsHavingCalculators; 
    
    

    
    /**
     * constructor
     * @param levelCount    defines how many levels (rows) we will have 
     * @param calculatorFactories   a prototype row of calculators
     */
    public CalculatorMatrix(int rowCount, List<DataColumn> dataColumns){
    	extractCalculatorsData(dataColumns);
    	this.calculators = createMultipleRows(rowCount, calculatorPrototypes);
    }
    
    
    
    
    /**
     * extracts from dataColumns the following info: 
     * 	1.calculators, 
     * 	2.dataColumnsHavinCalculators 
     *  3.index of dataColumns having calculators 
     *  
     * @param columns	data columns
     */
    protected void extractCalculatorsData(List<DataColumn> columns){
    	
    	//prepare the temporary values 
    	GroupCalculator[] tempCalcPrototypes = new GroupCalculator[columns.size()];
    	DataColumn[] tempDataCols = new DataColumn[columns.size()];
    	int[] tempColIndex = new int[columns.size()];
    	
    	//prepare also final value for calculatorsDistributionInDataColumnsArray
    	
    	int columnWithCalculatorsCount = 0;
    	for(int i=0; i<columns.size(); i++){
    		GroupCalculator calcPrototype = columns.get(i).getCalculator();
    		if(calcPrototype != null){
    			tempCalcPrototypes[columnWithCalculatorsCount] = calcPrototype;
    			tempDataCols[columnWithCalculatorsCount] = columns.get(i);
    			tempColIndex[columnWithCalculatorsCount] = i; 
    			columnWithCalculatorsCount++;
    		}
    	}
    	
    	//prepare the results    	
    	calculatorPrototypes = new GroupCalculator[columnWithCalculatorsCount];
    	dataColumnsHavingCalculators = new DataColumn[columnWithCalculatorsCount];
    	indexOfColumnsHavingCalculators = new int[columnWithCalculatorsCount];
    	
    	//copy the temporary values to final destination
    	System.arraycopy(tempCalcPrototypes, 0, calculatorPrototypes, 0, columnWithCalculatorsCount);
    	System.arraycopy(tempDataCols, 0, dataColumnsHavingCalculators, 0, columnWithCalculatorsCount);
    	System.arraycopy(tempColIndex, 0, indexOfColumnsHavingCalculators, 0, columnWithCalculatorsCount);
    }
    
    
    /**
     * clones the given prototypes and creates a matrix with rowCount rows and prototypes.length columns
     * 
     * @param rowCount
     * @param calcFactories
     * @return
     */
    private GroupCalculator[][] createMultipleRows(int rowCount, GroupCalculator[] calcPrototypes ){
        GroupCalculator[][] result = new GroupCalculator[rowCount][calcPrototypes.length];
    	for(int i=0; i< rowCount; i++){
    		result[i] = new GroupCalculator[calcPrototypes.length];
    		for (int j = 0; j < calcPrototypes.length; j++) {
    			result[i][j] = (GroupCalculator)calcPrototypes[j].clone();
    		}
    	}
        return result;
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
     * calls the compute method for all calculators in the matrix and passes as argument the 
     * values in the given columns
     * 
     * @param values
     * @param columnsToCompute
     * @deprecated use addValuesToEachRow(NewRowEvent)
     */
    public void addValuesToEachRow(Object[] values, int[] columnsIndexes)
    throws GroupCalculatorException{
        for(int i= 0; i < calculators.length; i++){
            for(int j = 0; j < calculators[i].length; j++){
                calculators[i][j].compute(values[columnsIndexes[j]]);
            }
        }
    }
    
    
    /**
     * calls the compute method for all calculators in the matrix and passes as argument the 
     * values in the given values. 
     * A correct call to this method will be one having values.length = calculators.length
     * 
     * @param values
     * @param columnsToCompute
     * @deprecated use addValuesToEachRow(NewRowEvent)
     */
    public void addValuesToEachRow(Object[] values) {
        for(int i= 0; i < calculators.length; i++){
            for(int j = 0; j < calculators[i].length; j++){
                calculators[i][j].compute(values[j]);
            }
        }
    }
    
    
    public void addValuesToEachRow(NewRowEvent newRowEvent){
    	for(int i= 0; i < calculators.length; i++){
            for(int j = 0; j < calculators[i].length; j++){
                calculators[i][j].compute(dataColumnsHavingCalculators[j].getValue(newRowEvent));
            }
        }
    }
    
    /**
     * returns the requested row of calculators
     * @param row
     * @return
     */
    public GroupCalculator[] getRow(int row){
        return calculators[row];
    }
    
      
    /**
     * gets the ICalculators matrix used by this helper class
     * @return
     */
    public GroupCalculator[][] getCalculators(){
        return calculators;
    }
    
    /**
     * calls the init method for each calculator on the specified level
     * 
     * @param level
     */
    public void initRow(int row){
        GroupCalculator[] calcs = getRow(row);
        for (int i = 0; i < calcs.length; i++) {
            calcs[i].init();
        }
    }
    
    /**
     * calls the init method of all calculators in this matrix
     */
    public void initAllCalculators(){
        for(int i=0; i< calculators.length; i++){
            initRow(i);
        }
    }
    
    /**
     * getter for the row count 
     * @return
     */
    public int getRowCount(){
    	return calculators.length;
    }
    
    
    /**
     * extracts the result for each calculator in the matrix
     * and creates a matrix with results
     * 
     * @return
     */
    public Object[][] exportResults(){
    	Object[][] results = new Object[calculators.length][];
    	for(int i=0; i<calculators.length; i++){
    		GroupCalculator[] row = calculators[i];
    		results[i] = new Object[row.length];
    		for(int j=0; j<row.length; j++){
    			results[i][j] = row[j].getResult();
    		}
    	}
    	return results;
    }




	public GroupCalculator[] getCalculatorPrototypes() {
		return calculatorPrototypes;
	}




	public void setCalculatorPrototypes(GroupCalculator[] calcPrototypes) {
		this.calculatorPrototypes = calcPrototypes;
	}




	public DataColumn[] getDataColumnsHavingCalculators() {
		return dataColumnsHavingCalculators;
	}




	public void setDataColumnsHavingCalculators(
			DataColumn[] dataColumnsHavingCalculators) {
		this.dataColumnsHavingCalculators = dataColumnsHavingCalculators;
	}




	public int[] getIndexOfColumnsHavingCalculators() {
		return indexOfColumnsHavingCalculators;
	}




	public void setIndexOfColumnsHavingCalculators(
			int[] indexOfColumnsHavingCalculators) {
		this.indexOfColumnsHavingCalculators = indexOfColumnsHavingCalculators;
	}
}
