<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<link rel="stylesheet" href="include/css/stree.css" type="text/css">
<script language="JavaScript" src="include/js/dtree.js"></script>
<link href="images/admincp/admincp.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript>

function getHostList(Nid,label){  
	var isManual = '${isManual}';
	if(isManual=="false"){
	   parent.getHostList(Nid,label,${num});
   }
   /* else {
   		parent.chooseModel(Nid,label);
   } */

}

</script>
	<script language="javascript">
	var d = new dTree('d');
	${scene};
	document.write(d);
	</script>
