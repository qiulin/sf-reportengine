/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class IntermComputedTotalsList implements Serializable{
	
	
	/**
	 * the serial version id
	 */
	private static final long serialVersionUID = 3388048740067218435L;
	
	
	private List<IntermediateTotalInfo> totalsDataList;
	
	public IntermComputedTotalsList(){
		totalsDataList = new ArrayList<IntermediateTotalInfo>(); 
	}
	
	public void addTotalsData(IntermediateTotalInfo totalsData){
		totalsDataList.add(totalsData); 
	}
	
	public Object getValueFor(int...position){
		Object result = null; 
		boolean allPositionsAreEqual =  true; 
		
		if(position != null){
			//iterate over dataList
			for (IntermediateTotalInfo totalInfo : totalsDataList) {
				int[] positionRelativeToHeader = totalInfo.getPositionRelativeToHeader();
			
				if(	positionRelativeToHeader != null 
					&& positionRelativeToHeader.length == position.length){
				
					allPositionsAreEqual =  true; 
					
					//iterate over positions
					for (int i = 0; i < positionRelativeToHeader.length && allPositionsAreEqual; i++) {
						if(positionRelativeToHeader[i] != position[i]){
							
							//if found one position not equal to the header row index then 
							//mark not all positions are equal in order to skip this 
							//IntermediateDataInfo and pass to the next one
							allPositionsAreEqual = false; 
						}
					}
				
					if(allPositionsAreEqual){
						//the first time we find that all positions are equal we exit the 
						//dataInfo loop and return
						result = totalInfo.getComputedValue(); 
						break; 
					}
				}else{
					//if position relative to header is null then 
					//this is a grand total
				}
			}//end loop totalsInfo 
		}else{
			for (IntermediateTotalInfo totalInfo : totalsDataList) {
				if(totalInfo.getPositionRelativeToHeader() == null){
					result = totalInfo.getComputedValue(); 
					break; 
				}
			}
		}
		return result;
	}
	
	public void empty(){
		totalsDataList.clear(); 
	}
	
	public Object getLastValueInTotalList(){
		return totalsDataList.get(totalsDataList.size()-1);
	}
	
	//TODO: try to remove this
	public List<IntermediateTotalInfo> getTotalsDataList(){
		return totalsDataList; 
	}
	
	public boolean equals(Object another){
		boolean result = false;
		if(another instanceof IntermComputedTotalsList){
			result = this.totalsDataList.equals(((IntermComputedTotalsList)another).getTotalsDataList()); 
		}
		return result; 
	}
	
	public String toString(){
		StringBuffer result = new StringBuffer("IntermCtTotalList"); 
		result.append(totalsDataList.toString());
		return result.toString(); 
	}
}
