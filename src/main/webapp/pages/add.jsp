<%@ include file="include/library.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<title>Add good</title>
<link rel="stylesheet" type="text/css"
	href="resources/css/struts-styles.css"></link>
</head>
<body>
	<div class="container">
		<nested:root name="productForm">
			<h1>
				Category
				<nested:write property="category" />
			</h1>
			<h2>
				Subcategory
				<nested:write property="subcategory" />
			</h2>
			<div class="goodsList-div">
				<h3>Fill in the fields</h3>
				<nested:form action="/SaveGood.do">
					<table class="content">
						<tr>
							<td>Producer:</td>
							<td><nested:text property="good.producer" /> <span
								class="error"><nested:write
										property="errors.producerError" /></span></td>
						</tr>
						<tr>
							<td>Model:</td>
							<td><nested:text property="good.model"
									title="Consists of two letters and three digits" /> <span
								class="error"><nested:write property="errors.modelError" /></span></td>
						</tr>
						<tr>
							<td>Date:</td>
							<td><nested:text property="good.date" title="dd-MM-YYYY" />
								<span class="error"><nested:write
										property="errors.dateError" /></span></td>
						</tr>
						<tr>
							<td>Color:</td>
							<td><nested:text property="good.color" /> <span
								class="error"><nested:write property="errors.colorError" /></span></td>
						</tr>
						<tr>
							<td>Price:</td>
							<td><nested:text property="good.price" /> <span
								class="error"><nested:write property="errors.priceError" /></span></td>
						</tr>
						<tr>
							<td>Not in stock <nested:checkbox property="good.notInStock" />
							</td>
							<td><span class="error"><nested:write
										property="errors.notInStockError" /></span></td>
						</tr>
						<tr>
							<td></td>
							<td class="addpage-submit-save"><nested:submit>
							SAVE
				</nested:submit></td>
						</tr>
					</table>
				</nested:form>
			</div>
			<div class="content addpage-submit-back">
			<nested:form action="/Subcategory.do">
				<nested:submit>
						BACK
				</nested:submit>
			</nested:form></div>
		</nested:root>
	</div>
</body>
</html>