/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.io.Serializable;

/**
 * Intermediate report row. 
 * Each intermediate report row contains information about : 
 *  <ol>
 *  	<li>values belonging to group columns in the final report (declared as group columns by the user)</li>
 *  	<li>values belonging to data columns in the final report (declared as data columns by the user)</li>
 *  	<li>values computed by the Intermediary report (coming from CrosstabData) 
 *  	<li>total values computed by Intermediary report on CrosstabData and destined for special total-data columns in the final report</li>
 *  </ol>
 * 
 * @author dragos balan
 * @since 0.4
 */
public class IntermediateReportRow implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2809271046992077641L;
	
	/**
	 * the only purpose of this flag is to know when to stop reading the ObjectStream when somebody reads it
	 * as the ObjectStream implementations don't have this feature.
	 */
	private boolean isLast = false; 
	
	private IntermOriginalGroupValuesList intermOrigGroupValuesList;
	private IntermOriginalDataColsList intermOriginalDataValues; 
	private IntermComputedDataList intermComputedDataList; 
	private IntermComputedTotalsList intermComputedTotalsList; 
	
	public IntermediateReportRow(){
		intermOrigGroupValuesList = new IntermOriginalGroupValuesList();
		intermOriginalDataValues = new IntermOriginalDataColsList(); 
		intermComputedDataList = new IntermComputedDataList();
		intermComputedTotalsList = new IntermComputedTotalsList();
	}
	
	public IntermOriginalGroupValuesList getIntermGroupValuesList(){
		return intermOrigGroupValuesList; 
	}
	
	public IntermOriginalDataColsList getIntermOriginalDataValuesList(){
		return intermOriginalDataValues; 
	}
	
	public IntermComputedDataList getIntermComputedDataList() {
		return intermComputedDataList;
	}

	public IntermComputedTotalsList getIntermComputedTotalsList() {
		return intermComputedTotalsList;
	}

	
	public void addOrigGroupValue(Object groupValue){
		this.intermOrigGroupValuesList.addGroupValue(groupValue); 
	}
	
	public void addOrigDataColValue(Object dataValue){
		this.intermOriginalDataValues.addDataColumnValue(dataValue); 
	}
	
	public void addIntermComputedData(IntermediateDataInfo info){
		this.intermComputedDataList.addData(info);
	}
	
	public void addIntermTotalsInfo(IntermediateTotalInfo info){
		this.intermComputedTotalsList.addTotalsData(info);
	}
	
	public void emptyRow(){
		intermOrigGroupValuesList.empty(); 
		intermOriginalDataValues.empty(); 
		intermComputedDataList.empty(); 
		intermComputedTotalsList.empty();
	}
	
	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}
	
	public boolean equals(Object another){
		boolean result = false; 
		
		if(another instanceof IntermediateReportRow){
			IntermediateReportRow anotherAsIRR = (IntermediateReportRow)another; 
			result = intermComputedTotalsList.equals(anotherAsIRR.getIntermComputedTotalsList())
					&& intermComputedDataList.equals(anotherAsIRR.getIntermComputedDataList())
					&& intermOrigGroupValuesList.equals(anotherAsIRR.getIntermGroupValuesList())
					&& intermOriginalDataValues.equals(anotherAsIRR.getIntermOriginalDataValuesList())
					; 
		}
		return result; 
	}
	
	public String toString(){
		StringBuilder result = new StringBuilder("IRR[isLast=");
		result.append(isLast);
		result.append(", ");
		result.append(intermOrigGroupValuesList); 
		result.append(", ");
		result.append(intermOriginalDataValues);
		result.append(", ");
		result.append(intermComputedDataList); 
		result.append(", ");
		result.append(intermComputedTotalsList); 
		result.append("]");
		return result.toString(); 
	}
}
