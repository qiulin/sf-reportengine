/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.ReportEngineRuntimeException;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.util.ContextKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos
 *
 */
public class ExternalSortPreparationStep extends AbstractReportStep{
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalSortPreparationStep.class);
	
	/**
	 * 
	 */
	private NewRowComparator COMPARATOR; 
	
	/**
	 * 
	 */
	private int maxRowsInMemory;
	
	/**
	 * the list of sorted files
	 */
	private List<File> sortedFiles = new ArrayList<File>();
	
	/**
	 * in memory holder of new row events 
	 */
	private List<NewRowEvent> tempValuesHolderList =  new ArrayList<NewRowEvent>();
	
	/**
	 * 
	 */
	public ExternalSortPreparationStep(){
		this(1000); 
	}
	
	/**
	 * 
	 * @param maxRowsInMemory
	 */
	public ExternalSortPreparationStep(int maxRowsInMemory){
		this.maxRowsInMemory = maxRowsInMemory; 
	}
	
	/**
	 * 
	 */
	@Override public void init(ReportContext context){
		super.init(context); 
		
		COMPARATOR = new NewRowComparator(getGroupingColumns(), getDataColumns()); 
	}
	
	/**
	 * 
	 */
	public void execute(NewRowEvent newRow){
		try{
			if(tempValuesHolderList.size() >= maxRowsInMemory){ 
				LOGGER.info("in memory list of rows has reached the maximum allowed {} items ", tempValuesHolderList.size());
				//first sorting in-memory list
				Collections.sort(tempValuesHolderList, COMPARATOR);
				//then save the sorted list into file
				sortedFiles.add(saveToFile(tempValuesHolderList));
				tempValuesHolderList.clear();
			}
			tempValuesHolderList.add(newRow);			  
		}catch(IOException ioExc){
			throw new ReportEngineRuntimeException(ioExc); 
		}
	}
	
	/**
	 * 
	 */
	@Override public void exit(ReportContext context){
		try{
			if(!tempValuesHolderList.isEmpty()){
				//first sorting in-memory list
				Collections.sort(tempValuesHolderList, COMPARATOR);
				sortedFiles.add(saveToFile(tempValuesHolderList)); 
			}
		}catch(IOException ioExc){
			throw new ReportEngineRuntimeException(ioExc); 
		}
		
		context.set(ContextKeys.SORTED_FILES, sortedFiles); 
		
		super.exit(context);
	}
	
	/**
	 * 
	 * @param rowsList
	 * @return
	 * @throws IOException
	 */
	public File saveToFile(List<NewRowEvent> rowsList) throws IOException  {
		//saving the sorted list into a temporary file
		File tempFile = File.createTempFile("temp", ".tmp");
		tempFile.deleteOnExit();
		
		LOGGER.info("saving the sorted rows list into {}", tempFile.getName());
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(tempFile));
		
		try {
			for(int i=0; i < rowsList.size()-1; i++) {
				outputStream.writeObject(new NewRowEventWrapper(rowsList.get(i), false));
			}
			
			//separate writing of last one
			outputStream.writeObject(new NewRowEventWrapper(rowsList.get(rowsList.size()-1), true));
		} finally {
			outputStream.close();
		}
		
		LOGGER.info("{} items saved in file {}", rowsList.size(), tempFile.getAbsolutePath());
		return tempFile; 
	}
}
