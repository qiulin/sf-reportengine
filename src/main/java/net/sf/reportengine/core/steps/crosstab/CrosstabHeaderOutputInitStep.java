/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import net.sf.reportengine.AbstractReport;
import net.sf.reportengine.CrossTabReport;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupingColumn;
import net.sf.reportengine.config.SecondProcessDataColumn;
import net.sf.reportengine.config.SecondProcessTotalColumn;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.IReportOutput;
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
	public void init(IAlgorithmContext reportContext) {
		IReportOutput reportOutput = (IReportOutput)reportContext.getOutput();
		IDataColumn[] dataColumns = (IDataColumn[])reportContext.get(AbstractReport.CONTEXT_KEY_DATA_COLUMNS);
		IGroupingColumn[] groupColumns = (IGroupingColumn[])reportContext.get(AbstractReport.CONTEXT_KEY_GROUPING_COLUMNS); 
		//ICrosstabHeaderRow[] headerRows = (ICrosstabHeaderRow[])reportContext.get(CrossTabReport.CONTEXT_KEY_CROSSTAB_HEADER_ROWS); 
		CtMetadata ctMetadata = (CtMetadata)reportContext.get(CrossTabReport.CONTEXT_KEY_CROSSTAB_METADATA);
		
//		reportOutput.startRow(); 
//		if(groupColumns != null && groupColumns.length > 0){
//			//we skip the first data column (obtained from an initial grouping column)
//			for (int col = 0; col < groupColumns.length; col++) {
//				reportOutput.output(new CellProps(groupColumns[col].getHeader(), 1, ctMetadata.getHeaderRowsCount(), ReportContent.CONTENT_COLUMN_HEADERS));		
//			}
//		}
//		
//		//we skip the first data column (obtained from an initial grouping column)
//		for (int col = 1; col < dataColumns.length; col++) {
//			reportOutput.output(new CellProps(dataColumns[col].getHeader(), 1, 1, ReportContent.CONTENT_COLUMN_HEADERS)); 				
//		}
//		
//		reportOutput.endRow(); 
		
		//next rows 
		outputHeaderRowsByLoopingColumns(reportOutput, ctMetadata, dataColumns, groupColumns); 
		
	}
	
	
	private void outputHeaderRowsByLoopingColumns(	IReportOutput reportOutput, 
									CtMetadata ctMetadata, 
									IDataColumn[] dataCols, 
									IGroupingColumn[] groupCols){
		for (int row = 0; row < ctMetadata.getHeaderRowsCount(); row++) {
			reportOutput.startRow(); 
			
			//handle grouping columns header first: only the last row will contain something
			//while the first will be empty
			if(row == ctMetadata.getHeaderRowsCount()-1){
				//if last header row write the normal column headers
				if(groupCols != null && groupCols.length > 0){
					for (int i = 0; i < groupCols.length; i++) {
						reportOutput.output(new CellProps(groupCols[i].getHeader())); 
					}
				}
				//output header for SecondCrossta ... 
				reportOutput.output(new CellProps(dataCols[0].getHeader()));
			}else{
				//if last header row: 
				if(groupCols != null && groupCols.length > 0){
					for (int i = 0; i < groupCols.length; i++) {
						reportOutput.output(new CellProps()); 
					}
				}
				//output header for SecondCrossta ... 
				reportOutput.output(new CellProps());
			}
			
			
			//now data columns
			int colspan = 1;
			if(row < ctMetadata.getHeaderRowsCount()-1){
				colspan = ctMetadata.getColspanForLevel(ctMetadata.getHeaderRowsCount()-2);
			}
			int currentColumn = 1; 
			while(currentColumn < dataCols.length){
				IDataColumn currentDataColumn = dataCols[currentColumn];
				
				if(currentDataColumn instanceof SecondProcessDataColumn){
					SecondProcessDataColumn currDataColumnAsSPDC = (SecondProcessDataColumn)currentDataColumn; 
					Object value = ctMetadata.getDistincValueFor(row, currDataColumnAsSPDC.getPosition()[row]);
					reportOutput.output(new CellProps(value, colspan));
					currentColumn += colspan; 
				}else{
					if(currentDataColumn instanceof SecondProcessTotalColumn){
						SecondProcessTotalColumn currDataColumnAsSPTC = (SecondProcessTotalColumn)currentDataColumn; 
						int[] position = currDataColumnAsSPTC.getPosition();
						
						if(position != null){
							if(row < position.length){
								Object value = ctMetadata.getDistincValueFor(row, position[row]);
								reportOutput.output(new CellProps(value));
							}else{
								if(row == position.length){
									reportOutput.output(new CellProps("Total"))	;
								}else{
									reportOutput.output(new CellProps());
								}
							}
						}else{
							//grand total
							if(row == 0){
								reportOutput.output(new CellProps("Grand Total"));
							}else{
								reportOutput.output(new CellProps());
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
