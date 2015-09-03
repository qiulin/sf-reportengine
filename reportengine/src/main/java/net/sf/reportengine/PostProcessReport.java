/**
 * copyright 2015 dragos balan
 */
package net.sf.reportengine;

import java.util.List;

import net.sf.reportengine.components.ReportComponent;
import net.sf.reportengine.out.PostProcessedFoReportOutput;

/**
 * @author dragos balan
 *
 */
class PostProcessReport extends AbstractReport<PostProcessedFoReportOutput> {

    PostProcessReport(PostProcessedFoReportOutput reportOutput, List<ReportComponent> components) {
        super(reportOutput, components);
    }

    /**
     * executes the report
     */
    public void execute() {
        PostProcessedFoReportOutput reportOutput = getReportOutput();
        try {
            reportOutput.open();
            outputFO(reportOutput);
            processFO(reportOutput);
        } finally {
            reportOutput.close();
        }
    }

    private void processFO(PostProcessedFoReportOutput reportOutput) {
        reportOutput.postProcess();
    }
}
