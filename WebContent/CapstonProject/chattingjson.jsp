<%@page import= "java.net.URLEncoder" %>
<%@page import= "java.net.URLDecoder" %>
<%@ page import="org.json.simple.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.sql.*"
    pageEncoding="UTF-8"%>


<%
	String jdbc_driver = "com.mysql.cj.jdbc.Driver";
	String DB_URL = "jdbc:mysql://localhost:3306/capstondesign?serverTimezone=Asia/Seoul";
	String sql = "select mynick, othernick, my_room_name, other_room_name, last_msg from chatting_room";
	Class.forName(jdbc_driver);
	Connection conn = DriverManager.getConnection(DB_URL, "root", "1234");
	PreparedStatement pstmt = conn.prepareStatement(sql);
	ResultSet rs = pstmt.executeQuery();
	
	
	JSONArray arr = new JSONArray();
	while(rs.next())
		{
			String mynick = URLEncoder.encode(rs.getString("mynick"), "UTF-8");
			String othernick = URLEncoder.encode(rs.getString("othernick"), "UTF-8");
			String my_room_name = URLEncoder.encode(rs.getString("my_room_name"), "UTF-8");
			String other_room_name = URLEncoder.encode(rs.getString("other_room_name"), "UTF-8");
			String last_msg = URLEncoder.encode(rs.getString("last_msg"), "UTF-8");


			
			String mynick1 = URLDecoder.decode(mynick, "UTF-8");
			String othernick1 = URLDecoder.decode(othernick, "UTF-8");
			String my_room_name1 = URLDecoder.decode(my_room_name, "UTF-8");
			String other_room_name1 = URLDecoder.decode(other_room_name, "UTF-8");
			String last_msg1 = URLDecoder.decode(last_msg, "UTF-8");

			 
			JSONObject obj = new JSONObject();
			obj.put("mynick", mynick1);
			obj.put("othernick", othernick1);
			obj.put("my_room_name", my_room_name1);
			obj.put("other_room_name", other_room_name1);
			obj.put("last_msg", last_msg1);

			if(obj != null)
				arr.add(obj);
		}
	out.print(arr);
	

%>