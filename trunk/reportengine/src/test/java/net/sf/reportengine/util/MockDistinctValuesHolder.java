/**
 * 
 */
package net.sf.reportengine.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class MockDistinctValuesHolder implements IDistinctValuesHolder {
	
	private Object[][] distinctValues; 
	
	public MockDistinctValuesHolder(Object[][]distinctValues){
		this.distinctValues = distinctValues;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.util.IDistinctValuesHolder#addValueIfNotExist(int, java.lang.Object)
	 */
	public int addValueIfNotExist(int headerGroupingLevel, Object value) {
		throw new IllegalAccessError("this method is not implemented");
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.util.IDistinctValuesHolder#getIndexFor(int, java.lang.Object)
	 */
	public int getIndexFor(int headerGroupingLevel, Object value) {
		return Arrays.binarySearch(distinctValues[headerGroupingLevel], value);
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.util.IDistinctValuesHolder#getDistinctValuesForLevel(int)
	 */
	public List<Object> getDistinctValuesForLevel(int level) {
		return Arrays.asList(distinctValues[level]);
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.util.IDistinctValuesHolder#getDistinctValuesCountForLevel(int)
	 */
	public int getDistinctValuesCountForLevel(int level) {
		return distinctValues[level].length; 
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.util.IDistinctValuesHolder#getRowsCount()
	 */
	public int getRowsCount() {
		return distinctValues.length; 
	}

}
