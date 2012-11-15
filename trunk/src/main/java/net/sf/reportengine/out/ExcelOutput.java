/*
 * Created on 19.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

import net.sf.reportengine.config.HorizontalAlign;
import net.sf.reportengine.core.ReportEngineRuntimeException;

import org.apache.log4j.Logger;
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


/**
 * A simple implementation of the AbstractReportOut having 
 * as result an Excel file. 
 * This is based on jakarta's poi library for excel format.
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
public class ExcelOutput implements IReportOutput {
    
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(ExcelOutput.class);
	
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
    
    private OutputStream outStream; 
    
    private int rowCount = 0;
    
    
    public ExcelOutput(String fileName){
    	try {
			outStream  = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			throw new ReportEngineRuntimeException(e); 
		}
    }
    
    
    /**
     * constructor 
     * @param out     the output stream
     */
    public ExcelOutput(OutputStream out){
        //super(out);
    	outStream = out; 
    }
    
    
    
//    /**
//     * 
//     * @param writer
//     */
//    public ExcelOutput(Writer writer){
//    	super(writer);
//    }

    /**
     * starts the report
     */
    public void open() {
    	logger.trace("opening excel output");
        //super.open();
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
        DEFAULT_HEADER_CELL_STYLE = workBook.createCellStyle();
        DEFAULT_HEADER_CELL_STYLE.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        DEFAULT_HEADER_CELL_STYLE.setFont(columnHeaderFont);
        DEFAULT_HEADER_CELL_STYLE.setFillForegroundColor(HSSFColor.CORNFLOWER_BLUE.index);
        DEFAULT_HEADER_CELL_STYLE.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    }
    
    /**
     * ends the current line and creates a new one
     */
    public void startRow(RowProps rowProperties) {
        //super.startRow(rowProperties);
    	rowCount++;
        currentRow = sheet.createRow((short) rowCount-1);
        currentCol = 0;
    }
    
    public void endRow(){
        //super.endRow();
    }
    
    /**
     * output the specified value 
     */
    public void output(CellProps algProps) {
        int colspan = algProps.getColspan();
        //int content = algProps.getContentType();
        HSSFCell cell = currentRow.createCell((short) getCurrentCol());
        HSSFCellStyle cellStyle = null;
        
        //setting the style depending on the content type
        //if(content == ReportConstants.CONTENT_COLUMN_HEADERS){
        //	cellStyle = DEFAULT_HEADER_CELL_STYLE;
        //}else{
            if(rowCount % 2 == 0 ){
                cellStyle = DEFAULT_EVEN_ROW_CELL_STYLE;
            }else{
                cellStyle = DEFAULT_ODD_ROW_CELL_STYLE;
            }
         //}
        
            
        cellStyle.setAlignment(translateHorizAlign(algProps.getHorizontalAlign()));
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        cell.setCellStyle(cellStyle);
        
        String valueToWrite = algProps.getValue() != null ? algProps.getValue().toString() : " ";
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
                               (short)getCurrentCol(), 
                               rowCount-1,
                               (short) ((getCurrentCol() + colspan) - 1)));
        }
        currentCol += colspan;
    }

    /**
     * ends the report
     */
    public void close() {
    	logger.trace("closing excel output");
        try {
        	//OutputStream writerOutputStream = new WriterOutputStream(getWriter());  
        	workBook.write(outStream);
        	//writerOutputStream.close(); 
            outStream.close();
            //super.close();
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
    
    private short translateHorizAlign(HorizontalAlign horizAlign){
        return HorizontalAlign.CENTER.equals(horizAlign)? HSSFCellStyle.ALIGN_CENTER :
        					HorizontalAlign.LEFT.equals(horizAlign) ?  HSSFCellStyle.ALIGN_LEFT : HSSFCellStyle.ALIGN_RIGHT;
    }
    
    
    public int getCurrentCol(){
        return this.currentCol;
    }
}
