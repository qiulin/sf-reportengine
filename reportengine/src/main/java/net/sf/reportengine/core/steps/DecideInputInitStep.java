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

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.ReportEngineRuntimeException;
import net.sf.reportengine.core.algorithm.AlgorithmContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.in.MultipleExternalSortedFilesInput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos
 *
 */
public class DecideInputInitStep implements AlgorithmInitStep {
	
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep#exit(java.util.Map, net.sf.reportengine.core.algorithm.AlgorithmContext)
	 */
	public void init(Map<IOKeys, Object> algoInput, AlgorithmContext context) {
		try {
			//if the report doesn't have the group values ordered 
			//then the sorting algorithm has created the external sorted files
			//which will serve as input from this point on
			if(!(Boolean)algoInput.get(IOKeys.HAS_GROUP_VALUES_ORDERED)){
				
				List<File> externalSortedFiles = (List<File>)algoInput.get(IOKeys.SORTED_FILES); 
				List<InputStream> externalSortedStreams = new ArrayList<InputStream>(); 
				for (File file : externalSortedFiles) {
						externalSortedStreams.add(new FileInputStream(file));
				}
				
				List<GroupColumn> groupCols = (List<GroupColumn>)algoInput.get(IOKeys.GROUP_COLS); 
				List<DataColumn> dataCols = (List<DataColumn>)algoInput.get(IOKeys.DATA_COLS); 
				
				NewRowComparator newRowComparator = new NewRowComparator(groupCols, dataCols); 
				
				algoInput.put(IOKeys.REPORT_INPUT, 
						new MultipleExternalSortedFilesInput(externalSortedStreams, newRowComparator));
			}
		} catch (FileNotFoundException e) {
			throw new ReportEngineRuntimeException(e); 
		}
	}
}
