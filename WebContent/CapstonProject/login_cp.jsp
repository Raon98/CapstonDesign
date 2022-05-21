<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.sql.*"
    pageEncoding="UTF-8"%>
    
    <% request.setCharacterEncoding("UTF-8"); %>
    
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 등록</title>
</head>
<body>
<div>
<h2>로그인</h2>
<hr>
<form method="post" name="form1">
	<table border="1" cellspacing="1" cellpadding="5">
	
		<tr>
			<td>이메일</td>
			<td><input type="text" name="email_front" size=20> @ <input type="text" name="email_end" size=20></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="password" size=20></td>
		</tr>

		<tr>
			<td colspan="2" align="center">
				<input type="submit" name="Submit" value="로그인">
				<input type="reset" name="Cancel" value="취소"></td>
		</tr>
	</table>
</form>
<hr>
</div>

<%
	//데이터베이스 연결관련 변수 선언
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	String email_front, email_end, password;

	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost:3306/capstondesign?serverTimezone=Asia/Seoul";

	try{
		// JDBC 드라이버 로드
		Class.forName(jdbc_driver);
				
		//데이터베이스 연결정보를 이용해 Connection 인스턴스 확보
		conn = DriverManager.getConnection(jdbc_url, "root", "1234");
		
		// select 문장을 문자열 형태로 구성한다.
		String sql = "select email_front, email_end, name, password from sign_up";
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		int i=1;
		

		email_front = request.getParameter("email_front");
		email_end = request.getParameter("email_end");
		password = request.getParameter("password");
		

		while(rs.next()) {
			if(email_front != null && email_end != null && password != null) {
			if(email_front.equals(rs.getString("email_front")) && email_end.equals(rs.getString("email_end"))
					&& password.equals(rs.getString("password"))) {
				out.println("true");
				out.println("name:" + rs.getString("name") + "/");
			} else {
				out.println("false");
			}
		}
		}
		rs.close();
		pstmt.close();
		conn.close();
	}
	catch(Exception e) {
		System.out.println(e);
	}
%>

