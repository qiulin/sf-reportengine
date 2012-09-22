/**
 * 
 */
package net.sf.reportengine.in;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.reportengine.core.ReportEngineRuntimeException;
import net.sf.reportengine.core.steps.crosstab.IntermComputedDataList;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalDataColsList;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalGroupValuesList;
import net.sf.reportengine.core.steps.crosstab.IntermComputedTotalsList;
import net.sf.reportengine.core.steps.crosstab.IntermediateDataInfo;
import net.sf.reportengine.core.steps.crosstab.IntermediateReportRow;
import net.sf.reportengine.core.steps.crosstab.IntermediateTotalInfo;

/**
 * @author Administrator
 *
 */
public class IntermediateCrosstabReportInput extends AbstractReportInput {
	
	/**
	 * the logger
	 */
	private static final Logger logger = Logger.getLogger(IntermediateCrosstabReportInput.class);
	
	/**
	 * 
	 */
	private ObjectInputStream intermCtLinesInputStream;
	
	/**
	 * 
	 */
	private IntermediateReportRow nextRawLine; 
	
	/**
	 * 
	 */
	private InputStream intermediateReportInputStream; 
	
	
	/**
	 * 
	 * @param input
	 */
	public IntermediateCrosstabReportInput(InputStream input){
		this.intermediateReportInputStream = input; 
	}
	
	@Override
	public void open(){
		super.open(); 
		try {
			intermCtLinesInputStream = new ObjectInputStream(intermediateReportInputStream);
			nextRawLine = readNextRow(); 
		} catch (FileNotFoundException e) {
			throw new ReportInputException(e); 
		} catch (IOException e) {
			throw new ReportInputException(e); 
		}
		logger.debug("Intermediate report input opened successfuly"); 
	}

	@Override
	public void close(){
		nextRawLine = null; 
		try {
			intermCtLinesInputStream.close();
			intermediateReportInputStream.close(); 
		} catch (IOException e) {
			throw new ReportInputException(e); 
		} 
		
		intermCtLinesInputStream = null; 
		intermediateReportInputStream = null; 
		super.close(); 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.in.AbstractReportInput#nextRow()
	 */
	public Object[] nextRow()  {
		logger.debug("requesting next intermediate row"); 
		Object[] result = null; 
		if(hasMoreRows()){
			result = transformIntermediateCrosstabLine(nextRawLine); 
			
			//prepare the next line
			if(!nextRawLine.isLast()){
				nextRawLine = readNextRow(); 
			}else{
				nextRawLine = null; 
			}
		}
		logger.debug("returning nextRow: "+Arrays.toString(result)); 
		return result; 
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.in.AbstractReportInput#hasMoreRows()
	 */
	public boolean hasMoreRows() {
		return nextRawLine != null;
	}

	private IntermediateReportRow readNextRow(){
		IntermediateReportRow result = null; 
		try {
			result = (IntermediateReportRow)intermCtLinesInputStream.readObject();
		} catch (ClassNotFoundException e) {
			throw new ReportEngineRuntimeException(e); 
		} catch (IOException e) {
			throw new ReportEngineRuntimeException(e);
		} 
		
		return result; 
	}
	
	/**
	 * the contract is that 
	 * 		result[0] is an instance of IntermGroupValuesList
	 *      result[1] is an instance of OriginalDataValueList
	 * 		result[2] is an instance of IntermComputedDataList
	 * 		result[3] an instance of IntermComputedTotalsList
	 * @param intermRow
	 * @return
	 */
	private Object[] transformIntermediateCrosstabLine(IntermediateReportRow intermRow){
		logger.debug("transforming line: "+intermRow);
		Object[] result = null; 
		
		if(intermRow != null){
			result = new Object[4];
			IntermOriginalGroupValuesList intermGroupValues = intermRow.getIntermGroupValuesList(); 
			if(intermGroupValues != null){
				result[0] = intermGroupValues; 
			}
			
			IntermOriginalDataColsList intermOrigDataColValues = intermRow.getIntermOriginalDataValuesList(); 
			if(intermOrigDataColValues != null){
				result[1] = intermOrigDataColValues; 
			}
			
			IntermComputedDataList intermDataList = intermRow.getIntermComputedDataList(); 
			if(intermDataList != null){
				result[2] = intermDataList; 
			}
			
			IntermComputedTotalsList intermTotals = intermRow.getIntermComputedTotalsList(); 
			if(intermTotals !=  null){
				result[3] = intermTotals; 
			}
		}
		
		return result; 
	}
	
}
