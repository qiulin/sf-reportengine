/**
 * copyright 2015 dragos balan
 */
package net.sf.reportengine;

import java.util.List;

import net.sf.reportengine.components.ReportComponent;
import net.sf.reportengine.out.AbstractReportOutput;
import net.sf.reportengine.out.ReportProps;

/**
 * @author dragos balan
 *
 */
abstract class AbstractReport<T extends AbstractReportOutput> implements Report {

    public final static String START_REPORT_TEMPLATE = "startReport.ftl";
    public final static String END_REPORT_TEMPLATE = "endReport.ftl";

    /**
     * the list of the components of this report
     */
    private final List<ReportComponent> components;

    /**
     * the report output
     */
    private final T reportOutput;

    /**
     * 
     * @param builder
     */
    AbstractReport(T reportOutput, List<ReportComponent> components) {
        this.reportOutput = reportOutput;
        this.components = components;
    }

    protected List<ReportComponent> getComponents() {
        return components;
    }

    protected T getReportOutput() {
        return reportOutput;
    }

    protected void outputFO(T reportOutput) {
        reportOutput.output(START_REPORT_TEMPLATE, new ReportProps(reportOutput.getFormat()));
        for (ReportComponent reportComponent : getComponents()) {
            reportComponent.output(reportOutput);
        }
        reportOutput.output(END_REPORT_TEMPLATE);
    }
}
