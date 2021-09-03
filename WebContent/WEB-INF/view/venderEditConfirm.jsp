<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${sessionScope['locale']}" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle var="bundle" basename="vender" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>venderEditConfirm</title>
</head>
<body>
<!-- DrinkEditorTable -->
<table border="1">
  <tr> <!-- index section -->
    <td>
      <fmt:message key="index" bundle="${bundle}" />:
    </td>
    <c:forEach varStatus="list" items="${sessionScope['drinkList']}">
      <td>
        ${list.index * 10}
      </td>
    </c:forEach>
  </tr>
  <tr> <!-- drinkJp section -->
    <td>
      <fmt:message key="name" bundle="${bundle}" />:
    </td>
    <c:forEach var="drinkJp" items="${sessionScope['drinkListJp']}">
      <td>
        ${drinkJp}
      </td>
    </c:forEach>
  </tr>
  <tr> <!-- EnglishName section -->
    <td>
      <fmt:message key="nameEn" bundle="${bundle}" />:
    </td>
    <c:forEach var="drinkEn" items="${sessionScope['drinkListEn']}">
      <td>
        ${drinkEn}
      </td>
    </c:forEach>
  </tr>
  <tr> <!-- price section -->
    <td>
      <fmt:message key="price" bundle="${bundle}" />:
    </td>
    <c:forEach var="price" items="${sessionScope['priceListStr']}">
      <td>
        <fmt:message key='currency' bundle='${bundle}'>
          <fmt:param value='${price}' />
        </fmt:message>
      </td>
    </c:forEach>
  </tr>
  <tr>
    <td colspan="3">Edit OK？</td>
    <td>
      <form action="EditConfirmServlet" method="POST">
        <button name="confirm" value="ok"> OK </button>
        <button name="confirm" value="ng"> NG </button>
      </form>
    </td>
  </tr>
</table>
</body>
</html>