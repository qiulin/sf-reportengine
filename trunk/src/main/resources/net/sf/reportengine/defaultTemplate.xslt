<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
<xsl:output method="html" />

<xsl:template match="report">
	<html>
	<head>
		<title>
			<xsl:value-of select="title" />
		</title>
	</head>
	<body>
		<h1>
			<xsl:value-of select="title" /> 
		</h1>
		<table cellspacing="0" cellpadding="1" border="0">
			<xsl:apply-templates select="row" />
		</table>		
	</body>
	</html>
</xsl:template>

<xsl:template match="row">
	<xsl:choose>
	<xsl:when test="@rowNumber mod 2 = 0">
		<tr bgcolor="#F5F5F5">
			<xsl:apply-templates />
		</tr>	
	</xsl:when>
	<xsl:otherwise>
		<tr bgcolor="#FFFFFF">
			<xsl:apply-templates />
		</tr>
	</xsl:otherwise>	
	</xsl:choose>
</xsl:template>

<xsl:template match="table-header">
	<xsl:variable name="colspan" select="@colspan" />
	<th colspan="{$colspan}">
		<xsl:value-of select="current()" />
	</th>
</xsl:template>

<xsl:template match="row-header">
	<xsl:variable name="colspan" select="@colspan" />
	<td colspan="{$colspan}">
		<xsl:value-of select="current()" />
	</td>
</xsl:template>

<xsl:template match="data">
	<xsl:variable name="colspan" select="@colspan" />
	<td colspan="{$colspan}">
		<xsl:value-of select="current()" />
	</td>
</xsl:template>

<xsl:template match="title">
<br />
<h2> <xsl:apply-templates /> </h2>
</xsl:template>

</xsl:stylesheet>