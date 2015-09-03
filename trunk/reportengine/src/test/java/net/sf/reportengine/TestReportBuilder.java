package net.sf.reportengine;

import net.sf.reportengine.out.HtmlReportOutput;
import net.sf.reportengine.out.PdfReportOutput;
import net.sf.reportengine.out.TiffReportOutput;

import org.junit.Assert;
import org.junit.Test;

public class TestReportBuilder {

    @Test
    public void testBuild() {

        Assert.assertTrue(new ReportBuilder(new PdfReportOutput(null)).build() instanceof PostProcessReport);
        Assert.assertTrue(new ReportBuilder(new TiffReportOutput(null)).build() instanceof PostProcessReport);
        Assert.assertTrue(new ReportBuilder(new HtmlReportOutput(null)).build() instanceof DefaultReport);
    }

}
