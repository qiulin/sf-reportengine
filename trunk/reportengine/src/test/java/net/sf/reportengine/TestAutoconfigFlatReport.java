/**
 * 
 */
package net.sf.reportengine;

import java.text.NumberFormat;

import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.SqlInput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.scenarios.AutodetectConfigurationScenario;

import org.junit.Test;

/**
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public class TestAutoconfigFlatReport {

	
	@Test
	public void testAutodetectColumnsFromMetadata(){
		AutoconfigFlatReport flatReport = new AutoconfigFlatReport(); 
		
		AutodetectConfigurationScenario.initScenario(); 
		
		flatReport.setIn(AutodetectConfigurationScenario.INPUT); 
		flatReport.setOut(new HtmlOutput("./target/testBasicAutodetect.html")); 
		flatReport.execute(); 
	}
	
	@Test
	public void testAutodetectColumnsFromMetadataAndPreferences(){
		AutoconfigFlatReport flatReport = new AutoconfigFlatReport(); 
		
		AutodetectConfigurationScenario.initScenario(); 
		
		flatReport.setIn(AutodetectConfigurationScenario.INPUT); 
		flatReport.setOut(new HtmlOutput("./target/testAutodetectWithUserPrefs.html")); 
		flatReport.forColumn("col1").setHeader("First column forced by prefs to right align")
									.setHAlign(HorizAlign.RIGHT); 
		flatReport.execute(); 
	}
	
	@Test
	public void testAutodetectFromDatabase(){
		AutoconfigFlatReport flatReport = new AutoconfigFlatReport(); 
		SqlInput input = new SqlInput(); 
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:./src/test/resources/databases/testdb");
		input.setSqlStatement("select id, country, region, city, sex, religion, value from testreport t order by id");
		flatReport.setIn(input); 
		flatReport.setOut(new HtmlOutput("./target/AutodetectFromSql.html")); 
		
		flatReport.execute();
	}
	
	@Test
	public void testAutodetectFromDatabaseWithUserPrefs(){
		AutoconfigFlatReport flatReport = new AutoconfigFlatReport(); 
		SqlInput input = new SqlInput(); 
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:./src/test/resources/databases/testdb");
		input.setSqlStatement("select country, region, city, sex, religion, value from testreport t order by country, region, city");
		flatReport.setIn(input); 
		flatReport.setOut(new HtmlOutput("./target/AutodetectFromSqlWithUserPrefsAndDbOrdering.html")); 
		
		flatReport.forColumn("COUNTRY").setHAlign(HorizAlign.RIGHT).setVAlign(VertAlign.TOP).setHeader("Changed header").group(); 
		flatReport.forColumn("VALUE").setVAlign(VertAlign.BOTTOM).useCalculator(Calculators.SUM).useFormatter(NumberFormat.getCurrencyInstance()); 
		flatReport.forColumn("REGION").group(); 
		flatReport.forColumn("CITY").group(); 
		
		flatReport.execute();
	}
	
	@Test
	public void testAutodetectFromDatabaseWithNoDeclaredOrderButSortedInternally(){
		AutoconfigFlatReport flatReport = new AutoconfigFlatReport(); 
		SqlInput input = new SqlInput(); 
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:./src/test/resources/databases/testdb");
		input.setSqlStatement("select country, region, city, sex, religion, value from testreport t "); //order by country, region, city
		flatReport.setIn(input); 
		flatReport.setOut(new HtmlOutput("./target/AutodetFrmSqlWithUserPrefsButOrderedInternally.html")); 
		
		//we need to declare that values are not sorted because the column preferences below don't have any sort declared
		flatReport.setValuesSorted(false); 
		
		flatReport.forColumn("COUNTRY").setHAlign(HorizAlign.RIGHT).setHeader("Changed header").group(); 
		flatReport.forColumn("VALUE").useCalculator(Calculators.SUM).useFormatter(NumberFormat.getCurrencyInstance()); 
		flatReport.forColumn("REGION").group(); 
		flatReport.forColumn("CITY").group(); 
		
		flatReport.execute();
	}
	
	@Test
	public void testAutodetectFromDatabaseWithProgramaticOrderingAndNonGroupColumnSorted(){
		AutoconfigFlatReport flatReport = new AutoconfigFlatReport.Builder()
					.title("Report with programatic ordering for non-grouping columns")
					.sortValues()
					.build(); 
		SqlInput input = new SqlInput(); 
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:./src/test/resources/databases/testdb");
		input.setSqlStatement("select country, region, city, sex, religion, value from testreport t "); 
		flatReport.setIn(input); 
		flatReport.setOut(new HtmlOutput("./target/AutodetFrmSqlWithProgrOrderingAndNonGroupColSorted.html")); 
		
		flatReport.forColumn("COUNTRY")	.setHAlign(HorizAlign.RIGHT)
										.setHeader("Changed header")
										.group();
		
		flatReport.forColumn("SEX")	.setHeader("Sorted ASC programatically with priority 1")
									.sortAsc(0); 
		flatReport.forColumn("VALUE")	.setHeader("Sorted ASC programatically with priority 2")
										.useCalculator(Calculators.SUM)
										.useFormatter(NumberFormat.getCurrencyInstance())
										.sortAsc(1); //non group column sorted
		
		flatReport.forColumn("REGION").group();	
		
		flatReport.forColumn("CITY").group();
		
		//we don't need to declare that values are not sorted because the column preferences above have sorting declared
		//flatReport.setValuesSorted(false); 								
		
		flatReport.execute();
	}
}