<html>
<head>
<% String contextPath = request.getContextPath();%>
<script>
	window.onload = function() {
		window.location = "<%=contextPath + "/seckill/list"%>";
	}
</script>
</head>
<body>
</body>
</html>