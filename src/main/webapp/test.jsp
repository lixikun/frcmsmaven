<%@page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type='text/javascript' src='/dwr/interface/LoginService.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
</head>
<body>
<script language="javascript">
	var an = LoginService.getA(function(a){
		alert(a.a);
	});	
</script>
</body>
</html>