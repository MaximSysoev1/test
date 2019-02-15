<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>SignIn</title>
</head>
<body>
<c:if test="${error!=''}">
    <div style="background-color: Red">
        <c:out value="${error}"></c:out>
    </div>
</c:if>
<form action = "${pageContext.servletContext.contextPath}/signin" method = "post">
    <table>
        <tr>
            <td>Логин</td><td><input type = "text" name="login"></td>
        </tr>
        <tr>
            <td>Пароль</td><td><input type = "password" name="password"></td>
        </tr>
        <tr>
            <td><input type="submit" value = "Войти"/></td>
        </tr>
    </table>
</form>
</body>
</html>
