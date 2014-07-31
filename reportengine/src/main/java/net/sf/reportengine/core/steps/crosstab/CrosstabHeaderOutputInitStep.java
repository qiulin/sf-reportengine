/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.SecondProcessDataColumn;
import net.sf.reportengine.config.SecondProcessDataColumnFromOriginalDataColumn;
import net.sf.reportengine.config.SecondProcessTotalColumn;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.steps.AbstractCrosstabInitStep;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.out.TitleProps;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.IOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * displays the column headers for the crosstab report
 * 
 * @author dragos balan
 * @since 0.4
 */
public class CrosstabHeaderOutputInitStep extends AbstractCrosstabInitStep{
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CrosstabHeaderOutputInitStep.class);
	
	/**
	 * 
	 */
	@Override protected void executeInit() {
		outputTitle(getReportTitle(), 
					getDataColumnsLength() + getGroupColumnsLength(), 
					getReportOutput());
		outputHeaderRows(	getReportOutput(), 
							getCrosstabMetadata(), 
							getDataColumns(), 
							getGroupColumns()); 
	}
	
	
	@Override public List<DataColumn> getDataColumns(){
		return (List<DataColumn>)getAlgoContext().get(ContextKeys.INTERNAL_DATA_COLS); 
	}
	
	@Override public List<GroupColumn> getGroupColumns(){
		return (List<GroupColumn>)getAlgoContext().get(ContextKeys.INTERNAL_GROUP_COLS); 
	}
	
	/**
	 * 
	 * @param title
	 * @param reportOutput
	 */
	private void outputTitle(	String reportTitle, 
								int colspan,  
								ReportOutput reportOutput){
		if(reportTitle != null){
        	reportOutput.outputTitle(new TitleProps(reportTitle, colspan));
        }
	}
	
	/**
	 * 
	 * @param reportOutput
	 * @param ctMetadata
	 * @param dataCols
	 * @param groupCols
	 */
	private void outputHeaderRows(	ReportOutput reportOutput, 
									CtMetadata ctMetadata, 
									List<DataColumn> dataCols, 
									List<GroupColumn> groupCols){
		//loop through all header rows
		for (int currHeaderRow = 0; currHeaderRow < ctMetadata.getHeaderRowsCount(); currHeaderRow++) {
			reportOutput.startHeaderRow(new RowProps(currHeaderRow)); 
			
			boolean isLastHeaderRow = currHeaderRow == ctMetadata.getHeaderRowsCount()-1; 
			
			//1. handle grouping columns header first 
			displayHeaderForGroupingCols(	groupCols, 
											reportOutput,
											isLastHeaderRow, 
											currHeaderRow);
			
			//2. now loop through data columns
			int currentColumn = 0; 
			while(currentColumn < dataCols.size()){
				DataColumn currentDataColumn = dataCols.get(currentColumn);
				
				//if this column is a column created during 
				if(currentDataColumn instanceof SecondProcessDataColumn){
					int colspan = displayDataColumnHeader(	(SecondProcessDataColumn)currentDataColumn,
															reportOutput,
															ctMetadata, 
															currHeaderRow, 
															isLastHeaderRow);
					currentColumn += colspan; 
				}else{
					if(currentDataColumn instanceof SecondProcessTotalColumn){
						displayHeaderForTotalColumn((SecondProcessTotalColumn)currentDataColumn,
													reportOutput, 
													ctMetadata, 
													currHeaderRow, 
													getCrosstabData().getCalculator().getLabel());
						currentColumn++;
					}else{
						if(currentDataColumn instanceof SecondProcessDataColumnFromOriginalDataColumn){
							displayHeaderForOriginalDataColumn(	currentDataColumn, 
																reportOutput,
																isLastHeaderRow, 
																currHeaderRow);
							currentColumn++; 
						}else{
							//no other type of data column is accepted
							throw new IllegalArgumentException("there's no handler for "+currentDataColumn.getClass());
						}
					}
				}
			}//end while 
			reportOutput.endHeaderRow(); 
		}
	}

	/**
	 * displays the headers for group columns
	 * @param groupCols
	 * @param reportOutput
	 * @param isLastHeaderRow
	 */
	private void displayHeaderForGroupingCols(	List<GroupColumn> groupCols,
												ReportOutput reportOutput, 
												boolean isLastHeaderRow,
												int rowNumber) {
		//if last header row write the normal column headers
		if(groupCols != null && groupCols.size() > 0){
			if(isLastHeaderRow){
				//for group columns only the last header row will contain something 
				// the first will be empty	
				for (int i = 0; i < groupCols.size(); i++) {
					reportOutput.outputHeaderCell(new CellProps.Builder(groupCols.get(i).getHeader())
												.colspan(1)
												.horizAlign(HorizAlign.CENTER)
												.rowNumber(rowNumber)
												.build()); 
				}
			}else{
				//first header rows will contain only spaces (for group headers):
				for (int i = 0; i < groupCols.size(); i++) {
					reportOutput.outputDataCell(new CellProps.Builder(ReportOutput.WHITESPACE).rowNumber(rowNumber).build()); 
				}
			}
		}else{
			LOGGER.debug("no group columns headers found"); 
		}		
	}

	/**
	 * displays the header for the original data columns
	 * @param currentDataColumn
	 * @param reportOutput
	 * @param isLastHeaderRow
	 */
	private void displayHeaderForOriginalDataColumn(	DataColumn currentDataColumn, 
														ReportOutput reportOutput,
														boolean isLastHeaderRow, 
														int rowNumber) {
		//only on the last header row we display the header values for the original data columns
		if(isLastHeaderRow){
			SecondProcessDataColumnFromOriginalDataColumn originalDataColumn = (SecondProcessDataColumnFromOriginalDataColumn)currentDataColumn; 
			reportOutput.outputHeaderCell(new CellProps.Builder(originalDataColumn.getHeader())
											.colspan(1)
											.horizAlign(HorizAlign.CENTER)
											.rowNumber(rowNumber)
											.build()); 
		}else{
			//first header rows will contain empty cells
			reportOutput.outputDataCell(new CellProps.Builder(ReportOutput.WHITESPACE).rowNumber(rowNumber).build());
		}
	}

	/**
	 * displays the headers for data columns of type SecondProcessTotalColumn
	 * 
	 * @param secondProcessTotalCol
	 * @param reportOutput
	 * @param ctMetadata
	 * @param currHeaderRow
	 */
	private void displayHeaderForTotalColumn(	SecondProcessTotalColumn secondProcessTotalCol,
												ReportOutput reportOutput, 
												CtMetadata ctMetadata, 
												int currHeaderRow, 
												String totalLabel) {
		int[] position = secondProcessTotalCol.getPosition();
		
		if(position != null){
			if(currHeaderRow < position.length){
				Object value = ctMetadata.getDistincValueFor(currHeaderRow, position[currHeaderRow]);
				reportOutput.outputHeaderCell(new CellProps.Builder(value)
											.colspan(1)
											.horizAlign(secondProcessTotalCol.getHorizAlign())
											.rowNumber(currHeaderRow)
											.build());
			}else{
				//if there's no position for this header row then this is a hard-coded "TOTAL" 
				if(currHeaderRow == position.length){
					reportOutput.outputHeaderCell(new CellProps.Builder(totalLabel)
												.horizAlign(HorizAlign.CENTER)
												.rowNumber(currHeaderRow)
												.build());
				}else{
					reportOutput.outputDataCell(new CellProps.Builder(ReportOutput.WHITESPACE).rowNumber(currHeaderRow).build());
				}
			}
		}else{
			//the only data column that has null positions is the grand total column
			if(currHeaderRow == 0){
				reportOutput.outputHeaderCell(new CellProps.Builder("Grand "+totalLabel)
											.horizAlign(HorizAlign.LEFT)
											.rowNumber(currHeaderRow)
											.build());
			}else{
				reportOutput.outputDataCell(CellProps.EMPTY_CELL);
			}
		}
	}

	/**
	 * displays the column header for objects of type SecondProcessDataColumn
	 * 
	 * @param secondProcDataColumn
	 * @param reportOutput
	 * @param ctMetadata
	 * @param currHeaderRow
	 * @param isLastHeaderRow
	 * 
	 * @return	the colspan
	 */
	private int displayDataColumnHeader(SecondProcessDataColumn secondProcDataColumn,
										ReportOutput reportOutput,
										CtMetadata ctMetadata, 
										int currHeaderRow, 
										boolean isLastHeaderRow ) {
		int colspan = 1;
		if(!isLastHeaderRow){
			//for all rows except the last header row we read the colspan
			colspan = ctMetadata.getColspanForLevel(ctMetadata.getHeaderRowsCount()-2);
		}
		
		Object value = ctMetadata.getDistincValueFor(currHeaderRow, secondProcDataColumn.getPosition()[currHeaderRow]);
		reportOutput.outputHeaderCell(new CellProps.Builder(value)
									.colspan(colspan)
									.horizAlign(secondProcDataColumn.getHorizAlign())
									.rowNumber(currHeaderRow)
									.build());
		return colspan;
	}
}