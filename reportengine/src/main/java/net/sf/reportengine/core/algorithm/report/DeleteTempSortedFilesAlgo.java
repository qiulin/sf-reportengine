package net.sf.reportengine.core.algorithm.report;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AbstractAlgo;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.ReportIoUtils;

public class DeleteTempSortedFilesAlgo extends AbstractAlgo {

    public DeleteTempSortedFilesAlgo() {
        super("Temporary sorted files cleaner");
    }

    public Map<AlgoIOKeys, Object> execute(Map<AlgoIOKeys, Object> input) {
        List<File> tempFiles = (List<File>)input.get(AlgoIOKeys.SORTED_FILES); 
        for(File file: tempFiles){
            ReportIoUtils.deleteTempFileIfNotDebug(file);
        }
        //return an empty result
        return new HashMap<AlgoIOKeys, Object>();
    }

}
