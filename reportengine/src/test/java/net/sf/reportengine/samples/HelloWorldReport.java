/**
 * copyright 2015 dragos balan
 */
package net.sf.reportengine.samples;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.Report;
import net.sf.reportengine.ReportBuilder;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.out.HtmlReportOutput;

public class HelloWorldReport {

	public static void main(String[] args) throws IOException {
		// preparation of output and components
		HtmlReportOutput output = new HtmlReportOutput(new FileWriter(
				"Hello.html"));
		ReportTitle title = new ReportTitle("Hello World report");

		// report set-up
		Report report = new ReportBuilder(output).add(title).build();
		// report execution
		report.execute();
	}
}
