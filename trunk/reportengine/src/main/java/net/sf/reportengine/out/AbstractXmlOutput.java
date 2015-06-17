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
package net.sf.reportengine.out;

import java.io.Writer;

/**
 * Abstract output for all xml based report output. 
 * The output looks like: 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public abstract class AbstractXmlOutput extends AbstractCharOutput {
	
	public final static String TAG_REPORT = "report";
	
    public final static String TAG_TITLE = "title";
    
    public final static String TAG_DATA_ROW = "data-row";
    
    public final static String TAG_DATA_CELL = "data-cell";
    
    public final static String TAG_TABLE_HEADER = "table-header";
    
    public final static String TAG_HEADER_ROW = "header-row";
    
    public final static String ATTR_ENGINE_VERSION = "engineVersion";
    
	public final static String ATTR_COLSPAN = "colspan";
	
    public final static String ATTR_COLUMN_NUMBER = "colNumber";
    
	public final static String ATTR_ROW_NUMBER = "rowNumber";
	
	public final static String ATTR_HORIZ_ALIGN = "horizAlign";
	
	public final static String ATTR_VERT_ALIGN = "vertAlign";
	
	/**
	 * empty constructor
	 */
	public AbstractXmlOutput(){}
	
	/**
	 * 
	 * @param fileName
	 */
	public AbstractXmlOutput(String fileName){
		super(fileName); 
	}
	
	/**
     * 
     * @param writer
     */
    public AbstractXmlOutput(Writer writer){
    	this(writer, true);
    }
	
	/**
     * 
     * @param writer
     * @param managedWriter
     */
    public AbstractXmlOutput(Writer writer, boolean managedWriter){
    	super(writer, managedWriter);
    }
}
