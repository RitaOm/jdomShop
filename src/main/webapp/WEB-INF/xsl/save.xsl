<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:def="http://www.example.org/products"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:good="xalan://com.epam.testapp.bean.Good"
	xmlns:validator="xalan://com.epam.testapp.validator.GoodValidor">
	<xsl:output method="html" indent="yes" />

	<xsl:param name="category"></xsl:param>
	<xsl:param name="subcategory"></xsl:param>
	<xsl:param name="good"></xsl:param>
	<xsl:param name="validator"></xsl:param>

	<xsl:output method="xml" indent="yes" />

	<xsl:template match="/|node()|@*">
		<xsl:if test="validator:validate($validator, $good)">
			<xsl:copy>
				<xsl:apply-templates select="node()|@*" />
			</xsl:copy>
		</xsl:if>
	</xsl:template>

	<xsl:template
		match="/products/category[@category_name=$category]/subcategory[@subcategory_name=$subcategory]">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
			<xsl:element name="good">
				<xsl:element name="producer">
					<xsl:value-of select="good:getProducer($good)" />
				</xsl:element>
				<xsl:element name="model">
					<xsl:value-of select="good:getModel($good)" />
				</xsl:element>
				<xsl:element name="date">
					<xsl:value-of select="good:getDate($good)" />
				</xsl:element>
				<xsl:element name="color">
					<xsl:value-of select="good:getColor($good)" />
				</xsl:element>
				<xsl:choose>
					<xsl:when test="good:isNotInStock($good)">
						<xsl:element name="not_in_stock"></xsl:element>
					</xsl:when>
					<xsl:otherwise>
						<xsl:element name="price">
							<xsl:value-of select="good:getPrice($good)" />
						</xsl:element>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:element>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>