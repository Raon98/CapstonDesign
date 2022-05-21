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
<hr>
<form method="post" name="form1">
	<table border="1" cellspacing="1" cellpadding="5">
	
		<tr>
			<td>last_msg</td>
			<td><input type="text" name="last_msg" size=20></td>
		</tr>
		<tr>
			<td>my_room_name</td>
			<td><input type="text" name="my_room_name" size=20></td>
		</tr>
		<tr>
			<td>myNick</td>
			<td><input type="text" name="mynick" size=20></td>
		</tr>
		<tr>
			<td>otherNick</td>
			<td><input type="text" name="othernick" size=20></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" name="Submit" value="업데이트">
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
	PreparedStatement pstmt1 = null;

	ResultSet rs = null;
	
	String mynick, othernick, my_room_name, other_room_name, last_msg;

	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost:3306/capstondesign?serverTimezone=Asia/Seoul";

	try{
		// JDBC 드라이버 로드
		Class.forName(jdbc_driver);
				
		//데이터베이스 연결정보를 이용해 Connection 인스턴스 확보
		conn = DriverManager.getConnection(jdbc_url, "root", "1234");
		
		last_msg = request.getParameter("last_msg");
		my_room_name = request.getParameter("my_room_name");
		mynick = request.getParameter("mynick");
		othernick = request.getParameter("othernick");
		
		String sql = "select mynick, othernick from chatting_room";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			if(mynick !=null && my_room_name != null) {
				if(mynick.equals(rs.getString("mynick"))){
					String sql1 = "update chatting_room SET last_msg = ? WHERE my_room_name = ? and mynick = ?";
					pstmt1 = conn.prepareStatement(sql1);
					pstmt1.setString(1, last_msg);
					pstmt1.setString(2, my_room_name);
					pstmt1.setString(3, mynick);
					pstmt1.executeUpdate();
					pstmt1.close();
				} else if(mynick.equals(rs.getString("othernick"))) {
					String sql1 = "update chatting_room SET last_msg = ? WHERE other_room_name = ? and othernick = ?";
					pstmt1 = conn.prepareStatement(sql1);
					pstmt1.setString(1, last_msg);
					pstmt1.setString(2, my_room_name);
					pstmt1.setString(3, mynick);
					pstmt1.executeUpdate();
					pstmt1.close();
				}
			} 
		}
		pstmt.close();


	}
	catch(Exception e) {
		System.out.println(e);
	}
%>

