/**
 * 
 */
package net.sf.reportengine.samples;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.Report;
import net.sf.reportengine.ReportBuilder;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.FlatTableBuilder;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * this is your first report having the following steps
 * 
 * 1. construct the report output (html in this case) 2. construct the flat
 * table having the expenses.csv file as input 3. build the report for the
 * output defined in step 1 by adding a title, the previous table 4. report
 * execution
 */
public class FirstReportWithATable {

    public static void main(String[] args) throws IOException {

        HtmlReportOutput reportOutput =
            new HtmlReportOutput(new FileWriter("./target/FirstReportWithTable.html"));

        FlatTable flatTable =
            new FlatTableBuilder().input(new TextTableInput("./input/expenses.csv",
                                                            ","))
                                  .addDataColumn(new DefaultDataColumn.Builder(0).header("Month")
                                                                                 .build())
                                  .addDataColumn(new DefaultDataColumn.Builder(1).header("Spent on")
                                                                                 .build())
                                  .addDataColumn(new DefaultDataColumn.Builder(2).header("Amount")
                                                                                 .build())
                                  .build();

        // FileWriter is used just for demo
        Report report =
            new ReportBuilder(reportOutput).add(new ReportTitle("My first report"))
                                           .add(flatTable)
                                           .build();

        report.execute();
    }
}
