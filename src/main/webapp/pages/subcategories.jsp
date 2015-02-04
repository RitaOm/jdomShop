<%@ include file="include/library.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>Subcategories</title>
<link rel="stylesheet" type="text/css"
	href="resources/css/struts-styles.css"></link>	
</head>
<body>
	<div class="container">
		<nested:root name="productForm">
			<nested:present property="document.rootElement.children[${productForm.catIndex}]">
				<nested:define id="category" 
					property="document.rootElement.children[${productForm.catIndex}]" />
				<h1>
					Category
					<nested:write name="category" property="attributes[0].value" />
				</h1>
				<h2>Subcategories:</h2>
				<div class="content">
				<nested:iterate id="subcategory" name="category" property="children"
					indexId="index">
					<div class="href">
						<nested:size id="size" name="subcategory" property="children" />
						<html:link page="/Subcategory.do?index=${index}">
							<nested:write name="subcategory" property="attributes[0].value" />(${size})</html:link>
					</div>					
				</nested:iterate>
				<br>
				<nested:form action="/Categories.do">				
					<nested:submit>
						BACK
				</nested:submit>
				</nested:form>
				</div>
			</nested:present>
			<nested:notPresent property="document.rootElement.children[${productForm.catIndex}]">
				<h2>Wrong category path</h2>
				<nested:form action="/Categories.do">
					<nested:submit>
						Go to categories page
				</nested:submit>
				</nested:form>
			</nested:notPresent>
		</nested:root>
	</div>
</body>
</html>