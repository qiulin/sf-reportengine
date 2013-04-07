/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import java.io.IOException;
import java.io.Writer;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *  Algorithm step that caches all data 
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @version $Revision$
 * $log$
 */
public class DataCachingStep implements AlgorithmMainStep {
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DataCachingStep.class);
	
    /**
     * the cache writer
     */
    private Writer cache;
    
    /**
     * separator used inside the cache
     */
    private String separator;
    
    /**
     * 
     * @param cacheWriter
     * @param separator
     */
    public DataCachingStep(Writer cacheWriter, String separator){
        this.cache = cacheWriter;
        this.separator = separator;
    }
    
    /**
     * empty implementation
     */
    public void init(ReportContext reportContext) {} 
    
    /**
     * execute 
     */
    public void execute(NewRowEvent newRowEvent) {
        try {
            Object[] dataRow = newRowEvent.getInputDataRow();
            for(int i = 0; i < dataRow.length -1 ; i++){
                //logger.debug("writing "+dataRow[i]+" to cache");
                cache.write(dataRow[i].toString() + separator);
            }
            //write the last one
            cache.write(dataRow[dataRow.length-1].toString()+System.getProperty("line.separator"));
        } catch (IOException e) {
        	LOGGER.error("exception when writing into cache", e);
            throw new RuntimeException(e);
        }
    }
    
      
    
    /**
     * closes the cache writer
     */
    public void exit() {
        try{
            cache.close();
        }catch(IOException ioExc){
            LOGGER.error("exception when closing the cache", ioExc);
            throw new RuntimeException(ioExc);
        }
    }
}