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
package net.sf.reportengine.components;

import java.util.HashMap;
import java.util.Map;

import net.sf.reportengine.out.TitleProps;
import net.sf.reportengine.out.neo.AbstractReportOutput;

/**
 * @author dragos balan
 *
 */
public final class ReportTitle extends AbstractReportComponent {
	
	
	public static final String FM_TEMPLATE_NAME = "title.ftl";
	
	public static final String FM_ROOT_MODEL_NAME = "titleProps";
	
	
	/**
	 * the title
	 */
	public final String title; 
	
	
	/**
	 * the constructor of a report title
	 * @param title
	 */
	public ReportTitle(String title){
		this.title = title; 
	}
	
	/**
	 * 
	 */
	public void output(AbstractReportOutput out){
		Map<String, TitleProps> rootModel = new HashMap<String, TitleProps>(1);
		rootModel.put(FM_ROOT_MODEL_NAME, new TitleProps(title, 2)); 
		out.output(FM_TEMPLATE_NAME, rootModel); 
	}
}
