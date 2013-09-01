/**
 * 
 */
package net.sf.reportengine.samples.flat.autoconfig;

import net.sf.reportengine.AutoconfigFlatReport;
import net.sf.reportengine.in.SqlInput;
import net.sf.reportengine.out.ExcelOutput;

/**
 * simple auto configured report
 *
 */
public class AutoconfigReportFirstSample {
	
	public static void main(String... args){
		
		SqlInput input = new SqlInput(); 
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:./inputData/databases/testdb");
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setSqlStatement("select country, region, city, sex, religion, value from testreport t order by country, region, city");
		
		new AutoconfigFlatReport.Builder()
			.input(input)
			.output(new ExcelOutput("./output/FirstAutodetect.xls"))
			.build()
		.execute(); 
	}
}
