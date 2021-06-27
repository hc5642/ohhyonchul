<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
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
<script>
/* JSTL 특성상 코드내 달러와 중괄호를 함께쓰면 컴파일 오류 발생 */
function loadRule() {
	var rules = document.getElementById("loadRule").value.split("$");
	var constMaps = document.getElementsByName("constMap");
	for (var i=0; i<constMaps.length; i++) {
		if ( typeof rules[i+1] === "undefined") {
			continue;
		}
		if ( rules[i+1].indexOf("'") > 0 ) {
			constMaps[i].value = rules[i+1].substr(2).replaceAll("'}","");
		} else {
			constMaps[i].value = "$" + rules[i+1];
		}
	}
	/* add size */
	var reqColSizes = document.getElementsByName("reqColSize");
	var resColSizes = document.getElementsByName("resColSize");
	var index = 0;
	var numberValue = 0;
	for (var i=0; i<reqColSizes.length; i++) {
		numberValue = reqColSizes[i].value*1;
		index = index + numberValue;
		reqColSizes[i].value = index;
		
	}
	index = 0;
	for (var i=0; i<resColSizes.length; i++) {
		numberValue = resColSizes[i].value*1;
		index = index + numberValue;
		resColSizes[i].value = index;
	}
	
}
function changeData() {
	var inputObj = document.getElementById("inputData");		//원본데이터
	var reqCols = document.getElementsByName("reqCol");			//컬럼길이
	var reqColSizes = document.getElementsByName("reqColSize");	//누적길이
	var index = 0;
	var tempValue = "";
	for (var i = 0; i < reqCols.length; i++) {
		reqCols[i].value = inputObj.value.substr(index,reqColSizes[i].value-index);
		index = reqColSizes[i].value;
	}
	if ( index < inputObj.value.length ) {
		alert("WARN : OVER INPUT DATA");
	}
	exportMap();
}
function exportMap() {
	var reqMaps = document.getElementsByName("reqMap");
	var constMaps = document.getElementsByName("constMap");
	var reqColSizes = document.getElementsByName("reqColSize");	// 요청 누적 컬럼 리스트
	var resColSizes = document.getElementsByName("resColSize"); // 응답 누적 컬럼 리스트
	var index = 0;
	for (var i=0; i<resColSizes.length; i++) {
		/* CONST MAP 에 상수가 있는경우 reqMap 컬럼값 삭제 */
		if ( constMaps[i].value.length > 0 && !constMaps[i].value.startsWith("$")) {
			reqMaps[i].value ="";
		}
		/* reqMap 컬럼값에 따른 액션을 정의 */
		if ( reqMaps[i].value == "" ) {
			constMaps[i].disabled = null;		// reqMap 컬럼값이 없는 경우 상수매핑 OPEN
		} else if ( reqMaps[i].value != "" ) {
			if ( reqMaps[i].value == 1 ) {		// reqMap[i] == 1 인경우 {0, ~} 세팅
				constMaps[i].value = "$"+"{0,"+reqColSizes[reqMaps[i].value-1].value+"}";
			} else if ( reqMaps[i].value > reqColSizes.length) {
				constMaps[i].value = "";		// 지정된 값이 요청컬럼개수보다 많은경우 빈값세팅
			} else {
				constMaps[i].value = "$"+"{"+reqColSizes[reqMaps[i].value-2].value+","+reqColSizes[reqMaps[i].value-1].value+"}";
			}
			constMaps[i].disabled="true";
		}
	}
	generate();
}
function generate() {
	var inputObj = document.getElementById("inputData");
	var constMaps = document.getElementsByName("constMap");
	var finalData = "";
	var saveData = "";
	var startIndex = 0;
	var endIndex = 0;
	for (var i=0; i<constMaps.length; i++) {
		if ( constMaps[i].value.startsWith("$")) {
			var words = constMaps[i].value.split(",");
			startIndex = words[0].substr(2);
			endIndex = words[1].substr(0,words[1].indexOf("}"));
			finalData += inputObj.value.substr(startIndex, endIndex-startIndex);
			saveData += constMaps[i].value;
		} else {
			finalData += constMaps[i].value;
			saveData += "$"+"{'" + constMaps[i].value + "'}";
		}
	}
	document.getElementById("expectData").value = finalData;
	document.getElementById("saveData").value = saveData;
}
function save() {
	
}
</script>
<meta charset="UTF-8">
<title>FEP SIM RULE EDITOR</title>
</head>
<body onload="loadRule(); exportMap();">
<h1>FEP SIMULATOR RULE EDITOR</h1>
<h3>SELECT TX</h3>
<ul>
	<li>SELECT APPL : ${applist}</li>
	<li>SELECT MSG : ${msglist}</li>
	<li>WORKING THREDS : ${threadlist}</li>
</ul>
<h3>INPUT DATA</h3>
<ul>
	<li>CURRENT ROLE : 
		<input type="button" value="LOAD RULE" onclick="loadRule();"/> ${loadRule}
		<input type="hidden" value="${loadRule}" id="loadRule" />
	</li>
	<li>REQUEST DATA : <input type="text" style="width: 80%;" id="inputData" onkeyup="changeData();" value="${input}"/></li>
	<!-- KBANKKFTC003020030000020210102121111   1                                                                                                                                                     8201211337413  -->
</ul>
<blockquote>
<table>
<tr>
	<th style="width: 40%">${msgname}(${msg}) 요청</th>
	<th style="width: 60%">${msgname}(${msg}) 응답</th>
</tr>
<tr>
	<td valign="top">
		<table>
		<tr>
			<th>SEQ</th>
			<th>KOR NAME</th>
			<th>TYPE</th>
			<th>SIZE</th>
			<th>INPUT</th>
		</tr>
		<c:forEach var="reqcol" items="${fn:split(reqdata, '$') }" varStatus="reqStatus">
		<tr>
			<td>${reqStatus.count}</td>
			<c:forEach var="col" items="${fn:split(reqcol, ';') }" varStatus="colStatus">
				<c:choose>
				<c:when test="${colStatus.index == 0}">
					<td>${fn:replace(col,'{','')}</td>
				</c:when>
				<c:when test="${colStatus.index == 1}">
					<td>${col}</td>
				</c:when>
				<c:when test="${colStatus.index == 2}">
					<td>${fn:replace(col,'}','')}
						<input type="hidden" name="reqColSize" value="${fn:replace(col,'}','')}" />
					</td>
				</c:when>
				</c:choose>
			</c:forEach>
			<td style="padding: 0px;"><input type="text" name="reqCol" style="width: 100px;" /></td>
		</tr>
		</c:forEach>
		</table>
	</td>
	<td valign="top">
		<table>
		<tr>
			<th>SEQ</th>
			<th>KOR NAME</th>
			<th>TYPE</th>
			<th>SIZE</th>
			<th>REQ-MAP</th>
			<th>CONST-MAP</th>
		</tr>
		<c:forEach var="rescol" items="${fn:split(resdata, '$') }" varStatus="resStatus">
		<tr>
			<td>${resStatus.count}</td>
			<c:forEach var="col" items="${fn:split(rescol, ';') }" varStatus="colStatus">
			<c:choose>
				<c:when test="${colStatus.index == 0}">
					<td>${fn:replace(col,'{','')}</td>
				</c:when>
				<c:when test="${colStatus.index == 1}">
					<td>${col}</td>
				</c:when>
				<c:when test="${colStatus.index == 2}">
					<td>${fn:replace(col,'}','')}
						<input type="hidden" name="resColSize" value="${fn:replace(col,'}','')}" />
					</td>
				</c:when>
			</c:choose>
			</c:forEach>
			<td style="padding: 0px;"><input type="text" style="width: 20px; text-align: center" name="reqMap" onkeyup="exportMap();" value="${resStatus.count}"/></td>
			<td style="padding: 0px;"><input type="text" style="width: 90%;" name="constMap" onkeyup="exportMap();" /></td>
		</tr>
		</c:forEach>
		</table>
	</td>
</tr>
</table>
</blockquote>
<h3>SAVE DATA</h3>
<ul>
	<li>EXPECT RESPONSE DATA : <input type="button" value="WORK TEST" onclick="exportMap(); " /></li>
</ul>
<blockquote>
	<textarea style="width: 100%;" rows="5" id="expectData"></textarea>
</blockquote>
<form name="frm1" action="/sim/save/${app}/${msg}" method="post">
<ul>
	<li>SAVE RULE DATA : <input type="submit" value="SAVE RULE" /> 
	<input type="checkbox" name="iswork"/> RUN WORK THREAD 
	<input type="checkbox" name="isdown"/> STOP WORK THREAD</li>
</ul>
<blockquote>
	<textarea style="width: 100%;" rows="5" id="saveData"></textarea>
</blockquote>
</form>
</body>
</html>