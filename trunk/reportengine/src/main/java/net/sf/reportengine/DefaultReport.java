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

import java.util.List;

import net.sf.reportengine.components.ReportComponent;
import net.sf.reportengine.out.AbstractReportOutput;
import net.sf.reportengine.out.ReportProps;

/**
 * @author dragos balan
 *
 */
final class DefaultReport implements Report {

	public final static String START_REPORT_TEMPLATE = "startReport.ftl";
	public final static String END_REPORT_TEMPLATE = "endReport.ftl";

	/**
	 * the list of the components of this report
	 */
	private final List<ReportComponent> components;

	/**
	 * the report output
	 */
	private final AbstractReportOutput reportOutput;

	/**
	 * 
	 * @param builder
	 */
	DefaultReport(AbstractReportOutput reportOutput,
			List<ReportComponent> components) {
		this.reportOutput = reportOutput;
		this.components = components;
	}

	/**
	 * executes the report
	 */
	public void execute() {
		reportOutput.open();
		reportOutput.output(START_REPORT_TEMPLATE,
				new ReportProps(reportOutput.getFormat()));
		for (ReportComponent reportComponent : components) {
			reportComponent.output(reportOutput);
		}
		reportOutput.output(END_REPORT_TEMPLATE);
		reportOutput.close();
	}
}
