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
  <tr> <!-- index section -->
    <td>
      <fmt:message key="index" bundle="${bundle}" />:
    </td>
    <c:forEach varStatus="list" items="${sessionScope['drinkList']}">
      <td>
        <input type="text" name="indexList" placeholder="${list.index * 10}" />
      </td>
    </c:forEach>
      <td>
        <input type="text" name="appendList" size="5" />
      </td>
  </tr>

  <tr> <!-- drinkName section -->
    <td>
      <fmt:message key="name" bundle="${bundle}" />:
    </td>
    <c:forEach var="drink" items="${sessionScope['drinkList']}">
      <td>
        <input type="text" name="drinkList" placeholder="${drink}" />
      </td>
    </c:forEach>
      <td>
        <input type="text" name="appendList" size="5" />
      </td>
  </tr>

  <tr> <!-- EnglishName section -->
    <td>
      <fmt:message key="nameEn" bundle="${bundle}" />:
    </td>
    <c:forEach varStatus="list" items="${sessionScope['drinkList']}">
      <td>  </td>
    </c:forEach>
    <td>
        <input type="text" name="appendList" size="5" />
    </td>
  </tr>

  <tr> <!-- price section -->
    <td>
      <fmt:message key="price" bundle="${bundle}" />:
    </td>
    <c:forEach var="price" items="${sessionScope['priceListStr']}">
      <td>
        <input type="text" name="priceList" placeholder="
            <fmt:message key='currency' bundle='${bundle}'>
              <fmt:param value='${price}' />
            </fmt:message>
          " />
      </td>
    </c:forEach>
    <td>
        <input type="text" name="appendList" size="5" />
    </td>
  </tr>

  <tr> <!-- button section -->
    <td>
      <fmt:message key="delete" bundle="${bundle}" />:
    </td>
    <c:forEach varStatus="list" items="${sessionScope['drinkList']}" >
      <td>
        <button name="delete" value="delete${list.index}">
          <fmt:message key="delete" bundle="${bundle}" />
        </button>
      </td>
    </c:forEach>
      <td>
        <button name="append">
          <fmt:message key="append" bundle="${bundle}" />
        </button>
      </td>
  </tr>
  </table>
</form>
</body>
</html>