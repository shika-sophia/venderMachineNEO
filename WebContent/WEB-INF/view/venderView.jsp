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

<form name="orderForm" action="MainVenderServlet" method="POST">
    <!-- drinkTable -->
    <table id="drinkTable" border="1">
    <tr> <!-- drink section -->
      <c:forEach var="drink" items="${requestScope['data'].drinkList}" >
        <td>${drink}</td>
      </c:forEach>
    </tr>
    <tr> <!-- price section -->
      <c:forEach var="price" items="${requestScope['data'].priceList}" >
        <td>${price}</td>
      </c:forEach>
    </tr>
    <tr> <!-- button section -->
      <c:set var="index" value="0" />
      <c:forEach var="canBuy" items="${requestScope['canBuyList']}" >
        <td> <!-- true時のみボタン有効  -->
          <c:if test="${canBuy}" >
            <button type="submit" name="order" value="req${index}">　●　</button>
          </c:if>
          <c:if test="${!canBuy}" >
            <button disabled>　×　</button>
          </c:if>
          <c:set var="index" value="${index + 1}" />
        </td>
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
          <input type="submit" value="コイン入金" />
        </td>
      </tr>
      <tr>
        <td id="didBuyCell">購入リスト<br />
            <c:forEach var="didBuy" items="${requestScope['didBuyList']}">
                ${didBuy},&thinsp;
            </c:forEach>
        </td>
        <td>
            <button name="order" value="finish">コイン返金</button>
        </td>
      </tr>
    </table>
    <input type="hidden" name="order" value="input0">
</form>
</body>
</html>

<!--
【考察】 JavaScript
◆<form>内 onsubmit属性
onsubmit="return document.orderForm.order.value != '' "
ラジオボタンが未チェックの場合、送信無効。
購入ボタン時にも作用してしまい、ラジオボタンをチェックしていないと
ボタンが無効になる。

onsubmit属性を消去すると、
未チェックのまま入金で order=nullとなり、NullPointerException

<input type="hidden" name="order" value="input0">で解決

-->