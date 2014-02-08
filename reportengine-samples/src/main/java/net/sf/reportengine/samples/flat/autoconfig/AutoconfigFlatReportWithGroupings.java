/**
 * 
 */
package net.sf.reportengine.samples.flat.autoconfig;

import net.sf.reportengine.AutoconfigFlatReport;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.in.SqlInput;
import net.sf.reportengine.out.Html5Output;

/**
 * simple auto configured report having some default configurations overwritten
 */
public class AutoconfigFlatReportWithGroupings {
	
	public static void main(String... args){
		
		SqlInput input = new SqlInput(); 
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:./inputData/databases/testdb");
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setSqlStatement(	"select country, region, city, sex, religion, value "+
								"from testreport "+
								"order by country, region, city");
		
		AutoconfigFlatReport report = new AutoconfigFlatReport.Builder()
			.input(input)
			.output(new Html5Output("./output/AutoconfigFromDatabase.html"))
			.build(); 
		
		report.forColumn("COUNTRY").group().horizAlign(HorizAlign.CENTER); 
		report.forColumn("REGION").group().header("East/West"); 
		report.forColumn("VALUE").useCalculator(GroupCalculators.SUM); 
		
		report.execute(); 
	}
}
