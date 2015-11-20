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
package net.sf.reportengine;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.components.ReportComponent;
import net.sf.reportengine.out.AbstractReportOutput;
import net.sf.reportengine.out.PostProcessedFoReportOutput;

/**
 * This is the one and only builder for any report
 * Typical usage: 
 * <pre>
 *  Report report = new ReportBuilder(new HtmlReportOutput(new FileWriter("/temp/test.html")))
 *                      .add(new ReportTile("My first report"))
 *                      .add(...)
 *                      .add(other report components here)
 *                      .build(); 
 *  report.execute(); 
 * </pre>
 * 
 * @see Report
 * @author dragos balan
 * @since 0.13
 */
public class ReportBuilder {

    /**
	 * 
	 */
    private List<ReportComponent> components;

    /**
	 * 
	 */
    private final AbstractReportOutput reportOutput;

    /**
     * 
     * @param output
     */
    public ReportBuilder(AbstractReportOutput output) {
        this.reportOutput = output;
        this.components = new ArrayList<ReportComponent>();
    }

    /**
     * adds a new component to the report
     * 
     * @param newComponent
     *            the component
     */
    public ReportBuilder add(ReportComponent newComponent) {
        components.add(newComponent);
        return this;
    }

    public AbstractReportOutput getOutput() {
        return reportOutput;
    }

    public List<ReportComponent> getComponents() {
        return components;
    }

    public Report build() {
        Report result = null;
        if (reportOutput instanceof PostProcessedFoReportOutput) {
            result = new PostProcessReport((PostProcessedFoReportOutput) reportOutput, components);
        } else {
            result = new DefaultReport(reportOutput, components);
        }

        return result;
    }
}
