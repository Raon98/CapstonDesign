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
			<td>휴대폰</td>
			<td><input type="text" name="phone_num" size=20></td>
		</tr>
		<tr>
		<tr>
			<td>이메일</td>
			<td><input type="text" name="email_front" size=20> @ <input type="text" name="email_end" size=20></td>
		</tr>
		<tr>
			<td>닉네임</td>
			<td><input type="text" name="nick" size=20></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="pwd" size=20></td>
		</tr>
		<tr>
			<td>성별</td>
			<td><input type="text" name="gender" size=20></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" name="Submit" value="등록">
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

	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost:3306/capstondesign?serverTimezone=Asia/Seoul";
	
	try{
		// JDBC 드라이버 로드
		Class.forName(jdbc_driver);
		
		//데이터베이스 연결정보를 이용해 Connection 인스턴스 확보
		conn = DriverManager.getConnection(jdbc_url, "root", "1234");
		
		String sql = "select phone_num, nickname, email_front, email_end from sign_up";
		int i = 0;
		int k = 0;
		int j = 0;
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			if(rs.getString("phone_num").equals(request.getParameter("phone_num"))) {
				i=1;
			}
			if(rs.getString("nickname").equals(request.getParameter("nick"))) {
				k=1;
			}
			String email = rs.getString("email_front") + "@" + rs.getString("email_end");
			if(email.equals(request.getParameter("email_front") + "@" + request.getParameter("email_end"))) {
				j=1;
			}
		}
		
		//Connection 클래스의 인스턴스로부터 SQL문 작성을 위한 Statement 준비
		String sql2 = "insert into sign_up values(?,?,?,?,?,?,?)";
		pstmt = conn.prepareStatement(sql2);
		pstmt.setString(1, request.getParameter("name"));
		pstmt.setString(2, request.getParameter("phone_num"));
		pstmt.setString(3, request.getParameter("email_front"));
		pstmt.setString(4, request.getParameter("email_end"));
		pstmt.setString(5, request.getParameter("nick"));
		pstmt.setString(6, request.getParameter("pwd"));
		pstmt.setString(7, request.getParameter("gender"));
		
		
		//id 값을 입력한 경우 sql 문장을 수행.
		if(request.getParameter("phone_num") != null && i == 0 && k == 0 && j==0) {
			pstmt.executeUpdate();
		}
		if(i==1 && k==1 && j==1) {
			out.print("sameNumNickEmail");
		} else if (i==0 && k==1 && j==0) {
			out.print("sameNick/");
		} else if (i==1 && k==0  && j==0) {
			out.print("sameNum/");
		} else if (i==0 && k==0  && j==1) {
			out.print("sameEmail/");
		} else if (i==1 && k==1 && j==0) {
			out.print("sameNumNick/");
		} else if (i==1 && k==0 && j==1) {
			out.print("sameNumEmail/");
		} else if (i==0 && k==1 && j==1) {
			out.print("sameNickEmail/");
		}
		i = 0;
		k = 0;
		j = 0;
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
			out.println("name"+ " : " + rs.getString("name")+ "email"+ " : " + rs.getString("email_front") + "@" + rs.getString("email_end") + "phonenum"+ " : " + rs.getString("phone_num")
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
    