<?xml version="1.0" encoding="utf-8"?>
<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
<fo:layout-master-set>
        	<fo:simple-page-master 	master-name="A3" 
        						page-height="59.4cm" 
        						page-width="42cm" 
        						margin-top="2cm" 
        						margin-bottom="2cm" 
        						margin-left="2cm" 
        						margin-right="2cm">
          		<fo:region-body/>
        	</fo:simple-page-master>
        
        	<fo:simple-page-master 	master-name="A4" 
        						page-width="297mm"
								page-height="210mm" 
								margin-top="1cm" 
								margin-bottom="1cm"
								margin-left="1cm" 
								margin-right="1cm">
  				<fo:region-body margin="3cm"/>
  				<fo:region-before extent="2cm"/>
  				<fo:region-after extent="2cm"/>
  				<fo:region-start extent="2cm"/>
  				<fo:region-end extent="2cm"/>
			</fo:simple-page-master>
		</fo:layout-master-set>
      
      
      	<fo:page-sequence master-reference="${reportProps.format.pageSize}">
      		<fo:flow flow-name="xsl-region-body">
				<#-- this is for later use
				<fo:block 	font-family="ArialUnicodeMS" 
						font-size="24pt" 
						font-style="normal" 
						space-after="5mm"
						text-align="center">
					Title goes here
          		</fo:block> 
          		-->