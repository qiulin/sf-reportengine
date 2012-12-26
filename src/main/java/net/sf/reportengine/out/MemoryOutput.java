/**
 * 
 */
package net.sf.reportengine.out;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * This output class keeps an in-memory array of objects wich can be later returned 
 * by calling {@link #getCellMatrix()}
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 * @deprecated please use one of the html, excel, fo outputters with a StringWriter in order to obtain a similar functionality
 */
public class MemoryOutput implements IReportOutput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(MemoryOutput.class);
	
	private ArrayList<CellProps[]> cellMatrix;
	private ArrayList<CellProps> currentRowOfCells;
	
	
	public MemoryOutput(){
		this.cellMatrix = new ArrayList<CellProps[]>();
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

	public void open() {}

	public void close() {}
}
