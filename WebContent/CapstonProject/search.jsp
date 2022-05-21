<%@page import= "java.net.URLEncoder" %>
<%@page import= "java.net.URLDecoder" %>
<%@ page import="org.json.simple.*" %>
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
<h2>검색</h2>
<hr>


<form method="post" name="form1">
	<table border="1" cellspacing="1" cellpadding="5">
		<tr>
			<td>검색</td>
			<td><input type="text" name="search" size=20></td>
		</tr>
		
		<tr>
			<td colspan="2" align="center">
				<input type="submit" name="Submit" value="검색">
				<input type="reset" name="Cancel" value="취소"></td>
		</tr>

	</table>
</form>
<hr>
<p>
# 등록 목록<p>
</div>
</body>
</html>




<%
	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String DB_URL = "jdbc:mysql://localhost:3306/capstondesign?serverTimezone=Asia/Seoul";
	String search = "\"%" + request.getParameter("search") + "%\"";
	
	if(search != null) {
		String sql = "select nick, title, text from board where title like " + search;
		Class.forName(jdbc_driver);
		Connection conn = DriverManager.getConnection(DB_URL, "root", "1234");
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		JSONArray arr = new JSONArray();
		while(rs.next())
			{
				String nick = URLEncoder.encode(rs.getString("nick"), "UTF-8");
				String title = URLEncoder.encode(rs.getString("title"), "UTF-8");
				String text = URLEncoder.encode(rs.getString("text"), "UTF-8");
				
				String nick1 = URLDecoder.decode(nick, "UTF-8");
				String title1 = URLDecoder.decode(title, "UTF-8");
				String text1 = URLDecoder.decode(text, "UTF-8");


				 
				JSONObject obj = new JSONObject();
				obj.put("nick", nick1);
				obj.put("title", title1);
				obj.put("text", text1);


				if(obj != null)
					arr.add(obj);
			}
		out.print(arr);
	}

%>

    