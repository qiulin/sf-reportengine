/**
 * 
 */
package net.sf.reportengine.out;

import java.util.ArrayList;

/**
 * This output class keeps an in-memory array of objects which can be later returned 
 * by calling {@link #getCellMatrix()}. 
 * Only for testing purposes. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.7
 */
public class CellPropsArrayOutput implements IReportOutput {
	
	private TitleProps titleProps = null; 
	private ArrayList<CellProps[]> cellMatrix;
	private ArrayList<CellProps> currentRowOfCells;
	
	public CellPropsArrayOutput(){
		this.cellMatrix = new ArrayList<CellProps[]>();
	}
	
	public void open() {}
	
	public void outputTitle(TitleProps titleProperties){
		this.titleProps = titleProperties; 
	}
	
	
	public void output(CellProps cellProps) {
		currentRowOfCells.add(cellProps);
	}
	
	public void startRow(RowProps rowProperties){
		currentRowOfCells = new ArrayList<CellProps>();
	}
	
	public void endRow(){
		CellProps[] completeRow = currentRowOfCells.toArray(new CellProps[currentRowOfCells.size()]);
		cellMatrix.add(completeRow);
	}
	
	public CellProps[][] getCellMatrix(){
		CellProps[][] result = new CellProps[cellMatrix.size()][];
		for(int i = 0; i < cellMatrix.size(); i++){
			result[i] = cellMatrix.get(i);
		}
		return result;
	}

	public void close() {}
}
