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
<style>
  a{
    text-decoration: none;
    font-weight: bolder;
  }
</style>
<head>
  <title>检查结果</title>
  <base href="http://localhost:8080/ActSmart/">
</head>
<body>
<div style="text-align: center; border: red solid">
<c:if test="${requestScope.wrongCount == 0}">
  <h3>恭喜你, 答对了所有习题!</h3>
</c:if>
<c:if test="${requestScope.wrongCount != 0}">
    <h3>很遗憾, 还有<span style="color: red">${requestScope.wrongCount}</span>道题没有答对,
      正确率:<span style="color: yellowgreen">${requestScope.accuracy}</span>
    </h3>
</c:if>
<form action="check">
  <c:forEach items="${sessionScope.list}" var="map" varStatus="v">
    (${v.index + 1}) <%--  题号--%>
    <label>
        ${map["param1"]} ${map["operator"]} ${map["param2"]} = ${requestScope.myList[v.index]}
<%-- 我写的计算式 --%>
    </label>

    <c:if test="${requestScope.checkList[v.index]}">
      <span style="color: yellowgreen; font-weight: bold">&nbsp;&nbsp;正确&nbsp;&nbsp;</span>
    </c:if>
    <c:if test="${! requestScope.checkList[v.index]}">
      <span style="color: red; font-weight: bold">&nbsp;&nbsp;错误&nbsp;&nbsp;</span>
      <%--        如果答案,错误,那么显示正确答案--%>
      <span style="color: blue">${requestScope.resultList[v.index]}</span>
    </c:if>

    <br/>
    <br/>
  </c:forEach>
  <a href="reset">重新设置</a>&nbsp;&nbsp;&nbsp;&nbsp;
  <a href="create?count=${sessionScope.count}&range=${sessionScope.range}">再试一次</a>

</form>
</div>
</body>
</html>
