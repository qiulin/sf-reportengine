/**
 * 
 */
package net.sf.reportengine.samples.flat;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.Report;
import net.sf.reportengine.components.FlatTable;
import net.sf.reportengine.components.ReportTitle;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.SqlTableInput;
import net.sf.reportengine.out.HtmlReportOutput;

/**
 * Normal report having as input an SQL query
 * @author dragos balan
 *
 */
public class FlatReportFromSqlQuery {

	
	public static void main(String[] args) throws IOException{
		
		//input configuration
		SqlTableInput input = new SqlTableInput("jdbc:hsqldb:file:E:/projects/java/reportengine-trunk/reportengine/src/test/resources/databases/testdb", 
				"org.hsqldb.jdbcDriver", 
				"SA", 
				"",
				"select country, region, city, sex, religion, value from testreport t order by country, region, city");
		
		
		FlatTable table = new FlatTable.Builder()
			//the configuration below doesn't make too much sense as it skips some columns
			//but it's just for demo purposes
			.addDataColumn(new DefaultDataColumn("country", 0))
			.addDataColumn(new DefaultDataColumn("city", 2)) 
			.addDataColumn(new DefaultDataColumn("sex", 3))
			.addDataColumn(new DefaultDataColumn("value", 5))
			.input(input)
		.build(); 
		
		//the one and only execute
		new Report.Builder(new HtmlReportOutput(new FileWriter("./output/ReportFromDbQuery.html")))
			.add(new ReportTitle("Report from SQL input"))
			.add(table)
			.build()
		.execute();
	}
}
