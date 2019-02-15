<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Редактирование товара</title>
</head>
<body>
<c:if test="${error!=''}">
    <div style="background-color: Red">
        <c:out value="${error}"></c:out>
    </div>
</c:if>
<form action="" method="post"  enctype="multipart/form-data">
    <table border="0" width="900px">
        <tr>
            <td align="center" colspan="6"><h3>Обновление товара</h3></td>
        </tr>
        <tr><td height="25px"></td></tr>
        <tr>
            <td align="left"><b>Категория</b></td>
            <td align="left"><b>Название</b></td>
            <td align="left"><b>Описание</b></td>
            <td align="left"><b>Цена</b></td>
            <td align="left"><b>Кол-во</b></td>
            <td></td>
        </tr>
        <tr><td height="10px;"></td></tr>
        <tr>
            <td>
                <select name="cat">
                    <c:forEach items="${categoryes}" var="cat">
                        <c:if test="${item.categoryId==cat.id}">
                            <option value="${cat.id}" name="${cat.id}" selected="selected"><c:out value="${cat.catName}"></c:out></option>
                        </c:if>
                        <c:if test="${item.categoryId!=cat.id}">
                            <option value="${cat.id}" name="${cat.id}"><c:out value="${cat.catName}"></c:out></option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
             <td valign="top"><input type="text" name="name" value="<c:out value="${item.itemName}"></c:out>"/></td>
            <td valign="top"><textarea rows="5" cols="35" name="desc"><c:out value="${item.description}"></c:out></textarea></td>
            <td valign="top"><input type="text" name="price"  value="<c:out value="${item.price}"></c:out>"/></td>
            <td valign="top"><input type="text" name="volume" value="<c:out value="${item.volume}"></c:out>"/></td>
            <td valign="top"><input type="submit" value="Обновить"></td>
        </tr>
        <tr><td height="10px;"></td></tr>
        <tr><td colspan="6"><b><a href="${pageContext.servletContext.contextPath}/list"><< Назад</a></b></td></tr>
    </table>
</form>
</body>
</html>
