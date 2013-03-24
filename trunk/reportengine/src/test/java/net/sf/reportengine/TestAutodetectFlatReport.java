/**
 * 
 */
package net.sf.reportengine;

import junit.framework.TestCase;
import net.sf.reportengine.config.HorizontalAlign;
import net.sf.reportengine.in.SqlInput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.scenarios.AutodetectConfigurationScenario;

/**
 * @author dragos balan
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
		AutodetectFlatReport flatReport = new AutodetectFlatReport(); 
		
		AutodetectConfigurationScenario.initScenario(); 
		
		flatReport.setIn(AutodetectConfigurationScenario.INPUT); 
		flatReport.setOut(new HtmlOutput("./target/testBasicAutodetect.html")); 
		flatReport.execute(); 
	}
	
	
	public void testAutodetectColumnsFromMetadataAndPreferences(){
		AutodetectFlatReport flatReport = new AutodetectFlatReport(); 
		
		AutodetectConfigurationScenario.initScenario(); 
		
		flatReport.setIn(AutodetectConfigurationScenario.INPUT); 
		flatReport.setOut(new HtmlOutput("./target/testAutodetectWithUserPrefs.html")); 
		flatReport.forColumn("col1").setHeader("First column forced by prefs to right align")
									.setHAlign(HorizontalAlign.RIGHT); 
		flatReport.execute(); 
	}
	
	public void testAutodetectFromDatabase(){
		AutodetectFlatReport flatReport = new AutodetectFlatReport(); 
		SqlInput input = new SqlInput(); 
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:E:/projects/java/reportengine-trunk/reportengine/src/test/resources/databases/testdb");
		input.setSqlStatement("select id, country, region, city, sex, religion, value from testreport t order by id");
		flatReport.setIn(input); 
		flatReport.setOut(new HtmlOutput("./target/AutodetectFromSql.html")); 
		
		flatReport.execute();
	}
	
	public void testAutodetectFromDatabaseWithUserPrefs(){
		AutodetectFlatReport flatReport = new AutodetectFlatReport(); 
		SqlInput input = new SqlInput(); 
		input.setDbUser("SA");
		input.setDbPassword("");
		input.setDbDriverClass("org.hsqldb.jdbcDriver");
		input.setDbConnString("jdbc:hsqldb:file:E:/projects/java/reportengine-trunk/reportengine/src/test/resources/databases/testdb");
		input.setSqlStatement("select id, country, region, city, sex, religion, value from testreport t order by id");
		flatReport.setIn(input); 
		flatReport.setOut(new HtmlOutput("./target/AutodetectFromSqlWithUserPrefs.html")); 
		
		flatReport.forColumn("COUNTRY").setHAlign(HorizontalAlign.RIGHT).setHeader("Changed header"); 
		
		flatReport.execute();
	}
}
