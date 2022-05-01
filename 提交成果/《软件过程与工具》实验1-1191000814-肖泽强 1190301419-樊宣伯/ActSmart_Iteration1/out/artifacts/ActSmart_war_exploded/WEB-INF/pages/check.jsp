<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hp
  Date: 2021/10/15
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>测试结果</title>
  <base href="http://localhost:8081/ActSmart/">
</head>
<body>
<form action="check">
  <c:forEach items="${sessionScope.list}" var="map" varStatus="v">
    (${v.index + 1}) <%--  题号--%>
    <label>
        ${map["param1"]} ${map["operator"]} ${map["param2"]} = ${requestScope.myList[v.index]}
<%--        我写的计算式--%>
    </label>

    <c:if test="${requestScope.checkList[v.index]}">
      <span style="color: green; font-weight: bold">正确</span>
    </c:if>
    <c:if test="${! requestScope.checkList[v.index]}">
      <span style="color: red; font-weight: bold">错误</span>
      <%--        如果答案,错误,那么显示正确答案--%>
      <span style="color: deepskyblue">${requestScope.resultList[v.index]}</span>
    </c:if>

    <br/>
    <br/>
  </c:forEach>

    <a href="create">再试一次</a>

</form>
</body>
</html>
