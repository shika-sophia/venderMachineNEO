<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>

<% List<String> drinkName = (List<String>) session.getAttribute("drinkName"); %>
<% List<Integer> price = (List<Integer>) session.getAttribute("price"); %>
<% int moneyStock = (Integer) request.getAttribute("moneyStock"); %>
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
＊投入金額＊ <%= moneyStock %><br>
<br>
<br>
＊お金を入れてください＊
<form action="/venderMachineNEO/MoneyServlet" method="post">
<p>
  <input type="radio" name="moneyFlow" value="1000">1000円　
  <input type="radio" name="moneyFlow" value="500">500円　
  <input type="radio" name="moneyFlow" value="100">100円　
  <input type="radio" name="moneyFlow" value="50">50円　
  <input type="radio" name="moneyFlow" value="10">10円　
  <input type="submit" value="投入">
</p>
</form>
</body>
</html>