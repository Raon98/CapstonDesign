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
<h2>공동구매 신청</h2>
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
				<input type="submit" name="Submit" value="공동구매 신청">
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
	
	int nowCount, headCount;
	String nick, title, nowCountstr;

	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost:3306/capstondesign?serverTimezone=Asia/Seoul";

	try{
		// JDBC 드라이버 로드
		Class.forName(jdbc_driver);
				
		//데이터베이스 연결정보를 이용해 Connection 인스턴스 확보
		conn = DriverManager.getConnection(jdbc_url, "root", "1234");
		
		String sql = "select title, headcount, nowCount from group_buying";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		title = request.getParameter("title");

	
		
		while(rs.next()) {
			if(title !=null) {
				if(title.equals(rs.getString("title"))){
					headCount = Integer.parseInt(rs.getString("headcount"));
					nowCount = Integer.parseInt(rs.getString("nowCount"));
					if(nowCount < headCount) {
						nowCount++;
						nowCountstr = Integer.toString(nowCount);
						String sql1 = "update group_buying SET nowCount = ? WHERE title = ?";
						pstmt1 = conn.prepareStatement(sql1);
						pstmt1.setString(1, nowCountstr);
						pstmt1.setString(2, title);
						pstmt1.executeUpdate();
						pstmt1.close();
						} else {
							out.print("정원초과");
					} 
				}
			}
			
		}
		pstmt.close();
		
			
		
	}
	catch(Exception e) {
		System.out.println(e);
	}
%>

