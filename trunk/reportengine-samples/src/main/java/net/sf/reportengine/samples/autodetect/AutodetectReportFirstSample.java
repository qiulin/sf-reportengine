/**
 * 
 */
package net.sf.reportengine.samples.autodetect;

import net.sf.reportengine.AutodetectFlatReport;
import net.sf.reportengine.in.SqlInput;
import net.sf.reportengine.out.ExcelOutput;

/**
 * @author dragos balan
 *
 */
public class AutodetectReportFirstSample {
	
	public static void main(String... args){
		
		SqlInput input = new SqlInput(); 
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:E:/projects/java/reportengine-trunk/reportengine/src/test/resources/databases/testdb");
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setSqlStatement("select country, region, city, sex, religion, value from testreport t order by country, region, city");
		
		AutodetectFlatReport report = new AutodetectFlatReport(); 
		report.setIn(input); 
		report.setOut(new ExcelOutput("./output/FirstAutodetect.xls")); 
		report.execute(); 
	}
}
