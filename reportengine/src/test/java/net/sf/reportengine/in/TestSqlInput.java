/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.reportengine.in;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestSqlInput  {
    
    private static final Object[][] EXPECTED_DATA = new Object[][]{
        new Object[]{1,"USA","EAST","New York","Males","Catholic",1001},
        new Object[]{2,"USA","EAST","New York","Males","Orthodox",2001},
        new Object[]{3,"USA","EAST","New York","Females","Catholic",1002},
        new Object[]{4,"USA","EAST","New York","Females","Orthodox",2002},
        new Object[]{5,"USA","EAST","Chicago","Males","Orthodox",101},
        new Object[]{6,"USA","EAST","Chicago","Females","Muslim",101}
    };
    
    private static Connection testConnection;
    
    
    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
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

    @Test
    public void testNextAndHasMore(){
    	SqlConnectionBasedTableInput dataProvider = new SqlConnectionBasedTableInput(
    			testConnection, 
    			"select id, country, region, city, sex, religion, value from testreport t order by id");
    	
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
    }
    
    @Test
    public void testNonQuery(){
    	SqlTableInput tableInput = null; 
    	try{
    		tableInput = new SqlTableInput(
    				"jdbc:hsqldb:mem:testdb",
    				"org.hsqldb.jdbcDriver",
    				"sa",
    				"",
    				"INSERT INTO testreport VALUES(1,'USA','EAST','New York','Males','Catholic',1001)");
    		tableInput.open(); 
    		
    		fail("an error should have been thrown by the statement above"); 
    	}catch(TableInputException e){
    		assertTrue(e.getCause() instanceof java.sql.SQLException);
    		assertEquals("statement does not generate a result set", e.getCause().getMessage()); 
    	}finally{
    		tableInput.close(); 
    	}
    }
    
    @AfterClass
	public static void oneTimeTearDown() throws Exception {
		Statement createTable = testConnection.createStatement();
        createTable.execute("DROP TABLE testreport");
        
        testConnection.close(); 
	}
}
