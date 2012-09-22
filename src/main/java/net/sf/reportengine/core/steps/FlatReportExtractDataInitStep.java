/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.AbstractReport;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.core.calc.ICalculator;

/**
 * @author Administrator
 *
 */
public class FlatReportExtractDataInitStep implements IAlgorithmInitStep {
	
	public final static int NO_CALCULATOR_ON_THIS_POSITION = -1; 
	
	public static final String CONTEXT_KEY_DISTRIBUTION_OF_CALCULATORS = "net.sf.reportengine.calcDistribution";
	
    /**
     * this array keeps track of the distribution of calculators in the dataColumns array. 
     * For example: We have a report with 6 data columns but only 3 of them have calculators (let's say the 2nd and the 4th and the 5th) 
     * In order to be able to retrieve the right calculator value for a given dataColumn index ( as set in the configuration of the report)
     * we will fill this array with 
     * 
     * array index:		0				1		2				3		4		5
     * array value: 	NO_CALCULATOR	0		NO_CALCULATOR	1		2		NO_CALCULATOR
     * 
     * Next time when we have the data column index and we need the result of the calculator (in a specific row of the matrix)
     * we will call  calculatorsDistributionInDataColumnsArray[dataColumIndex] and we will get the index of the column in the calculator matrix
     */
    private int[] calculatorsDistributionInDataColumnsArray; 
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep#init(net.sf.reportengine.core.algorithm.IAlgorithmContext)
	 */
	public void init(IAlgorithmContext reportContext) {
		IDataColumn[] dataCols = (IDataColumn[])reportContext.get(AbstractReport.CONTEXT_KEY_DATA_COLUMNS);
		calculatorsDistributionInDataColumnsArray = extractDistribution(dataCols); 
		reportContext.set(CONTEXT_KEY_DISTRIBUTION_OF_CALCULATORS, calculatorsDistributionInDataColumnsArray);
	}
	
	private int[] extractDistribution(IDataColumn[] dataCols){
		int[] result = new int[dataCols.length];
    	
    	int columnWithCalculatorsCount = 0;
    	for(int i=0; i<dataCols.length; i++){
    		ICalculator calculator = dataCols[i].getCalculator();
    		if(calculator != null){
    			result[i] = columnWithCalculatorsCount; 
    			columnWithCalculatorsCount++;
    		}else{
    			result[i] = NO_CALCULATOR_ON_THIS_POSITION; 
    		}
    		
    	}
		
		return result; 
	}

}
