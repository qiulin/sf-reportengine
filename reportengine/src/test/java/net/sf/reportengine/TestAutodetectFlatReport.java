/**
 * 
 */
package net.sf.reportengine;

import java.text.NumberFormat;

import junit.framework.TestCase;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.SqlInput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.scenarios.AutodetectConfigurationScenario;

/**
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public class TestAutodetectFlatReport extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAutodetectColumnsFromMetadata(){
		AutoconfigFlatReport flatReport = new AutoconfigFlatReport(); 
		
		AutodetectConfigurationScenario.initScenario(); 
		
		flatReport.setIn(AutodetectConfigurationScenario.INPUT); 
		flatReport.setOut(new HtmlOutput("./target/testBasicAutodetect.html")); 
		flatReport.execute(); 
	}
	
	
	public void testAutodetectColumnsFromMetadataAndPreferences(){
		AutoconfigFlatReport flatReport = new AutoconfigFlatReport(); 
		
		AutodetectConfigurationScenario.initScenario(); 
		
		flatReport.setIn(AutodetectConfigurationScenario.INPUT); 
		flatReport.setOut(new HtmlOutput("./target/testAutodetectWithUserPrefs.html")); 
		flatReport.forColumn("col1").setHeader("First column forced by prefs to right align")
									.setHAlign(HorizAlign.RIGHT); 
		flatReport.execute(); 
	}
	
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
	
	public void testAutodetectFromDatabaseWithUserPrefs(){
		AutoconfigFlatReport flatReport = new AutoconfigFlatReport(); 
		SqlInput input = new SqlInput(); 
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:./src/test/resources/databases/testdb");
		input.setSqlStatement("select country, region, city, sex, religion, value from testreport t order by country, region, city");
		flatReport.setIn(input); 
		flatReport.setOut(new HtmlOutput("./target/AutodetectFromSqlWithUserPrefs.html")); 
		
		flatReport.forColumn("COUNTRY").setHAlign(HorizAlign.RIGHT).setHeader("Changed header").setGroup(true); 
		flatReport.forColumn("VALUE").setCalculator(Calculators.SUM).setFormatter(NumberFormat.getCurrencyInstance()); 
		flatReport.forColumn("REGION").setGroup(true); 
		flatReport.forColumn("CITY").setGroup(true); 
		
		flatReport.execute();
	}
}
