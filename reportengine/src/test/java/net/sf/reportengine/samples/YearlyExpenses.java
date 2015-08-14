/**
 * 
 */
package net.sf.reportengine.samples;

import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.reportengine.ReportBuilder;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.FlatTableBuilder;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.TextTableInput;
import net.sf.reportengine.out.PdfReportOutput;

public class YearlyExpenses {

    public static void main(String[] args) throws IOException {

        FlatTable table =
            new FlatTableBuilder().input(new TextTableInput("./input/yearlyExpenses.txt",
                                                            "\t"))

                                  // groups configuration
                                  .addGroupColumn(new DefaultGroupColumn.Builder(0).header("Year")
                                                                                   .horizAlign(HorizAlign.LEFT)
                                                                                   .level(0)
                                                                                   .build())
                                  .addGroupColumn(new DefaultGroupColumn.Builder(1).header("Month")
                                                                                   .horizAlign(HorizAlign.LEFT)
                                                                                   .level(1)
                                                                                   .build())

                                  // data columns
                                  .addDataColumn(new DefaultDataColumn.Builder(2).header("Spent on")
                                                                                 .horizAlign(HorizAlign.LEFT)
                                                                                 .build())
                                  .addDataColumn(new DefaultDataColumn.Builder(3).header("Amount")
                                                                                 .horizAlign(HorizAlign.RIGHT)
                                                                                 .useCalculator(GroupCalculators.SUM,
                                                                                                "%.2f")
                                                                                 .build())

                                  .build();

        new ReportBuilder(new PdfReportOutput(new FileOutputStream("./target/YearlyExpensesReport.pdf"))).add(new ReportTitle("Yearly expenses report"))
                                                                                                         .add(table)
                                                                                                         .build()
                                                                                                         .execute();
    }
}
