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

import static java.sql.Types.BIGINT;
import static java.sql.Types.DECIMAL;
import static java.sql.Types.DOUBLE;
import static java.sql.Types.FLOAT;
import static java.sql.Types.INTEGER;
import static java.sql.Types.LONGNVARCHAR;
import static java.sql.Types.NCHAR;
import static java.sql.Types.NUMERIC;
import static java.sql.Types.NVARCHAR;
import static java.sql.Types.REAL;
import static java.sql.Types.SMALLINT;
import static java.sql.Types.TINYINT;
import static java.sql.Types.VARCHAR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.HorizAlign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * <p>
 * Report Input implementation for database queries <br/>
 * There are two ways of setting up this data provider :<br/>
 *      Use case 1: providing database url, driver class , user and password<br/> 
 *      Use case 2: providing a connection  
 * </p> 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class SqlTableInput extends AbstractTableInput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SqlTableInput.class);
	
    /**
     * the sql query
     */
    private String sqlStatement;
    
    /**
     * database user
     */
    private String dbUser;
    
    /**
     * database password
     */
    private String dbPassword;
    
    /**
     * database connection string 
     */
    private String dbConnString;
    
    /**
     * driver class
     */
    private String dbDriverClass;
    
    /**
     * database connection 
     */
    private Connection dbConnection;
    
    /**
     * if true the lifecycle of dbConnection (opened/closed) is controlled by this class
     * or not. 
     */
    private boolean managedDbConnection = true; 
    
    /**
     * columns count
     */
    private int columnsCount = Integer.MIN_VALUE;
    
    /**
     * the result set holding query results
     */
    private ResultSet resultSet;
    
    /**
     * next row with data
     */
    private List<Object> nextRow ;
    
    /**
     * hasMoreRows flag 
     */
    private boolean hasMoreRows = false;
    
   
    
    /**
     * Empty constructor for query data provider. Just using this simple constructor it's not enough <br/>
     * for calling open() and proceed in reading data. 
     * You have to add some more properties like dbUser, dbPassword, sqlStatement or you can 
     * provide your own connection and use the <source>QueryDataProvider(Connection conn)</source>
     */
    public SqlTableInput(){}
    
    
    /**
     * Builds the sql input based on the provided connection which will be managed (closed) externally. 
     * If you want to control the lifecycle of the connection you should use the other constructor.
     * Besides the connection you have to provide a query statement using <source>setSqlStatement(String)</source>
     * 
     * @param conn      the connection provided 
     */
    public SqlTableInput(Connection conn){
        this(conn, false);  
    }
    
    /**
     * Builds the sql input based on the provided connection which will be managed (closed) 
     * according to the managedConnection flag.
     * Besides the connection you have to provide a query statement using <source>setSqlStatement(String)</source>
     * 
     * @param conn      the connection provided 
     * @param managedConnection 	if true the connection will be managed (close) by this class
     */
    public SqlTableInput(Connection conn, boolean managedConnection){
        this.dbConnection = conn;
        this.managedDbConnection = managedConnection; 
    }
    
    
    /**
     * this is the preferred way to construct this provider
     * @param dbConnString      the database connection string 
     * @param driverClass       the driver fully qualified class name
     * @param dbUser            the database user
     * @param dbPassword        database password
     */
    public SqlTableInput(	String dbConnString, 
                      		String driverClass, 
                      		String dbUser, 
                      		String dbPassword){
        this.dbConnString = dbConnString;
        this.dbDriverClass = driverClass;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    
    /**
     * initializes the connection 
     *
     * @throws TableInputException
     */
    private void initDBConnection() throws SQLException, ClassNotFoundException {
        Class.forName(dbDriverClass);
        dbConnection = DriverManager.getConnection(dbConnString, dbUser, dbPassword);
        dbConnection.setAutoCommit(false);
    }
    
    /**
     * opens the Input in order to start reading from it
     */
    public void open() {
    	super.open();
    	try {
    		if(dbConnection == null){
    			LOGGER.info("creating an internal managed connection to the database..."); 
                initDBConnection();
                managedDbConnection = true; 
            }else{
            	LOGGER.info("using provided connection..."); 
            	managedDbConnection = false; 
            }
    		
    		//executing the sql
    		resultSet = executeSql(); 
    		
    		//now reading the metadata
			setColumnMetadata(readMetaData(resultSet));
			
			hasMoreRows = resultSet.first();	            
		} catch (SQLException e) {
			closeDbConnection();
			throw new TableInputException(e); 
		} catch(ClassNotFoundException e){
			closeDbConnection();
			throw new TableInputException("the driver class could not be found in classpath", e); 
		}
    }
    
    /**
     * Closes the input meaning : "the reading session it's done !"
     */
    public void close() throws TableInputException {
        closeDbConnection();
        super.close();
    }
    
    /**
     * closes the result set and the database connection
     */
	private void closeDbConnection() {
		try {
            if(resultSet != null ){
                resultSet.close();
            }
            if(managedDbConnection && dbConnection != null && !dbConnection.isClosed()){
            	LOGGER.info("closing internally managed db connection .. "); 
                dbConnection.close();
                dbConnection = null; //so that a new connection is created if the input is opened for a second time
            }else{
            	LOGGER.info("external db connection not closed."); 
            }
        } catch (SQLException e) {
            throw new TableInputException("SQL Error when closing Input ! See the cause !", e);            
        }
	}
    
    /**
     * returns the next row
     */
    public List<Object> nextRow() throws TableInputException {
        try {
            //a hidden verification if it's open
            if (hasMoreRows()) {
                //nextRow = new Object[columnsCount];
            	nextRow = new ArrayList<Object>(columnsCount); 
                for (int i = 0; i < columnsCount; i++) {
                    //nextRow[i] = resultSet.getObject(i + 1);
                	nextRow.add(resultSet.getObject(i + 1));
                }
            }else{
                nextRow = null;
            }
            
            //moving the pointer to the next row 
            hasMoreRows = resultSet.next();
            
        } catch (SQLException e) {
        	closeDbConnection();
            throw new TableInputException("An SQL Exception occured !", e);
        }
        return nextRow;
    }
    
    /**
     * returns true if there are more rows to read
     */
    public boolean hasMoreRows() {
        return hasMoreRows;
        
    }
    
    
    private ResultSet executeSql() throws SQLException{
    	LOGGER.info("executing query {} ", sqlStatement);
    	PreparedStatement stmt = dbConnection.prepareStatement(sqlStatement,   
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

		
		return stmt.executeQuery();
    }
    
    
    /**
     * reads the metadata from the result set for getting the 
     * column names, column types and other 
     * 
     * @throws TableInputException
     */
    private List<ColumnMetadata> readMetaData(ResultSet rs) throws SQLException{
    	LOGGER.info("reading input metadata..."); 
        columnsCount = rs.getMetaData().getColumnCount(); 
        return extractMetadata(resultSet.getMetaData()); 
    }
    
    /**
     * 
     * @param rsMetadata
     * @return
     * @throws SQLException
     */
    private List<ColumnMetadata> extractMetadata(ResultSetMetaData rsMetadata) throws SQLException{
    	List<ColumnMetadata> columnMetadata = new ArrayList<ColumnMetadata>(rsMetadata.getColumnCount());
        //looping through columns to get their type
        for(int i=0; i < columnsCount; i++){
        	columnMetadata.add(new ColumnMetadata(	rsMetadata.getColumnName(i+1), 
													rsMetadata.getColumnLabel(i+1), 
													getAlignmentFromColumnType(rsMetadata.getColumnType(i+1)))); 
        }
        return columnMetadata; 
    }
    
    
    /**
     * 
     * @param colType
     * @return
     */
    private HorizAlign getAlignmentFromColumnType(int colType){
    	HorizAlign result = null; 
    	if(	colType == INTEGER || colType == BIGINT || colType == DECIMAL || colType == DOUBLE 
    		|| colType == NUMERIC || colType == FLOAT ||  colType == REAL
    		|| colType == SMALLINT || colType == TINYINT){
    		result = HorizAlign.RIGHT; 
    	}else{
    		if(colType == LONGNVARCHAR || colType == NCHAR || colType == NVARCHAR || colType == VARCHAR){
    			result = HorizAlign.LEFT; 
    		}else{
    			result = HorizAlign.CENTER; 
    		}
    	}
    	
    	return result; 
    }
    
    /**
     * @return Returns the dbConnString.
     */
    public String getDbConnString() {
        return dbConnString;
    }
    
    /**
     * @param dbConnString The dbConnString to set.
     */
    public void setDbConnString(String dbConnString) {
        this.dbConnString = dbConnString;
    }
    
    /**
     * @return Returns the dbDriverClass.
     */
    public String getDbDriverClass() {
        return dbDriverClass;
    }
    
    /**
     * @param dbDriverClass The dbDriverClass to set.
     */
    public void setDbDriverClass(String dbDriverClass) {
        this.dbDriverClass = dbDriverClass;
    }
    
    /**
     * @return Returns the dbPassword.
     */
    public String getDbPassword() {
        return dbPassword;
    }
    
    /**
     * @param dbPassword The dbPassword to set.
     */
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
    
    /**
     * @return Returns the dbUser.
     */
    public String getDbUser() {
        return dbUser;
    }
    
    /**
     * @param dbUser The dbUser to set.
     */
    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }
    
    /**
     * @return Returns the sqlStatement.
     */
    public String getSqlStatement() {
        return sqlStatement;
    }
    
    /**
     * @param sqlStatement The sqlStatement to set.
     */
    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }
    
    /**
     * database connection setter . 
     * Use this method to avoid the internal database connection construction  
     * Note 1 :When using this method you are not required to 
     * provide dbUser, password or any other parameters 
     * 
     * Note 2: this class will not not touch the connection ( no open / close)
     * 
     * @param conn  the connection 
     */
    public void setConnection(Connection conn){
        this.dbConnection = conn;
    }
}
