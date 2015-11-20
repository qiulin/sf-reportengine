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
package net.sf.reportengine;

/**
 * <p>
 * The main interface for report execution. 
 * </p>
 * <p>
 * The usage is:<br/>
 * <pre>
 * 	Report report = new ReportBuilder(new HtmlReportOutput(new FileWriter("/temp/test.html")))
 * 						.add(...)
 * 						.add(...)
 * 						...
 * 						.build(); 
 *  report.execute(); 
 * </pre>
 * </p>
 * 
 * @see ReportBuilder
 * @author dragos balan
 * @since 0.13.0
 */
public interface Report {

	/**
	 * runs the report
	 */
	public void execute();

}
