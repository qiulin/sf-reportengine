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
public class MockDistinctValuesHolder implements DistinctValuesHolder {
	
	private Object[][] distinctValues; 
	
	public MockDistinctValuesHolder(Object[][]distinctValues){
		this.distinctValues = distinctValues;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.util.DistinctValuesHolder#addValueIfNotExist(int, java.lang.Object)
	 */
	public int addValueIfNotExist(int headerGroupingLevel, Object value) {
		throw new IllegalAccessError("this method is not implemented");
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.util.DistinctValuesHolder#getIndexFor(int, java.lang.Object)
	 */
	public int getIndexFor(int headerGroupingLevel, Object value) {
		return Arrays.binarySearch(distinctValues[headerGroupingLevel], value);
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.util.DistinctValuesHolder#getDistinctValuesForLevel(int)
	 */
	public List<Object> getDistinctValuesForLevel(int level) {
		return Arrays.asList(distinctValues[level]);
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.util.DistinctValuesHolder#getDistinctValuesCountForLevel(int)
	 */
	public int getDistinctValuesCountForLevel(int level) {
		return distinctValues[level].length; 
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.util.DistinctValuesHolder#getRowsCount()
	 */
	public int getRowsCount() {
		return distinctValues.length; 
	}

}
