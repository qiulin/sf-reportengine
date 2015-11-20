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

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.out.ReportOutput;

/**
 * <p>
 * This is a normal tabular report (don't get confused by its name) having a layout like:
 * <table>
 * <tr>
 * <td><b>column 1</b></td>
 * <td><b>column 2</b></td>
 * <td><b>column 3</b></td>
 * </tr>
 * <tr>
 * <td>data 11</td>
 * <td>data 12</td>
 * <td>data 13</td>
 * </tr>
 * <tr>
 * <td>data 21</td>
 * <td>data 22</td>
 * <td>data 23</td>
 * <tr>
 * <tr>
 * <td>data 31</td>
 * <td>data 32</td>
 * <td>data 33</td>
 * <tr>
 * </table>
 *
 * Any flat report needs at least the following elements configured:
 * <ul>
 * <li>input</li>
 * <li>columns</li>
 * </ul>
 * Usually the flat table is used as a component of a report:
 * <pre>
 * {@code
 * FlatTable flatTable = new FlatTableBuilder()
 *    .input(new TextInput("./inputData/expenses.csv",","))
 *    .addDataColumn(new DefaultDataColumn.Builder(0).header("Country").build())
 *    .addDataColumn(new DefaultDataColumn.Builder(1).header("City").build())
 *    .addDataColumn(new DefaultDataColumn.Builder(2).header("Population").build())
 *    .build();
 *    
 * Report report = new ReportBuilder(new DefaultReportOutput(new FileWriter("/tmp/report.html")));
 * report.add(flatTable); 
 * report.execute(); 
 *}
 *</pre> 
 * but it can be used also as a stand alone component
 * 
 * <pre>
 *  {@code
 *      ReportOutput reportOutput = new DefaultReportOutput(new FileWriter("/tmp/testFlatTable.html"))
 *      reportOutput.open(); 
 *      
 *      FlatTable flatTable = new FlatTableBuilder()
 *     .input(new TextInput("./inputData/expenses.csv",","))
 *     .addDataColumn(new DefaultDataColumn.Builder(0).header("Country").build())
 *     .addDataColumn(new DefaultDataColumn.Builder(1).header("City").build())
 *     .addDataColumn(new DefaultDataColumn.Builder(2).header("Population").build())
 *     .build();
 *     
 *      flatTable.output(reportOutput); 
 *      reportOuptut.close(); 
 *  }
 * </pre>
 * 
 * </p>
 * 
 * @see TableInput
 * @see ReportOutput
 * @see DataColumn
 * @see GroupColumn
 * 
 * @author dragos balan
 */
public interface FlatTable extends ReportComponent {

}
