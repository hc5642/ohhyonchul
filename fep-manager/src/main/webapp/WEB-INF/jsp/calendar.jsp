<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
@font-face { 
	font-family: rix;
	src: url(/font/rix2.woff2);
}
body {
	font-family: rix;
	font-size: 10pt;
}
</style>
<script>
function saveItem(key, value) {
	if ( window.event.keyCode == 9 ) {
		document.frm.key.value = key;
		document.frm.value.value = value;
		document.frm.submit();
	}
}
</script>
</head>
<body>
<h1>FEP CALENDA 대외계 칼렌더 - ${yearInt }.${monthInt }</h1>
<img src="/images/sample.png" />
<table style="width: 100%; table-layout:fixed;" border=1>
<tr>
	<th style="width: 10%;">SUN</th>
	<th style="width: 16%;">MON</th>
	<th style="width: 16%;">TUE</th>
	<th style="width: 16%;">WEN</th>
	<th style="width: 16%;">THS</th>
	<th style="width: 16%;">FRI</th>
	<th style="width: 10%;">SAT</th>
</tr>
<c:set var="isContinue" value="true"/>
<c:forEach var="row" items="${dayTable}" varStatus="row_status">
<c:if test="${isContinue eq 'true'}">
	<tr style="height: 100px;">
		<c:forEach var="col" items="${row}" varStatus="cal_status">
		<c:choose>
			<c:when test="${col > 0}">
				<td valign="top">
					<b>${col}</b><br/>
					<div contenteditable='true' onkeydown="saveItem('CAL.${yearInt}.${monthInt}.${col}', this.innerHTML);">
						<c:set var="tempKey">CAL.${yearInt}.${monthInt}.${col}</c:set>
						${contents[tempKey]}
					</div>
				</td>
			</c:when>
			<c:otherwise>
				<td style="background-color: #CCCCCC">
					&nbsp;
				</td>
				<c:if test="${row_status.index > 1}">
					<c:set var="isContinue" value="false"/>
				</c:if>
			</c:otherwise>
		</c:choose>
		</c:forEach>
	</tr>
</c:if>
</c:forEach>
</table>
<form name="frm" method="POST">
	<input type="hidden" name="key" value="" />
	<input type="hidden" name="value" value="" />
	<input type="hidden" name="year" value="${yearInt}" />
	<input type="hidden" name="month" value="${monthInt}" />
</form>
</body>
</html>