/**
 * 
 */
package net.sf.reportengine;

import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.calc.GroupCalculators;
import net.sf.reportengine.core.calc.SumGroupCalculator;
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
		
		AutodetectConfigurationScenario.initScenario(); 
		
		AutoconfigFlatReport flatReport = new AutoconfigFlatReport.Builder()
			.title("Autoconfig flat report")
			.input(AutodetectConfigurationScenario.INPUT)
			.output(new HtmlOutput("./target/testBasicAutodetect.html"))
			.build(); 
		flatReport.execute(); 
	}
	
	@Test
	public void testAutodetectColumnsFromMetadataAndPreferences(){
		AutoconfigFlatReport flatReport = new AutoconfigFlatReport(); 
		
		AutodetectConfigurationScenario.initScenario(); 
		
		flatReport.setIn(AutodetectConfigurationScenario.INPUT); 
		flatReport.setOut(new HtmlOutput("./target/testAutodetectWithUserPrefs.html")); 
		flatReport.forColumn("col1").header("First column forced by prefs to right align")
									.horizAlign(HorizAlign.RIGHT); 
		flatReport.execute(); 
	}
	
	@Test
	public void testAutodetectFromDatabase(){
		AutoconfigFlatReport flatReport = new AutoconfigFlatReport(); 
		flatReport.setTitle("autoconfig report from sql"); 
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
		
		flatReport.forColumn("COUNTRY").horizAlign(HorizAlign.RIGHT).vertAlign(VertAlign.TOP).header("Changed header").group(); 
		flatReport.forColumn("VALUE").vertAlign(VertAlign.BOTTOM).useCalculator(new SumGroupCalculator(), "%f $").valuesFormatter("%d $"); 
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
		
		flatReport.forColumn("COUNTRY").horizAlign(HorizAlign.RIGHT).header("Changed header").group(); 
		flatReport.forColumn("VALUE").useCalculator(new SumGroupCalculator(), "%f $").valuesFormatter("%d $"); 
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
		
		flatReport.forColumn("COUNTRY")	.horizAlign(HorizAlign.RIGHT)
										.header("Changed header")
										.group();
		
		flatReport.forColumn("SEX")	.header("Sorted ASC programatically with priority 1")
									.sortAsc(0); 
		flatReport.forColumn("VALUE")	.header("Sorted ASC programatically with priority 2")
										.useCalculator(new SumGroupCalculator(), "%f $")
										.valuesFormatter("%d $")
										.sortAsc(1); //non group column sorted
		
		flatReport.forColumn("REGION").group();	
		
		flatReport.forColumn("CITY").group();
		
		//we don't need to declare that values are not sorted because the column preferences above have sorting declared
		//flatReport.setValuesSorted(false); 								
		
		flatReport.execute();
	}
}