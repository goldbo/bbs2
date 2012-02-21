<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style type="text/css">body{ overflow:hidden}

	body{
	margin: 0px;
	padding: 0px;
	}

</style>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<%
String obj=request.getParameter("file");
String cTask=request.getParameter("cTask");
if(obj==null){
obj="";
}
if(cTask==null){
	cTask="";
}
 %>
<title><%=obj%></title>
<link rel="stylesheet" type="text/css" href="inc/style.css">
<script type="text/javascript" src="<%=basePath %>inc/action.js"></script>
</head>
<body topmargin=0   leftmargin=0>
<input type="hidden" value="<%=cTask %>" id="cTask">
<object id="UnityObject" width="640" classid="clsid:444785F1-DE89-4295-863A-D46C3A781394" codebase="http://webplayer.unity3d.com/download_webplayer/3.0/uo/UnityObject.js" height="480">
<param name="src" value="<%=request.getContextPath()%>/<%=obj%>" />
<param name="disableContextMenu" value="true" />
<param name="backgroundcolor" value="92CFEE" />
<param name="bordercolor" value="000000" />
<param name="textcolor" value="FFFFFF" />
<param name="logoimage" value="images/loadingbar.png" />
<param name="progressbarimage" value="images/Pbar.png" />
<param name="progressframeimage" value="images/Fbar.png" />
<embed id="UnityEmbed" width="640" height="480" src="samsungi909.unity3d" type="application/vnd.unity" backgroundcolor="92CFEE" bordercolor="000000" logoimage="images/loadingbar.png" progressbarimage="images/Pbar.png" progressframeimage="images/Fbar.png" disableContextMenu="true" pluginspage="http://webplayer.unity3d.com/download_webplayer/3.0/uo/UnityObject.js"></embed></object>
<script src="spaceaction.js" type="text/javascript"></script>
</body>
<script language="javascript1.1" type="text/javascript">

  function toclose()
  {
	self.parent.turnoff('products');
	}
  function toclose2()
  {
	window.open("products.html","_self")
	}
	var cTask = document.getElementById("cTask").value;
if(cTask!=""){
	actionTask(cTask);   //update subTask state
}
</script>
</html>


