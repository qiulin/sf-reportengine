package net.sf.reportengine.out;

import java.io.File;

import net.sf.reportengine.util.ReportIoUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPostProcessedFoReportOutput {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testPostProcess() {
        String OUTPUT_PATH = "./target/TestPostProcessedFoReportOutput.pdf";
        PdfOutputFormat format = new PdfOutputFormat();

        PostProcessedFoReportOutput classUnderTest =
            new PostProcessedFoReportOutput(ReportIoUtils.createOutputStreamFromPath(OUTPUT_PATH),
                                            format,
                                            "application/pdf",
                                            null,
                                            null);

        classUnderTest.open();

        classUnderTest.output("startReport.ftl", new ReportProps(format));
        classUnderTest.output("title.ftl", new TitleProps("Hello from a post processed FO report"));
        classUnderTest.output("endReport.ftl");

        classUnderTest.close();

        classUnderTest.postProcess();

        File pdfResult = new File(OUTPUT_PATH);
        Assert.assertTrue(pdfResult.exists());
        Assert.assertTrue(pdfResult.isFile());
        Assert.assertTrue(pdfResult.length() > 0);
    }

}
