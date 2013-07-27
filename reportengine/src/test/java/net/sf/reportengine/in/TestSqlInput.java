/*
 * Created on 01.11.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;


public class TestSqlInput extends TestCase {
    
	
    private static final Object[][] EXPECTED_DATA = new Object[][]{
        new Object[]{1,"USA","EAST","New York","Males","Catholic",1001},
        new Object[]{2,"USA","EAST","New York","Males","Orthodox",2001},
        new Object[]{3,"USA","EAST","New York","Females","Catholic",1002},
        new Object[]{4,"USA","EAST","New York","Females","Orthodox",2002},
        new Object[]{5,"USA","EAST","Chicago","Males","Orthodox",101},
        new Object[]{6,"USA","EAST","Chicago","Females","Muslim",101}
    };
    
    private static Connection testConnection;
    
    static{
    	 try {
    		 Class.forName("org.hsqldb.jdbcDriver");
		
	         testConnection = DriverManager.getConnection("jdbc:hsqldb:mem:testdb","sa","");
	         testConnection.setAutoCommit(false);
	         
	         Statement createTable = testConnection.createStatement();
	         createTable.execute("CREATE TABLE testreport(Id INTEGER PRIMARY KEY, country VARCHAR(50),  region VARCHAR(50),  city VARCHAR(50),  sex VARCHAR(10),  religion VARCHAR(50),  value INTEGER)");
	         
	         Statement insertStatement = testConnection.createStatement();
	         insertStatement.addBatch("INSERT INTO testreport VALUES(1,'USA','EAST','New York','Males','Catholic',1001)");
	         insertStatement.addBatch("INSERT INTO testreport VALUES(2,'USA','EAST','New York','Males','Orthodox',2001)");
	         insertStatement.addBatch("INSERT INTO testreport VALUES(3,'USA','EAST','New York','Females','Catholic',1002)");
	         insertStatement.addBatch("INSERT INTO testreport VALUES(4,'USA','EAST','New York','Females','Orthodox',2002)");
	         insertStatement.addBatch("INSERT INTO testreport VALUES(5,'USA','EAST','Chicago','Males','Orthodox',101)");
	         insertStatement.addBatch("INSERT INTO testreport VALUES(6,'USA','EAST','Chicago','Females','Muslim', 101)");
	         insertStatement.executeBatch();
	         
    	} catch (ClassNotFoundException e) {
 			e.printStackTrace();
 		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    
    
    protected void setUp() throws Exception {
        super.setUp();
    }

    
    public void testNextAndHasMore(){
    	SqlInput dataProvider = new SqlInput(testConnection);
    	dataProvider.setSqlStatement("select id, country, region, city, sex, religion, value from testreport t order by id");
    	
        int currentRow = 0;
        try {
            dataProvider.open();
            while(dataProvider.hasMoreRows()){
                List<Object> nextRow = dataProvider.nextRow();
                assertTrue(nextRow.equals(Arrays.asList(EXPECTED_DATA[currentRow])));
                currentRow++;
            }           
            dataProvider.close();            
        } catch (Throwable e) {
            e.printStackTrace();
            fail(e.getMessage()); 
        }
        assertEquals(currentRow, EXPECTED_DATA.length);
        assertNotNull(testConnection); 
        
        try {
        	//since testConnection is an external managed connection we need to make 
        	//sure the connection is not closed by the SqlInput
			assertFalse(testConnection.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("error encountered while checking if db connection is closed"); 
		}
        
        //closing manually the connection so that all other 
        //methods can re-create a connection ( for some reason the 
        //hsqldb doesn't accept more than one connection a a time)
        try {
			testConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
    
    public void testNonQuery(){
    	SqlInput dataProvider = null; 
    	try{
    		dataProvider = new SqlInput(); 
    		dataProvider.setDbConnString("jdbc:hsqldb:mem:testdb");
    		dataProvider.setDbDriverClass("org.hsqldb.jdbcDriver");
    		dataProvider.setDbUser("sa");
    		dataProvider.setDbPassword("");
    	
    		dataProvider.setSqlStatement("INSERT INTO testreport VALUES(1,'USA','EAST','New York','Males','Catholic',1001)");
    		dataProvider.open(); 
    		
    		fail("an error should have been thrown by the statement above"); 
    	}catch(Throwable e){
    		assertTrue(e.getCause() instanceof java.sql.SQLException);
    		assertEquals("statement does not generate a result set", e.getCause().getMessage()); 
    	}finally{
    		dataProvider.close(); 
    	}
    }
   
    public void testReadMetadata(){
    	SqlInput dataProvider = new SqlInput();
    	dataProvider.setSqlStatement("select id, country, region, city, sex, religion, value from testreport t order by id");
    	dataProvider.setDbConnString("jdbc:hsqldb:mem:testdb");
		dataProvider.setDbDriverClass("org.hsqldb.jdbcDriver");
		dataProvider.setDbUser("sa");
		dataProvider.setDbPassword("");
    	dataProvider.open(); 
    	
    	assertNotNull(dataProvider.getColumnMetadata()); 
    	assertEquals(7, dataProvider.getColumnMetadata().size());
    	assertEquals("ID", dataProvider.getColumnMetadata().get(0).getColumnLabel());
    	
    	dataProvider.close(); 
    }
   
}
