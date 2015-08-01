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
package net.sf.reportengine.out;

import freemarker.template.SimpleHash;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractReportOutput implements ReportOutput {
	
	/**
	 * the output format
	 */
	private final OutputFormat format; 
	
	/**
	 * 
	 * @param format
	 */
	public AbstractReportOutput(OutputFormat format){
		this.format = format; 
	}
	
	
//	/**
//	 * 
//	 * @param templateName
//	 * @param modelName
//	 * @param value
//	 */
//	public <T> void output(String templateName, String modelName, T value){
//		//Map<String, T> model = new HashMap<String, T>(1); 
//		SimpleHash hash = new SimpleHash(); 
//		hash.put(modelName, value); 
//		output(templateName, hash); 
//	}
	
	/**
	 * 
	 * @param templateName
	 */
	public void output(String templateName){
		output(templateName, null); 
	}
	
	public OutputFormat getFormat() {
		return format;
	}
	
}
