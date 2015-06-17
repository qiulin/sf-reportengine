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

import java.io.OutputStream;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.fop.apps.MimeConstants;

/**
 * <p>PNG report output. </p>
 * 
 * Usage : 
 * <pre>
 * 		//png output into a file
 * 		ReportOutput reportOutput = new PngOutput("c:/temp/test.png"); 
 * 		or
 * 		// png output into a stream 
 * 		ReportOutput reportOutput = new PngOutput(new FileOutputStream("c:\\temp\\test.png"));
 * 		or
 * 		//png output into memory (not recommended)
 * 		ReportOutput reportOutput = new PngOutput();  
 * </pre>
 * 
 * @author dragos balan
 */
public class PngOutput extends AbstractFopOutput {

	/**
	 * generates png into memory as a {@code ByteArrayOutputStream}
	 */
	public PngOutput() {
		super(); 
	}

	/**
	 * generates the png onto the specified file path. 
	 * The file will be created. 
	 * 
	 * @param filePath the path of the file where png should be generated
	 */
	public PngOutput(String filePath) {
		super(filePath);
	}
	
	/**
	 * generates the png onto the specified file path and during the creation of the png the specified
	 * fop configuration will be used.
	 * 
	 * @param filePath	the path of the file where png will be generated
	 * @param append 	true if you want to append the content to an existing file
	 */
	public PngOutput(String filePath, boolean append){
		super(filePath, append); 
	}
	
	/**
	 * generates the png onto the specified file path and during the creation of the png the specified
	 * fop configuration will be used.
	 * 
	 * @param filePath	the path of the file where png will be generated
	 * @param append 	true if you want to append the content to an existing file
	 * @param fopConfig	the custom fop configuration
	 */
	public PngOutput(String filePath, boolean append, Configuration fopConfig){
		super(filePath, append, fopConfig); 
	}
	
	/**
	 * outputs the generated png into the output stream provided
	 * 
	 * @param out	the output stream
	 */
	public PngOutput(OutputStream out) {
		super(out);
	}
	
	/**
	 * generates png into the specified output stream using the custom fop configuration. 
	 * 
	 * @param out		the output stream 
	 * @param fopConfig	the fop custom configuration
	 */
	public PngOutput(OutputStream out, Configuration fopConfig) {
		super(out, true, fopConfig);
	}
	
	
	/**
	 * generates png into the specified output stream using the custom fop configuration. 
	 * 
	 * @param out		the output stream 
	 * @param managedOutputStream if true, the lifecycle (open/close) of the outputStream is controlled by this class
	 * @param fopConfig	the fop custom configuration
	 */
	public PngOutput(OutputStream out, boolean managedOutputStream, Configuration fopConfig) {
		super(out, managedOutputStream, fopConfig);
	}

	/**
	 * returns MIME_PNG
	 */
	@Override protected String getMimeType() {
		return MimeConstants.MIME_PNG; 
	}

}
