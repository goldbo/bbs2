<%@ page language="java"  pageEncoding="utf-8"%>
<%@include file="/common/page/common.jsp"%>
<link rel="stylesheet" href="include/css/stree.css" type="text/css">
<script language="JavaScript" src="include/js/dtree.js"></script>
<link href="images/admincp/admincp.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript>
function getHostList(id,resid,desc){ 
}
</script>
	<script language="javascript">
	var d = new dTree('d');
	${scene};
	document.write(d);
	</script>
