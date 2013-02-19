/**
 * 
 */
package net.sf.reportengine.samples.flat;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.in.DbQueryInput;
import net.sf.reportengine.out.HtmlOutput;

/**
 * Normal report having as input an SQL query
 * @author dragos balan
 *
 */
public class FlatReportFromSqlQuery {

	
	public static void main(String[] args) {
		
		FlatReport report = new FlatReport(); 
		report.setReportTitle("Report obtained from SQL query"); 
		
		//input configuration
		DbQueryInput reportInput = new DbQueryInput(); 
		reportInput.setDbConnString("jdbc:oracle:thin:@172.21.45.35:1521:icisdev");
		reportInput.setDbDriverClass("oracle.jdbc.driver.OracleDriver"); 
		reportInput.setDbUser("AIS_DEV");
		reportInput.setDbPassword("AIS_DEV"); 
		reportInput.setSqlStatement("select atl_id, atl_declaration_id, atl_declaration_type , atl_quantity from at_logs order by atl_id");
		report.setIn(reportInput);
		
		report.addDataColumn(new DefaultDataColumn("atl_id", 0));
		report.addDataColumn(new DefaultDataColumn("atl_declaration_id", 1)); 
		report.addDataColumn(new DefaultDataColumn("atl_declaration_type", 2));
		report.addDataColumn(new DefaultDataColumn("atl_quantity", 3));
				
		report.setOut(new HtmlOutput("./output/ReportFromDbQuery.html"));
		
		report.execute();
	}
}
