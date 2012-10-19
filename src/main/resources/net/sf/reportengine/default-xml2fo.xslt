<?xml version="1.0"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
  <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>

<xsl:template match="report">
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
      
      
      	<fo:page-sequence master-reference="A4">
      		<fo:flow flow-name="xsl-region-body">
        
			<fo:block 	font-family="ArialUnicodeMS" 
						font-size="24pt" 
						font-style="normal" 
						space-after="5mm"
						text-align="center">
				<!-- <xsl:value-of select="@title" /> -->
				<xsl:value-of select="row/title" />
          	</fo:block> 
			
			<fo:block 	font-family="ArialUnicodeMS" 
						font-size="12pt"
						font-style="normal" 
						>
				<fo:table><!-- table-layout="fixed" -->
					<fo:table-header>
						<fo:table-row>  
						<xsl:for-each select="row/table-header">
							  <!-- <fo:table-column/>column-width="3cm" -->
							  	<xsl:variable name="colspan" select="@colspan" />
							  	<fo:table-cell number-columns-spanned="{$colspan}">
		 								<fo:block font-weight="bold">
		 									<xsl:value-of select="row/table-header" />
											<!-- <xsl:value-of select="current()" /> -->
										</fo:block>
								</fo:table-cell>							  
						</xsl:for-each>
						</fo:table-row> 
					</fo:table-header>
					
					<fo:table-body>
						
					</fo:table-body>						
				</fo:table>
			</fo:block>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
</xsl:template>

<xsl:template match="row">
	<xsl:choose>
	<xsl:when test="@rowNumber mod 2 = 0">
		<fo:table-row background-color="#F5F5F5">
			<xsl:apply-templates />
		</fo:table-row>	
	</xsl:when>
	<xsl:otherwise>
		<fo:table-row background-color="#FFFFFF">
			<xsl:apply-templates />
		</fo:table-row>
	</xsl:otherwise>	
	</xsl:choose>
</xsl:template>

 <!-- 
<xsl:template match="row/table-header">
	<xsl:variable name="colspan" select="@colspan" />
	<fo:table-cell number-columns-spanned="{$colspan}">
		 <fo:block font-weight="bold">
				<xsl:value-of select="current()" />
		</fo:block>
	</fo:table-cell>
</xsl:template>

<xsl:template match="row-header">
	<xsl:variable name="colspan" select="@colspan" />
	
	<fo:table-cell number-columns-spanned="{$colspan}">
		 <fo:block>
				<xsl:value-of select="current()" />
			</fo:block>
	</fo:table-cell>
</xsl:template>
-->
 


<xsl:template match="data">
	<xsl:variable name="colspan" select="@colspan" />
	<fo:table-cell number-columns-spanned="{$colspan}">
		 <fo:block>
				<xsl:value-of select="current()" />
			</fo:block>
	</fo:table-cell>
</xsl:template>


</xsl:stylesheet>
