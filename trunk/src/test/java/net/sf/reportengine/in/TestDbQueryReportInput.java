/*
 * Created on 01.11.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;

import junit.framework.TestCase;


public class TestDbQueryReportInput extends TestCase {
    
	
    private static final Object[][] EXPECTED_DATA = new Object[][]{
        new Object[]{1,"USA","EAST","New York","Males","Catholic",1001},
        new Object[]{2,"USA","EAST","New York","Males","Orthodox",2001},
        new Object[]{3,"USA","EAST","New York","Females","Catholic",1002},
        new Object[]{4,"USA","EAST","New York","Females","Orthodox",2002},
        new Object[]{5,"USA","EAST","Chicago","Males","Orthodox",101},
        new Object[]{6,"USA","EAST","Chicago","Females","Muslim",101}
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
                assertTrue(Arrays.equals(nextRow, EXPECTED_DATA[currentRow]));
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
