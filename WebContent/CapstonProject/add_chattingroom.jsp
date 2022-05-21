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
			<td>내 닉네임</td>
			<td><input type="text" name="mynick" size=20></td>
		</tr>
		<tr>
			<td>상대방 닉네임</td>
			<td><input type="text" name="othernick" size=20></td>
		</tr>
		<tr>
			<td>내 채팅방 이름</td>
			<td><input type="text" name="my_room_name" size=20></td>
		</tr>
		<tr>
			<td>상대방 채팅방 이름</td>
			<td><input type="text" name="other_room_name" size=20></td>
		</tr>
		<tr>
			<td>마지막 메시지</td>
			<td><input type="text" name="last_msg" size=20></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" name="add" value="추가"></td>
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
		
		//Connection 클래스의 인스턴스로부터 SQL문 작성을 위한 Statement 준비
		String sql = "insert into chatting_room values(?,?,?,?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, request.getParameter("mynick"));
		pstmt.setString(2, request.getParameter("othernick"));
		pstmt.setString(3, request.getParameter("my_room_name"));
		pstmt.setString(4, request.getParameter("other_room_name"));
		pstmt.setString(5, request.getParameter("last_msg"));

		if(request.getParameter("mynick") != null && request.getParameter("othernick") != null) {
			if(request.getParameter("mynick").equals(request.getParameter("othernick"))) {
				out.println("오류");
			} else {
				pstmt.executeUpdate();
			}
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
		String sql = "select mynick, othernick, my_room_name, other_room_name, last_msg from chatting_room";
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		int i=1;

		while(rs.next()) {
			out.println("mynick"+ " : " + rs.getString("mynick")+ "othernick"+ " : " + rs.getString("othernick")+ 
					"my_room_name : " + rs.getString("my_room_name") + "other_room_name : " + rs.getString("other_room_name") +
					"last_msg" + rs.getString("last_msg"));
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
    