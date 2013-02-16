/**
 * 
 */
package net.sf.reportengine.samples.flat;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.DbQueryInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * @author dragos balan
 *
 */
public class FlatReportFromSqlQuery {

	
	public static void main(String[] args) {
		
		FlatReport report = new FlatReport(); 
		
		//input configuration
		DbQueryInput reportInput = new DbQueryInput(); 
		reportInput.setDbConnString("jdbc:oracle:thin:@172.21.45.35:1521:icisdev");
		reportInput.setDbDriverClass("oracle.jdbc.driver.OracleDriver"); 
		reportInput.setDbUser("scott");
		reportInput.setDbPassword("tiger"); 
		reportInput.setSqlStatement("select id, firstName, lastName , salary from emp");
		report.setIn(reportInput);
		
		report.addDataColumn(new DefaultDataColumn("id",0));
		report.addDataColumn(new DefaultDataColumn("FirstName", 3)); 
		report.addDataColumn(new DefaultDataColumn("LastName", 6));
		report.addDataColumn(new DefaultDataColumn("Quantity", 10));
				
		report.setOut(new HtmlOutput("./output/ReportFromDbQuery.html"));
		
		report.execute();
	}
}
