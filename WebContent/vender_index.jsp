<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>

<% List<String> drinkName = (List<String>) session.getAttribute("drinkName"); %>
<% List<Integer> price = (List<Integer>) session.getAttribute("price"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vender Machine INDEX</title>
<link rel="stylesheet/css" href="venderStyle.css" >

</head>
<body>
＊ Drink Menu ＊
<% for (int i = 0; i < drinkName.size(); i++){%>
<p id="menu"><%= drinkName.get(i) %> : <%= price.get(i) %>円</p>
<% }//for i %>
<br>
<br>
＊お金を入れてください＊
<form action="/venderMachineNEO/MoneyServlet" method="post">
<p>
  <input type="radio" name="money" value="1000">1000円　
  <input type="radio" name="money" value="500">500円　
  <input type="radio" name="money" value="100">100円　
  <input type="radio" name="money" value="50">50円　
  <input type="radio" name="money" value="10">10円　
  <input type="submit" value="投入">
</p>
</form>
</body>
</html>