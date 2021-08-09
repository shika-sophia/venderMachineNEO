<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${sessionScope['locale']}" />
<c:set var="EX_RATE" value="${sessionScope['EX_RATE']}" />
<c:set var="current" value="${requestScope['current']}" />
  <c:if test="${locale != 'ja' && locale != 'ja_JP'}">
    <c:set var="current" value="${current / EX_RATE}" />
  </c:if>
<fmt:setLocale value="${locale}" />
<fmt:setBundle var="bundle" basename="vender" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vender View Bundle</title>
</head>
<body>
<form name="orderForm" action="MainVenderBundleServlet" method="POST">
    <!-- drinkTable -->
    <table id="drinkTable" border="1">
    <tr> <!-- drink section -->
      <c:forEach var="drink" items="${sessionScope['drinkList']}" >
        <td>${drink}</td>
      </c:forEach>
    </tr>
    <tr> <!-- price section -->
      <c:forEach var="price" items="${sessionScope['priceListStr']}" >
        <td>
          <fmt:message key="currency" bundle="${bundle}">
            <fmt:param>
              <fmt:formatNumber value="${price}" maxFractionDigits="2" />
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
          <fmt:message key="currency" bundle="${bundle}">
            <fmt:param>
              <fmt:formatNumber value="${current}" maxFractionDigits="2" />
            </fmt:param>
          </fmt:message>
        </td>
      </tr>
      <tr>
        <td>
           <c:forEach var="select" items="${sessionScope['selectListStr']}" >
              <input type="radio" name="order" value="input${select}">
                <fmt:message key="currency" bundle="${bundle}">
                  <fmt:param>
                    <fmt:formatNumber value="${select}" maxFractionDigits="2"/>
                  </fmt:param>
                </fmt:message>
            </c:forEach>
        </td>
        <td>
          <button type="submit">
            <fmt:message key="in" bundle="${bundle}" />
          </button>
        </td>
      </tr>
      <tr>
        <td id="didBuyCell">
            <fmt:message key="list" bundle="${bundle}" /><br />
            <c:forEach var="didBuy" items="${requestScope['didBuyList']}">
                ${didBuy},&thinsp;
            </c:forEach>
        </td>
        <td>
          <button name="order" value="finish">
            <fmt:message key="out" bundle="${bundle}" />
          </button>
        </td>
      </tr>
    </table>
<br />
    <button name="order" value="<fmt:message key='language' bundle='${bundle}' />">
        <fmt:message key="language" bundle="${bundle}" />
    </button>
    <input type="hidden" name="order" value="input0">
</form>
</body>
</html>

