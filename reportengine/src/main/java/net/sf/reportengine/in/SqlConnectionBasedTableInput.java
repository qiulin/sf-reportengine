package net.sf.reportengine.in;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlConnectionBasedTableInput extends AbstractTableInput implements ColumnMetadataHolder {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SqlConnectionBasedTableInput.class);
	
	/**
     * the connection to the database
     */
    private final Connection dbConnection; 
    
    /**
     * if true the lifecycle of dbConnection (closing) is controlled by this class
     */
    private final boolean closeConnectionWhenDone ; 
    
    /**
     * the sql statement
     */
    private final String sqlStatement; 
   
    
    /**
     * a helpder table input on which all method request will be delegated
     */
    private JdbcResultsetTableInput resultSetTableInput; 
    
    /**
     * Builds the sql input based on the provided connection which will be managed (closed) externally. 
     * If you want to control the lifecycle of the connection you should use the other constructor.
     * Besides the connection you have to provide a query statement using <source>setSqlStatement(String)</source>
     * 
     * @param conn      the connection provided 
     */
    public SqlConnectionBasedTableInput(Connection conn, String sqlStatement){
        this(conn, sqlStatement, false);  
    }
    
    /**
     * Builds the sql input based on the provided connection which will be managed (closed) 
     * according to the managedConnection flag.
     * Besides the connection you have to provide a query statement using <source>setSqlStatement(String)</source>
     * 
     * @param conn      the connection provided 
     * @param sqlStmt	the sql statement 
     * @param managedConnection 	if true the connection will be managed (close) by this class
     */
    public SqlConnectionBasedTableInput(Connection conn, 
    									String sqlStmt, 
    									boolean closeConnectionWhenDone){
        this.dbConnection = conn;
        this.sqlStatement = sqlStmt; 
        this.closeConnectionWhenDone = closeConnectionWhenDone; 
    }
    
    
    /**
     * opens the Input in order to start reading from it
     */
    public void open() {
    	super.open();
    	try {
    		resultSetTableInput = new JdbcResultsetTableInput(executeSql()); 
    		resultSetTableInput.open();	            
		} catch (SQLException e) {
			closeDbConnection();
			throw new TableInputException(e); 
		}     
    }
    
    /**
     * Closes the input meaning : "the reading session it's done !"
     */
    public void close(){
    	if(resultSetTableInput != null){
        	resultSetTableInput.close();
        }
        closeDbConnection();
        super.close();
    }
    
    /**
     * closes the result set and the database connection
     */
	private void closeDbConnection() {
		try {
            if(closeConnectionWhenDone && dbConnection != null && !dbConnection.isClosed()){
            	LOGGER.info("closing the db connection .. "); 
                dbConnection.close();
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
    public List<Object> nextRow() {
       return resultSetTableInput.nextRow(); 
    }
    
    /**
     * returns true if there are more rows to read
     */
    public boolean hasMoreRows() {
        return resultSetTableInput.hasMoreRows(); 
        
    }
    
    /**
     * 
     * @return
     * @throws SQLException
     */
    private ResultSet executeSql() throws SQLException{
    	LOGGER.info("executing query {} ", sqlStatement);
    	PreparedStatement stmt = dbConnection.prepareStatement(sqlStatement,   
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

		
		return stmt.executeQuery();
    }
    
    /**
     * 
     */
    public List<ColumnMetadata> getColumnMetadata() {
		List<ColumnMetadata> result = null; 
		if(isOpen()){
			result = resultSetTableInput.getColumnMetadata(); 
		}else{
			throw new TableInputException("Before calling SqlTableInput.getColumnMetadata() you have to open the input"); 
		}
		return result; 
	}
    
}
