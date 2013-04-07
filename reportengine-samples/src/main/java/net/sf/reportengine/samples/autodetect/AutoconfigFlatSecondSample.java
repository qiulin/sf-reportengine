/**
 * 
 */
package net.sf.reportengine.samples.autodetect;

import net.sf.reportengine.AutoconfigFlatReport;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.SqlInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * simple auto configured report having some default configurations overwritten
 */
public class AutoconfigFlatSecondSample {
	
	public static void main(String... args){
		
		SqlInput input = new SqlInput(); 
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:./inputData/databases/testdb");
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setSqlStatement("select country, region, city, sex, religion, value from testreport t order by country, region, city");
		
		AutoconfigFlatReport report = new AutoconfigFlatReport(); 
		report.setIn(input); 
		report.setOut(new HtmlOutput("./output/ConfiguredAutodetect.html")); 
		
		report.forColumn("COUNTRY").setGroup(true).setHAlign(HorizAlign.CENTER); 
		report.forColumn("REGION").setGroup(true).setHeader("East/West"); 
		report.forColumn("VALUE").setCalculator(Calculators.SUM); 
		
		report.execute(); 
	}
}
