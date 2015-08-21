/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package net.sf.reportengine.out;

import java.io.File;
import java.io.OutputStream;

import net.sf.reportengine.util.ReportIoUtils;

/**
 * 
 * @author dragos balan
 *
 */
public class PostProcessedFoReportOutput extends AbstractReportOutput {

    private final OutputStream outStream;

    private final AbstractFreemarkerReportOutput foOutput;

    private final File tempFile;

    private final PostProcessor postProcessor;

    /**
     * 
     * @param outStream
     * @param outFormat
     */
    public PostProcessedFoReportOutput(OutputStream outStream,
                                       FoOutputFormat outFormat,
                                       PostProcessor postProcessor) {
        super(outFormat);
        this.outStream = outStream;
        this.tempFile = ReportIoUtils.createTempFile("report-fo");
        this.foOutput =
            new FoReportOutput(ReportIoUtils.createWriterFromFile(tempFile), true, outFormat);
        this.postProcessor = postProcessor;
    }

    public void open() {
        super.open();
        foOutput.open();
    }

    public void close() {
        try {
            foOutput.close();
            postProcessor.process(tempFile, outStream);
        } finally {
            super.close();
        }
    }

    public void output(String templateName, Object model) {
        foOutput.output(templateName, model);
    }
}
