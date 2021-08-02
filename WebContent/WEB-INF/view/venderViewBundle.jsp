<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${sessionScope['locale']}" />
<c:set var="EX_RATE" value="${sessionScope['EX_RATE']}" />
<c:set var="current" value="${requestScope['current']}" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle var="bundle" basename="vender" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vender View Bundle</title>
</head>
<body>
<form name="orderForm" action="MainVenderServlet" method="POST">
    <!-- drinkTable -->
    <table id="drinkTable" border="1">
    <tr> <!-- drink section -->
      <c:forEach var="drink" items="${sessionScope['drinkList']}" >
        <td>${drink}</td>
      </c:forEach>
    </tr>
    <tr> <!-- price section -->
      <c:forEach var="price" items="${sessionScope['priceList']}" >
        <td>
          <fmt:message key="currency" bundle="bundle">
            <fmt:param>
              <fmt:formatNumber value="${price}" />
            </fmt:param>
          </fmt:message>
        </td>
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
        <td>
          <c:choose>
            <c:when test="${locale == 'ja' || locale == 'ja_JP'}">
                ${current}円
            </c:when>
            <c:otherwise>
                             ＄${current / EX_RATE}
            </c:otherwise>
          </c:choose>
        </td>
      </tr>
      <tr>
        <td>
          <c:choose>
            <c:when test="${locale == 'ja' || locale == 'ja_JP'}">
              <c:forEach var="selectValue" items="10, 50, 100, 500, 1000">
                <input type="radio" name="order" value="input${selectValue}">${selectValue}円
              </c:forEach>
            </c:when>
            <c:otherwise>
              <c:forEach var="selectValue" items="10, 50, 100, 500, 1000">
                <input type="radio" name="order" value="input${selectValue}">＄${selectValue / EX_RATE}
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </td>
        <td>
          <c:choose>
            <c:when test="${locale == 'ja' || locale == 'ja_JP'}">
                <input type="submit" value="コイン入金" />
            </c:when>
            <c:otherwise>
                <input type="submit" value="COIN  IN " />
            </c:otherwise>
          </c:choose>
        </td>
      </tr>
      <tr>
        <td id="didBuyCell">
            <c:choose>
              <c:when test="${locale == 'ja' || locale == 'ja_JP'}">購入リスト<br />
              </c:when>
              <c:otherwise>Purchase List: <br /></c:otherwise>
            </c:choose>
            <c:forEach var="didBuy" items="${requestScope['didBuyList']}">
                ${didBuy},&thinsp;
            </c:forEach>
        </td>
        <td>
          <c:choose>
            <c:when test="${locale == 'ja' || locale == 'ja_JP'}">
                <button name="order" value="finish">コイン返金</button>
            </c:when>
            <c:otherwise>
                <button name="order" value="finish">COIN OUT</button>
            </c:otherwise>
          </c:choose>

        </td>
      </tr>
    </table>
    <input type="hidden" name="order" value="input0">
</form>
</body>
</html>

