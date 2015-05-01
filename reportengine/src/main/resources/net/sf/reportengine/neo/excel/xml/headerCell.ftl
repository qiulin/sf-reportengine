<Cell 
	<#if cellProps.colspan gt 1>
	ss:MergeAcross="${cellProps.colspan-1}"
	</#if>
	ss:StyleID="headerCellStyle">
	<Data ss:Type="String">${cellProps.value}</Data>
</Cell>
