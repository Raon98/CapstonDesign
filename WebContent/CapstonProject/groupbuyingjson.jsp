<%@page import= "java.net.URLEncoder" %>
<%@page import= "java.net.URLDecoder" %>
<%@ page import="org.json.simple.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.sql.*"
    pageEncoding="UTF-8"%>


<%
	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String DB_URL = "jdbc:mysql://localhost:3306/capstondesign?serverTimezone=Asia/Seoul";
	String sql = "select nick, title, price, headcount, text, area, nowCount, watchnick from group_buying";
	Class.forName(jdbc_driver);
	Connection conn = DriverManager.getConnection(DB_URL, "root", "1234");
	PreparedStatement pstmt = conn.prepareStatement(sql);
	ResultSet rs = pstmt.executeQuery();
	
	
	JSONArray arr = new JSONArray();
	while(rs.next())
		{
			String nick = URLEncoder.encode(rs.getString("nick"), "UTF-8");
			String title = URLEncoder.encode(rs.getString("title"), "UTF-8");
			String price = URLEncoder.encode(rs.getString("price"), "UTF-8");
			String headcount = URLEncoder.encode(rs.getString("headcount"), "UTF-8");
			String text = URLEncoder.encode(rs.getString("text"), "UTF-8");
			String area = URLEncoder.encode(rs.getString("area"), "UTF-8");
			String nowCount = URLEncoder.encode(rs.getString("nowCount"), "UTF-8");
			String watchnick = URLEncoder.encode(rs.getString("watchnick"), "UTF-8");



			
			String nick1 = URLDecoder.decode(nick, "UTF-8");
			String title1 = URLDecoder.decode(title, "UTF-8");
			String price1 = URLDecoder.decode(price, "UTF-8");
			String headcount1 = URLDecoder.decode(headcount, "UTF-8");
			String text1 = URLDecoder.decode(text, "UTF-8");
			String area1 = URLDecoder.decode(area, "UTF-8");
			String nowCount1 = URLDecoder.decode(nowCount, "UTF-8");
			String watchnick1 = URLDecoder.decode(watchnick, "UTF-8");


			 
			JSONObject obj = new JSONObject();
			obj.put("nick", nick1);
			obj.put("title", title1);
			obj.put("price", price1);
			obj.put("headcount", headcount1);
			obj.put("text", text1);
			obj.put("area", area1);
			obj.put("nowCount", nowCount1);
			obj.put("watchnick", watchnick1);

			if(obj != null)
				arr.add(obj);
		}
	out.print(arr);
	

%>