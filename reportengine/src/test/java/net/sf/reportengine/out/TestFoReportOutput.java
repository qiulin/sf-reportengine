package net.sf.reportengine.out;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class TestFoReportOutput {

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOutputInDifferentPageSizes() throws IOException {
        FoReportOutput classUnderTest =
            new FoReportOutput(new FileWriter("./target/TestOutputA4Portrait.fo"));
        classUnderTest.open();
        classUnderTest.output("startReport.ftl", new ReportProps(new FoOutputFormat()));
        classUnderTest.output("title.ftl",
                              new TitleProps("this string has been outputed from a unit test"));
        classUnderTest.output("endReport.ftl");
        classUnderTest.close();
    }

}
