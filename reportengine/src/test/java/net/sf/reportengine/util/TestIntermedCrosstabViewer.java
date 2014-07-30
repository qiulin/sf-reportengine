/**
 * 
 */
package net.sf.reportengine.util;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

/**
 * @author balan
 *
 */
public class TestIntermedCrosstabViewer {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link net.sf.reportengine.util.IntermedCrosstabViewer#exportToHtml()}.
	 */
	@Test
	public void testExportToHtml() {
		InputStream intermInput = ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput2x2x1xT.rep");
		IntermedCrosstabViewer viewer = new IntermedCrosstabViewer(intermInput, "./target/intermReportView.html");
		viewer.exportToHtml(); 
	}

}
