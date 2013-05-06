/*
 * Created on 29.10.2005
 * Author : dragos balan 
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
public class SqlInput extends AbstractReportInput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SqlInput.class);
	
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
     * columns count
     */
    private int columnsCount = Integer.MIN_VALUE;
    
    /**
     * the result set holding query resutls
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
    public SqlInput(){}
    
    /**
     * Use this constructor if you have a connection pooling system and you want to provide your own connection.
     * Besides the connection you have to provide a query statement using <source>setSqlStatement(String)</source>
     * @param conn      the connection provided 
     */
    public SqlInput(Connection conn){
        this.dbConnection = conn;
    }
    
    
    /**
     * this is the preffered way to construct this provider
     * @param dbConnString      the database connection string 
     * @param driverClass       the driver fully qualified class name
     * @param dbUser            the database user
     * @param dbPassword        database password
     */
    public SqlInput(	String dbConnString, 
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
     * @throws ReportInputException
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
			readMetaData();
			hasMoreRows = resultSet.first();	            
		} catch (SQLException e) {
			closeDbConnection();
			throw new ReportInputException(e); 
		} catch(ClassNotFoundException e){
			closeDbConnection();
			throw new ReportInputException("the driver class could not be found in classpath", e); 
		}
    }
    
    /**
     * Closes the input meaning : "the reading session it's done !"
     */
    public void close() throws ReportInputException {
    	super.close();
        closeDbConnection();
    }
    
    /**
     * closes the result set and the database connection
     */
	private void closeDbConnection() {
		try {
            if(resultSet != null ){
                resultSet.close();
            }
            if(dbConnection != null && !dbConnection.isClosed()){
                dbConnection.close();
            }
        } catch (SQLException e) {
            throw new ReportInputException("SQL Error when closing Input ! See the cause !", e);            
        }
	}
    
    /**
     * returns the number of columns
     */
    public int getColumnsCount(){
        return columnsCount;
    }
    
    /**
     * returns the next row
     */
    public List<Object> nextRow() throws ReportInputException {
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
            throw new ReportInputException("An SQL Exception occured !", e);
        }
        return nextRow;
    }
    
    /**
     * returns true if there are more rows to read
     */
    public boolean hasMoreRows() {
        return hasMoreRows;
        
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
     * Use this method to avoid the lazy database connection construction  
     * Please note that when using this method you are not required to 
     * provide dbUser, password or any other parameters 
     * 
     * @param conn  the connection 
     */
    public void setConnection(Connection conn){
        this.dbConnection = conn;
    }
    
    /**
     * getter for database connection
     * @return  the database connectio 
     */
    public Connection getConnection(){
        return this.dbConnection;
    }
    
    /**
     * reads the metadata from the result set for getting the 
     * column names, column types and other 
     * 
     * @throws ReportInputException
     */
    private void readMetaData() throws SQLException, ClassNotFoundException{
    	LOGGER.debug("reading input metadata..."); 
        if(dbConnection == null){
            initDBConnection();
        }
        
        PreparedStatement stmt = dbConnection.prepareStatement(sqlStatement,   
                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
        
        //execute the statement : the return should be true 
        //if it's a select query or false if it's an update
        resultSet = stmt.executeQuery();
        
        columnsCount = resultSet.getMetaData().getColumnCount(); 
        setColumnMetadata(extractMetadata(resultSet.getMetaData())); 
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
}
