<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 확인</title>
<link rel="stylesheet" href="../resources/css/shopping.css">
<script type="text/javascript" src="../resources/script/board.js"></script>
</head>
<body>
	<div align="center">
		<h1>비밀번호 확인</h1>
		<form action="/board/check" name="frm" method="post">
			<input type="hidden" name="num" value="${num}">
			<table style="width: 80%">
				<tr>
					<th>비밀번호</th>
				    <td><input type="password" name="pass" size="20"></td>
				</tr>
			</table>
			<br>
			<input type = "submit" value="확인" onclick="return passCheck()">
			<br><br>
			<div style="color: red; font-size: 20px">
				${message}
			</div>
		</form>
	</div>
</body>
</html>