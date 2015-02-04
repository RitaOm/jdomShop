<%@ include file="include/library.jsp"%>
<!DOCTYPE>
<html>
<head>
<title>Categories</title>
<link rel="stylesheet" type="text/css"
	href="resources/css/struts-styles.css"></link>
</head>
<body>
	<div class="container">
		<h1>Categories</h1>
		<nested:root name="productForm">
			<div class="content">
				<nested:iterate id="category"
					property="document.rootElement.children" indexId="index">
					<div class="href">
						<nested:size id="size" name="category" property="children" />
						<html:link page="/Category.do?index=${index}">
							<nested:write name="category" property="attributes[0].value" />(${size})</html:link>
					</div>
				</nested:iterate>
			</div>
		</nested:root>
	</div>
</body>
</html>