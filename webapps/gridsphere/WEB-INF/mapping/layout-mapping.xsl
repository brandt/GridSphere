<?xml version="1.0" ?>
<!--
 Stylesheet for merging Castor mapping file with other files with fragments.
 Files for including are specified as parameter "includefiles", separated by commas
 Example:

 saxon layout-mapping.xml layout-mapping.xsl includefiles=/path/file1.xml,/path/file2.xml

 $Id$

-->
<xsl:stylesheet version="1.0" 
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
        xmlns="http://schemas.xmlsoap.org/wsdl/"
        >
<xsl:strip-space elements="*"/>
<xsl:output method="xml" encoding="UTF-8" indent="yes" doctype-public="-//EXOLAB/Castor Object Mapping DTD Version 1.0//EN" doctype-system="http://castor.exolab.org/mapping.dtd"/>

<xsl:param name="includefiles" />

<xsl:template match="/mapping">
 <xsl:copy>
   <!-- all original document -->
   <xsl:apply-templates select="@*|node()"/>
   <!-- recursion for iterating over filenames -->
   <xsl:if test="$includefiles!=''">
     <xsl:call-template name="nextfile" >
        <xsl:with-param name="files" select="$includefiles" />
     </xsl:call-template>
   </xsl:if>
 </xsl:copy>
</xsl:template>

<!-- recursive copy -->
<xsl:template match="@*|node()">
    <xsl:copy> <xsl:apply-templates select="@*|node()"/> </xsl:copy>
</xsl:template>

<!-- include file content -->
<xsl:template name="include">
   <xsl:param name="filename" />
   <xsl:comment> start of included file <xsl:value-of select="$filename" /> </xsl:comment>
   <xsl:copy-of select="document($filename,/*)/node()" />
   <xsl:comment> end of included file <xsl:value-of select="$filename" /> </xsl:comment>
</xsl:template>

<!-- iteration over filenames -->
<xsl:template name="nextfile">
   <xsl:param name="files" />
   <!-- first filename in list -->
   <xsl:variable name="first" select="substring-before($files,',')" />
   <xsl:choose>
    <!-- if it is empty, there was no separator -->
    <xsl:when test="$first=''">
      <xsl:call-template name="include" >
           <xsl:with-param name="filename" select="$files" />
      </xsl:call-template>
    </xsl:when>
    <!-- include first filename, then call this template for the rest -->
    <xsl:otherwise>
      <xsl:call-template name="include" >
           <xsl:with-param name="filename" select="$first" />
      </xsl:call-template>
      <xsl:call-template name="nextfile" >
           <xsl:with-param name="files" select="substring-after($files,',')" />
      </xsl:call-template>
    </xsl:otherwise>
   </xsl:choose>
</xsl:template>

</xsl:stylesheet>
