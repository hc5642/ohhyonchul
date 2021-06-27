<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
@font-face { font-family: d2coding; src: url(/font/D2Coding.eot); }
body { font-family: d2coding; font-size: 10pt;}
input[type=text] { font-family: d2coding; padding: 0px 0px; margin: 0px 0; border: 1px solid #CCCCCC; }
input[type=button] { font-family: d2coding; border: 1px solid #CCCCCC; }
textarea { font-family: d2coding; font-size: 10pt; border: 1px solid #CCCCCC;}
table { width: 100%; border-collapse: collapse; }
tr { border: 0px solid #CCCCCC; } 
th { border: 1px solid #CCCCCC; padding: 5px; text-align: center; font-weight: bold; background-color: #f9f9f9; } 
td { border: 1px solid #CCCCCC; padding: 5px; text-align: center; }
</style>
</head>
<body>
<h1>TEST OUT for HTTP</h1>
<form name="frm" action="./test-out-http" method="POST">
<h3>REQUEST INFO</h3>
<ul>
	<li>요청 메시지 작성</li>
</ul>
<blockquote>
<table>
<tr>
	<th colspan="2">SOURCE MSG</th>
</tr>
<tr>
	<td style="width: 20%;">TARGET URL</td>
	<td><input type="text" name="req_url" style="width: 95%;" value="${req_url}"/></td>
</tr>
<tr>
	<td>HEADERS</td>
	<td><input type="text" name="req_headers" style="width: 95%;" value="${req_headers}"/></td>
</tr>
<tr>
	<td>PARAMETERS</td>
	<td><input type="text" name="req_params" style="width: 95%;" value="${req_params}"/></td>
</tr>
<tr>
	<td>
		BODY<br/>
		<select name="req_type" >
		<c:choose>
		<c:when test="${req_type eq 'byte'}">
			<option value="string">STRING</option>
			<option value="byte" selected>BYTE</option>
		</c:when>
		<c:otherwise>
			<option value="string" selected>STRING</option>
			<option value="byte">BYTE</option>
		</c:otherwise>
		</c:choose>
		</select>
	</td>
	<td>
		<textarea style="width: 95%;" rows="15" name="req_body">${req_body}</textarea>
	</td>
</tr>
</table>
</blockquote>
<ul>
	<li>
		전송 : 
		<input type="button" value="GET SEND" onclick="frm.req_method.value='get';frm.submit();" />
		<input type="button" value="POST SEND" onclick="frm.req_method.value='post';frm.submit();" />
		<input type="hidden" name="req_method" />
	</li>
</ul>
</form>
<h3>RESPONSE INFO</h3>
<UL>
	<li>HTTP RESPONSE CODE : ${res_code}</li>
	<li>HTTP HEADERS : ${res_headers}</li>
	<li>HTTP BODY : ${res_body}</li>
</UL>
</body>
</html>