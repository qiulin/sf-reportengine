/**
 * 
 */
package net.sf.reportengine.out;

import java.util.ArrayList;

/**
 * This output class keeps an in-memory array of objects which can be later returned 
 * by calling {@link #getDataCellMatrix()}. 
 * Only for testing purposes. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.7
 */
public class CellPropsArrayOutput implements ReportOutput {
	
	private TitleProps titleProps = null; 
	private ArrayList<CellProps[]> dataCellMatrix;
	private ArrayList<CellProps> currentRowOfCells;
	private ArrayList<CellProps[]> headerCellMatrix; 
	
	public CellPropsArrayOutput(){
		this.dataCellMatrix = new ArrayList<CellProps[]>();
		this.headerCellMatrix = new ArrayList<CellProps[]>();
	}
	
	public void open() {}
	
	public void outputTitle(TitleProps titleProperties){
		this.titleProps = titleProperties; 
	}
	
	public void startReport(ReportProps props){
		
	}
	
	public void endReport(){
		
	}
	
	public void startHeaderRow(RowProps props){
		currentRowOfCells = new ArrayList<CellProps>(); 
	}
	
	public void endHeaderRow(){
		CellProps[] completeRow = currentRowOfCells.toArray(new CellProps[currentRowOfCells.size()]);
		headerCellMatrix.add(completeRow);
	}
	
	public void outputHeaderCell(CellProps props){
		currentRowOfCells.add(props); 
	}
	
	public void outputDataCell(CellProps cellProps) {
		currentRowOfCells.add(cellProps);
	}
	
	public void startDataRow(RowProps rowProperties){
		currentRowOfCells = new ArrayList<CellProps>();
	}
	
	public void endDataRow(){
		CellProps[] completeRow = currentRowOfCells.toArray(new CellProps[currentRowOfCells.size()]);
		dataCellMatrix.add(completeRow);
	}
	
	public CellProps[][] getHeaderCellMatrix(){
		CellProps[][] result = new CellProps[headerCellMatrix.size()][];
		for(int i = 0; i < headerCellMatrix.size(); i++){
			result[i] = headerCellMatrix.get(i);
		}
		return result;
	}
	
	public CellProps[][] getDataCellMatrix(){
		CellProps[][] result = new CellProps[dataCellMatrix.size()][];
		for(int i = 0; i < dataCellMatrix.size(); i++){
			result[i] = dataCellMatrix.get(i);
		}
		return result;
	}

	public void close() {}
}
