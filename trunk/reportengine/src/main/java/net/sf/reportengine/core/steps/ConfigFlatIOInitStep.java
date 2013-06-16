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

import net.sf.reportengine.core.ReportEngineRuntimeException;
import net.sf.reportengine.in.MultipleExternalSortedFilesInput;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class ConfigFlatIOInitStep extends ConfigReportIOInitStep{
	
	@Override protected ReportInput configReportInput(){
		ReportInput result; 
		try {
			//if the report doesn't have the group values ordered 
			//then the sorting algorithm has created the external sorted files
			//which will serve as input from this point on
			if(!(Boolean)getAlgoInput().get(IOKeys.HAS_GROUP_VALUES_ORDERED)){
				
				List<File> externalSortedFiles = (List<File>)getAlgoInput().get(IOKeys.SORTED_FILES); 
				List<InputStream> externalSortedStreams = new ArrayList<InputStream>(); 
				for (File file : externalSortedFiles) {
						externalSortedStreams.add(new FileInputStream(file));
				}
				
				result = new MultipleExternalSortedFilesInput(
								externalSortedStreams, 
								new NewRowComparator(getGroupColumns(), getDataColumns()));
			}else{
				result = super.configReportInput();  
			}
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
//			if(!(Boolean)getAlgoInput().get(IOKeys.HAS_GROUP_VALUES_ORDERED)){
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
