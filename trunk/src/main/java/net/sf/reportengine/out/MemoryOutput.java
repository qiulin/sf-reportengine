/**
 * 
 */
package net.sf.reportengine.out;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * @author dragos
 *
 */
public class MemoryOutput extends AbstractOutput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(MemoryOutput.class);
	
	private ArrayList<CellProps[]> cellMatrix;
	private ArrayList<CellProps> currentRowOfCells;
	
	
	public MemoryOutput(){
		this.cellMatrix = new ArrayList<CellProps[]>();
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.AbstractOutput#output(net.sf.reportengine.out.CellProps)
	 */
	@Override
	public void output(CellProps cellProps) {
		currentRowOfCells.add(cellProps);
	}
	
	public void startRow(){
		currentRowOfCells = new ArrayList<CellProps>();
	}
	
	public void endRow(){
		CellProps[] completeRow = currentRowOfCells.toArray(new CellProps[]{});
		cellMatrix.add(completeRow);
	}
	
	public CellProps[][] getCellMatrix(){
		CellProps[][] result = new CellProps[cellMatrix.size()][];
		for(int i = 0; i < cellMatrix.size(); i++){
			result[i] = cellMatrix.get(i);
		}
		return result;
	}
}
