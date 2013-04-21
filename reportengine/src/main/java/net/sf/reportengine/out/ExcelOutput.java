/*
 * Created on 19.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.ReportContent;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A simple implementation of the AbstractReportOut having 
 * as result an Excel file. 
 * This is based on jakarta's poi library for excel format.
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
public class ExcelOutput extends AbstractByteBasedOutput {
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelOutput.class);
	
    /**
     * the xls workbook
     */
    private HSSFWorkbook workBook;
    
    /**
     * the xls sheet
     */
    private HSSFSheet sheet;
    
    /**
     * the name of the sheet that holds the report
     */
    private String sheetName = "Report";
    
    /**
     * current row 
     */
    private HSSFRow currentRow;
    
    /**
     * common odd row cell style
     */
    private HSSFCellStyle DEFAULT_ODD_ROW_CELL_STYLE;
    
    /**
     * common even row cell style
     */
    private HSSFCellStyle DEFAULT_EVEN_ROW_CELL_STYLE;
    
    /**
     * the heder style
     */
    private HSSFCellStyle DEFAULT_HEADER_CELL_STYLE ;
    
    /**
     * column
     */
    private int currentCol = 0;
    
    /**
     * 
     */
    private int titleRowSpan = 0; 
    
    /**
     * 
     */
    private int headerRowSpan = 0;
    
    /**
     * outputs in memory (if no other outputStream is set)
     */
    public ExcelOutput(){
    	super(); 
    }
    
    /**
     * output into a file 
     * @param fileName
     */
    public ExcelOutput(String fileName){
    	super(fileName); 
    }
    
    
    /**
     * output into an outputStream
     * @param out     the output stream
     */
    public ExcelOutput(OutputStream out){
        super(out); 
    }
        
    /**
     * starts the report
     */
    public void open() {
    	markAsOpen(); 
    	
    	LOGGER.trace("opening excel output...");
    	
        workBook = new HSSFWorkbook();
        sheet = workBook.createSheet(sheetName);

        HSSFPalette customPalette = workBook.getCustomPalette();
        customPalette.setColorAtIndex(HSSFColor.GREY_25_PERCENT.index,(byte)245, (byte)245, (byte)245);
        
        //settings for odd row style
        DEFAULT_ODD_ROW_CELL_STYLE = workBook.createCellStyle();
        DEFAULT_ODD_ROW_CELL_STYLE.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        DEFAULT_ODD_ROW_CELL_STYLE.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        DEFAULT_ODD_ROW_CELL_STYLE.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        //settings for even row style
        DEFAULT_EVEN_ROW_CELL_STYLE = workBook.createCellStyle();
        DEFAULT_EVEN_ROW_CELL_STYLE.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        DEFAULT_EVEN_ROW_CELL_STYLE.setFillForegroundColor(HSSFColor.WHITE.index);
        DEFAULT_EVEN_ROW_CELL_STYLE.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        //settings for header style 
        HSSFFont columnHeaderFont = workBook.createFont();
        columnHeaderFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        columnHeaderFont.setColor(HSSFColor.WHITE.index); 
        DEFAULT_HEADER_CELL_STYLE = workBook.createCellStyle();
        DEFAULT_HEADER_CELL_STYLE.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        DEFAULT_HEADER_CELL_STYLE.setFont(columnHeaderFont);
        DEFAULT_HEADER_CELL_STYLE.setFillForegroundColor(HSSFColor.BLACK.index);
        DEFAULT_HEADER_CELL_STYLE.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    }
    
    /*
     * (non-Javadoc)
     * @see net.sf.reportengine.out.IReportOutput#outputTitle(net.sf.reportengine.out.TitleProps)
     */
    public void outputTitle(TitleProps titleProps){
    	currentRow = sheet.createRow(0); 
    	Cell cell = currentRow.createCell(0);
    	cell.setCellValue(titleProps.getTitle());
    	cell.setCellType(Cell.CELL_TYPE_STRING); 
    	
    	sheet.addMergedRegion(
                new Region(0, 
                           (short)0, 
                           0,
                           (short)(titleProps.getColspan()-1)));
    	titleRowSpan++; 
    }
    
    
    /**
     * ends the current line and creates a new one
     */
    public void startRow(RowProps rowProperties) {
        short currentRowNumber = 0; 
        if(rowProperties.getContent().equals(ReportContent.COLUMN_HEADER)){
        	headerRowSpan++;
        	currentRowNumber = (short)(rowProperties.getRowNumber() + titleRowSpan);  
        }else{
        	//data rows
        	currentRowNumber = (short)(rowProperties.getRowNumber() + titleRowSpan + headerRowSpan);  
        }
        currentRow = sheet.createRow(currentRowNumber);
        currentCol = 0;
    }
    
    
    /**
     * output the specified value 
     */
    public void output(CellProps cellProps) {
        int colspan = cellProps.getColspan();
        int rowCount = cellProps.getRowNumber(); 
        
        HSSFCell cell = currentRow.createCell((short) getCurrentCol());
        HSSFCellStyle cellStyle = null;
        
        //setting the style depending on the content type
        if(cellProps.getContentType().equals(ReportContent.COLUMN_HEADER)){
        	cellStyle = DEFAULT_HEADER_CELL_STYLE;
        }else{
            if(rowCount % 2 == 0 ){
                cellStyle = DEFAULT_EVEN_ROW_CELL_STYLE;
            }else{
                cellStyle = DEFAULT_ODD_ROW_CELL_STYLE;
            }
         }
        
            
        cellStyle.setAlignment(translateHorizAlign(cellProps.getHorizontalAlign()));
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        cell.setCellStyle(cellStyle);
        
        String valueToWrite = cellProps.getValue() != null ? cellProps.getValue().toString() : " ";
        try {
            BigDecimal cellValue = new BigDecimal(valueToWrite);
            cell.setCellValue(cellValue.doubleValue());
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        } catch (NumberFormatException ex) {
            cell.setCellValue(new HSSFRichTextString(valueToWrite));
        }
        
        if (colspan != 1) {
            sheet.addMergedRegion(
                    new Region(rowCount-1, 
                               (short)currentCol, 
                               rowCount-1,
                               (short) (currentCol + colspan - 1)));
        }
        currentCol += colspan;
    }
    
    public void endRow(){
    	
    }
    
    /**
     * ends the report
     */
    public void close() {
    	LOGGER.trace("closing excel output...");
        try {
        	workBook.write(getOutputStream());
            super.close();//flushes the output stream and closes the output
        } catch (IOException exc) {
        	throw new RuntimeException(exc);
        }
    }
    
    public String getSheetName(){
        return sheetName;
    }
    
    public void setSheetName(String name){
        this.sheetName = name;
    }
    
    private short translateHorizAlign(HorizAlign horizAlign){
        return HorizAlign.CENTER.equals(horizAlign)? HSSFCellStyle.ALIGN_CENTER :
        					HorizAlign.LEFT.equals(horizAlign) ?  HSSFCellStyle.ALIGN_LEFT : HSSFCellStyle.ALIGN_RIGHT;
    }
    
    public int getCurrentCol(){
        return currentCol;
    }
}
