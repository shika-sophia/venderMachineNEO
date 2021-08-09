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
<title>Vender Editor</title>
</head>
<body>
<form>
  <!-- DrinkEditorTable -->
  <table border="1">
  <tr> <!-- drink section -->
    <c:forEach var="drink" items="${sessionScope['drinkList']}" varStatus="list">
      <td>
        <input type="text" name="drinkList" value="drink${list.index}"
          placeholder="${drink}" />
      </td>
    </c:forEach>
  </tr>
  <tr> <!-- price section -->
    <c:forEach var="price" items="${sessionScope['priceListStr']}" varStatus="list">
      <td>
        <input type="text" name="priceList" value="price${list.index}"
          placeholder="
            <fmt:message key='currency' bundle='bundle'>
              <fmt:param value='${price}' />
            </fmt:message>
          " />
      </td>
    </c:forEach>
  </tr>
  <tr> <!-- button section -->

  </tr>
  </table>
</form>
</body>
</html>