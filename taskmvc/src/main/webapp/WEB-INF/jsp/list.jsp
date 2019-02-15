<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<html>
<head>
    <title>List</title>
</head>
<body>

<sql:setDataSource var = "snapshot" driver = "org.postgresql.Driver"
                   url = "jdbc:postgresql://localhost:5432/warehouse"
                   user = "postgres"  password = "password"/>

<table border="1" cellpadding="0" cellspacing="0" width="100%">
<c:if test="${role==1}">
    <tr>
        <td colspan="2" align="center" valign="middle"><h3>Администратор</h3></td>
    </tr>
    <c:if test="${catSize==0}">
        <tr>
            <td width="250px" align="center" valign="middle">
                <h4>Не создано категорий</h4>
                <a href="${pageContext.servletContext.contextPath}/addcat">Добавить категорию</a>
            </td>
        </tr>
    </c:if>
    <c:if test="${catSize>0}">
        <tr>
            <td width="650px" valign="top">
                <!-- Вывод категорий -->
                <form action="" method="post">
                    <table width="100%">
                        <tr>
                            <td colspan="4" align="center"><b><a href="${pageContext.servletContext.contextPath}/addcat">Добавить категорию</a></b></td>
                        </tr>
                        <tr><td height="40px"></td></tr>
                        <tr>
                            <td><h4>Название</h4></td>
                            <td><h4>Описание</h4></td>
                            <td><h4>Кол-во товаров</h4></td>
                            <td colspan="2" align="center"><h4>Действия</h4></td>
                        </tr>
                        <c:forEach items="${categoryes}" var="cat">
                            <c:set var="desc" value="${cat.description}"/>
                            <tr>
                                <td valign="top">
                                    <a href="${pageContext.servletContext.contextPath}/list?id=${cat.id}">
                                        <c:out value="${cat.catName}"></c:out>
                                    </a>
                                </td>
                                <td valign="top"><c:out value="${fn:substring(desc, 0, 150)}"></c:out>...</td>
                                <td valign="top">
                                    <sql:query dataSource = "${snapshot}" var = "result">
                                        SELECT count(category_id) from items where category_id=${cat.id}
                                    </sql:query>
                                     <c:forEach  var="row" items="${result.rows}">
                                        ${row.count}
                                     </c:forEach>
                                </td>
                                <td valign="top"><a href="${pageContext.servletContext.contextPath}/editcat?id=${cat.id}">Править</a></td>
                                <td valign="top"><input type="submit" value="Удалить"></td>
                            </tr>
                            <tr>
                                <td colspan="4" height="1px" style="background: #cccccc"></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td height="50px"></td>
                        </tr>
                    </table>
                </form>
                <!-- Конец вывода категорий -->
            </td>
            <!-- Вывод товаров -->

                <td align="center" valign="top">
                    <table width="100%" align="center">
                        <tr><td valign="top" colspan="5" align="center"><b> <a href="${pageContext.servletContext.contextPath}/additem">Добавить товар</a></b></td></tr>
                        <tr><td height="40px"></td></tr>
                        <tr>
                            <td><h4>Название</h4></td>
                            <td><h4>Описание</h4></td>
                            <td><h4>Цена</h4></td>
                            <td><h4>Количество</h4></td>
                            <td><h4>Действие</h4></td>
                        </tr>

                        <c:if test="${id==null}">
                          <c:forEach items="${items}" var="item">
                            <tr>
                              <td><c:out value="${item.itemName}"></c:out></td>
                              <td><c:out value="${item.description}"></c:out></td>
                              <td><c:out value="${item.price}"></c:out></td>
                              <td><c:out value="${item.volume}"></c:out></td>
                              <td><a href="${pageContext.servletContext.contextPath}/edititem?id=${item.id}">Править</a>&nbsp;&nbsp;<input type="submit" value="Удалить"></td>
                            </tr>
                          </c:forEach>
                        </c:if>

                        <!-- Вывод товаров выбранной категории -->
                        <c:if test="${id!=null}">
                            <c:forEach items="${items}" var="item">
                                <tr>
                                    <td><c:out value="${item.itemName}"></c:out></td>
                                    <td><c:out value="${item.description}"></c:out></td>
                                    <td><c:out value="${item.price}"></c:out></td>
                                    <td><c:out value="${item.volume}"></c:out></td>
                                    <td><a href="${pageContext.servletContext.contextPath}/edititem?id=${item.id}">Править</a>&nbsp;&nbsp;<input type="submit" value="Удалить"></td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <!-- -->

                      </table>
                </td>
            <!-- Конец вывода товаров -->
        </tr>
    </c:if>
</c:if>




    <c:if test="${role==2}">
        <tr>
            <td colspan="2" align="center" valign="middle"><h3>User</h3></td>
        </tr>
        <c:if test="${catSize==0}">
            <tr>
                <td width="250px" align="center" valign="middle">
                    <h4>Не создано категорий</h4>
                    <a href="${pageContext.servletContext.contextPath}/addcat">Добавить категорию</a>
                </td>
            </tr>
        </c:if>
        <c:if test="${catSize>0}">
            <tr>
                <td width="650px" valign="top">
                    <!-- Вывод категорий -->
                    <form action="" method="post">
                        <table width="100%">

                            <tr><td height="40px"></td></tr>
                            <tr>
                                <td><h4>Название</h4></td>
                                <td><h4>Описание</h4></td>
                                <td><h4>Кол-во товаров</h4></td>

                            </tr>
                            <c:forEach items="${categoryes}" var="cat">
                                <c:set var="desc" value="${cat.description}"/>
                                <tr>
                                    <td valign="top">
                                        <a href="${pageContext.servletContext.contextPath}/list?id=${cat.id}">
                                            <c:out value="${cat.catName}"></c:out>
                                        </a>
                                    </td>
                                    <td valign="top"><c:out value="${fn:substring(desc, 0, 150)}"></c:out>...</td>
                                    <td valign="top">
                                        <sql:query dataSource = "${snapshot}" var = "result">
                                            SELECT count(category_id) from items where category_id=${cat.id}
                                        </sql:query>
                                        <c:forEach  var="row" items="${result.rows}">
                                            ${row.count}
                                        </c:forEach>
                                    </td>

                                </tr>
                                <tr>
                                    <td colspan="4" height="1px" style="background: #cccccc"></td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td height="50px"></td>
                            </tr>
                        </table>
                    </form>
                    <!-- Конец вывода категорий -->
                </td>
                <!-- Вывод товаров -->

                <td align="center" valign="top">
                    <table width="100%" align="center">

                        <tr><td height="40px"></td></tr>
                        <tr>
                            <td><h4>Название</h4></td>
                            <td><h4>Описание</h4></td>
                            <td><h4>Цена</h4></td>
                            <td><h4>Количество</h4></td>
                        </tr>

                        <c:if test="${id==null}">
                            <c:forEach items="${items}" var="item">
                                <tr>
                                    <td><c:out value="${item.itemName}"></c:out></td>
                                    <td><c:out value="${item.description}"></c:out></td>
                                    <td><c:out value="${item.price}"></c:out></td>
                                    <td><c:out value="${item.volume}"></c:out></td>

                                </tr>
                            </c:forEach>
                        </c:if>

                        <!-- Вывод товаров выбранной категории -->
                        <c:if test="${id!=null}">
                            <c:forEach items="${items}" var="item">
                                <tr>
                                    <td><c:out value="${item.itemName}"></c:out></td>
                                    <td><c:out value="${item.description}"></c:out></td>
                                    <td><c:out value="${item.price}"></c:out></td>
                                    <td><c:out value="${item.volume}"></c:out></td>

                                </tr>
                            </c:forEach>
                        </c:if>
                        <!-- -->

                    </table>
                </td>
                <!-- Конец вывода товаров -->
            </tr>
        </c:if>
    </c:if>



</table>

</body>
</html>
