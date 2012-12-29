/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.List;

import net.sf.reportengine.config.HorizontalAlign;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.config.SecondProcessDataColumn;
import net.sf.reportengine.config.SecondProcessTotalColumn;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.CtMetadata;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class CrosstabHeaderOutputInitStep implements IAlgorithmInitStep {
	
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(CrosstabHeaderOutputInitStep.class);
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep#init(net.sf.reportengine.core.algorithm.IAlgorithmContext)
	 */
	public void init(IReportContext reportContext) {
		IReportOutput reportOutput = (IReportOutput)reportContext.getOutput();
		List<IDataColumn> dataColumns = (List<IDataColumn>)reportContext.get(ContextKeys.CONTEXT_KEY_DATA_COLUMNS);
		List<IGroupColumn> groupColumns = (List<IGroupColumn>)reportContext.get(ContextKeys.CONTEXT_KEY_GROUPING_COLUMNS); 
		//ICrosstabHeaderRow[] headerRows = (ICrosstabHeaderRow[])reportContext.get(ContextKeys.CONTEXT_KEY_CROSSTAB_HEADER_ROWS); 
		CtMetadata ctMetadata = (CtMetadata)reportContext.get(ContextKeys.CONTEXT_KEY_CROSSTAB_METADATA);
		
//		reportOutput.startRow(); 
//		if(groupColumns != null && groupColumns.length > 0){
//			//we skip the first data column (obtained from an initial grouping column)
//			for (int col = 0; col < groupColumns.length; col++) {
//				reportOutput.output(new CellProps(groupColumns[col].getHeader(), 1, ctMetadata.getHeaderRowsCount(), ReportContent.COLUMN_HEADER));		
//			}
//		}
//		
//		//we skip the first data column (obtained from an initial grouping column)
//		for (int col = 1; col < dataColumns.length; col++) {
//			reportOutput.output(new CellProps(dataColumns[col].getHeader(), 1, 1, ReportContent.COLUMN_HEADER)); 				
//		}
//		
//		reportOutput.endRow(); 
		
		//next rows 
		outputHeaderRowsByLoopingColumns(reportOutput, ctMetadata, dataColumns, groupColumns); 
		
	}
	
	
	private void outputHeaderRowsByLoopingColumns(	IReportOutput reportOutput, 
													CtMetadata ctMetadata, 
													List<IDataColumn> dataCols, 
													List<IGroupColumn> groupCols){
		for (int row = 0; row < ctMetadata.getHeaderRowsCount(); row++) {
			reportOutput.startRow(new RowProps(ReportContent.COLUMN_HEADER)); 
			
			//handle grouping columns header first: only the last row will contain something
			//while the first will be empty
			if(row == ctMetadata.getHeaderRowsCount()-1){
				//if last header row write the normal column headers
				if(groupCols != null && groupCols.size() > 0){
					for (int i = 0; i < groupCols.size(); i++) {
						reportOutput.output(new CellProps.Builder(groupCols.get(i).getHeader())
													.colspan(1)
													.contentType(ReportContent.COLUMN_HEADER)
													.horizAlign(HorizontalAlign.CENTER)
													.build()); 
					}
				}
				//output header for SecondCrossta ... 
				reportOutput.output(new CellProps.Builder(dataCols.get(0).getHeader())
											.colspan(1)
											.contentType(ReportContent.COLUMN_HEADER)
											.horizAlign(HorizontalAlign.CENTER)
											.build());
			}else{
				//if last header row: 
				if(groupCols != null && groupCols.size() > 0){
					for (int i = 0; i < groupCols.size(); i++) {
						reportOutput.output(new CellProps.Builder(IReportOutput.WHITESPACE).build()); 
					}
				}
				//output header for SecondCrossta ... 
				reportOutput.output(new CellProps.Builder(IReportOutput.WHITESPACE).build());
			}
			
			
			//now data columns
			int colspan = 1;
			if(row < ctMetadata.getHeaderRowsCount()-1){
				colspan = ctMetadata.getColspanForLevel(ctMetadata.getHeaderRowsCount()-2);
			}
			int currentColumn = 1; 
			while(currentColumn < dataCols.size()){
				IDataColumn currentDataColumn = dataCols.get(currentColumn);
				
				if(currentDataColumn instanceof SecondProcessDataColumn){
					SecondProcessDataColumn currDataColumnAsSPDC = (SecondProcessDataColumn)currentDataColumn; 
					Object value = ctMetadata.getDistincValueFor(row, currDataColumnAsSPDC.getPosition()[row]);
					reportOutput.output(new CellProps.Builder(value)
												.colspan(colspan)
												.contentType(ReportContent.COLUMN_HEADER)
												.horizAlign(currDataColumnAsSPDC.getHorizAlign())
												.build());
					currentColumn += colspan; 
				}else{
					if(currentDataColumn instanceof SecondProcessTotalColumn){
						SecondProcessTotalColumn currDataColumnAsSPTC = (SecondProcessTotalColumn)currentDataColumn; 
						int[] position = currDataColumnAsSPTC.getPosition();
						
						if(position != null){
							if(row < position.length){
								Object value = ctMetadata.getDistincValueFor(row, position[row]);
								reportOutput.output(new CellProps.Builder(value)
															.colspan(1)
															.contentType(ReportContent.COLUMN_HEADER)
															.horizAlign(currDataColumnAsSPTC.getHorizAlign())
															.build());
							}else{
								if(row == position.length){
									reportOutput.output(new CellProps.Builder("Total")
																.contentType(ReportContent.COLUMN_HEADER)
																.horizAlign(HorizontalAlign.CENTER)
																.build());
								}else{
									reportOutput.output(new CellProps.Builder(IReportOutput.WHITESPACE).build());
								}
							}
						}else{
							//grand total
							if(row == 0){
								reportOutput.output(new CellProps.Builder("Grand Total")
															.contentType(ReportContent.COLUMN_HEADER)
															.horizAlign(HorizontalAlign.LEFT)
															.build());
							}else{
								reportOutput.output(new CellProps.Builder(IReportOutput.WHITESPACE).build());
							}
						}
						currentColumn++;
					}else{
						throw new IllegalArgumentException("there's no handler for "+currentDataColumn.getClass()); 
					}
				}
			}//end while 
			reportOutput.endRow(); 
		}
	}
}