<%--
/**
 * @title /WEB-INF/view/venderEditor.jsp
 * @formAction EditorServlet
 * @formMethod POST
 * @content drinkList, priceListの編集・追加・削除の入力フォーム
 *
 * @inputData ---- jsp -> Servlet ----
 * @param id -> indexEditList
 * @param dr -> drinkEditList
 * @param pr -> priceEditList
 * @param ap -> appendEditList
 * @param de -> deleteEditList
 *
 * @author shika
 * @date 2021-08-10
 */
 --%>
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
<form action="EditorServlet" method="POST">
  <!-- DrinkEditorTable -->
  <table border="1">
  <tr> <!-- index section -->
    <td>
      <fmt:message key="index" bundle="${bundle}" />:
    </td>
    <c:forEach varStatus="list" items="${sessionScope['drinkList']}">
      <td>
        <input type="text" name="id" placeholder="${list.index * 10}" />
      </td>
    </c:forEach>
      <td>
        <input type="text" name="ap" size="5" />
      </td>
  </tr>

  <tr> <!-- drinkName section -->
    <td>
      <fmt:message key="name" bundle="${bundle}" />:
    </td>
    <c:forEach var="drink" items="${sessionScope['drinkList']}">
      <td>
        <input type="text" name="dr" placeholder="${drink}" />
      </td>
    </c:forEach>
      <td>
        <input type="text" name="ap" size="5" />
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
        <input type="text" name="ap" size="5" />
    </td>
  </tr>

  <tr> <!-- price section -->
    <td>
      <fmt:message key="price" bundle="${bundle}" />:
    </td>
    <c:forEach var="price" items="${sessionScope['priceListStr']}">
      <td>
        <input type="text" name="pr" placeholder="
            <fmt:message key='currency' bundle='${bundle}'>
              <fmt:param value='${price}' />
            </fmt:message>
          " />
      </td>
    </c:forEach>
    <td>
        <input type="text" name="ap" size="5" />
    </td>
  </tr>

  <tr> <!-- button section -->
    <td>　</td>
    <c:forEach varStatus="list" items="${sessionScope['drinkList']}" >
      <td>
        <button name="de" value="de${list.index}">
          <fmt:message key="delete" bundle="${bundle}" />
        </button>
      </td>
    </c:forEach>
      <td>
        <button>
          <fmt:message key="append" bundle="${bundle}" />
        </button>
      </td>
  </tr>
  </table>
</form>
</body>
</html>

<%--
http://localhost:8080/venderMachineNEO/EditorServlet
  ?id=&id=&id=&id=&id=&ap=50
  &dr=&dr=&dr=&dr=&dr=Milk&ap=Soda&ap=SodaEn
  &pr=&pr=&pr=&pr=&pr=&ap=1.20&de=de3
 --%>