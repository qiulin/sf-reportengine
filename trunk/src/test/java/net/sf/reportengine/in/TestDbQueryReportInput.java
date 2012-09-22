/*
 * Created on 01.11.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import junit.framework.TestCase;
import net.sf.reportengine.util.ReportEngineArrayUtils;


public class TestDbQueryReportInput extends TestCase {
    
    private static final String[][] EXPECTED_DATA = new String[][]{
        new String[]{"1","USA","EAST","New York","Males","Catholic","1001"},
        new String[]{"2","USA","EAST","New York","Males","Orthodox","2001"},
        //new String[]{"3","USA","EAST","New York","Males","Catholic","1001"},
        new String[]{"3","USA","EAST","New York","Females","Catholic","1002"},
        new String[]{"4","USA","EAST","New York","Females","Orthodox","2002"},
        new String[]{"5","USA","EAST","Chicago","Males","Orthodox","101"},
        new String[]{"6","USA","EAST","Chicago","Females","Muslim","101"}
//        ,
//        new String[]{"8","USA","WEST","Washington","Males","Orthodox","501"},
//        new String[]{"9","USA","WEST","Washington","Males","Muslim","502"},
//        new String[]{"10","USA","WEST","Washington","Females","Catholic","50"},
//        new String[]{"11","USA","SOUTH","Dallas","Males","Catholic","100"},
//        new String[]{"12","USA","SOUTH","Dallas","Females","Muslim","200"},
//        new String[]{"13","USA","NORTH","Cincinatti","Males","Orthodox","50"},
//        new String[]{"14","USA","NORTH","Cincinatti","Females","Orthodox","50"}
    };
    
    private DbQueryReportInput dataProvider;
    private Connection testConnection;
    
    
    protected void setUp() throws Exception {
        super.setUp();
        dataProvider = new DbQueryReportInput();
//        dataProvider.setDbConnString("jdbc:hsqldb:mem:testdb");
//        dataProvider.setDbDriverClass("org.hsqldb.jdbcDriver");
//        dataProvider.setDbUser("sa");
//        dataProvider.setDbPassword("");
        dataProvider.setSqlStatement("select id, country, region, city, sex, religion, value from testreport t order by id");
        
        
        Class.forName("org.hsqldb.jdbcDriver");
        testConnection = DriverManager.getConnection("jdbc:hsqldb:mem:testdb","sa","");
        testConnection.setAutoCommit(false);
        
        Statement createTable = testConnection.createStatement();
        createTable.execute("CREATE TABLE testreport(Id INTEGER PRIMARY KEY, country VARCHAR(50),  region VARCHAR(50),  city VARCHAR(50),  sex VARCHAR(10),  religion VARCHAR(50),  value INTEGER)");
        
        Statement insertStatement = testConnection.createStatement();
        insertStatement.addBatch("INSERT INTO testreport "+ 
        						"VALUES(1,'USA','EAST','New York','Males','Catholic',1001)");
        insertStatement.addBatch(
        						"INSERT INTO testreport "+
        						"VALUES(2,'USA','EAST','New York','Males','Orthodox',2001)");
        insertStatement.addBatch(						
        						"INSERT INTO testreport "+
        						"VALUES(3,'USA','EAST','New York','Females','Catholic',1002)");
        insertStatement.addBatch(
        						"INSERT INTO testreport "+
        						"VALUES(4,'USA','EAST','New York','Females','Orthodox',2002)");
        insertStatement.addBatch(
        						"INSERT INTO testreport "+
        						"VALUES(5,'USA','EAST','Chicago','Males','Orthodox',101)");
        insertStatement.addBatch(
        						"INSERT INTO testreport "+
        						"VALUES(6,'USA','EAST','Chicago','Females','Muslim', 101)");
        insertStatement.executeBatch();
        dataProvider.setConnection(testConnection);
        }

    
    public void testNextAndHasMore(){
        boolean flawless = true;
        int currentRow = 0;
        try {
            dataProvider.open();
            //dataProvider.first();
            while(dataProvider.hasMoreRows()){
                Object[] nextRow = dataProvider.nextRow();
                assertTrue(ReportEngineArrayUtils.equalArraysAsStrings(nextRow, EXPECTED_DATA[currentRow]));
                currentRow++;
            }           
            dataProvider.close();            
        } catch (Throwable e) {
            flawless = false;
            e.printStackTrace();
        }
        
        assertTrue(flawless);
        assertEquals(currentRow, EXPECTED_DATA.length);
        
        
    }
   
    
   
}
