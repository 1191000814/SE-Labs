<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>练习题</title>
  <base href="http://localhost:8080/ActSmart/">
</head>

<style>
  input{
    width: 100px;
  }
</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
  $(function(){
    $(":submit").click(function(){
      let pass = true;
      let invalid = true;
      const p = /\s*\d+\s*/ // 输入内容只能是数字,两边可以有空格
      $(":text.result").each(function(){
        if($(this).val().trim() === "" || $(this).val().trim() === null){
          pass = false
          return false
        }
        else if(p.test($(this).val()) === false){
          invalid = false;
          return false
        }
      })
      if(pass === false){
        alert("您还有没答完的题")
        return false
      }
      if(invalid === false){
        alert("答案只能输入数字")
        return false
      }
    })
  })
</script>

<body>
<div style="border: dodgerblue solid; text-align: center">
<h3>加减法练习测试题</h3>
<form action="check" method="post">
  <c:forEach items="${sessionScope.list}" var="map" varStatus="v">
    (${v.index + 1})
    <label>
    ${map["param1"]} ${map["operator"]} ${map["param2"]} =
    <input type="text" class="result" name="result${v.index}">
<%--      在输入框的name中使用序号以区别--%>
      <input type="hidden" name="rightResult${v.index}" value="${map["result"]}">
    <%--      隐藏的输入框: 正确答案,但是会被提交到servlet--%>
    </label>

    <br/>
    <br/>
  </c:forEach>
    <input type="submit" value="提交答案"/>
</form>
</div>
</body>
</html>