<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>${title }</title>
</head>
<body>
<h1>${title }</h1>
<form name="frm" action="/log/list" method="GET">
Search Key : <input type="text" name="searchKey" value="${searchKey }"/>
<input type="submit" value="SEARCH" />
<input type="button" onclick="location.href='/log/insert';" value="INSERT TEST" />
</form>
<table border=1>
<tr>
	<th>SEQ_NUM</th>
	<th>INST_CODE</th>
	<th>INST_NAME</th>
	<th>APPL_CODE</th>
	<th>APPL_NAME</th>
	<th>TYPE_CLCD</th>
	<th>EDIT/DELETE</th>
</tr>
<tr>
	<td>-</td>
	<td><input type="text" onKeyUp="document.frm_insert.instCode.value=this.value" /></td>
	<td><input type="text" onKeyUp="document.frm_insert.instName.value=this.value" /></td>
	<td><input type="text" onKeyUp="document.frm_insert.applCode.value=this.value" /></td>
	<td><input type="text" onKeyUp="document.frm_insert.applName.value=this.value" /></td>
	<td><input type="text" onKeyUp="document.frm_insert.typeClcd.value=this.value" /></td>
	<td><input type="button" value="INSERT" onclick="document.frm_insert.submit();" /></td>
</tr>
<c:forEach var="row" items="${list }">
<tr>
	<td><input type="text" value="${row.seqNum }" onkeyUp="document.frm_edit.seqNum.value=this.value;" /></td>
	<td><input type="text" value="${row.instCode }" onkeyUp="document.frm_edit.instCode.value=this.value;"/></td>
	<td><input type="text" value="${row.instName }" onkeyUp="document.frm_edit.instName.value=this.value;"/></td>
	<td><input type="text" value="${row.applCode }" onkeyUp="document.frm_edit.applCode.value=this.value;"/></td>
	<td><input type="text" value="${row.applName }" onkeyUp="document.frm_edit.applName.value=this.value;"/></td>
	<td><input type="text" value="${row.typeClcd }" onkeyUp="document.frm_edit.typeClcd.value=this.value;"/></td>
	<td>
		<input type="button" value="EDIT" onclick="document.frm_edit.submit();"/>
		<input type="button" value="DELETE" onclick="location.href='/log/delete?seqNum=${row.seqNum}'; " />
	</td>
</tr>
</c:forEach>
</table>
<form name="frm_insert" action="/log/insert" method="POST">
	<input type="text" name="instCode" />
	<input type="text" name="instName" />
	<input type="text" name="applCode" />
	<input type="text" name="applName" />
	<input type="text" name="typeClcd" />
</form>
<form name="frm_edit" action="/log/edit" method="POST">
	<input type="text" name="seqNum" />
	<input type="text" name="instCode" />
	<input type="text" name="instName" />
	<input type="text" name="applCode" />
	<input type="text" name="applName" />
	<input type="text" name="typeClcd" />
</form>
</body>
</html>