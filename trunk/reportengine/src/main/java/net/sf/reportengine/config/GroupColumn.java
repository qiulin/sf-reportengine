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
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;


/**
 * Group column for flat and crosstab reports
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public interface GroupColumn {
	
	/**
	 * this appears in the final report as the title/header of the column
	 * @return
	 */
	public String getHeader(); 
	
	
	/**
	 * the level of grouping. 
	 * The grouping levels start with 0 (the most important), 1, 2, 3 ..
	 *  
	 * @return an integer representing the level of grouping
	 */
	public int getGroupingLevel();
	
	/**
	 * 
	 * @param newRowEvent
	 * @return
	 */
	public Object getValue(NewRowEvent newRowEvent); 
	
	/**
	 * returns the formatted value for the given object according to the 
	 * formatting rules of this grouping column
	 * @param object
	 */
	public String getFormattedValue(Object object);
	
	/**
	 * returns the horizontal alignment of the values of this column
	 */
	public HorizAlign getHorizAlign();
	
	/**
	 * returns the vertical alignment of the values of this column
	 * @return
	 */
	public VertAlign getVertAlign();
	
	/**
	 * whether or not this group column should display duplicate group values. 
	 * Usually there are many group values displayed one after the other 
	 * 
	 * <table>
	 * <tr>
	 * 	<td>Europe</td><td>South</td><td>100</td>
	 * </tr>
	 * <tr>
	 * 	<td>Europe</td><td>North</td><td>207</td>
	 * </tr>
	 * <tr>
	 * 	<td>Europe</td><td>East</td><td>103</td>
	 * </tr>
	 * <tr>
	 * 	<td>Europe</td><td>West</td><td>120</td>
	 * </tr>
	 * </table>
	 * 
	 * This method controls whether the duplicated Europe value should be displayed multiple times or not: 
	 * 
	 * <table>
	 * <tr>
	 * 	<td>Europe</td><td>South</td><td>100</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>North</td><td>207</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>East</td><td>103</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>West</td><td>120</td>
	 * </tr>
	 * <tr>
	 * 	<td>Asia</td><td>South</td><td>300</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>North</td><td>267</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>East</td><td>564</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>West</td><td>122</td>
	 * </tr>
	 * </table>
	 * 
	 * @return
	 */
	public boolean showDuplicates(); 
	
	/**
	 * whether you want to sort this ascending or descending
	 * 
	 * @return
	 */
	public SortType getSortType(); 
}
