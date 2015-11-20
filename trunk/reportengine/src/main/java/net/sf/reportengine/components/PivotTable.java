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
package net.sf.reportengine.components;

/**
 * <p>Pivot Table interface</p>
 * 
 * A pivot table has a layout like:
 * <table border="2" >
 *  <th>
 *      <td colspan="1" rowspan="1" ></td>
 *      <td colspan="1" rowspan="1" ></td>
 * <td colspan="3" rowspan="1" align="center">
 *  Males
 * </td><td colspan="3" rowspan="1" align="center">
 *  Females
 * </td></tr>
 * <tr><td colspan="1" rowspan="1" >
 *  Region
 * </td><td colspan="1" rowspan="1" >
 *  Country
 * </td><td colspan="1" rowspan="1" >
 *  Elephants
 * </td><td colspan="1" rowspan="1" >
 *  Rhinos
 * </td><td colspan="1" rowspan="1" >
 *  Squirrels
 * </td><td colspan="1" rowspan="1" >
 *  Elephants
 * </td><td colspan="1" rowspan="1" >
 *  Rhinos
 * </td><td colspan="1" rowspan="1" >
 *  Squirrels
 * </td>
 * </th>
 * <tr class="even" >
 * 
 * <td colspan="1" rowspan="1" >
 *  North
 * </td>
 * <td colspan="1" rowspan="1" >
 *  Sweden
 * </td>
 * <td colspan="1" rowspan="1" >
 *  1000
 * </td>
 * <td colspan="1" rowspan="1" >
 *  10
 * </td>
 * <td colspan="1" rowspan="1" >
 *  4
 * </td>
 * <td colspan="1" rowspan="1" >
 *  1
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * </tr><tr class="odd" >
 * 
 * <td colspan="1" rowspan="1" >
 *   
 * </td>
 * <td colspan="1" rowspan="1" >
 *  Norway
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  100
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * </tr><tr class="even" >
 * 
 * <td colspan="1" rowspan="1" >
 *  South
 * </td>
 * <td colspan="1" rowspan="1" >
 *  Italy
 * </td>
 * <td colspan="1" rowspan="1" >
 *  2000
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * </tr><tr>
 * 
 * <td colspan="1" rowspan="1" >
 *  East
 * </td>
 * <td colspan="1" rowspan="1" >
 *  Romania
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  200
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * </tr>
 * <tr class="even" >
 * 
 * <td colspan="1" rowspan="1" >
 *  West
 * </td>
 * <td colspan="1" rowspan="1" >
 *  France
 * </td>
 * <td colspan="1" rowspan="1" >
 *  300
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  3000
 * </td>
 * <td colspan="1" rowspan="1" >
 *  30
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * <td colspan="1" rowspan="1" >
 *  0
 * </td>
 * </tr>        
 * </table>
 * 
 * @see PivotTableBuilder
 * @author dragos balan
 * @since 0.13
 */
public interface PivotTable extends ReportComponent {

}
