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

import java.util.List;

import net.sf.reportengine.out.neo.AbstractReportOutput;

/**
 * This is a container of report components
 * 
 * @author dragos balan
 *
 */
public class ReportComponentContainer implements ReportComponent {
	
	/**
	 * the components inside this container
	 */
	private List<ReportComponent> components;

	/* (non-Javadoc)
	 * @see net.sf.reportengine.components.ReportComponent#output(net.sf.reportengine.out.neo.NewReportOutput)
	 */
	public void output(AbstractReportOutput out) {
		for (ReportComponent reportComponent : components) {
			reportComponent.output(out);
		}
	}
}
