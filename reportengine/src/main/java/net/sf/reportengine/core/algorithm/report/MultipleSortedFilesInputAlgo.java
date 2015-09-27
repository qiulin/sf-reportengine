/**
 * copyright 2015 dragos balan
 */
package net.sf.reportengine.core.algorithm.report;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.steps.NewRowComparator;
import net.sf.reportengine.in.MultipleExternalSortedFilesTableInput;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.StepIOKeys;

/**
 * 
 * @author dragos balan
 *
 */
public class MultipleSortedFilesInputAlgo extends LoopThroughTableInputAlgo {
    
    /**
     * 
     * @param algorithmName
     */
    public MultipleSortedFilesInputAlgo(String algorithmName){
        this(algorithmName, new HashMap<StepIOKeys, AlgoIOKeys>()); 
    }
    
    /**
     * 
     * @param algorithmName
     * @param stepToAlgoOutputMapping
     */
    public MultipleSortedFilesInputAlgo(String algorithmName, Map<StepIOKeys, AlgoIOKeys> stepToAlgoOutputMapping) {
        super(algorithmName, stepToAlgoOutputMapping);
    }
    
    /**
     * 
     */
    protected TableInput buildTableInput(Map<AlgoIOKeys, Object> inputParams) {
        return new MultipleExternalSortedFilesTableInput(
                       (List<File>) inputParams.get(AlgoIOKeys.SORTED_FILES),
                       new NewRowComparator((List<GroupColumn>) inputParams.get(AlgoIOKeys.GROUP_COLS),
                                            (List<DataColumn>) inputParams.get(AlgoIOKeys.DATA_COLS)));
    }
}
