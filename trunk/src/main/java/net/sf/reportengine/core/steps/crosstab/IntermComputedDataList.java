/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class IntermComputedDataList implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -308220872583199439L;
	
	/**
	 * 
	 */
	private List<IntermediateDataInfo> dataList; 
	
	
	/**
	 * 
	 */
	public IntermComputedDataList(){
		dataList = new ArrayList<IntermediateDataInfo>();
	}
	
	public void addData(IntermediateDataInfo info){
		dataList.add(info);
	}
	
	public List<IntermediateDataInfo> getDataList(){
		return dataList; 
	}
	
	public void empty(){
		dataList.clear(); 
	}
	
	public boolean equals(Object another){
		boolean result = false;
		if(another instanceof IntermComputedDataList){
			result = this.dataList.equals(((IntermComputedDataList)another).getDataList()); 
		}
		return result; 
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer("IntermComputedDataList");
		buffer.append(dataList.toString());
		return buffer.toString(); 
	}
	
	public Object getValueFor(int...headerRowIndex){
		Object result = null; 
		boolean allPositionsAreEqual =  true; 
		
		//iterate over dataList
		for (IntermediateDataInfo dataInfo : dataList) {
			int[] positionRelativeToHeader = dataInfo.getPositionRelativeToHeaderRows();
			if(	positionRelativeToHeader != null 
				& positionRelativeToHeader.length == headerRowIndex.length){
				
				allPositionsAreEqual =  true; 
				
				//iterate over positions
				for (int i = 0; i < positionRelativeToHeader.length && allPositionsAreEqual; i++) {
					if(positionRelativeToHeader[i] != headerRowIndex[i]){
						
						//if found one position not equal to the header row index then 
						//mark not all positions are equal in order to skip this 
						//IntermediateDataInfo and pass to the next one
						allPositionsAreEqual = false; 
					}
				}
				
				if(allPositionsAreEqual){
					//the first time we find that all positions are equal we exit the 
					//dataInfo loop and return
					result = dataInfo.getValue(); 
					break; 
				}
			}else{
				throw new IllegalArgumentException("Invalid position array : "+headerRowIndex);
			}
		}
		return result;
	}
	
}
