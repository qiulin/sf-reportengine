<!-- row ${rowProps.content} ${rowProps.rowNumber} -->
<#if rowProps.content == "COLUMN_HEADER">
	<fo:table-row>
	
<#elseif rowProps.content = "DATA">
	<fo:table-row <#if rowProps.rowNumber % 2 == 0>background-color="#C0C0C0"<#else>background-color="#FFFFFF"</#if>>
	
<#elseif rowProps.content = "REPORT_TITLE">
	<fo:table-row>
	
</#if>