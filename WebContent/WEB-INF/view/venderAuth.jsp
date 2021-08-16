<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Authenication to Edit</title>
</head>
<body>
<form action = "AuthServlet" method="POST">
  <table border="1">
    <tr>
      <td colspan="2">
        ${requestScope['msg']}
      </td>
    </tr>
    <tr>
      <td>User Name: </td>
      <td>
        <input type="text" name="user" size="20" required/>
      </td>
    </tr>
    <tr>
      <td>Password: </td>
      <td>
        <input type="password" name="pass" size="20" required/>
      </td>
    </tr>
    <tr>
      <td>
        <input type="submit" value="Submit" />
        <button formaction="MainVenderBundleServlet" formmethod="GET">Return</button>
      </td>
    </tr>
  </table>
</form>
</body>
</html>