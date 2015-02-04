<%@ include file="include/library.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<title>Goods</title>
<link rel="stylesheet" type="text/css"
	href="resources/css/struts-styles.css"></link>
</head>
<body>
	<div class="container">
		<nested:root name="productForm">
			<nested:present
				property="document.rootElement.children[${productForm.catIndex}].children[${productForm.subcatIndex}]">
				<nested:nest
					property="document.rootElement.children[${productForm.catIndex}]">
					<h1>
						Category
						<nested:write property="attributes[0].value" />
					</h1>
					<h2>
						Subcategory
						<nested:write
							property="children[${productForm.subcatIndex}].attributes[0].value" />
					</h2>
					<div class="goodsList-div">
						<h3>Goods:</h3>
						<nested:size id="size"
							property="children[${productForm.subcatIndex}].children" />
						<c:choose>
							<c:when test="${size eq 0}">
								<p>There are no goods to show</p>
							</c:when>
							<c:otherwise>
								<nested:form action="/UpdateGoods.do"
									onsubmit="return validateForm(${size});">
									<table class="goodsList-table">
										<tr>
											<th>Producer</th>
											<th>Model (2 letters and 3 digits)</th>
											<th>Date (dd-MM-yyyy)</th>
											<th>Color</th>
											<th>Price or Not in stock</th>
										</tr>
										<nested:iterate
											property="document.rootElement.children[${productForm.catIndex}].children[${productForm.subcatIndex}].children"
											indexId="index">
											<tr>
												<td><nested:text property="child(producer).text2"
														styleId="producer${index}" /></td>
												<td><nested:text property="child(model).text2"
														styleId="model${index}" /></td>
												<td><nested:text property="child(date).text2"
														styleId="date${index}" /></td>
												<td><nested:text property="child(color).text2"
														styleId="color${index}" /></td>
												<td class="price"><nested:empty property="child(price)">
														<html:text property="value(${index})" disabled="true"
															styleId="price${index}" />
													</nested:empty> <nested:notEmpty property="child(price)">
														<nested:text property="child(price).text2"
															styleId="price${index}" />
													</nested:notEmpty> <html:multibox name="productForm" property="idsNotInStock"
														styleId="notInStock${index}"
														onclick="changePriceState(this, ${index});">
									${index}
								</html:multibox></td>
											</tr>
										</nested:iterate>
									</table>
									<div class="goods-button-update">
										<nested:submit styleClass="goodsList-update-submit">
							UPDATE
				</nested:submit>
									</div>
								</nested:form>
							</c:otherwise>
						</c:choose>
						<div class="goods-button-back">
							<nested:form action="/Category.do">
								<nested:submit>
						BACK
				</nested:submit>
							</nested:form>
						</div>
						<div class="goods-button-add">
							<nested:form action="/AddNewGood.do">
								<nested:submit>
							ADD
				</nested:submit>
							</nested:form>
						</div>
					</div>
				</nested:nest>
			</nested:present>
			<nested:notPresent
				property="document.rootElement.children[${productForm.catIndex}].children[${productForm.subcatIndex}]">
				<h2>Wrong category or subcategory path</h2>
				<nested:form action="/Categories.do">
					<nested:submit>
						Go to categories page
				</nested:submit>
				</nested:form>
			</nested:notPresent>
		</nested:root>
	</div>
	<script src='resources/js/jquery-2.1.1.min.js'></script>
	<script src='resources/js/goods.js'></script>
</body>
</html>