/**
 * copyright 2015 dragos balan
 */
package net.sf.reportengine.core.algorithm.report;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AbstractAlgo;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.ReportIoUtils;

/**
 * @author dragos balan
 *
 */
public class DeleteTempIntermFilesAlgo extends AbstractAlgo {

    public DeleteTempIntermFilesAlgo() {
        super("Temporary intermediate files cleaner");
    }

    /* (non-Javadoc)
     * @see net.sf.reportengine.core.algorithm.Algorithm#execute(java.util.Map)
     */
    public Map<AlgoIOKeys, Object> execute(Map<AlgoIOKeys, Object> input) {
        File tempIntermFile = (File) input.get(AlgoIOKeys.INTERMEDIATE_OUTPUT_FILE);
        ReportIoUtils.deleteTempFileIfNotDebug(tempIntermFile);
        //return an empty result
        return new HashMap<AlgoIOKeys, Object>();
    }

}
