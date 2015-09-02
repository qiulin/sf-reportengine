/**
 * copyright 2015 dragos balan
 */
package net.sf.reportengine.out;

import java.io.OutputStream;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.fop.apps.MimeConstants;

/**
 * @author dragos balan
 *
 */
public class TiffReportOutput extends PostProcessedFoReportOutput {

    public TiffReportOutput(OutputStream outStream) {
        this(outStream, new TiffOutputFormat());
    }

    public TiffReportOutput(OutputStream outStream, TiffOutputFormat outputFormat) {
        this(outStream, outputFormat, null);
    }

    public TiffReportOutput(OutputStream outStream,
                            TiffOutputFormat outputFormat,
                            Configuration fopConfig) {
        super(outStream, outputFormat, MimeConstants.MIME_TIFF, fopConfig, null);
    }
}
