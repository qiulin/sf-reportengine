/**
 * 
 */
package net.sf.reportengine.samples.autodetect;

import net.sf.reportengine.AutodetectFlatReport;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.SqlInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * @author dragos balan
 *
 */
public class ConfiguredAutodetectSample {
	
	public static void main(String... args){
		
		SqlInput input = new SqlInput(); 
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:./inputData/databases/testdb");
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setSqlStatement("select country, region, city, sex, religion, value from testreport t order by country, region, city");
		
		AutodetectFlatReport report = new AutodetectFlatReport(); 
		report.setIn(input); 
		report.setOut(new HtmlOutput("./output/ConfiguredAutodetect.html")); 
		
		report.forColumn("COUNTRY").setGroup(true).setHAlign(HorizAlign.CENTER); 
		report.forColumn("REGION").setGroup(true).setHeader("EastCoast/WestCoast"); 
		report.forColumn("VALUE").setCalculator(Calculators.SUM); 
		
		report.execute(); 
	}
}
