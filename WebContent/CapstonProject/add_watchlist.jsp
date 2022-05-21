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
<<<<<<< HEAD
<h2>관심목록</h2>
<hr>
<form method="post" name="form1">
	<table border="1" cellspacing="1" cellpadding="5">
	
		<tr>
			<td>관심목록 선택한 사람 닉네임</td>
			<td><input type="text" name="watchnick" size=20></td>
		</tr>
		<tr>
			<td>제목</td>
			<td><input type="text" name="title" size=20></td>
		</tr>
		<tr>
			<td>닉네임</td>
			<td><input type="text" name="nick" size=20></td>
		</tr>

		<tr>
			<td colspan="2" align="center">
				<input type="submit" name="Submit" value="추가">
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
	PreparedStatement pstmt2 = null;
	ResultSet rs = null;

	String nick, title;
	String watchlist = "";

	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost:3306/capstondesign?serverTimezone=Asia/Seoul";

	try{
		// JDBC 드라이버 로드
		Class.forName(jdbc_driver);
				
		//데이터베이스 연결정보를 이용해 Connection 인스턴스 확보
		conn = DriverManager.getConnection(jdbc_url, "root", "1234");
		
		String sql = "select title, nick, watchnick from group_buying";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		title = request.getParameter("title");
	  
		
		while(rs.next()) {
			if(title !=null) {
				if(title.equals(rs.getString("title"))){
					if(rs.getString("watchnick") != null) {
						watchlist = rs.getString("watchnick");
					}
						if(!watchlist.contains(request.getParameter("watchnick") + ",")) {
							String sql1 = "update group_buying SET watchnick = ? WHERE title = ? and nick = ?";
							pstmt1 = conn.prepareStatement(sql1);
							pstmt1.setString(1, rs.getString("watchnick") + request.getParameter("watchnick") + ",");
							pstmt1.setString(2, title);
							pstmt1.setString(3, request.getParameter("nick"));
							pstmt1.executeUpdate();
							pstmt1.close();
						} else {
							String sql1 = "update group_buying set watchnick = replace(?, ?, ?) WHERE title = ? and nick = ?";
							pstmt1 = conn.prepareStatement(sql1);
							pstmt1.setString(1, rs.getString("watchnick"));
							pstmt1.setString(2, request.getParameter("watchnick") + ",");
							pstmt1.setString(3, "");
							pstmt1.setString(4, title);
							pstmt1.setString(5, request.getParameter("nick"));
							pstmt1.executeUpdate();
							pstmt1.close();
						}
				}
				
			}
=======
<h2>닉네임 변경</h2>
<hr>
<form method="post" name="form1">
	<table border="1" cellspacing="1" cellpadding="5">
	
		<tr>
			<td>제목</td>
			<td><input type="text" name="title" size=20></td>
		</tr>
		<tr>
			<td>닉네임</td>
			<td><input type="text" name="nick" size=20></td>
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
	PreparedStatement pstmt1 = null;
	PreparedStatement pstmt2 = null;
	ResultSet rs = null;
	
	String nick, title;

	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost:3306/capstondesign?serverTimezone=Asia/Seoul";

	try{
		// JDBC 드라이버 로드
		Class.forName(jdbc_driver);
				
		//데이터베이스 연결정보를 이용해 Connection 인스턴스 확보
		conn = DriverManager.getConnection(jdbc_url, "root", "1234");
		
		String sql = "select title, watchnick from group_buying";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		title = request.getParameter("title");
	
		
		while(rs.next()) {
			if(title !=null) {
				if(title.equals(rs.getString("title"))){
					String sql1 = "update group_buying SET watchnick = ? WHERE title = ?";
					pstmt1 = conn.prepareStatement(sql1);
					pstmt1.setString(1, rs.getString("nick") + "| " +request.getParameter("nick"));
					pstmt1.setString(2, request.getParameter("title"));
					pstmt1.executeUpdate();
					pstmt1.close();
				} else {
					out.print("없는게시판");
				}
			}
			
>>>>>>> refs/remotes/origin/master
		}
		pstmt.close();
		
			
		
	}
	catch(Exception e) {
		System.out.println(e);
	}
%>

