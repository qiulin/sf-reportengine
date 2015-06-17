/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package net.sf.reportengine.in;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Report Input implementation based on a 2 dimensional in memory array
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class ArrayTableInput extends AbstractTableInput {
	
	/**
	 * the data to be returned row by row
	 */
	private Object[][] data;
	
	/**
	 * the current row
	 */
	private int currentRow = 0;
	
	/**
	 * 
	 * @param data
	 */
	public ArrayTableInput(Object[][] data){
		this.data = data;
	}
	
	/**
     * 
     */
    public void open() throws ReportInputException{
    	super.open(); 
    	setColumnMetadata(readMetadata());
    	currentRow = 0;
    }

	public boolean hasMoreRows() {
		return currentRow < data.length ;
	}

	
	public List<Object> nextRow(){
		return Arrays.asList(data[currentRow++]);
	}
		
	
	private List<ColumnMetadata> readMetadata(){
		List<ColumnMetadata> result = new ArrayList<ColumnMetadata>(); 
		if(data != null && data.length > 0 && data[0] != null && data[0].length >0){
			int arrayLength = data[0].length; 
			for (int i = 0; i < arrayLength; i++) {
				String columnId = ""+i;
				ColumnMetadata metadata = new ColumnMetadata(columnId, columnId);
			}
		}
		
		return result;
	}
}
