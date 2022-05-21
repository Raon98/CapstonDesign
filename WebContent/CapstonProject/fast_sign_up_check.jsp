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
<h2>회원가입</h2>
<hr>
<form method="post" name="form1">
	<table border="1" cellspacing="1" cellpadding="5">
		<tr>
			<td>이름</td>
			<td><input type="text" name="name" size=20></td>
		</tr>
		<tr>
			<td>이메일</td>
			<td><input type="text" name="email" size=20></td>
		</tr>
		<tr>
			<td>성별</td>
			<td><input type="text" name="gender" size=20></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" name="check" value="체크"></td>
		</tr>
	</table>
</form>
<hr>
</div>

<%
	//데이터베이스 연결관련 변수 선언
	Connection conn = null;
	PreparedStatement pstmt = null;

	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost:3306/capstondesign?serverTimezone=Asia/Seoul";
	
	try{
		// JDBC 드라이버 로드
		Class.forName(jdbc_driver);
		
		//데이터베이스 연결정보를 이용해 Connection 인스턴스 확보
		conn = DriverManager.getConnection(jdbc_url, "root", "1234");
		
		String sql = "select name, email_front, email_end, gender from sign_up";
		int i = 0;

		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			if(rs.getString("name").equals(request.getParameter("name")) &&
					(rs.getString("email_front") + "@" + rs.getString("email_end")).equals(request.getParameter("email")) &&
					rs.getString("gender").equals(request.getParameter("gender"))) {
				i = 1;
			}
		}

		if(i==1) {
			out.print("signup");
		}
		i = 0;

	} catch (Exception e) {
		e.printStackTrace();
	}
%>


<p>
# 등록 목록<p>
<%
	try{
		// select 문장을 문자열 형태로 구성한다.
		String sql = "select name, email_front, email_end, phone_num, password, nickname, gender from sign_up";
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		int i=1;

		while(rs.next()) {
			out.println("name"+ " : " + rs.getString("name")+ "email"+ " : " + rs.getString("email_front") + "@" + rs.getString("email_end") + " , phonenum"+ " : " + rs.getString("phone_num")
			+" , password"+ ":"+ rs.getString("password")+ " , nickname"+ ":"+ rs.getString("nickname")+ " , gender"+ ":"+ rs.getString("gender")+"<BR>");
		}
		rs.close();
		pstmt.close();
		conn.close();
	}
	catch(Exception e) {
		System.out.println(e);
	}
%>
</body>
</html>
    