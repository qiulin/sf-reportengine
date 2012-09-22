/*
 * Created on 04.12.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps.ct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import net.sf.reportengine.config.ICtColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;

import org.apache.log4j.Logger;

/**
 * <p>
 *      Searches for distinct values. 
 *      Please note that the final result will be available only after calling exit() method. 
 * </p> 
 * 
 * @author dragos balan(dragos dot balan at gmail dot com)
 * @since 0.1
 */
public class DistinctValuesDetectorStep extends AbstractReportStep{
    
    
    /**
     * the one and only logger
     */
    private static final Logger logger = Logger.getLogger(DistinctValuesDetectorStep.class);
    
    /**
     * the context key for detected distinct values
     */
    public static final String CONTEXT_KEY_HEADER_DISTINCT_VALUES = "net.sf.reportengine.ct.DistinctValues";
    
   
    /**
     * array of treeSets. For each header column a TreeSet containing the 
     * distinct values is maintained. The length of the array is equal to 
     * the number of columns for which you need to detect the distinct values.
     */
    private ArrayList<TreeSet<String>> distinctValuesAsSet;
    
    
    /**
     * default constructor
     */
    public DistinctValuesDetectorStep(){
        
    }
    
    
    /**
     * @see <code>IAlgorithmInitStep.init(IAlgorithmContext)</code> 
     */
    public void init(IAlgorithmContext algoContext){
        super.init(algoContext);
        
        ICtColumn[] headerColumns = getCtHeaderColumns();
        
        if(logger.isDebugEnabled()){
            logger.debug("init detector for distinct values on columns : "+Arrays.toString(headerColumns));
        }
        
        distinctValuesAsSet = new ArrayList<TreeSet<String>>(headerColumns.length);
        for (int i = 0; i < headerColumns.length; i++) {
            distinctValuesAsSet.add(i, new TreeSet<String>());
        }
    }
    
      
    /**
     * this step's execute method
     */
    public void execute(NewRowEvent rowEvent){
        ICtColumn[] headerColumns = (ICtColumn[])getCtHeaderColumns();
		for(int i=0; i<headerColumns.length; i++){
			distinctValuesAsSet.get(i).add(headerColumns[i].getFormattedValue(rowEvent));
		}
    }
    
    /**
     * 
     */
    public void exit(){
        
        //construct the distinct values as array of strings
        String[][] distinctValues = new String[distinctValuesAsSet.size()][]; 
        for (int i = 0; i < distinctValuesAsSet.size(); i++) {
            distinctValues[i] = new String[distinctValuesAsSet.get(i).size()];
            
            //distinctValues[i] = (String[])distinctValuesAsSet[i].toArray(new String[]{});
            distinctValuesAsSet.get(i).toArray(distinctValues[i]);
        }
        
        // add distinct values to the report context
        getContext().set(CONTEXT_KEY_HEADER_DISTINCT_VALUES, distinctValues);        
    }
    
}
