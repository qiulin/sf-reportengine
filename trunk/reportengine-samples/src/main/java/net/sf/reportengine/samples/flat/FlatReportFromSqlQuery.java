/**
 * 
 */
package net.sf.reportengine.samples.flat;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.SqlInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * Normal report having as input an SQL query
 * @author dragos balan
 *
 */
public class FlatReportFromSqlQuery {

	
	public static void main(String[] args) {
		
		FlatReport report = new FlatReport(); 
		report.setTitle("Report obtained from SQL query"); 
		
		//input configuration
		SqlInput input = new SqlInput(); 
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:E:/projects/java/reportengine-trunk/reportengine/src/test/resources/databases/testdb");
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setSqlStatement("select country, region, city, sex, religion, value from testreport t order by country, region, city");
		
		//setting the input
		report.setIn(input);
		
		//the configuration below doesn't make too much sense as it skips some columns
		//but it's just for demo purposes
		report.addDataColumn(new DefaultDataColumn("country", 0));
		report.addDataColumn(new DefaultDataColumn("city", 2)); 
		report.addDataColumn(new DefaultDataColumn("sex", 3));
		report.addDataColumn(new DefaultDataColumn("value", 5));
		
		//setting the output
		report.setOut(new HtmlOutput("./output/ReportFromDbQuery.html"));
		
		//the one and only execute
		report.execute();
	}
}
