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
package net.sf.reportengine.config;



/**
 * enumeration of possible horizontal alignments
 * 
 * @author dragos balan
 * @since 0.6
 */
public enum HorizAlign {
	LEFT("left", "left"/*, HSSFCellStyle.ALIGN_LEFT*/), 
	CENTER("center", "center"/*, HSSFCellStyle.ALIGN_CENTER*/), 
	RIGHT("right", "right"/*, HSSFCellStyle.ALIGN_RIGHT*/); 
	
	private String htmlCode; 
	private String foCode; 
	//private short hssfCode; 
	
	private HorizAlign(String htmlCode, String foCode/*, short hssfCode*/){
		this.htmlCode = htmlCode; 
		this.foCode = foCode; 
		//this.hssfCode = hssfCode; 
	}
	
	public String getHtmlCode(){
		return htmlCode; 
	}
	
	public String getFoCode(){
		return foCode; 
	}
	
//	public short getHssfCode(){
//		return hssfCode; 
//	}
}
