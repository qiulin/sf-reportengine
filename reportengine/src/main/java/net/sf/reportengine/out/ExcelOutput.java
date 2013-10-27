/*
 * Created on 19.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.OutputStream;

import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.VertAlign;

import org.apache.commons.lang.StringUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A simple implementation of the AbstractReportOut having as result an Excel file 
 * based on jakarta's poi library for excel.
 * 
 * <pre>
 * Usage: 
 * 		ReportOutput reportOutput = new ExcelOutput("c:/temp/test.xls"); 
 * </pre>
 * @author dragos balan (dragos dot balan at gmail dot com)
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
//    private HSSFCellStyle DEFAULT_HEADER_CELL_STYLE ;
    
    /**
     * column
     */
    private short currentCol = 0;
    
    /**
     * 
     */
    private int titleRowSpan = 0; 
    
    /**
     * 
     */
    private int headerRowSpan = 0;
    
    /**
     * 
     */
    private HSSFFont columnHeaderFont;
    
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
        DEFAULT_ODD_ROW_CELL_STYLE.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        DEFAULT_ODD_ROW_CELL_STYLE.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        //settings for even row style
        DEFAULT_EVEN_ROW_CELL_STYLE = workBook.createCellStyle();
        DEFAULT_EVEN_ROW_CELL_STYLE.setFillForegroundColor(HSSFColor.WHITE.index);
        DEFAULT_EVEN_ROW_CELL_STYLE.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        //settings for header style 
        columnHeaderFont = workBook.createFont();
        columnHeaderFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        columnHeaderFont.setColor(HSSFColor.WHITE.index); 
//        DEFAULT_HEADER_CELL_STYLE = workBook.createCellStyle();
//        DEFAULT_HEADER_CELL_STYLE.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        DEFAULT_HEADER_CELL_STYLE.setFont(columnHeaderFont);
//        DEFAULT_HEADER_CELL_STYLE.setFillForegroundColor(HSSFColor.BLACK.index);
//        DEFAULT_HEADER_CELL_STYLE.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    }
    
    /**
	 * empty implementation
	 */
	public void startReport(ReportProps reportProps){
		LOGGER.trace("startReport"); 
	}
	
    
    /*
     * (non-Javadoc)
     * @see net.sf.reportengine.out.ReportOutput#outputTitle(net.sf.reportengine.out.TitleProps)
     */
    public void outputTitle(TitleProps titleProps){
    	currentRow = sheet.createRow(0); 
    	
		 HSSFCellStyle cellStyle = workBook.createCellStyle();
		 cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		 cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	
    	Cell cell = currentRow.createCell(0);
    	cell.setCellValue(titleProps.getTitle());
    	cell.setCellType(Cell.CELL_TYPE_STRING); 
    	cell.setCellStyle(cellStyle); 
    	
    	sheet.addMergedRegion(
                new Region(0, 
                           (short)0, 
                           0,
                           (short)(titleProps.getColspan()-1)));
    	titleRowSpan++; 
    }
    
    public void startHeaderRow(RowProps rowProperties){
         currentRow = sheet.createRow((short)(rowProperties.getRowNumber() + titleRowSpan));
         currentCol = 0;
         
    }
    
    public void outputHeaderCell(CellProps cellProps){
    	int rowCount = cellProps.getRowNumber() + titleRowSpan; 
        
    	//LOGGER.debug("on row {} output header cell {}", rowCount, cellProps);
    	
        HSSFCell cell = currentRow.createCell(currentCol);
        HSSFCellStyle cellStyle = workBook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFont(columnHeaderFont);
        cellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        cellStyle.setAlignment(cellProps.getHorizAlign().getHssfCode());
        cellStyle.setVerticalAlignment(cellProps.getVertAlign().getHssfCode()); 
        cell.setCellStyle(cellStyle);
        
        String valueToWrite = cellProps.getValue() != null ? cellProps.getValue().toString() : WHITESPACE;
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(new HSSFRichTextString(valueToWrite));
        
        int colspan = cellProps.getColspan();
        if (colspan != 1) {
            sheet.addMergedRegion(
                    new Region(rowCount, 
                               (short)currentCol, 
                               rowCount,
                               (short) (currentCol + colspan - 1)));
            LOGGER.debug("add new reqion for colspan on header row {} until column {} ", rowCount, currentCol + colspan - 1);
        }
        currentCol += colspan;
    }
    
    public void endHeaderRow(){
    	headerRowSpan++;
    }
    
    /**
     * ends the current line and creates a new one
     */
    public void startDataRow(RowProps rowProperties) {
        short currentRowNumber = (short)(rowProperties.getRowNumber() + titleRowSpan + headerRowSpan);  
        currentRow = sheet.createRow(currentRowNumber);
        currentCol = 0;
    }
    
    
    /**
     * output the specified value 
     */
    public void outputDataCell(CellProps cellProps) {
        int rowCount = cellProps.getRowNumber() + titleRowSpan + headerRowSpan ; 
        
        //LOGGER.debug("on row {} output data cell {}", rowCount, cellProps);
        
        HSSFCell cell = currentRow.createCell(currentCol);
        HSSFCellStyle cellStyle = null;
        
        if(rowCount % 2 == 0 ){
        	//settings for even row style
            cellStyle = DEFAULT_EVEN_ROW_CELL_STYLE;
        }else{
            //settings for odd row style
        	cellStyle = DEFAULT_ODD_ROW_CELL_STYLE; 
        }
            
        cellStyle.setAlignment(cellProps.getHorizAlign().getHssfCode());
        cellStyle.setVerticalAlignment(cellProps.getVertAlign().getHssfCode());
        
        cell.setCellStyle(cellStyle);
        
        String valueToWrite = cellProps.getValue() != null ? cellProps.getValue().toString() : WHITESPACE;
        
        if(StringUtils.isNumeric(valueToWrite)){
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(valueToWrite);
        }else{
            cell.setCellValue(new HSSFRichTextString(valueToWrite));
        }
        
        int colspan = cellProps.getColspan(); 
        if (colspan != 1) {
            sheet.addMergedRegion(
                    new Region(rowCount, 
                               (short)currentCol, 
                               rowCount,
                               (short) (currentCol + colspan - 1)));
            LOGGER.debug("add new reqion for colspan on row {}", rowCount);
        }
        currentCol += colspan;
    }
    
    public void endDataRow(){}
    
    /**
	 * empty implementation
	 */
	public void endReport(){}
    
    /**
     * ends the report
     */
    public void close() {
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
}
