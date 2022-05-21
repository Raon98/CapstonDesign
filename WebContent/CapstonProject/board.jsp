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
			<td>닉네임</td>
			<td><input type="text" name="nick" size=20></td>
		</tr>
		<tr>
			<td>방 이름</td>
			<td><input type="text" name="room_name" size=20></td>
		</tr>
		<tr>
		<tr>
			<td>방 메시지</td>
			<td><input type="text" name="room_message" size=20></td>
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
	int i = 1;

	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost:3306/capstondesign?serverTimezone=Asia/Seoul";
	
	try{
		// JDBC 드라이버 로드
		Class.forName(jdbc_driver);
		
		//데이터베이스 연결정보를 이용해 Connection 인스턴스 확보
		conn = DriverManager.getConnection(jdbc_url, "root", "1234");
		
		String sql = "select room_num from board";

		if(request.getParameter("nick") != null) {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getString("room_num") == null) {
					i = 1;
				}
				else {
					i = Integer.parseInt(rs.getString("room_num")) + 1;
				}	
			}
			pstmt.close();
				
			if(i == 1) {
				String sql2 = "insert into board values(?,?,?,?)";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, "1");
				pstmt.setString(2, request.getParameter("nick"));
				pstmt.setString(3, request.getParameter("room_name"));
				pstmt.setString(4, request.getParameter("room_message"));
				pstmt.executeUpdate();
				pstmt.close();
			} else {
				String sql2 = "insert into board values(?,?,?,?)";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, Integer.toString(i));
				pstmt.setString(2, request.getParameter("nick"));
				pstmt.setString(3, request.getParameter("room_name"));
				pstmt.setString(4, request.getParameter("room_message"));
			}
			pstmt.executeUpdate();
		}
		
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
%>



<p>
# 등록 목록<p>
<%
	try{
		// select 문장을 문자열 형태로 구성한다.
		String sql = "select room_num, nick, room_name, room_message from board";
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while(rs.next()) {
			out.println("room_num"+ " : " + rs.getString("room_num")+ " , nick"+ " : " + rs.getString("nick") 
			+" , room_name"+ ":"+ rs.getString("room_name")+ " , room_message"+ ":"+ rs.getString("room_message"));
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
    