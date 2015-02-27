/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.ReportEngineRuntimeException;
import net.sf.reportengine.in.MultipleExternalSortedFilesInput;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.util.IOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 *
 */
public class ConfigMultiExternalFilesInputInitStep extends ConfigReportIOInitStep{
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigMultiExternalFilesInputInitStep.class);
	
	@Override protected ReportInput configReportInput(Map<IOKeys, Object> inputParams){
		ReportInput result = null; 
		try {
			//if the report doesn't have the values ordered 
			//then the sorting algorithm has created external sorted files
			//which will serve as input from this point on
			
			//if(!(Boolean)getAlgoInput().get(IOKeys.HAS_VALUES_ORDERED)){
				
				List<File> externalSortedFiles = (List<File>)inputParams.get(IOKeys.SORTED_FILES); 
				if(externalSortedFiles != null && !externalSortedFiles.isEmpty()){
					List<InputStream> externalSortedStreams = new ArrayList<InputStream>(); 
					for (File file : externalSortedFiles) {
						externalSortedStreams.add(new FileInputStream(file));
					}
					
					result = new MultipleExternalSortedFilesInput(
									externalSortedStreams, 
									new NewRowComparator(getGroupColumns(inputParams), getDataColumns(inputParams)));
				}else{
					LOGGER.error("No external sorted files found. The report is missconfigured."); 
				}
			//}else{
			//	result = super.configReportInput();  
			//}
		} catch (FileNotFoundException e) {
			throw new ReportEngineRuntimeException(e); 
		}
		
		return result;
	}
	
	
//	@Override protected void executeInit() {
//		try {
//			//if the report doesn't have the group values ordered 
//			//then the sorting algorithm has created the external sorted files
//			//which will serve as input from this point on
//			if(!(Boolean)getAlgoInput().get(IOKeys.HAS_VALUES_ORDERED)){
//				
//				List<File> externalSortedFiles = (List<File>)getAlgoInput().get(IOKeys.SORTED_FILES); 
//				List<InputStream> externalSortedStreams = new ArrayList<InputStream>(); 
//				for (File file : externalSortedFiles) {
//						externalSortedStreams.add(new FileInputStream(file));
//				}
//				
//				NewRowComparator newRowComparator = new NewRowComparator(getGroupColumns(), getDataColumns()); 
//				
//				getAlgoInput().put(IOKeys.REPORT_INPUT, 
//						new MultipleExternalSortedFilesInput(externalSortedStreams, newRowComparator));
//			}
//		} catch (FileNotFoundException e) {
//			throw new ReportEngineRuntimeException(e); 
//		}
//	}
}
