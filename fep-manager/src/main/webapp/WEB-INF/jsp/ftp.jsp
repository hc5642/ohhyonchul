<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FEP SERVER VIEWER</title>
<style>
@font-face { font-family: d2coding; src: url(/font/D2Coding.eot); }
body { font-family: d2coding; font-size: 10pt;}
input[type=text] { font-family: d2coding; padding: 0px 0px; margin: 0px 0; border: 1px solid #CCCCCC; }
input[type=button] { font-family: d2coding; border: 1px solid #CCCCCC; }
textarea { font-family: d2coding; font-size: 9pt; border: 1px solid #CCCCCC; width: 95%; height: 450px; border: 0px;}
table { width: 100%; border-collapse: collapse; }
tr { border: 0px solid #CCCCCC; } 
th { border: 1px solid #CCCCCC; padding: 5px; text-align: center; font-weight: bold; background-color: #f9f9f9; } 
td { border: 1px solid #CCCCCC; padding: 5px;}
</style>
<script>
function send(path) {
	document.frm.path.value = path;
	document.frm.submit();
}
</script>
</head>
<body>
<h1>FEP SERVER VIEWER</h1>
<form name="frm" action="/ftp" method="post">
<ul>
	<li>BASE PATH : <input type="text" value="${path}" name="path"> <input type="submit" value="SUBMIT" />
		<a href="#" onclick="send('/dev');">/dev</a> | <a href="#" onclick="send('/kbkdat');">/kbkdat</a> | <a href="#" onclick="send('/kbklog');">/kbklog</a> | <a href="#" onclick="send('/kbksw');">/kbksw</a>
	</li> 
</ul>
<blockquote>
<table>
<tr>
	<th style="width: 30%;">FILE TREE</th>
	<th>VIEWER</th>
</tr>
<tr style="height: 500px;vertical-align : top;">
	<td>
		<img src="/images/folder.png" style="width: 15px; height: 15px;"> <a href="#" onclick="send('${pathback}');">${path}</a><br/>
		<c:forEach var="folder" items="${folderlist}">
			&nbsp;&nbsp;<img src="/images/folder.png" style="width: 15px; height: 15px;"> <a href="#" onclick="send('${path}/${folder.key}');">${folder.key}</a> (${folder.value})<br/>
		</c:forEach>
		<c:forEach var="file" items="${filelist}">
			&nbsp;&nbsp;<img src="/images/file.png" style="width: 15px; height: 15px;"> <a href="#" onclick="frm.pathview.value='${file.key}'; frm.submit();">${file.key}</a> (${file.value})<br/>
		</c:forEach>
	</td>
	<td>
		<textarea>${viewtext}</textarea>
	</td>
</tr>
</table>
</blockquote>
<input type="text" name="pathview" />
</form>
</body>
</html>