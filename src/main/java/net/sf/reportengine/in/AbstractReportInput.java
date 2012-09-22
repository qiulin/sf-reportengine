/*
 * Created on 29.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.util.Arrays;

/**
 * Abstract implementation for IReportInput.
 * Use this class as starter for any other ReportInput implementation you may think of. 
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
public abstract class AbstractReportInput implements IReportInput {

   
    public final static String DEFAULT_COLUMN_HEADER = "Column";

    //private String[] columnHeaders;

    //private int[] columnTypes;

    private boolean isOpen = false;

    //public abstract Object[] nextRow() throws ReportInputException;

    //public abstract boolean hasMoreRows() ;

    /**
     * 
     */
    public void open() throws ReportInputException{
        if(isOpen){
            throw new IllegalStateException("You cannot open twice the same input. Close it and then reopen it !");
        }
        isOpen = true;
    }

    /**
     * 
     */
    public void close() throws ReportInputException {
        if(!isOpen){
            throw new IllegalStateException("You cannot close an input which is not open !");
        }
        isOpen = false;
    }

   

//    /**
//     * colums headers setter
//     * @param columnHeaders
//     */
//    public void setColumnHeaders(String[] columnHeaders){
//        this.columnHeaders = columnHeaders;
//    }

    

//    public int[] getColumnTypes(){
//        if(columnTypes == null && getColumnsCount()>0){
//            columnTypes = new int[getColumnsCount()];
//            Arrays.fill(columnTypes, java.sql.Types.VARCHAR);
//        }
//        return columnTypes;
//    }
//
//    public void setColumnTypes(int[] types){
//        this.columnTypes = types;
//    }

}
