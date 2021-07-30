<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vender Machine NEO</title>
</head>
<body>

<form action="MainVenderServlet" method="POST">
<!-- drinkTable -->
<table id="drinkTable" border="1">
<tr>
  <c:forEach var="drink" items="${requestScope['data'].drinkList}" >
    <td>${drink}</td>
  </c:forEach>
</tr>
<tr>
  <c:forEach var="price" items="${requestScope['data'].priceList}" >
    <td>${price}</td>
  </c:forEach>
</tr>
<tr>
  <c:forEach var="canBuy" items="${requestScope['canBuyList']}" >
    <td>${canBuy}</td>
  </c:forEach>
</tr>
</table>
<br />
<!-- inputTable -->
<table id="inputTable" border="1">
  <tr>
    <td>${requestScope['msg']}</td>
    <td>${requestScope['current']}</td>
  </tr>
  <tr>
    <td>
      <input type="radio" name="order" value="input10">10
      <input type="radio" name="order" value="input50">50
      <input type="radio" name="order" value="input100">100
      <input type="radio" name="order" value="input500">500
      <input type="radio" name="order" value="input1000">1000
    </td>
    <td>
      <input type="submit" value="コイン投入">
    </td>
  </tr>
</table>
</form>
</body>
</html>