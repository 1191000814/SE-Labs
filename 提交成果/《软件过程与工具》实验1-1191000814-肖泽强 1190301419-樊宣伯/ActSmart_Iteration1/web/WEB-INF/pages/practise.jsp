<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: hp
  Date: 2021/10/14
  Time: 19:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>练习题</title>
  <base href="http://localhost:8081/ActSmart/">
</head>

<body>
<form action="check" method="post">
  <c:forEach items="${sessionScope.list}" var="map" varStatus="v">
    (${v.index + 1})
    <label>
    ${map["param1"]} ${map["operator"]} ${map["param2"]} = <input type="text" name="result${v.index}">
<%--      在输入框的name中使用序号以区别--%>

      <input hidden name="rightResult${v.index}" value="${map["result"]}">
<%--      隐藏的正确答案,但是会被提交到servlet--%>
    </label>

    <br/>
    <br/>
  </c:forEach>
    <input type="submit" value="提交答案"/>
</form>
</body>
</html>
