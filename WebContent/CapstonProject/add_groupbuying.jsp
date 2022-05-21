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
<h2>공동구매</h2>
<hr>
<form method="post" name="form1">
	<table border="1" cellspacing="1" cellpadding="5">
		<tr>
			<td>닉네임</td>
			<td><input type="text" name="nick" size=20></td>
		</tr>
		<tr>
			<td>제목</td>
			<td><input type="text" name="title" size=20></td>
		</tr>
		<tr>
			<td>가격</td>
			<td><input type="text" name="price" size=20></td>
		</tr>
		<tr>
			<td>모집인원</td>
			<td><input type="text" name="headcount" size=20></td>
		</tr>
		<tr>
			<td>내용</td>
			<td><input type="text" name="text" size=20></td>
		</tr>
		<tr>
			<td>지역</td>
			<td><input type="text" name="area" size=20></td>
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
		String sql = "insert into group_buying values(?,?,?,?,?,?,1,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, request.getParameter("nick"));
		pstmt.setString(2, request.getParameter("title"));
		pstmt.setString(3, request.getParameter("price"));
		pstmt.setString(4, request.getParameter("headcount"));
		pstmt.setString(5, request.getParameter("text"));
		pstmt.setString(6, request.getParameter("area"));
		pstmt.setString(7, "");
		

		if(request.getParameter("nick") != null && request.getParameter("title") != null) {
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
<<<<<<< HEAD
		String sql = "select title, price, headcount, text, area, nowCount, watchnick  from group_buying";
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		int i=1;

		while(rs.next()) {
			out.println("title"+ " : " + rs.getString("title")+ " price"+ " : " + rs.getString("price")+ 
					" headcount : " + rs.getString("headcount") + " text : " + rs.getString("text") +
					" area : " + rs.getString("area") +  " nowCount : " + rs.getString("nowCount")
					+  " watchnick : " + rs.getString("watchnick"));
=======
		String sql = "select title, price, headcount, text, area, nowCount  from group_buying";
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		int i=1;

		while(rs.next()) {
			out.println("title"+ " : " + rs.getString("title")+ " price"+ " : " + rs.getString("price")+ 
					" headcount : " + rs.getString("headcount") + " text : " + rs.getString("text") +
					" area : " + rs.getString("area") +  " nowCount : " + rs.getString("nowCount"));
>>>>>>> refs/remotes/origin/master
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
    