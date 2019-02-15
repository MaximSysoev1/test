<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Редактировать категоирию</title>
</head>
<body>
<c:if test="${error!=''}">
    <div style="background-color: Red">
        <c:out value="${error}"></c:out>
    </div>
</c:if>
<form action="" method="post">
    <table align="center" border="0" width="600px">
        <tr>
            <td align="center" rowspan="2" colspan="3"><h3>Обновлении категории</h3></td>
        </tr>
        <tr><td height="25px"></td></tr>
        <tr>
            <td align="left"><b>Название категории</b></td>
            <td align="left"><b>Описание</b></td>
        </tr>
        <tr><td height="10px"></td></tr>
        <tr>
            <td valign="top"><input type = "text" name="name" value = "<c:out value="${cat.catName}"/>"></td>
            <td valign="top"><textarea rows="5" cols="35" name="text"><c:out value="${cat.description}"></c:out></textarea></td>
            <td valign="top"><input type="submit" value="Обновить"></td>
        </tr>
        <tr><td height="10px"></td></tr>
        <tr><td colspan="3" align="left"><b><a href="${pageContext.servletContext.contextPath}/list"><< Назад</a></b></td></tr>
    </table>
</form>
</body>
</html>
